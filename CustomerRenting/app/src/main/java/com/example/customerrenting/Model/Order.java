package com.example.customerrenting.Model;

public class Order {
    private String CustomerID;
    private String OrderID;
    private String ProvideID;
    private String vehicle_id;

    public Order() {

    }

    public Order(String customerID, String orderID, String provideID, String vehicl_id) {
        CustomerID = customerID;
        OrderID = orderID;
        ProvideID = provideID;
        vehicle_id = vehicl_id;

    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getProvideID() {
        return ProvideID;
    }

    public void setProvideID(String provideID) {
        ProvideID = provideID;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
}

