package com.example.customerrenting.Services.Vehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddVehicleActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private String documentId, downloadUrl;
    private Uri mImageURI;
    private EditText vehicle_name, vehicle_seats, vehicle_price, vehicle_owner, vehicle_number;
    private CheckBox vehicle_available;
    private Button btnAdd;
    private ImageView vehicle_imgView;
    private FirebaseFirestore dtb_vehicle, dtb_user, dtb_update;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String imageID;
    private User user = new User();
    private Vehicle vehicle = new Vehicle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
    }
}