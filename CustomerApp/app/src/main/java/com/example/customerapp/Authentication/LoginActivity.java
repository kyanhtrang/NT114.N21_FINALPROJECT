package com.example.customerapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerapp.MainActivity;
import com.example.customerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
        txtView_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER){
                        login(v);
                        Log.d("Login", "Enter Pressed");
                        return true;
                    }
                }
                return false;
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
    public void login(View signin) {
        String email = txtView_email.getText().toString().trim();
        String password = txtView_password.getText().toString().trim();

        if (!validateForm()){
            return;
        }
        progressDialog.show();
       mAuth.signInWithEmailAndPassword(email, password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Log.d("Firebase Auth", "Login Successful");
                           mAuth.getCurrentUser();
                           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                           startActivity(intent);
                           progressDialog.cancel();
                       }
                   }
               })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                        Log.d("Firebase Auth", "Login Fail");
                        progressDialog.cancel();
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
        if (currentUser != null)  updateUI(currentUser);
    }


}