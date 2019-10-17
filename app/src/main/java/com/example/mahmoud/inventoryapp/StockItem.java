package com.example.mahmoud.inventoryapp;

/**
 * Created by mahmoud on 6/16/2017.
 */

public class StockItem {

    int id;
    String productName;
    String Price;
    int quantity;
    String supplierName;
    String supplierPhone;
    String supplierEmail;
    String image;

    public StockItem(int id, String productName, String price, int quantity, String supplierName, String supplierPhone, String supplierEmail, String image) {
        this.id = id;
        this.productName = productName;
        Price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
        this.supplierPhone = supplierPhone;
        this.supplierEmail = supplierEmail;
        this.image = image;
    }

    public StockItem() {

    }
}
