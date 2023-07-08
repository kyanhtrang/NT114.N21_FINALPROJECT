package com.example.customerrenting.Fragments.MainFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.customerrenting.Adapter.UsersAdapter;

import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class MessageFragment extends Fragment {
    private ArrayList<User> users;
    private UsersAdapter friendAdapter;
    RecyclerView friendRecyclerView;
    private FirebaseUser firebaseUser;
    FirebaseFirestore database;
    private ImageView imgAvt;
    User user;
    private onClickInterface onClickInterface;;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        database = FirebaseFirestore.getInstance();

        imgAvt = view.findViewById(R.id.profilePic);
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
        database.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user = document.toObject(User.class);
                                users.add(user);
                            }
                            friendAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    private void getImage(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUserID(firebaseUser.getUid());
        database.collection("Users")
                .whereEqualTo("userID", user.getUserID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user.setAvatarURL(document.getString("avatarURL"));
                                if (!document.getString("avatarURL").isEmpty()) {
                                    Picasso.get().load(user.getAvatarURL()).into(imgAvt);
                                } else {
                                    user.setAvatarURL("");
                                }
                            }
                        }
                    }
                });
    }

}
