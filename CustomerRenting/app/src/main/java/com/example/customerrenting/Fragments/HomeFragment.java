package com.example.customerrenting.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.customerrenting.Adapter.PopularAdapter;
import com.example.customerrenting.Adapter.VehicleAdapter;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.Model.VehicleType;
import com.example.customerrenting.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter1;

    private RecyclerView rcvVehical;
    private RecyclerView rcvPopular;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setRcvVehical();
        setRcvPopular();
        return view;
    }

    private void setRcvVehical() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvVehical = view.findViewById(R.id.rcvVehical);
        rcvVehical.setLayoutManager(linearLayoutManager);
        ArrayList<VehicleType> vehicles = new ArrayList<>();
        vehicles.add(new VehicleType("Ô tô", "xeoto"));
        vehicles.add(new VehicleType("Taxi", "taxi"));
        vehicles.add(new VehicleType("Xe máy", "xemay"));
        vehicles.add(new VehicleType("Xe đạp", "xedap"));
        adapter = new VehicleAdapter(vehicles);
        rcvVehical.setAdapter(adapter);
    }

    private void setRcvPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvPopular = view.findViewById(R.id.rcvPopular);
        rcvPopular.setLayoutManager(linearLayoutManager);
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("", "", "", "xe 4 chổ", "500000", "", "", "", ""));
        vehicles.add(new Vehicle("", "", "", "xe bán tải", "1000000", "", "", "", ""));
        vehicles.add(new Vehicle("", "", "", "xe Vision", "200000", "", "", "", ""));
        vehicles.add(new Vehicle("", "", "", "xe máy", "150000", "", "", "", ""));
        adapter = new PopularAdapter(vehicles);
        rcvPopular.setAdapter(adapter);
    }
}
