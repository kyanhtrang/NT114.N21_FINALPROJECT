package com.example.customerrenting.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.customerrenting.Services.Authentication.LoginActivity;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.UsersManagement.UserProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {
    private View view;
    private Button btnSignout, btnProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        btnSignout = (Button) view.findViewById(R.id.logout_btn);
        btnProfile = (Button) view.findViewById(R.id.user_profile_btn) ;
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { profile(); }
        });
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    public void profile() {
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        startActivity(intent);
    }
}