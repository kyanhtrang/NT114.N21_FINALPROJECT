package com.example.customerapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.customerapp.MainActivity;
import com.example.customerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    Button sendbtn;
    EditText phonenumber_input;
    String CodeSent;
    String phonenumber, fullname, gender, birth;
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
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(CodeSent,code);
        signInWithPhoneAuthCredential(credential);
    }
    FirebaseFirestore dtb;
    FirebaseUser user;
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "Success");

                            user = task.getResult().getUser();
                            dtb = FirebaseFirestore.getInstance();

                            Map<String, Object> info = new HashMap<>();
                            info.put("fullname",fullname);
                            info.put("gender",gender);
                            info.put("uid",user.getUid().toString());
                            info.put("birth",birth);
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
                });
    }
    private PinView otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);
        init();
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendotp();
            }
        });
    }

    private void storedata(Map<String, Object> info){
        dtb.collection("Users").document(user.getUid().toString())
                .set(info)
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

        phonenumber_input = findViewById(R.id.phonenumber);
        otp = findViewById(R.id.pin_view);
        sendbtn = findViewById(R.id.btn_sendotp);
        fullname = getIntent().getStringExtra("fullname");
        gender = getIntent().getStringExtra("gender");
        birth = getIntent().getStringExtra("birth");
    }
    private void sendotp(){
        // [START start_phone_auth]
        phonenumber = phonenumber_input.getText().toString().trim();
        if (phonenumber.length() > 9) {
            phonenumber = "+84" + phonenumber.substring(1);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phonenumber,
                    60L,
                    TimeUnit.SECONDS,
                    OTP.this,
                    mCallbacks);
        }
        else {
            Toast.makeText(this,"Hãy nhập đúng số điện thoại!", Toast.LENGTH_LONG).show();
        }
    }

    private void callNextScreenFromOTP() {
        String code = otp.getText().toString();
        if (!code.isEmpty()){
            verifyCode(code);
        }
        Intent intent = new Intent(OTP.this, MainActivity.class);
        startActivity(intent);
    }
}
