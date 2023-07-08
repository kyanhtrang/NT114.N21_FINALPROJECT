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
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.Booking.VehicleDetailActivity;
import com.example.customerrenting.Services.Vehicle.ShowAllVehicleActivity;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder>{
    ShowAllVehicleActivity showAllVehicleActivity;
    Vehicle vehicle;
    ArrayList<Vehicle> vehicles;
    onClickInterface onClickInterface;
    public VehicleAdapter(ShowAllVehicleActivity context, ArrayList<Vehicle> vehicles, onClickInterface onClickInterface) {
        this.showAllVehicleActivity = context;
        this.vehicles = vehicles;
        this.onClickInterface = onClickInterface;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(showAllVehicleActivity).inflate(R.layout.vehicle_card, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final int pos = position;
        vehicle = vehicles.get(position);
        holder.name.setText(vehicle.getVehicle_name());
        holder.price.setText(vehicle.getVehicle_price());
        holder.provider.setText(vehicle.getSupplier_name());
        Glide.with(showAllVehicleActivity).load(vehicle.getVehicle_imageURL()).into(holder.vehicleImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.setClick(pos);
                vehicle = vehicles.get(pos);
                Intent intent = new Intent(showAllVehicleActivity, VehicleDetailActivity.class);
                intent.putExtra("vehicle_id", vehicle.getVehicle_id());
                showAllVehicleActivity.startActivity(intent);
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
            name = itemView.findViewById(R.id.card_vehicle_name);
            price = itemView.findViewById(R.id.card_tv_vehicle_price);
            provider = itemView.findViewById(R.id.card_provider_name);
            vehicleImage = itemView.findViewById(R.id.card_img_vehicle);
        }

    }
}