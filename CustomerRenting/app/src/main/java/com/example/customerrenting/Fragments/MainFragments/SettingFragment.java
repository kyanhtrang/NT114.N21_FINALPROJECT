package com.example.customerrenting.Fragments.MainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.customerrenting.ChangePasswordActivity;
import com.example.customerrenting.Services.Authentication.LoginActivity;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.UsersManagement.ViewProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {
    private View view;
    TextView  btnProfile, btnSignout, btnChangePassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        btnSignout = (TextView) view.findViewById(R.id.logout);
        btnProfile = (TextView) view.findViewById(R.id.accountSettings) ;
        btnChangePassword = (TextView) view.findViewById(R.id.changepassword) ;
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
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });
        return view;
    }

    private void ChangePassword() {
        Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    public void profile() {
        Intent intent = new Intent(getActivity(), ViewProfileActivity.class);
        startActivity(intent);
    }
}