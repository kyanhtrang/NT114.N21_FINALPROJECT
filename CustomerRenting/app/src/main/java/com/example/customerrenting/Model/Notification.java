package com.example.customerrenting.Model;

public class Notification {
    private String id_user;
    private String title;
    private String body;

    public Notification() {
    }

    public Notification(String id_user, String title, String body) {
        this.id_user = id_user;
        this.title = title;
        this.body = body;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
