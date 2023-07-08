package com.example.customerrenting.Services.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    Button sendbtn;
    private PhoneAuthCredential credential;
    private String CodeSent;
    private String phoneNumber, noti;
    private FirebaseFirestore mDb;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            CodeSent = s;
        }
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code!=null){
                otp.setText(code);
                verifyCode(code);

                FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                        .build();
                mDb.setFirestoreSettings(settings);
                DocumentReference newUserRef = mDb
                        .collection("Users")
                        .document(FirebaseAuth.getInstance().getUid());
                newUserRef.update("phoneNumber", phoneNumber).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

/*
                                Toast.makeText(ValidatePhoneActivity.this, "Update phone number successful", Toast.LENGTH_LONG).show();
*/
                            Intent intent = new Intent(OTP.this, CCCDActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    private void verifyCode(String code) {
        credential = PhoneAuthProvider.getCredential(CodeSent,code);
//        signInWithPhoneAuthCredential(credential);
    }
    FirebaseFirestore dtb;
    FirebaseUser user;
/*    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Firebase Listener", "onComplete Function Called");
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "Success");

                            user = task.getResult().getUser();
                            dtb = FirebaseFirestore.getInstance();

                            Map<String, Object> info = new HashMap<>();
                            info.put("email", email);
                            info.put("fullname",fullname);
                            info.put("gender",gender);
                            info.put("uid",user.getUid());
                            info.put("birth",birth);
                            info.put("phonenum", phonenumber);
                            storedata(info);
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.e("Login", "Failure: ", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTP.this,"Mã OTP không đúng", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FireBase Auth", "Failed to create user");
                    }
                });
    }*/
    private PinView otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);
        init();
        /*sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendotp();
            }
        });*/
    }

    private void storedata(Map<String, Object> info){
        dtb = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .build();
        dtb.setFirestoreSettings(settings);
        DocumentReference userRef = dtb.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.set(info)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("FireStore","User's Data Created succesfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FireStore","Can't Create User Data") ;
                    }
                });
    }
    private void init(){
        TextView tvNoti = findViewById(R.id.tv_noti);
        otp = findViewById(R.id.pin_view);
        phoneNumber = getIntent().getStringExtra("phonenum");
        noti = "Mã xác thực đã gửi về số điện thoại\n" + phoneNumber;
        tvNoti.setText(noti);
        sendotp();
    }
    private void sendotp(){
        // [START start_phone_auth]
        if (phoneNumber.length() >= 10) {
            phoneNumber = "+84" + phoneNumber.substring(1);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60L,
                    TimeUnit.SECONDS,
                    OTP.this,
                    mCallbacks);
        }
        else {
            Toast.makeText(this,"Hãy nhập đúng số điện thoại!", Toast.LENGTH_LONG).show();
        }
    }
    public void callNextScreenFromOTP(View view) {
        String code = otp.getText().toString();
        if (!code.isEmpty()){
            verifyCode(code);
            Intent intent = new Intent(OTP.this, CCCDActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void returnToPreviousIntent(View view) {
        finish();
    }
}
