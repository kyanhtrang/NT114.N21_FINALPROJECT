package com.example.customerrenting;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.customerrenting.Model.User;

public class ChatActivity extends AppCompatActivity {

    private TextView name;
    private User receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        intit();
        loadReceiverDetails();
    }

    private void loadReceiverDetails() {
        receiver =(User) getIntent().getSerializableExtra("userID");
        name.setText(receiver.getFullName());
    }

    private void intit() {
        name = (TextView) findViewById(R.id.friendName);
    }
}