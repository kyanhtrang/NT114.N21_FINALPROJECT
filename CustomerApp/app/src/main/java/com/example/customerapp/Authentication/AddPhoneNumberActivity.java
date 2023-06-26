package com.example.customerapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.customerapp.R;

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
                if (!validatePhonenum(phone.getText().toString())) {
                    intent.putExtra("phonenum", phone.getText().toString().trim());
                    intent.putExtra("email", getIntent().getStringExtra("email"));
                    intent.putExtra("password", getIntent().getStringExtra("password"));
                    intent.putExtra("fullname", getIntent().getStringExtra("fullname"));
                    intent.putExtra("gender", getIntent().getStringExtra("gender"));
                    intent.putExtra("birth", getIntent().getStringExtra("birth"));
                    Log.d("AddPhoneNumber", "This is a solid phone number");
                    startActivity(intent);
                }
            }
        });
    }
    private boolean validatePhonenum(String val) {
        String checkPassword = "^" +
                "(?=S+$)" +           //no white spaces
                ".{9,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            phone.setError("Hãy nhập số điện thoại");
            return false;
        } else if (!val.matches(checkPassword)) {
            phone.setError("Mật khẩu phải có ít nhất 6 ký tự!");
            return false;
        } else {
            phone.setError(null);
            return true;
        }

    }
}