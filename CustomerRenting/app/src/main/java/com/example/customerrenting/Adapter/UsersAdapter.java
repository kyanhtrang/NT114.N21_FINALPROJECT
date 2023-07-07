package com.example.customerrenting.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerrenting.Model.User;
import com.example.customerrenting.R;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    ArrayList<User> users;

    public UsersAdapter(ArrayList<User> users) {this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.Name.setText(users.get(position).getFullName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        //ImageView imgVehical;
        ConstraintLayout mainLayout;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.friendName);
            //imgVehical=itemView.findViewById(R.id.imgVehical);
            //mainLayout=itemView.findViewById(R.id.friend);
        }
    }
}
