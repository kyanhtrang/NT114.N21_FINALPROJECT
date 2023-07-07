package com.example.customerrenting.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerrenting.Adapter.UsersAdapter;
import com.example.customerrenting.Adapter.VehicleAdapter;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;

public class MessageFragment extends Fragment {
    /*private FirebaseAuth mAuth;
    private FirebaseDatabase database;*/
    private ArrayList<User> users;
    private TextView Name;
    private User user;
    private UsersAdapter friendadapter;
    RecyclerView friendRecyclewiew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        /*database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();*/
        users = new ArrayList<>();
        //Name = view.findViewById(R.id.title);
        friendRecyclewiew = view.findViewById(R.id.friendRecyclerView);
        getUsers();
        /*user.getFullName();
        users.add(user);
        friendadapter = new UsersAdapter(users);
        friendRecyclewiew.setAdapter(friendadapter);*/


        /*database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                users.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    if(!user.getUserID().equals(mAuth.getUid())){
                        database.getReference().child("users")
                                .child(mAuth.getUid())
                                .child("friendList")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snapshot2 : snapshot.getChildren()){
                                            String check = String.valueOf(snapshot2.getValue());

                                            if(check.equals(user.getUserID())){
                                                users.add(user);
                                            }

                                        }
                                        //friendsAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        return view;
    }
    private void getUsers(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("Users").get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        user = new User();
                        user.fullName = document.get("fullName").toString();
                        users.add(user);
                        Toast.makeText(getActivity(),user.fullName, Toast.LENGTH_LONG).show();
                    }
                    friendadapter = new UsersAdapter(users);
                    friendRecyclewiew.setAdapter(friendadapter);
                    friendRecyclewiew.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}