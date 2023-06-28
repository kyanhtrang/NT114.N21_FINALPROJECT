package com.example.customerrenting.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;

import java.util.ArrayList;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>{

    ArrayList<Vehicle> vehicles;

    public VehicleAdapter(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehical,parent,false);
        return new VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        holder.vehicalName.setText(vehicles.get(position).getVehicalName());
        String picurl = "";
        switch (position){
            case 0:
                picurl="xeoto";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.vehical_background));
                break;
            case 1:
                picurl="taxi";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.vehical_background1));
                break;
            case 2:
                picurl="xemay";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.vehical_background2));
                break;
            case 3:
                picurl="xedap";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.vehical_background3));
                break;
        }
        int drawableResourceID=holder.itemView.getContext().getResources().getIdentifier(picurl,"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceID)
                .into(holder.imgVehical);
    }

    @Override
    public int getItemCount() {
         return vehicles.size();
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder{
        TextView vehicalName;
        ImageView imgVehical;
        ConstraintLayout mainLayout;
        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicalName=itemView.findViewById(R.id.tvVehicalName);
            imgVehical=itemView.findViewById(R.id.imgVehical);
            mainLayout=itemView.findViewById(R.id.layoutVehical);
        }
    }
}
