package com.example.customerrenting.Model;

public class Vehicle {
    private String vehicalName;
    private String vehicalImage;

    public Vehicle() {
    }

    public Vehicle(String vehicalName, String vehicalImage) {
        this.vehicalName = vehicalName;
        this.vehicalImage = vehicalImage;
    }

    public String getVehicalName() {
        return vehicalName;
    }

    public void setVehicalName(String vehicalName) {
        this.vehicalName = vehicalName;
    }

    public String getVehicalImage() {
        return vehicalImage;
    }

    public void setVehicalImage(String vehicalImage) {
        this.vehicalImage = vehicalImage;
    }
}
