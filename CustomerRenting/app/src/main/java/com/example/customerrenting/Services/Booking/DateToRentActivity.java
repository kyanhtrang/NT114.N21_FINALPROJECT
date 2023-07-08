package com.example.customerrenting.Services.Booking;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerrenting.Model.CreateOrder;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class DateToRentActivity extends AppCompatActivity {
    private DatePickerDialog picker;
    TextView ngayNhan, soNgayThue;
    Button btnSubmit;
    FirebaseFirestore dtb;
    String vehicle_id;
    CreateOrder orderApi;
    String token;
    String amount = "1000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_to_rent);

        vehicle_id = getIntent().getStringExtra("vehicle_id");

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
                        String date = "";
                        if (i2 < 10) date = "0" + i2; else date += i2;
                        if (i1 < 10) date = date + "/0" + (i1 + 1); else date += i1 + 1;
                        date += "/" + i;
                        ngayNhan.setText(date);
                    }
                }, year, month, day);
                picker.show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (soNgayThue.getText() != null) {
                    Integer days = (Integer) Integer.valueOf(soNgayThue.getText().toString());
                    if (days >= 0) {
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
    private void setAvailable(){
        dtb = FirebaseFirestore.getInstance();
        DocumentReference vehicle = dtb.collection("Vehicles").document(vehicle_id);
        vehicle.update("vehicle_availability", "unavalable");
    }
    private void callZaloPay(){
        orderApi = new CreateOrder();
        createOrder();
        checkOut(token);
        postPayment();
    }
    private void postPayment() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "Da thanh toan");
        data.put("customer_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        data.put("vehicle_id", vehicle_id);
        dtb.collection("Orders")
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DateToRentActivity.this, "Thuê xe thành công", Toast.LENGTH_LONG).show();
                            setAvailable();
                            Log.d("PostPayment", "Success");
                        }
                        else {
                            Log.e("PostPayment", "Fail");
                        }
                    }
                });

    }
    private void checkOut(String token) {
        ZaloPaySDK.getInstance().payOrder(DateToRentActivity.this, token, "demozpdk://app", new PayOrderListener() {
            @Override
            public void onPaymentSucceeded(String s, String s1, String s2) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Transaction Token", token);
                        Toast.makeText(DateToRentActivity.this, String.format("TransactionId: %s - TransToken: %s", s, s1), Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onPaymentCanceled(String s, String s1) {
                Log.e("Transaction Token", s);
                Toast.makeText(DateToRentActivity.this, String.format("Canceled", s, s1), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                Log.e("Transaction Token", s);
                Toast.makeText(DateToRentActivity.this, String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), s), Toast.LENGTH_LONG).show();
            }
        });
        Log.e("Token", token);
    }
    private void createOrder(){
        try {
            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("returncode");
            if (code.equals("1")) {
                token = data.getString("zptranstoken");
                Log.e("Token", token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}