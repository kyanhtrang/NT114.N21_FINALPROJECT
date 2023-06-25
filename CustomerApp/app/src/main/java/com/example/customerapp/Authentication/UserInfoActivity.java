package com.example.customerapp.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class UserInfoActivity extends AppCompatActivity {
    Button nextBtn;
    TextView fullname;
    RadioGroup gender;
    DatePicker datePicker;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        init();
    }
    private void init(){
        //nextBtn = findViewById(R.id.user_info_nextbtn);
        fullname = findViewById(R.id.signup_fullname);
        gender = findViewById(R.id.signup_gender_select);
        datePicker = findViewById(R.id.datepicker);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
    public void confirminformation(View view){
        if (validateFullName() == validateAge() == validateGender() == true) {
            Intent intent = new Intent(UserInfoActivity.this, AddPhoneNumberActivity.class);
            intent.putExtra("email", getIntent().getStringExtra("email"));
            intent.putExtra("password", getIntent().getStringExtra("password"));
            intent.putExtra("fullname", fullname.getText().toString());
            intent.putExtra("gender", gender.getCheckedRadioButtonId());
            intent.putExtra("birth", datePicker.getDayOfMonth() + ":" + datePicker.getMonth() + ":" + datePicker.getYear());
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Thông tin không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateGender() {
        if (gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Hãy chọn giới tính", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;
        if (isAgeValid < 0) {
            Toast.makeText(this, "Ngày sinh không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
    private boolean validateFullName() {
        String val = fullname.getText().toString().trim();
        if (val.isEmpty()) {
            fullname.setError("Họ Tên không thể trống");
            return false;
        } else {
            fullname.setError(null);
            return true;
        }
    }

}