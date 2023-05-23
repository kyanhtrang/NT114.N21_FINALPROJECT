package com.example.customerapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;

import com.example.customerapp.R;

public class CredentialActivity extends AppCompatActivity {

    private ScrollView scrollView;
    String _fullname, _email, _username, _password, _date, _gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_credential);
    }
}
