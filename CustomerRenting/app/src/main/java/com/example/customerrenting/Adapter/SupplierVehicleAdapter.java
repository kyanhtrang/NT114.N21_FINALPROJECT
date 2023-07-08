package com.example.customerrenting.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customerrenting.Fragments.SupplierFragments.SupplierHomeFragment;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.Vehicle.UpdateVehicleActivity;

import java.util.ArrayList;

public class SupplierVehicleAdapter extends RecyclerView.Adapter<SupplierVehicleAdapter.MyViewHolder>{
    SupplierHomeFragment supplierHomeFragment;
    Vehicle vehicle;
    ArrayList<Vehicle> vehicles;
    com.example.customerrenting.Model.onClickInterface onClickInterface;
    public SupplierVehicleAdapter(SupplierHomeFragment context, ArrayList<Vehicle> vehicles, onClickInterface onClickInterface) {
        this.supplierHomeFragment = context;
        this.vehicles = vehicles;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(supplierHomeFragment.getActivity()).inflate(R.layout.vehicle_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int pos = position;
        vehicle = vehicles.get(position);
        holder.name.setText(vehicle.getVehicle_name());
        holder.price.setText(vehicle.getVehicle_price());
        holder.provider.setText(vehicle.getSuppier_name());
        Glide.with(supplierHomeFragment.getActivity()).load(vehicle.getVehicle_imageURL()).into(holder.vehicleImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.setClick(pos);
                vehicle = vehicles.get(pos);
                Intent intent = new Intent(supplierHomeFragment.getActivity(), UpdateVehicleActivity.class);
                intent.putExtra("vehicle_id", vehicle.getVehicle_id());
                supplierHomeFragment.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, provider;
        ImageView vehicleImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.vehicle_name);
            price = itemView.findViewById(R.id.tv_vehicle_price);
            provider = itemView.findViewById(R.id.provider_name);
            vehicleImage = itemView.findViewById(R.id.img_vehicle);
        }
    }
}
