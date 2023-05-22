package com.example.customerapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.customerapp.R;

public class LoginActivity extends AppCompatActivity {
    TextView txtView_register, txtView_email, txtView_password;
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
    void init (){
        txtView_register = findViewById(R.id.btn_register);
        txtView_email = findViewById(R.id.email);
        txtView_password = findViewById(R.id.password);
    }
}