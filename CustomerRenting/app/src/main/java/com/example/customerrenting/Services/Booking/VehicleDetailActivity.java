package com.example.customerrenting.Services.Booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class VehicleDetailActivity extends AppCompatActivity {
    private ImageView vehicleImage;
    private TextView providerName, providerGmail, providerPhone, providerAddress;
    private TextView vehicleName, vehiclePrice, vehicleNumber, vehicleSeats, vehicleType;
    private Button btnBook;
    private String vehicleID;
    private FirebaseFirestore dtb_vehicle;
    private Vehicle vehicle = new Vehicle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        Intent intent = getIntent();
        vehicleID = intent.getStringExtra("vehicle_id");
        vehicle.setVehicle_id(vehicleID);

        init();

        getDetail();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VehicleDetailActivity.this, DateToRentActivity.class);
                i.putExtra("vehicle_id", vehicleID);
                startActivity(i);
            }
        });
    }

    private void init() {
        btnBook = findViewById(R.id.btn_book);

        vehicleImage = findViewById(R.id.vehicle_img);
        providerName = findViewById(R.id.tv_provider_name);
        //providerAddress = findViewById(R.id.tv_provider_address);
        providerPhone = findViewById(R.id.tv_provider_phone);

        vehicleName = findViewById(R.id.tv_vehicle_name);
        vehicleNumber = findViewById(R.id.tv_vehicle_plate_number);
        vehicleSeats = findViewById(R.id.tv_vehicle_seats);
        vehiclePrice = findViewById(R.id.tv_vehicle_price);
        vehicleType = findViewById(R.id.tv_vehicle_type);

        dtb_vehicle = FirebaseFirestore.getInstance();
    }

    private void getDetail() {
        dtb_vehicle.collection("Vehicles")
                .whereEqualTo("vehicle_id", vehicle.getVehicle_id())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){

                                vehicle.setSupplier_id(document.get("supplier_id").toString());
                                vehicle.setVehicle_id(document.get("vehicle_id").toString());
                                vehicle.setVehicle_availability(document.get("vehicle_availability").toString());

                                vehicle.setSupplier_name(document.get("supplier_name").toString());
                                providerName.setText(vehicle.getSupplier_name());

                                vehicle.setVehicle_name(document.get("vehicle_name").toString());
                                vehicleName.setText(vehicle.getVehicle_name());

                                vehicle.setVehicle_price(document.get("vehicle_price").toString());
                                vehiclePrice.setText(vehicle.getVehicle_price());

                                vehicle.setVehicle_seats(document.get("vehicle_seats").toString());
                                vehicleSeats.setText(vehicle.getVehicle_seats());

                                vehicle.setVehicle_type(document.get("vehicle_type").toString());
                                vehicleType.setText(vehicle.getVehicle_type());

                                vehicle.setVehicle_number(document.get("vehicle_number").toString());
                                vehicleNumber.setText(vehicle.getVehicle_number());

                                if (!document.get("vehicle_imageURL").toString().isEmpty()) {
                                    vehicle.setVehicle_imageURL(document.get("vehicle_imageURL").toString());
                                    Picasso.get().load(vehicle.getVehicle_imageURL()).into(vehicleImage);
                                }
                                else {
                                    vehicle.setVehicle_imageURL("");
                                }

                            }

                        }
                    }
                });

    }
}