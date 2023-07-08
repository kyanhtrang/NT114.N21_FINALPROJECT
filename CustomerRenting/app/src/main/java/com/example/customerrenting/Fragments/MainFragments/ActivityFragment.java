package com.example.customerrenting.Fragments.MainFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.customerrenting.Adapter.ActivityAdapter;
import com.example.customerrenting.Model.Activity;
import com.example.customerrenting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ActivityFragment extends Fragment {

    RecyclerView recyclerView;
    ActivityAdapter notificationAdapter;
    ArrayList<Activity> activities;
    FirebaseFirestore dtb_noti;
    ProgressDialog progressDialog;
    String current_user_id;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        recyclerView = view.findViewById(R.id.activity_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang lấy dữ liệu...");
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance().getReference();
        dtb_noti = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();

        activities = new ArrayList<Activity>();
        notificationAdapter = new ActivityAdapter(ActivityFragment.this, activities);
        recyclerView.setAdapter(notificationAdapter);

        EventChangeListener();
        progressDialog.cancel();
        return view;
    }
    private void EventChangeListener()
    {
        dtb_noti.collection("Activity")
                .whereEqualTo("customer_id", current_user_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Activity temp = new Activity();
                                temp.setActivity_id(document.get("activity_id").toString());
                                temp.setSupplier_id(document.get("supplier_id").toString());
                                temp.setCustomer_id(document.get("customer_id").toString());
                                temp.setStatus(document.get("status").toString());
                                temp.setVehicle_id(document.get("vehicle_id").toString());
                                activities.add(temp);
                                notificationAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getContext(), "Không thể lấy thông tin đơn hàng ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}