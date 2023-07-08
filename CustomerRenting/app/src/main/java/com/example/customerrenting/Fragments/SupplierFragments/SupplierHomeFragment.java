package com.example.customerrenting.Fragments.SupplierFragments;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class SupplierHomeFragment extends Fragment {

    ImageView imageView;
    DocumentReference imageRef;
    RecyclerView recyclerView;
    ArrayList<Vehicle> vehicles;
    SupplierVehicleAdapter adapter;

    FirebaseFirestore dtb_vehicle;
    private FirebaseUser firebaseUser;
    private User user = new User();
    private onClickInterface onclickInterface;

    ProgressDialog progressDialog;
    private View view;
    private Button btnAdd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_supplier_home, container, false);

        btnAdd = (Button) view.findViewById(R.id.btn_add);
        onclickInterface = new onClickInterface() {
            @Override
            public void setClick(int position) {
                vehicles.indexOf(position);
                Log.d("Position: ","Position is " + position);
                adapter.notifyDataSetChanged();
            }
        };
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), AddVehicleActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });


/*        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang lấy dữ liệu...");
        progressDialog.show();*/

        recyclerView = view.findViewById(R.id.vehicle_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dtb_vehicle = FirebaseFirestore.getInstance();
        vehicles = new ArrayList<Vehicle>();
        adapter = new SupplierVehicleAdapter(SupplierHomeFragment.this, vehicles, onclickInterface);
        recyclerView.setAdapter(adapter);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUserID(firebaseUser.getUid());

        try {
            EventChangeListener();
            //Toast.makeText(getContext(),"Catching...", Toast.LENGTH_LONG).show();
        } catch (Exception exception){
            Toast.makeText(getContext(), "Exception", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void EventChangeListener()
    {
        dtb_vehicle.collection("Vehicles")
                .whereEqualTo("provider_id", user.getUserID())
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
                                temp.setSuppier_name(document.get("provider_name").toString());
                                vehicles.add(temp);
                                adapter.notifyDataSetChanged();
                                /*                                progressDialog.cancel();*/
                            }
                        } else {
                            Toast.makeText(getContext(), "Không thể lấy thông tin xe", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}