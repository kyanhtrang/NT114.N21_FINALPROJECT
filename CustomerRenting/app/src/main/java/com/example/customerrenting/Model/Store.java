package com.example.customerrenting.Model;

public class Store {
    private String StoreID, SupplierID, StoreName;
    private String Size;
    private int Numbers;
    private String address;

    public Store() {
    }

    public Store(String storeId, String supplierID, String storeName, String size, int numbers, String address) {
        StoreID = storeId;
        SupplierID = supplierID;
        StoreName = storeName;
        Size = size;
        Numbers = numbers;
        this.address = address;
    }

    public int getNumbers() {
        return Numbers;
    }

    public void setNumbers(int numbers) {
        Numbers = numbers;
    }

    public String getStoreId() {
        return StoreID;
    }

    public void setStoreId(String storeID) {
        StoreID = storeID;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
