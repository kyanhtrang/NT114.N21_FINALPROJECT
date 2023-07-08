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

import com.example.customerrenting.Adapter.UsersAdapter;
import com.example.customerrenting.ChatActivity;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.Model.onClickUserItem;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MessageFragment extends Fragment implements onClickUserItem {
    private ArrayList<User> users;
    private UsersAdapter friendAdapter;
    RecyclerView friendRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        users = new ArrayList<>();
        friendRecyclerView = view.findViewById(R.id.friendRecyclerView);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendAdapter = new UsersAdapter(users, this);
        friendRecyclerView.setAdapter(friendAdapter);
        getUsers();
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

    @Override
    public void onClickUserItem(User user) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        /*Bundle bundle = new Bundle();
        bundle.putString("hotel_id", mUser.getUserID());
        intent.putExtras(bundle);*/
        startActivity(intent);
        getActivity().finish();
    }
}
