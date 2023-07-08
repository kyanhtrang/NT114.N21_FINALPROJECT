package com.example.customerrenting.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.customerrenting.R;
import com.example.customerrenting.Services.PushNotifications.FCMSend;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {
    View view;
    String token, idsupplier;
    private FirebaseFirestore dtbVehicle;
    ArrayList<String> id_supplier = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notice, container, false);
        dtbVehicle = FirebaseFirestore.getInstance();
        String id_vehicle = "rwqZxIF2JDRRJVoCgk6q";
        getID(id_vehicle);
        return view;
    }

    public void getID(String id_vehicle){
        dtbVehicle.collection("Vehicles")
                .whereEqualTo("vehicle_id", id_vehicle)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = new String();
                                id = document.get("supplier_id").toString();
                                getToken(id);
                            }
                        }
                    }
                });
        //Toast.makeText(getContext(), id_supplier.get(1), Toast.LENGTH_SHORT).show();
    }
    public void getToken(String id_supplier){
        dtbVehicle.collection("Users")
                .whereEqualTo("userID", id_supplier)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                token = document.get("token").toString();
                                sendNoti(token);
                            }
                        }
                    }
                });
    }

    public void sendNoti(String token){
        FCMSend.pushNotification(
                getContext(),
                token,
                "Thong bao",
                "Co don hang moi"
        );
    }
}