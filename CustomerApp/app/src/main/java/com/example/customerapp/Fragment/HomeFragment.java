package com.example.customerapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.customerapp.Adapter.VehicleAdapter;
import com.example.customerapp.Model.Vehicle;
import com.example.customerapp.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private RecyclerView rcvVehical;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);
        setRcvVehical();
        return view;
    }

    private void setRcvVehical(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false);
        rcvVehical = view.findViewById(R.id.rcvVehical);
        rcvVehical.setLayoutManager(linearLayoutManager);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("Ô tô", "xeoto"));
        vehicles.add(new Vehicle("Taxi", "taxi"));
        vehicles.add(new Vehicle("Xe máy", "xemay"));
        vehicles.add(new Vehicle("Xe đạp", "xedap"));
        adapter = new VehicleAdapter(vehicles);
        rcvVehical.setAdapter(adapter);
    }
}