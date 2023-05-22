package com.example.customerapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.example.customerapp.R;

public class PhoneVerifyActivity extends AppCompatActivity {

    private ScrollView scrollView;
    String _fullname = getIntent().getStringExtra("fullname");
    String _email = getIntent().getStringExtra("fullname");
    String _username = getIntent().getStringExtra("fullname");
    String _password = getIntent().getStringExtra("fullname");
    String _date = getIntent().getStringExtra("fullname");
    String _gender = getIntent().getStringExtra("fullname");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phone_verify);

        sendVerificationCode(){

        }

    }

    private void sendVerificationCode(){

    }
}