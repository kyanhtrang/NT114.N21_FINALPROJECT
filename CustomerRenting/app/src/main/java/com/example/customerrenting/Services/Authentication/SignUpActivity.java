package com.example.customerrenting.Services.Authentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerrenting.Model.User;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.UsersManagement.UserInfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class SignUpActivity extends AppCompatActivity {
    private EditText reInputPassword, inputPassword, inputEmail;

    private String Email, Password, confirmPassword;
    private Button btnSignUp;
    private TextView btnToSignin;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore dtbUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseApp.initializeApp(this);

        init();

        inputPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_ENTER){
                        createAccount();
                    }
                }
                return false;
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() == !validatePassword(inputPassword.getText().toString().trim()) == !validatePassword(reInputPassword.getText().toString().trim()) == !validateConfirm() == false) {
                    createAccount();
                }
                else Log.d("SignUp", "Validation does not matches!");
            }
        });
    }
    private void init(){
        inputEmail = findViewById(R.id.signup_email);
        inputPassword = findViewById(R.id.input_password);
        reInputPassword = findViewById(R.id.reinput_password);
        btnToSignin = findViewById(R.id.btn_to_signin);
        btnSignUp = findViewById(R.id.btn_sinup);

        mAuth = FirebaseAuth.getInstance();
        dtbUser = FirebaseFirestore.getInstance();
    }

    private boolean validateEmail() {
        String val = inputEmail.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            inputEmail.setError("Email không thể trống");
            return false;
        } else if (!val.matches(checkEmail)) {
            inputEmail.setError("Email không hợp lệ!");
            return false;
        } else {
            inputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String val) {
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{3,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            inputPassword.setError("Mật khẩu bắt buộc phải có");
            return false;
        } /*else if (!val.matches(checkPassword)) {
            password.setError("Mật khẩu phải có ít nhất 6 ký tự!");
            return false;
        }*/ else {
            inputPassword.setError(null);
            return true;
        }
    }

    private boolean validateConfirm(){
        if (!(inputPassword.getText().toString().trim() == reInputPassword.getText().toString().trim()))
            return false;
        reInputPassword.setError("Mật khẩu không khớp");
        return true;
    }

    public void createAccount(){
        Email = inputEmail.getText().toString();
        Password = inputPassword.getText().toString();
        confirmPassword = reInputPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Tạo User thành công");

                            firebaseUser = mAuth.getCurrentUser();

                            if (firebaseUser != null) {
                                firebaseUser.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    Toast.makeText(SignUpActivity.this, "Verification email sent to " + Email, Toast.LENGTH_LONG).show();

                                                    createUser();
                                                } else {
                                                    Log.e("TAG", "sendEmailVerification failed!", task.getException());
                                                }
                                            }
                                        });
                            }
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Không thể tạo User",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUser(){
        user = new User();
        user.setEmail(Email);
        user.setUserID(FirebaseAuth.getInstance().getUid());

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .build();
        dtbUser.setFirestoreSettings(settings);

        DocumentReference newUserRef = dtbUser
                .collection("Users")
                .document(FirebaseAuth.getInstance().getUid());
        newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    finish();
                }else{
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}