package com.example.customerrenting.Fragments.SupplierFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.customerrenting.Adapter.SupplierVehicleAdapter;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.Vehicle.AddVehicleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SupplierHomeFragment extends Fragment {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private ArrayList<Vehicle> vehicles;
    private SupplierVehicleAdapter adapter;

    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private User user = new User();
    private onClickInterface onClickInterface;

    private Button btnAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier_home, container, false);

        btnAdd = view.findViewById(R.id.btn_add);
        onClickInterface = new onClickInterface() {
            @Override
            public void setClick(int position) {
                vehicles.get(position);
                Log.d("Position: ", "Position is " + position);
                adapter.notifyDataSetChanged();
            }
        };
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddVehicleActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        recyclerView = view.findViewById(R.id.vehicle_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        db = FirebaseFirestore.getInstance();
        vehicles = new ArrayList<>();
        adapter = new SupplierVehicleAdapter(SupplierHomeFragment.this, vehicles, onClickInterface);
        recyclerView.setAdapter(adapter);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            user.setUserID(firebaseUser.getUid());
            EventChangeListener();
        } else {
            Toast.makeText(getContext(), "User is not authenticated", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void EventChangeListener() {
        db.collection("Vehicles")
                .whereEqualTo("supplier_id", user.getUserID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            vehicles.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Vehicle temp = document.toObject(Vehicle.class);
                                temp.setVehicle_id(document.getId());
                                vehicles.add(temp);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Failed to retrieve vehicles", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
