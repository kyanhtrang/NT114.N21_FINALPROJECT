package com.example.customerrenting.Model;

public class VehicleTemplate {
    private String vehicalName;
    private String vehicalImage;

    public VehicleTemplate() {
    }

    public VehicleTemplate(String vehicalName, String vehicalImage) {
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
