package com.example.customerrenting.Services.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.customerrenting.Model.User;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePasswordActivity extends AppCompatActivity {

    private FirebaseUser user;
    private EditText currentPass, newPass, confirmPass;
    private String current, newPassword, confirmNew;
    private Button btnContinue;
    private Boolean isValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        init();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPass();

                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), current);

                if(isValid)
                {
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Mật khẩu hiện tại đã được xác thực thành công
                                        user.updatePassword(newPassword)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thành công",Toast.LENGTH_LONG).show();
                                                            finish();
                                                        } else {
                                                            Toast.makeText(ChangePasswordActivity.this, "Đã xảy ra lỗi trong quá trình thay đổi mật khẩu",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu hiện tại không chính xác",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void init()
    {
        currentPass = findViewById(R.id.current_password);
        newPass = findViewById(R.id.new_password);
        confirmPass = findViewById(R.id.confirm_password);
        btnContinue = findViewById(R.id.btn_continue);

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void getPass()
    {
        current = currentPass.getText().toString();
        newPassword = newPass.getText().toString();
        confirmNew = confirmPass.getText().toString();
        checkPassword();
    }

    private void checkPassword() {

        if (!newPassword.equals(confirmNew))
        {
            Toast.makeText(this,"Mật khẩu không khớp, mời nhập lại",Toast.LENGTH_LONG).show();
            newPass.setText("");
            confirmPass.setText("");
            isValid = false;
        }
        else if(newPassword.isEmpty())
        {
            Toast.makeText(this,"Vui lòng nhập mật khẩu",Toast.LENGTH_LONG).show();
            newPass.setText("");
            confirmPass.setText("");
            isValid = false;
        }
    }
}