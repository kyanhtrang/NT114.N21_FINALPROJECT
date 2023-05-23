package com.example.customerapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerapp.MainActivity;
import com.example.customerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    TextView txtView_register, txtView_email, txtView_password;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        txtView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean validateForm() {
        boolean valid = true;
        String email = txtView_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            txtView_email.setError("Required.");
            valid = false;
        } else {
            txtView_email.setError(null);
        }
        String password = txtView_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            txtView_password.setError("Required.");
            valid = false;
        } else {
            txtView_password.setError(null);
        }
        return valid;
    }
    private void login() {
        String email = txtView_email.getText().toString();
        String password = txtView_password.getText().toString();

        if (!validateForm()){
            return;
        }progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String uid = firebaseUser.getUid();
                            FirebaseFirestore.getInstance().collection("Users").document(uid)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();

                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    // do something with the retrieved data
                                                    String username = document.getString("username");
                                                    String phonenumber = document.getString("phoneNumber");
                                                    if (username != null && username.isEmpty()) {
                                                        Intent intent = new Intent(LoginActivity.this, CredentialActivity.class);
                                                        intent.putExtra("phone", phonenumber);
                                                        startActivity(intent);
                                                    }
                                                    else
                                                    {
                                                        Intent intent = new Intent(LoginActivity.this, CredentialActivity.class);
                                                        startActivity(intent);
                                                        onStop();
                                                    }

                                                } else {

                                                    // the document does not exist
                                                }
                                            } else {
                                                // handle the error
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser currentUser) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    void init (){
        txtView_register = findViewById(R.id.btn_register);
        txtView_email = findViewById(R.id.email);
        txtView_password = findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


}