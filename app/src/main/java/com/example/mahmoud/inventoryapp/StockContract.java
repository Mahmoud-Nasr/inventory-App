package com.example.mahmoud.inventoryapp;

import android.provider.BaseColumns;

/**
 * Created by mahmoud on 6/16/2017.
 */

public class StockContract  {



    public  class StockEntry implements BaseColumns{
        public static final String TableName="Stock";
        public static final String ID=BaseColumns._ID;
        public static final String Name="name";
        public static final String Price= "price";
        public static final String Quantity="quantity";
        public static final String SupplierName="supplierName";
        public static final String SupplierPhone="supplierPhone";
        public static final String SupplierEmail="SupplierEmail";
        public static final String SupplierImage="image";

    }
}
