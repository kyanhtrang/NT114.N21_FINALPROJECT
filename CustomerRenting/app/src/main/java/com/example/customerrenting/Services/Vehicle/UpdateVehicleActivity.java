package com.example.customerrenting.Services.Vehicle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateVehicleActivity extends AppCompatActivity {

    private ImageView vehicleImage;
    private TextView vehicleName, vehiclePrice, vehicleNumber, vehicleSeats;
    private Spinner spType;
    private Button btnUpdate, btnDelete;

    private String vehicleID;

    private FirebaseFirestore dtb_vehicle;
    private Vehicle vehicle = new Vehicle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vehicle);

        Intent intent = getIntent();
        vehicleID = intent.getStringExtra("vehicle_id");
        vehicle.setVehicle_id(vehicleID);

        init();

        ArrayList<String> items = new ArrayList<>();
        items.add("Xe máy");
        items.add("Xe đạp");
        items.add("Ô tô");
        items.add("Xe khách");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);

        getDetail();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

    }

    private void init()
    {
        btnUpdate = findViewById(R.id.btn_updatevehicle);
        btnDelete = findViewById(R.id.btn_deletevehicle);
// -------------------------------------------------
        vehicleName = findViewById(R.id.et_name);
        vehicleNumber = findViewById(R.id.et_number);
        vehicleSeats = findViewById(R.id.et_seats);
        vehiclePrice = findViewById(R.id.et_price);
        spType = findViewById(R.id.sp_type);
//--------------------------------------------------
        vehicleImage = findViewById(R.id.img_view);
//--------------------------------------------------
        dtb_vehicle = FirebaseFirestore.getInstance();
        vehicleName.setEnabled(false);
    }

    private void getDetail() {
        dtb_vehicle.collection("Vehicles")
                .whereEqualTo("vehicle_id", vehicle.getVehicle_id())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                vehicle.setSupplier_id(document.get("supplier_id").toString());
                                vehicle.setVehicle_id(document.get("vehicle_id").toString());
                                vehicle.setVehicle_availability(document.get("vehicle_availability").toString());

                                vehicle.setVehicle_name(document.get("vehicle_name").toString());
                                vehicleName.setText(vehicle.getVehicle_name());

                                vehicle.setVehicle_price(document.get("vehicle_price").toString());
                                vehiclePrice.setText(vehicle.getVehicle_price());

                                vehicle.setVehicle_seats(document.get("vehicle_seats").toString());
                                vehicleSeats.setText(vehicle.getVehicle_seats());

                                vehicle.setVehicle_type(document.get("vehicle_type").toString());
                                String type = document.get("vehicle_type").toString();
                                if (type.equals("Xe máy")) {
                                    spType.setSelection(0);
                                } else if (type.equals("Xe đạp")) {
                                    spType.setSelection(1);
                                } else if (type.equals("Ô tô")) {
                                    spType.setSelection(2);
                                } else {
                                    spType.setSelection(3);
                                }
                                
                                vehicle.setVehicle_number(document.get("vehicle_number").toString());
                                vehicleNumber.setText(vehicle.getVehicle_number());

                                vehicle.setVehicle_imageURL(document.get("vehicle_imageURL").toString());

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

    private void update(){
        Map<String, Object> data = new HashMap<>();
        Boolean flag = false;
        String platenumber = vehicleNumber.getText().toString();
        String seats = vehicleSeats.getText().toString();
        String price = vehiclePrice.getText().toString();
        String type = spType.getSelectedItem().toString();

        if (!platenumber.equals(vehicle.getVehicle_number())){
            data.put("vehicle_number",platenumber);
            flag = true;
        }
        if (!seats.equals(vehicle.getVehicle_seats())){
            data.put("vehicle_seats", seats);
            flag = true;
        }
        if (!price.equals(vehicle.getVehicle_price())){
            data.put("vehicle_price", price);
            flag = true;
        }
        if (!type.equals(vehicle.getVehicle_type())){
            data.put("vehicle_type",type);
            flag = true;
        }
        if (flag) {
            dtb_vehicle.collection("Vehicles")
                    .document(vehicleID)
                    .set(data, SetOptions.merge())
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateVehicleActivity.this, "Không thể cập nhật thông tin", Toast.LENGTH_LONG).show();
                            return;
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UpdateVehicleActivity.this, "Câp nhật thông tin thành công", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        }
    }

    private void delete(){
        dtb_vehicle.collection("Vehicles")
                .document(vehicleID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Delete Vehicle", "Xóa xe thành công");
                        Toast.makeText(UpdateVehicleActivity.this, "Xóa xe thành công", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Delete Vehicle", "Không thể xóa xe", e);
                        Toast.makeText(UpdateVehicleActivity.this, "Không thể xóa xe", Toast.LENGTH_LONG).show();
                    }
                });
    }

}