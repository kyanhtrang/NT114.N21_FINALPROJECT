package com.example.customerrenting.Model;


public class Activity {
    private String customer_id;
    private String supplier_id;
    private String status;
    private String activity_id;
    private String vehicle_id;

    private String dropoff;

    private String pickup;

    public Activity()

    {

    }

    public Activity(String customer_id, String provide_id, String status, String activity_id, String vehicle_id) {
        this.customer_id = customer_id;
        this.supplier_id = provide_id;
        this.status = status;
        this.activity_id = activity_id;
        this.vehicle_id = vehicle_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String provide_id) {
        this.supplier_id = provide_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;

    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public String getDropoff() {
        return dropoff;
    }

    public String getPickup() {
        return pickup;
    }

}
