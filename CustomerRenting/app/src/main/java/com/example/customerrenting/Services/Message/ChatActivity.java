package com.example.customerrenting.Services.Message;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.customerrenting.Adapter.ChatAdapter;
import com.example.customerrenting.Model.Message;
import com.example.customerrenting.Model.User;
import com.example.customerrenting.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    private TextView name;
    private User receiver;
    private FirebaseFirestore database;
    private ArrayList<Message> messages;
    private ChatAdapter chatAdapter;

    String senderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
        loadReceiverDetails();
    }

    private void loadReceiverDetails() {
        receiver =(User) getIntent().getSerializableExtra("userID");
        name.setText(receiver.getFullName());
    }

    private void init() {
        name = (TextView) findViewById(R.id.friendName);
        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(messages, "userID");
        database = FirebaseFirestore.getInstance();
    }

    private void sendMessage(){
        HashMap<String, Objects> message = new HashMap<>();
        //message.put()
    }
}