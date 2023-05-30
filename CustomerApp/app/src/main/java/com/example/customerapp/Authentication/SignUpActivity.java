package com.example.customerapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.customerapp.R;

public class SignUpActivity extends AppCompatActivity {
    EditText repeatpassword, password, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_ENTER){
                        if (!validateEmail() | !validatePassword(password.getText().toString().trim()) | !validatePassword(repeatpassword.getText().toString().trim()) | !validaterpeat()) {
                            Intent intent = new Intent(SignUpActivity.this, OTP.class);
                            intent.putExtra("email", email.getText().toString().trim());
                            intent.putExtra("password", password.getText().toString().trim());
                            startActivity(intent);
                        }
                    }
                }
                return false;
            }
        });
    }
    private void init(){
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        repeatpassword = findViewById(R.id.signup_repeatpassword);
    }
    private boolean validateEmail() {
        String val = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("Email không thể trống");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Email không hợp lệ!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    private boolean validatePassword(String val) {
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=S+$)" +           //no white spaces
                ".{3,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            password.setError("Mật khẩu bắt buộc phải có");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Mật khẩu phải có ít nhất 6 ký tự!");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
    private boolean validaterpeat(){
        if (!(password.getText().toString().trim() == repeatpassword.getText().toString().trim()))
            return false;
        return true;
    }
}