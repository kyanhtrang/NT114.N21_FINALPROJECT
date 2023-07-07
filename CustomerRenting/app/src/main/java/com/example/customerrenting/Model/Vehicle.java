package com.example.customerrenting.Model;

public class Vehicle {
    String vehicle_id, provider_id, provider_name;
    String vehicle_name, vehicle_price, vehicle_seats, vehicle_number, vehicle_availability;
    String vehicle_imageURL;

    public Vehicle() {
    }

    public Vehicle(String vehicle_id, String provider_id, String provider_name, String vehicle_name, String vehicle_price, String vehicle_seats, String vehicle_number, String vehicle_availability, String vehicle_imageURL) {
        this.vehicle_id = vehicle_id;
        this.provider_id = provider_id;
        this.provider_name = provider_name;
        this.vehicle_name = vehicle_name;
        this.vehicle_price = vehicle_price;
        this.vehicle_seats = vehicle_seats;
        this.vehicle_number = vehicle_number;
        this.vehicle_availability = vehicle_availability;
        this.vehicle_imageURL = vehicle_imageURL;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
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
