package com.example.customerrenting.Services.Vehicle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;
import com.example.customerrenting.SupplierActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddVehicleActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private String documentId, downloadUrl;
    private Uri mImageURI;
    private EditText vehicle_name, vehicle_seats, vehicle_price, vehicle_number;
    private CheckBox vehicle_available;
    private Button btnAdd;
    private ImageView vehicle_imgView;
    private FirebaseFirestore dtb_vehicle, dtb_user, dtb_update, dtb_store;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String imageID;
    private User user = new User();
    private Vehicle vehicle = new Vehicle();
    private Spinner spType;

    ActivityResultLauncher<String> pickImagesFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent()
            , new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        mImageURI = result;
                        vehicle_imgView.setImageURI(result);
                    }
                    uploadImage();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        init();
        ArrayList<String> items = new ArrayList<>();
        items.add("Xe máy");
        items.add("Xe đạp");
        items.add("Ô tô");
        items.add("Xe khách");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);

        vehicle_imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImagesFromGallery.launch("image/*");
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVehicle();
                /*if(FullFill())
                {
                    addVehicle();
                }
                else
                {
                    Toast.makeText(AddVehicleActivity.this, "Vui lòng nhập đủ các thông tin", Toast.LENGTH_LONG).show();
                }*/
            }
        });

    }

    private void init() {
        vehicle_name = findViewById(R.id.et_name);
        vehicle_seats = findViewById(R.id.et_seats);
        vehicle_price = findViewById(R.id.et_price);
        vehicle_number = findViewById(R.id.et_number);
        vehicle_imgView = findViewById(R.id.img_view);
        spType = findViewById(R.id.sp_type);
        btnAdd = findViewById(R.id.btn_add);

        dtb_vehicle = FirebaseFirestore.getInstance();
        dtb_user = FirebaseFirestore.getInstance();
        dtb_update = FirebaseFirestore.getInstance();
        dtb_store = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUserID(firebaseUser.getUid());
    }

    private void uploadImage() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(mImageURI != null)
        {
            imageID = UUID.randomUUID().toString();
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("VehicleImages/"+ imageID);
            ref.putFile(mImageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AddVehicleActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri.toString();
                                    //Toast.makeText(getBaseContext(), "Upload success! URL - " + downloadUrl, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddVehicleActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void addVehicle() {

        dtb_user.collection("Users")
                .whereEqualTo("userID", user.getUserID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                getStoreID();
                                vehicle.setSupplier_id(document.get("userID").toString());
                                vehicle.setSupplier_name(document.get("fullName").toString());
                                vehicle.setSupplier_address(document.get("address").toString() + " " + document.get("city").toString());
                                vehicle.setSupplier_email(document.get("email").toString());
                                vehicle.setSupplier_phone(document.get("phoneNumber").toString());

                                vehicle.setVehicle_name(vehicle_name.getText().toString());
                                vehicle.setVehicle_seats(vehicle_seats.getText().toString());
                                vehicle.setVehicle_price(vehicle_price.getText().toString() + " VND");
                                vehicle.setSupplier_name(user.getUserID());
                                vehicle.setVehicle_number(vehicle_number.getText().toString());
                                vehicle.setVehicle_availability("available");
                                vehicle.setVehicle_imageURL(downloadUrl);
                                vehicle.setVehicle_type(spType.getSelectedItem().toString());

                                dtb_vehicle.collection("Vehicles")
                                        .add(vehicle)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                vehicle.setVehicle_id(documentReference.getId());
                                                Log.e("", vehicle.getVehicle_id());
                                                updateData(vehicle.getVehicle_id());
                                                Intent intent = new Intent(AddVehicleActivity.this, SupplierActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(AddVehicleActivity.this, "Thêm xe thành công", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AddVehicleActivity.this, "Thêm xe thất bại", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }
                        else {
                            //
                            Toast.makeText(AddVehicleActivity.this, "Không thể lấy thông tin", Toast.LENGTH_LONG).show();
                        }
                    }

                    private void updateData(String vehicle_id) {
                        Log.e("", vehicle_id);
                        Map<String, Object> data = new HashMap<>();
                        data.put("vehicle_id", vehicle_id);

                        dtb_update.collection("Vehicles").document(vehicle_id)
                                .update(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddVehicleActivity.this, "DocumentSnapshot successfully updated!", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddVehicleActivity.this, "Error updating document", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
    }

    public void getStoreID()
    {
        dtb_store.collection("Stores")
                .whereEqualTo("supplierID", user.getUserID())z
    }
}