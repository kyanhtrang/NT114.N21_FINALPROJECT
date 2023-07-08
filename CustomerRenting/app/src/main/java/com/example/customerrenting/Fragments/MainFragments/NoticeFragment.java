package com.example.customerrenting.Fragments.MainFragments;

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
import android.widget.Toast;

import com.example.customerrenting.Adapter.NotificationAdapter;
import com.example.customerrenting.Adapter.VehicleTemplateAdapter;
import com.example.customerrenting.Model.Notification;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.VehicleTemplate;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.PushNotifications.FCMSend;
import com.example.customerrenting.Services.Vehicle.AddVehicleActivity;
import com.example.customerrenting.SupplierActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {
    View view;
    String token;
    private FirebaseFirestore dtbVehicle, dtbNoti;
    private RecyclerView rcvNoti;
    private RecyclerView.Adapter adapter;
    ArrayList<Notification> notifications = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_notice, container, false);
        rcvNoti = view.findViewById(R.id.rcvNoti);
        dtbVehicle = FirebaseFirestore.getInstance();
        dtbNoti = FirebaseFirestore.getInstance();
        setNoti();
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
                                sendNoti(token, "Thông báo", "Có đơn hàng mới");
                                Notification notification = new Notification();
                                addNoti(id_supplier, "Thông báo", "Có đơn hàng mới", notification);
                            }
                        }
                    }
                });
    }

    public void sendNoti(String token, String title, String body){
        FCMSend.pushNotification(
                getContext(),
                token,
                title,
                body
        );
    }

    public void addNoti(String idsuplier,String title, String body, Notification notification){
        notification = new Notification();
        notification.setId_user(idsuplier);
        notification.setTitle(title);
        notification.setBody(body);
       /* FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
        dtbNoti.setFirestoreSettings(settings);

        DocumentReference newUserRef = dtbNoti
                .collection("Notification")*/
        dtbNoti.collection("Notification")
                .add(notification)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        });

    }

    public void setNoti() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rcvNoti.setLayoutManager(linearLayoutManager);
        String id = FirebaseAuth.getInstance().getUid();
        adapter = new NotificationAdapter(notifications);
        rcvNoti.setAdapter(adapter);
        dtbNoti.collection("Notification")
                .whereEqualTo("id_user", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Notification notification = new Notification();
                                notification.setTitle(document.get("title").toString());
                                notification.setBody(document.get("body").toString());
                                notification.setId_user(id);
                                notifications.add(notification);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

    }


    /*dtb_vehicle.collection("Vehicles")
            .orderBy("vehicle_name",Query.Direction.ASCENDING)
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
                    temp.setProvider_name(document.get("provider_name").toString());
                    vehicles.add(temp);
                    adapter.notifyDataSetChanged();*//*
                                progressDialog.cancel();*//*
                }
            } else {
                Toast.makeText(getContext(), "Không thể lấy thông tin xe", Toast.LENGTH_SHORT).show();
            }
        }
    });*/

    /*private void createUser(){
        user = new User();
        user.setEmail(Email);
        user.setUserID(FirebaseAuth.getInstance().getUid());

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .build();
        dtbUser.setFirestoreSettings(settings);

        DocumentReference newUserRef = dtbUser
                .collection("Users")
                .document(FirebaseAuth.getInstance().getUid());
        newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    finish();
                }else{
                    View parentLayout = findViewById(android.R.id.content);
                    Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }*/
}