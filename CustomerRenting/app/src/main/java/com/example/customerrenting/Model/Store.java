package com.example.customerrenting.Model;

public class Store {
    private String StoreId, SupplierID, VehicleID, StoreName;
    private String Size;
    private int Numbers;
    private String address;

    public Store() {
    }

    public Store(String storeId, String supplierID, String vehicleID, String storeName, String size, int numbers, String address) {
        StoreId = storeId;
        SupplierID = supplierID;
        VehicleID = vehicleID;
        StoreName = storeName;
        Size = size;
        Numbers = numbers;
        this.address = address;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }

    public String getVehicleID() {
        return VehicleID;
    }

    public void setVehicleID(String vehicleID) {
        VehicleID = vehicleID;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getNumbers() {
        return Numbers;
    }

    public void setNumbers(int numbers) {
        Numbers = numbers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
