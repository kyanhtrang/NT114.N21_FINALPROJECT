package com.example.customerapp.User;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerapp.Model.User;
import com.example.customerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class UserProfileActivity extends AppCompatActivity {
    private TextView fullname, phonenum, gender, email, address, city, birthday;
    private ImageView avatar;
    private String imageID, downloadUrl, uploadtype;
    private FirebaseFirestore dtb;
    private User mUser;
    private Uri avatarUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtype = "UsersAvatar/";
                AvatarpickImagesFromGallery.launch("image/*");
            }
        });
    }
    private void init(){
        avatar = findViewById(R.id.profile_avatar);
        fullname = (TextView) findViewById(R.id.editTextName);
        phonenum = (TextView) findViewById(R.id.editTextPhone);
        gender = (TextView) findViewById(R.id.editTextGender);
        birthday = (TextView) findViewById(R.id.editTextDate);
        email = (TextView) findViewById(R.id.editTextTextEmailAddress);
        address = (TextView) findViewById(R.id.editTextAddress);
        city = (TextView) findViewById(R.id.editTextCity);
        dtb = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
        dtb.setFirestoreSettings(settings);
        getdata();
    }
    private void getdata(){
        DocumentReference userRef = dtb.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) { mUser = documentSnapshot.toObject(User.class); }})
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        setdata(mUser);
                    }
                });
        ;
    }
    private void setdata(User mUser) {
        if (mUser.getFullName() != null){ fullname.setText(mUser.getFullName().toString().trim()); }
        if (mUser.getAddress() != null) { address.setText(mUser.getAddress().toString().trim()); }
        if (mUser.getEmail() != null) { email.setText(mUser.getEmail().toString().trim());}
        if (mUser.getCity() != null) { city.setText(mUser.getCity().toString().trim());}
        if (mUser.getPhoneNumber() != null) { phonenum.setText(mUser.getPhoneNumber().toString().trim());}
        if (mUser.getBirthday() != null) { birthday.setText(mUser.getBirthday().toString().trim());}
        if (mUser.getAvatarURL() != null) {
            avatarUri = Uri.parse(mUser.getAvatarURL());
            if (avatarUri != null) Glide.with(UserProfileActivity.this).load(avatarUri).into(avatar);
        }
    }
    public void submit(View view) {
        String updtPhone = phonenum.getText().toString().trim()
                , updtEmail = email.getText().toString().trim()
                , updtFullname = fullname.getText().toString().trim()
                , updtAddress = address.getText().toString().trim()
                , updtCity = city.getText().toString().trim()
                , updtBirthday = birthday.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(updtFullname).setPhotoUri(avatarUri).build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserProfileActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        if (updtEmail != mUser.getEmail()) {
            user.updateEmail(updtEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("Profile","Email updating Complete.");
                        }
                    });
                }

        Map<String, Object> newUserData = new HashMap<>();
        newUserData.put("email", updtEmail);
        newUserData.put("phonenum", updtPhone);
        newUserData.put("fullname", updtFullname);
        newUserData.put("address", updtAddress);
        newUserData.put("city", updtCity);
        newUserData.put("birthday", updtBirthday);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
        dtb.setFirestoreSettings(settings);
        DocumentReference userRef = dtb.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.update(newUserData);
    }
    ActivityResultLauncher<String> AvatarpickImagesFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent() , new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        avatarUri = result;
                        avatar.setImageURI(result);
                    }
                    uploadImage(uploadtype);
                }
            });
    private void uploadImage(String type) {

        //Firebase
        FirebaseStorage storage;
        StorageReference storageReference;

        // Initialize storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(avatarUri != null) {
            imageID = UUID.randomUUID().toString();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Đang tải lên...");
            progressDialog.show();

            StorageReference ref = storageReference.child(type + "/"+ imageID);
            ref.putFile(avatarUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
/*
                            Toast.makeText(ProfileManagement.this, "Uploaded", Toast.LENGTH_SHORT).show();
*/
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri.toString();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UserProfileActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}