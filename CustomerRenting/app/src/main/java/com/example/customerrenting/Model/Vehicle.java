package com.example.customerrenting.Model;

public class Vehicle {
    String vehicle_id, supplier_id, supplier_name;
    String vehicle_name, vehicle_price, vehicle_seats, vehicle_number, vehicle_availability, vehicle_type;
    String vehicle_imageURL;

    public Vehicle() {
        this.vehicle_id = "";
        this.supplier_id = "";
        this.supplier_name = "";
        this.vehicle_name = "";
        this.vehicle_price = "";
        this.vehicle_seats = "";
        this.vehicle_number = "";
        this.vehicle_availability = "";
        this.vehicle_imageURL = "";
        this.vehicle_type = "";
    }

    public Vehicle(String vehicle_id, String supplier_id, String supplier_name, String vehicle_name, String vehicle_price, String vehicle_seats, String vehicle_number, String vehicle_availability, String vehicle_imageURL, String vehicle_type) {
        this.vehicle_id = vehicle_id;
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.vehicle_name = vehicle_name;
        this.vehicle_price = vehicle_price;
        this.vehicle_seats = vehicle_seats;
        this.vehicle_number = vehicle_number;
        this.vehicle_availability = vehicle_availability;
        this.vehicle_imageURL = vehicle_imageURL;
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getVehicle_price() {
        return vehicle_price;
    }

    public void setVehicle_price(String vehicle_price) {
        this.vehicle_price = vehicle_price;
    }

    public String getVehicle_seats() {
        return vehicle_seats;
    }

    public void setVehicle_seats(String vehicle_seats) {
        this.vehicle_seats = vehicle_seats;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getVehicle_availability() {
        return vehicle_availability;
    }

    public void setVehicle_availability(String vehicle_availability) {
        this.vehicle_availability = vehicle_availability;
    }

    public String getVehicle_imageURL() {
        return vehicle_imageURL;
    }

    public void setVehicle_imageURL(String vehicle_imageURL) {
        this.vehicle_imageURL = vehicle_imageURL;
    }
}
