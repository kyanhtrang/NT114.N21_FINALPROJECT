package com.example.customerrenting.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customerrenting.Model.Message;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Message> messages;
    private String senderID;

    public ChatAdapter(ArrayList<Message> messages, String senderID){
        this.messages = messages;
        this.senderID = senderID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_message,parent,false);
            return new ChatAdapter.SendMessageViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_message,parent,false);
        return new ChatAdapter.ReceiveMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == 1){
            ((SendMessageViewHolder)holder).bindData(messages.get(position));
        }
        ((ReceiveMessageViewHolder)holder).bindData(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getSenderID().equals(senderID)){
            return 1;
        }
        return 2;
    }

    static class SendMessageViewHolder extends RecyclerView.ViewHolder{
        TextView tvmessage, time;
        Message message;
        public SendMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmessage = itemView.findViewById(R.id.sendMessage);
            time = itemView.findViewById(R.id.sendMessageTime);
        }
        private void bindData(Message message) {
            this.message = message;
            tvmessage.setText(message.getMessage());
            time.setText(message.getTime());
        }

    }

    static class ReceiveMessageViewHolder extends RecyclerView.ViewHolder{
        TextView tvmessage, time;
        Message message;
        public ReceiveMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmessage = itemView.findViewById(R.id.sendMessage);
            time = itemView.findViewById(R.id.sendMessageTime);
        }
        private void bindData(Message message) {
            this.message = message;
            tvmessage.setText(message.getMessage());
            time.setText(message.getTime());
        }

    }
}
