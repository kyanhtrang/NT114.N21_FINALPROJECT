package com.example.customerrenting.Services.Booking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.customerrenting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class DateToRentActivity extends AppCompatActivity {
    private DatePickerDialog picker;
    TextView ngayNhan, soNgayThue;
    Button btnSubmit;
    FirebaseFirestore dtb;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_to_rent);

        String vehicle_id = getIntent().getStringExtra("vehicle_id");

        init();

        final Calendar calendar = Calendar.getInstance();

        ngayNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(DateToRentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        ngayNhan.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soNgayThue.getText() != null) {
                    Integer days = (Integer) Integer.valueOf(soNgayThue.getText().toString());
                    if (days >= 0) {
                        getData();
                        callZaloPay();
                    }
                } else {
                };
            }
        });

    }
    private void init(){
        ngayNhan = findViewById(R.id.editTextDate);
        soNgayThue = findViewById(R.id.editTextDays);
        btnSubmit = findViewById(R.id.btn_days_submit);
    }
    private void getData(){

    }
    private void callZaloPay(){

    }
}