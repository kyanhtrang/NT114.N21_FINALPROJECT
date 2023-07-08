package com.example.customerrenting.Services.Vehicle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.customerrenting.Adapter.VehicleAdapter;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowAllVehicleActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Vehicle> vehicles;
    VehicleAdapter adapter;
    FirebaseFirestore dtb_vehicle;
    private onClickInterface onclickInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_vehicle);
        onclickInterface = new onClickInterface() {
            @Override
            public void setClick(int position) {
                vehicles.indexOf(position);
                Log.d("Position: ","Position is " + position);
                adapter.notifyDataSetChanged();
            }
        };
        recyclerView = findViewById(R.id.vehicle_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowAllVehicleActivity.this));

        dtb_vehicle = FirebaseFirestore.getInstance();
        vehicles = new ArrayList<Vehicle>();
        adapter = new VehicleAdapter(ShowAllVehicleActivity.this, vehicles, onclickInterface);
        recyclerView.setAdapter(adapter);

        try {
            EventChangeListener();
        } catch (Exception exception){
            Toast.makeText(ShowAllVehicleActivity.this, "Exception", Toast.LENGTH_LONG).show();
        }
    }
    private void EventChangeListener()  {
        dtb_vehicle.collection("Vehicles")
                .orderBy("vehicle_name", Query.Direction.ASCENDING)
                //.whereEqualTo("vehicle_availability", "available")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Vehicle temp = new Vehicle();
                                temp.setVehicle_id(document.getId());
                                temp.setVehicle_name(document.get("vehicle_name").toString());
                                temp.setVehicle_price(document.get("vehicle_price").toString());
                                temp.setVehicle_imageURL(document.get("vehicle_imageURL").toString());
                                temp.setSupplier_name(document.get("supplier_name").toString());
                                vehicles.add(temp);
                                adapter.notifyDataSetChanged();/*
                                progressDialog.cancel();*/
                            }
                        } else {
                            Toast.makeText(ShowAllVehicleActivity.this, "Unavailable", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}