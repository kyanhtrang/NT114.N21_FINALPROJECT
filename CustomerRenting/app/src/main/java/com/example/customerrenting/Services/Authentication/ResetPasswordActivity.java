package com.example.customerrenting.Services.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.customerrenting.R;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset;
    private FirebaseAuth mAuth;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        init();
        inputEmail.setText(email);
    }
    private void init(){
        inputEmail = findViewById(R.id.editTextResetEmail);
        btnReset = findViewById(R.id.btn_resetPassword);
        mAuth = FirebaseAuth.getInstance();
    }

    public void sendResetMail(View view) {
        email = inputEmail.getText().toString();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "Một email xác nhận đã được gửi", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Không thể gửi email xác nhận", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}