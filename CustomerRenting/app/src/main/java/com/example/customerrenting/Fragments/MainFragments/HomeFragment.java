package com.example.customerrenting.Fragments.MainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customerrenting.Adapter.PopularAdapter;
import com.example.customerrenting.Model.Token;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;


import android.widget.TextView;

import com.example.customerrenting.Adapter.VehicleTemplateAdapter;

import com.example.customerrenting.Model.VehicleTemplate;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.Services.Vehicle.ShowAllVehicleActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private RecyclerView.Adapter adapter;

    private RecyclerView rcvVehical;
    private RecyclerView rcvPopular;
    private FirebaseFirestore dtb_token, dtb_user;
    private FirebaseUser firebaseUser;
    private onClickInterface onclickInterface;

    private TextView showa, title;
    private User user = new User();


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        title = view.findViewById(R.id.tvHiName);

        dtb_user = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUserID(firebaseUser.getUid());

        dtb_user.collection("Users")
                .whereEqualTo("userID", user.getUserID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                title.setText("Hi " + document.get("fullName").toString()) ;
                            }
                        }
                        else {
                            //
                            Toast.makeText(view.getContext(), "Không thể lấy thông tin", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        dtb_token = FirebaseFirestore.getInstance();
        setRcvVehical();
        setRcvPopular();
        showal();
        getToken();

        return view;
    }

    private void setRcvVehical() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvVehical = view.findViewById(R.id.rcvNoti);
        rcvVehical.setLayoutManager(linearLayoutManager);
        ArrayList<VehicleTemplate> vehicles = new ArrayList<>();
        vehicles.add(new VehicleTemplate("Ô tô", "xeoto"));
        vehicles.add(new VehicleTemplate("Taxi", "taxi"));
        vehicles.add(new VehicleTemplate("Xe máy", "xemay"));
        vehicles.add(new VehicleTemplate("Xe đạp", "xedap"));
        adapter = new VehicleTemplateAdapter(vehicles);
        rcvVehical.setAdapter(adapter);
    }
    private void setRcvPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvPopular = view.findViewById(R.id.rcvPopular);
        rcvPopular.setLayoutManager(linearLayoutManager);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
      
        vehicles.add(new Vehicle("", "", "", "xe 4 chổ", "500000", "", "", "", "", "", "", "", ""));
        vehicles.add(new Vehicle("", "", "", "xe bán tải", "1000000", "", "", "", "","", "", "", ""));
        vehicles.add(new Vehicle("", "", "", "xe Vision", "200000", "", "", "", "","", "", "", ""));
        vehicles.add(new Vehicle("", "", "", "xe máy", "150000", "", "", "", "", "", "", "", ""));
        adapter = new PopularAdapter(vehicles);
        rcvPopular.setAdapter(adapter);
    }

    private void getToken() {
        Token token = new Token();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .build();
        dtb_token.setFirestoreSettings(settings);

        DocumentReference newTokenRef = dtb_token
                .collection("Users")
                .document(FirebaseAuth.getInstance().getUid());
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                token.setToken(task.getResult());

                /*Map<String, Object> data = new HashMap<>();
                data.put("token", token.getToken());

                dtb_token.collection("Token").document(FirebaseAuth.getInstance().getUid())
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(view.getContext(), "DocumentSnapshot successfully updated!", Toast.LENGTH_LONG).show();
                            }
                        });*/
                Map<String, Object> data = new HashMap<>();
                data.put("token", token.getToken());
                newTokenRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                });
            }
        });
    }

    private void showal(){
        TextView textView = view.findViewById(R.id.txtShowAllVehicle);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShowAllVehicleActivity.class);
                startActivity(i);
            }
        });
    }

}
