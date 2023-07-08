package com.example.customerrenting.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customerrenting.Fragments.HomeFragment;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.R;
import com.example.customerrenting.Services.Booking.VehicleDetailActivity;

import java.util.ArrayList;

public class PopularVehicleAdapter extends RecyclerView.Adapter<PopularVehicleAdapter.PopularVehicleViewHolder>{
    HomeFragment homeFragment;
    ArrayList<Vehicle> popularVehicle;
    Vehicle vehicle;
    onClickInterface onClickInterface;

    public PopularVehicleAdapter(ArrayList<Vehicle> vehivehiclePopulares) {
        this.popularVehicle = vehivehiclePopulares;
    }

    public PopularVehicleAdapter(HomeFragment context, ArrayList<Vehicle> vehicles, onClickInterface onClickInterface) {
        this.homeFragment = context;
        this.popularVehicle = vehicles;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public PopularVehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle_popular,parent,false);
        return new PopularVehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularVehicleViewHolder holder, int position) {
        final int pos = position;
        vehicle = popularVehicle.get(position);
        holder.tvVehicleName.setText(popularVehicle.get(position).getVehicle_name());
        holder.tvVehiclePrice.setText(popularVehicle.get(position).getVehicle_price() + "/ngày");
        int drawableResourceID=holder.itemView.getContext().getResources().getIdentifier(popularVehicle.get(position).getVehicle_imageURL(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceID)
                .into(holder.imgVehical);
        holder.itemView.findViewById(R.id.tvRent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.setClick(pos);
                vehicle = popularVehicle.get(pos);
                Intent intent = new Intent(homeFragment.getActivity(), VehicleDetailActivity.class);
                intent.putExtra("vehicle_id", vehicle.getVehicle_id());
                homeFragment.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularVehicle.size();
    }

    public static class PopularVehicleViewHolder extends RecyclerView.ViewHolder {

        TextView tvVehicleName;
        ImageView imgVehical;
        ConstraintLayout mainLayout;
        TextView tvVehiclePrice;
        TextView btnRent;

        public PopularVehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicleName=itemView.findViewById(R.id.tvPopularName);
            imgVehical=itemView.findViewById(R.id.imgPopularVehicle);
            mainLayout=itemView.findViewById(R.id.layoutPopular);
            tvVehiclePrice=itemView.findViewById(R.id.tvVehiclePrice);
            btnRent=itemView.findViewById(R.id.tvRent);
        }
    }
}
