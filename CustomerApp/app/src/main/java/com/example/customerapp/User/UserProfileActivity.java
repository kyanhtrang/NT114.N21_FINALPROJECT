package com.example.customerapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.customerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class UserProfileActivity extends AppCompatActivity {
    DatePicker datePicker;
    TextView fullname, phonenum, gender, email;
    FirebaseFirestore dtb;
    FirebaseUser mUser;
    String origin_fullname, origin_phone, origin_gender, origin_email, origin_birth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
    }
    private void init(){
        fullname = (TextView) findViewById(R.id.editTextName);
        phonenum = (TextView) findViewById(R.id.editTextPhone);
        gender = (TextView) findViewById(R.id.editTextGender);
        datePicker = findViewById(R.id.user_profile_datepicker);
        email = (TextView) findViewById(R.id.editTextTextEmailAddress);
        dtb = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    private void getdata(){
        dtb.collection("Users").whereEqualTo("uid",mUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            Log.d("FireStore","Success");
                            origin_fullname = documentSnapshot.getDate("fullname").toString();
                            origin_phone = documentSnapshot.getDate("phonenum").toString();
                            origin_gender = documentSnapshot.getDate("gender").toString();
                            origin_email = documentSnapshot.getDate("email").toString();
                            origin_birth = documentSnapshot.getDate("birth").toString();
                        }
                    }
                });
    }
    private void settext(String name, String phone, String g, String mail, int day, int month, int year){
        fullname.setText(name);
        phonenum.setText(phone);
        gender.setText(g);
        datePicker.updateDate(year, month, day);
        email.setText(mail);
    }

}