package com.example.customerrenting.Services.UsersManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.customerrenting.R;
import com.example.customerrenting.Services.Authentication.AddPhoneNumberActivity;

public class RemindNotiActivity extends AppCompatActivity {

    private TextView btnTranfer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_noti);

        btnTranfer = findViewById(R.id.tv_tranfer);
        btnTranfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemindNotiActivity.this, AddPhoneNumberActivity.class);
                startActivity(intent);
            }
        });
    }
}