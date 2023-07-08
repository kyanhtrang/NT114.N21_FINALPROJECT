package com.example.customerrenting.Fragments.MainFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import com.example.customerrenting.Model.User;

import com.example.customerrenting.Services.Authentication.ChangePasswordActivity;
import com.example.customerrenting.Services.Authentication.LoginActivity;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.UsersManagement.ViewProfileActivity;
import com.example.customerrenting.CreateStore;
import com.example.customerrenting.SupplierActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class SettingFragment extends Fragment {
    private View view;
    private TextView  btnProfile, btnSignout, btnChangePassword, btnStore, btnTranfer;

    private FirebaseUser firebaseUser;
    private DocumentReference userRef;
    private FirebaseFirestore db;
    private User user = new User();

    private ImageView imgAvt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        db = FirebaseFirestore.getInstance();
        init();

        getImage();

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
        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists())
                            {
                                user.setHaveStore(Integer.parseInt(document.get("haveStore").toString()));
                                if (user.getHaveStore() == 0)
                                {
                                    createStore();
                                }
                                else
                                {
                                    Toast.makeText(view.getContext(), "Bạn đã có store, không thể tạo thêm", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });
            }
        });

        btnTranfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            if(document.exists())
                            {
                                user.setHaveStore(Integer.parseInt(document.get("haveStore").toString()));
                                if (user.getHaveStore() == 0)
                                {
                                    Toast.makeText(view.getContext(), "Bạn chưa có store, hãy tạo store", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Intent intent = new Intent(getActivity(), SupplierActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                });

            }
        });
        return view;
    }

    private void createStore() {

        Intent intent = new Intent(getActivity(), CreateStore.class);
        startActivity(intent);
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

    public void init()
    {
        imgAvt = view.findViewById(R.id.profilePic);
        btnSignout = view.findViewById(R.id.logout);
        btnProfile =  view.findViewById(R.id.accountSettings) ;
        btnChangePassword = view.findViewById(R.id.changepassword);
        btnStore = view.findViewById(R.id.store);
        btnTranfer = view.findViewById(R.id.tranfer);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user.setUserID(firebaseUser.getUid());
        userRef = db.collection("Users").document(user.getUserID());

    }

    public void getImage(){
        db.collection("Users")
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