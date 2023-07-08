package com.example.customerrenting.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerrenting.Model.Notification;
import com.example.customerrenting.Model.Vehicle;
import com.example.customerrenting.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewholder>{


    ArrayList<Notification> notifications;

    public NotificationAdapter(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle_popular,parent,false);
        return new NotificationAdapter.NotificationViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewholder holder, int position) {
        holder.tvTitle.setText(notifications.get(position).getTitle());
        holder.tvBody.setText(notifications.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public class NotificationViewholder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvBody;
        public NotificationViewholder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tv_titleNoti);
            tvBody=itemView.findViewById(R.id.tv_bodyNoti);
        }
    }
}
