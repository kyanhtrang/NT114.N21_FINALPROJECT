package com.example.customerrenting.Services.Authentication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.customerrenting.Model.User;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.UsersManagement.UpdateProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CCCDActivity extends AppCompatActivity {

    private ImageView front, behind;
    private String uploadtype, frontUrl, behindUrl;
    private Uri frontURI, behindURI;
    private String imageID;
    private Button btnUpdate;
    private FirebaseFirestore dtb_user;
    private FirebaseUser firebaseUser;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cccd);

        init();

        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtype = "IDCards/Front/";
                pickFrontImagesFromGallery.launch("image/*");
            }
        });

        behind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtype = "IDCards/Behind/";
                pickBehindImagesFromGallery.launch("image/*");
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveImageInFirestore();
            }
        });
    }

    private void init()
    {
        front = findViewById(R.id.img_front_CCCD);
        behind = findViewById(R.id.img_behind_CCCD);
        btnUpdate = findViewById(R.id.update_img);

        dtb_user = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUserID(firebaseUser.getUid());
    }

    ActivityResultLauncher<String> pickFrontImagesFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent()
            , new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        frontURI = result;
                        front.setImageURI(result);
                    }
                    uploadFront(uploadtype);
                }
            });

    ActivityResultLauncher<String> pickBehindImagesFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent()
            , new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        behindURI = result;
                        behind.setImageURI(result);
                    }
                    uploadBehind(uploadtype);
                }
            });

    private void uploadFront(String type) {

        //Firebase
        FirebaseStorage storage;
        StorageReference storageReference;

        // Initialize storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(frontURI != null)
        {
            imageID = UUID.randomUUID().toString();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child(type + "/"+ imageID);

            ref.putFile(frontURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
/*
                            Toast.makeText(CCCDActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
*/
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    frontUrl = uri.toString();
/*
                                    Toast.makeText(getBaseContext(), "Upload success! URL - " + frontUrl, Toast.LENGTH_SHORT).show();
*/
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CCCDActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void uploadBehind(String type) {

        //Firebase
        FirebaseStorage storage;
        StorageReference storageReference;

        // Initialize storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(behindURI != null)
        {
            imageID = UUID.randomUUID().toString();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child(type + "/"+ imageID);

            ref.putFile(behindURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
/*
                            Toast.makeText(CCCDActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
*/
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    behindUrl = uri.toString();
/*
                                    Toast.makeText(getBaseContext(), "Upload success! URL - " + behindUrl, Toast.LENGTH_SHORT).show();
*/
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(CCCDActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void SaveImageInFirestore()
    {
        user.setFrontCard(frontUrl);
        user.setBehindCard(behindUrl);

        Map<String, Object> data = new HashMap<>();
        data.put("ciCardFront", user.getFrontCard());
        data.put("ciCardBehind", user.getFrontCard());

        dtb_user.collection("Users").document(firebaseUser.getUid())
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
/*
                        Toast.makeText(CCCDActivity.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_LONG).show();
*/
                        Intent intent = new Intent(CCCDActivity.this, UpdateProfileActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CCCDActivity.this, "Error updating document", Toast.LENGTH_LONG).show();
                    }
                });
    }
}