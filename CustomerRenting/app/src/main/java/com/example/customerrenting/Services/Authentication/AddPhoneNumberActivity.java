package com.example.customerrenting.Services.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.customerrenting.R;

public class AddPhoneNumberActivity extends AppCompatActivity {
    TextView phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);

        phone = (TextView) findViewById(R.id.input_phone);

        phone.setText(getIntent().getStringExtra("phonenum"));
        Intent intent = new Intent(AddPhoneNumberActivity.this, OTP.class);
        findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePhonenum(phone.getText().toString())) {
                    intent.putExtra("phonenum", phone.getText().toString().trim());
                    Log.d("AddPhoneNumber", "This is a solid phone number");
                    startActivity(intent);
                }
            }
        });
    }
    private boolean validatePhonenum(String val) {

        if (val.isEmpty()) {
            phone.setError("Hãy nhập số điện thoại");
            return false;
        } else if (val.length() < 9) {
            phone.setError("Vui lòng nhập đúng định dạng số điện thoại!");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }
}