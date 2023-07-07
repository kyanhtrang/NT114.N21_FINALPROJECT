package com.example.customerrenting.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder>{
    ArrayList<Vehicle> vehiclePopular;

    public PopularAdapter(ArrayList<Vehicle> vehivehiclePopulares) {
        this.vehiclePopular = vehivehiclePopulares;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehical_popular,parent,false);
        return new PopularAdapter.PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        holder.tvVehicleName.setText(vehiclePopular.get(position).getVehicle_name());
        holder.tvVehiclePrice.setText(vehiclePopular.get(position).getVehicle_price() + "/ngày");
        int drawableResourceID=holder.itemView.getContext().getResources().getIdentifier(vehiclePopular.get(position).getVehicle_imageURL(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceID)
                .into(holder.imgVehical);
    }

    @Override
    public int getItemCount() {
        return vehiclePopular.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {

        TextView tvVehicleName;
        ImageView imgVehical;
        ConstraintLayout mainLayout;
        TextView tvVehiclePrice;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicleName=itemView.findViewById(R.id.tvPopularName);
            imgVehical=itemView.findViewById(R.id.imgPopularVehicle);
            mainLayout=itemView.findViewById(R.id.layoutPopular);
            tvVehiclePrice=itemView.findViewById(R.id.tvVehiclePrice);
        }
    }
}
