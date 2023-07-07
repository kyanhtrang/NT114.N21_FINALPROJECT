package com.example.customerrenting.Services.Authentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerrenting.MainActivity;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.UsersManagement.UpdateProfileActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class LoginActivity extends AppCompatActivity {

    TextView txtView_register, txtView_email, txtView_password;
    private static final int RC_SIGN_IN = 0406;
    ProgressDialog progressDialog;
    ImageView btnGGsignin;
    private User user = new User();
    private FirebaseFirestore dtbUser;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Đăng nhập thành công vào Firebase Authentication

                                    // Lưu ID người dùng vào Firestore
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = user.getUid();

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    DocumentReference docRef = db.collection("User").document(uid);

                                    FirebaseFirestore.getInstance().collection("Users").document(uid)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {

                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            // do something with the retrieved data
                                                            String username = document.getString("fullName");
                                                            if (username.isEmpty()) {
                                                                Intent intent = new Intent(LoginActivity.this, UpdateProfileActivity.class);
                                                                startActivity(intent);
                                                            }
                                                            else
                                                            {
                                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                startActivity(intent);
                                                                onStop();
                                                            }

                                                        } else {
                                                            // the document does not exist
                                                            createUser();
                                                        }
                                                    } else {
                                                        // handle the error
                                                    }
                                                }
                                            });

                                }
                                else {
                                    // Đăng nhập thất bại vào Firebase Authentication
                                }
                            }
                        });


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btnGGsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        txtView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        txtView_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER){
                        login(v);
                        Log.d("Login", "Enter Pressed");
                        return true;
                    }
                }
                return false;
            }
        });
    }
    private boolean validateForm() {
        boolean valid = true;
        String email = txtView_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            txtView_email.setError("Required.");
            valid = false;
        } else {
            txtView_email.setError(null);
        }
        String password = txtView_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            txtView_password.setError("Required.");
            valid = false;
        } else {
            txtView_password.setError(null);
        }
        return valid;
    }
    public void login(View signin) {
        String email = txtView_email.getText().toString().trim();
        String password = txtView_password.getText().toString().trim();

        if (!validateForm()){
            return;
        }
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("Firebase Auth", "Login Successful");
                            mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            progressDialog.cancel();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Firebase Auth", "Login Fail");
                        progressDialog.cancel();
                    }
                });
    }
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    void init() {
        btnGGsignin = findViewById(R.id.btn_ggsignin);
        txtView_register = findViewById(R.id.btn_register);
        txtView_email = findViewById(R.id.input_email);
        txtView_password = findViewById(R.id.input_password);
        progressDialog = new ProgressDialog(this);
        dtbUser = FirebaseFirestore.getInstance(); // Initialize dtbUser variable
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    public void resetPassword(View view) {
        String iemail = txtView_email.getText().toString();
        try {
            if (!iemail.isEmpty()) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                if (!iemail.isEmpty()) {
                    intent.putExtra("email", iemail);
                }
                startActivity(intent);
            } else {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        } catch (Exception ex) {
            Log.e("ForgotPassword", ex.toString());
        }
    }

    private void createUser() {
        user.setUserID(FirebaseAuth.getInstance().getUid());

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .build();
        dtbUser.setFirestoreSettings(settings);

        DocumentReference newUserRef = dtbUser
                .collection("Users")
                .document(FirebaseAuth.getInstance().getUid());
        newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {

                    Toast.makeText(LoginActivity.this, "Tạo tài khoản thành công",Toast.LENGTH_LONG).show();

                } else {
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        progressDialog.cancel();
    }
}