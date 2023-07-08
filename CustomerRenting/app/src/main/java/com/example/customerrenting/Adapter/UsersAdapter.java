package com.example.customerrenting.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerrenting.ChatActivity;
import com.example.customerrenting.Fragments.MainFragments.MessageFragment;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.Model.onClickInterface;
import com.example.customerrenting.Model.onClickUserItem;
import com.example.customerrenting.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private ArrayList<User> users;
    private onClickInterface onClickInterface;;
    MessageFragment messageFragment;
    User user;
    public UsersAdapter(MessageFragment context, ArrayList<User> users, onClickInterface onClickInterface) {
        this.messageFragment = context;
        this.users = users;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(messageFragment.getActivity()).inflate(R.layout.message_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bindData(users.get(position));
        final int pos = position;
        user = users.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterface.setClick(pos);
                user = users.get(pos);
                Intent intent = new Intent(messageFragment.getActivity(), ChatActivity.class);
                intent.putExtra("userID", user.getUserID());
                messageFragment.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (users != null)
            return users.size();
        return 0;
    }

    /*public interface onClickInterface {
    }*/

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imgAvt;
        User user;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.friendName);
            imgAvt = itemView.findViewById(R.id.friendProfilePic);

        }

        private void bindData(User user) {
            this.user = user;
            name.setText(user.getFullName());

            String avatarURL = user.getAvatarURL();
            if (avatarURL != null && !avatarURL.isEmpty()) {
                imgAvt.setVisibility(View.VISIBLE);
                Picasso.get().load(avatarURL).into(imgAvt);
            } else {
                imgAvt.setVisibility(View.GONE);
            }
        }

        /*@Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION *//*&& clickInterface != null*//*) {
                clickInterface.setClick(position);
            }
        }*/
    }
}
