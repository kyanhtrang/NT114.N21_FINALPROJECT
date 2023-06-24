package com.example.customerapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.customerapp.R;

public class AddPhoneNumberActivity extends AppCompatActivity {
    Intent intent = new Intent(AddPhoneNumberActivity.this, OTP.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);
        TextView phone = (TextView) findViewById(R.id.input_phone_number);
        if (getIntent().getStringExtra("phonenum") == null) {
            intent.putExtra("phonenum",phone.getText().toString().trim());
        }
        else  intent.putExtra("phonenum", getIntent().getStringExtra("phonenum"));
    }

    public void callnewintent(View view) {
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("password", getIntent().getStringExtra("password"));
        intent.putExtra("fullname", getIntent().getStringExtra("fullname"));
        intent.putExtra("gender", getIntent().getStringExtra("gender"));
        intent.putExtra("birth", getIntent().getStringExtra("birth"));
        startActivity(intent);
    }
}