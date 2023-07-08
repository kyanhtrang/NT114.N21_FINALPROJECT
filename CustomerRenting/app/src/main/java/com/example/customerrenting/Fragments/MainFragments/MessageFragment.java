package com.example.customerrenting.Fragments.MainFragments;

import android.annotation.SuppressLint;

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


import android.widget.TextView;
import android.widget.Toast;

import com.example.customerrenting.Adapter.UsersAdapter;
import com.example.customerrenting.Adapter.VehicleAdapter;

import com.example.customerrenting.ChatActivity;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.Model.onClickUserItem;
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


import java.util.ArrayList;

public class MessageFragment extends Fragment {
    private ArrayList<User> users;
    private UsersAdapter friendAdapter;
    RecyclerView friendRecyclerView;
    private onClickInterface onClickInterface;;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        users = new ArrayList<>();
        friendRecyclerView = view.findViewById(R.id.friendRecyclerView);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        friendAdapter = new UsersAdapter(MessageFragment.this, users, onClickInterface);
        friendRecyclerView.setAdapter(friendAdapter);
        getUsers();
        onClickInterface = new onClickInterface() {
            @Override
            public void setClick(int position) {
                users.get(position);
                friendAdapter.notifyDataSetChanged();
            }
        };
        return view;
    }

    private void getUsers() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                users.add(user);
                            }
                            friendAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    /*@Override
    public void onclickUserItem(User mUser) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        *//*Bundle bundle = new Bundle();
        bundle.putString("hotel_id", mUser.getUserID());
        intent.putExtras(bundle);*//*
        startActivity(intent);
        getActivity().finish();
    }*/

    /*@Override
    public void onClickUserItem(User user) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        *//*Bundle bundle = new Bundle();
        bundle.putString("hotel_id", mUser.getUserID());
        intent.putExtras(bundle);*//*
        startActivity(intent);
        getActivity().finish();
    }*/
}
