package com.example.customerrenting.Model;

public class Message {
    private String senderID, receiverID, message, time;

    public Message(String senderID, String receiverID, String message, String time) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        this.time = time;
    }
    public Message(){

    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
