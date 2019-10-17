package com.example.mahmoud.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mahmoud on 6/16/2017.
 */

public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(Context context) {
        super(context, "stocks.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + StockContract.StockEntry.TableName
                + "(" +
                StockContract.StockEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StockContract.StockEntry.Name + " TEXT NOT NULL, "
                + StockContract.StockEntry.Price + " TEXT NOT NULL, "
                + StockContract.StockEntry.Quantity + " TEXT NOT NULL, "
                + StockContract.StockEntry.SupplierName + " TEXT NOT NULL, "
                + StockContract.StockEntry.SupplierEmail + " TEXT NOT NULL, "
                + StockContract.StockEntry.SupplierPhone + " TEXT NOT NULL, "
                + StockContract.StockEntry.SupplierImage + " TEXT NOT NULL "
                + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE " + StockContract.StockEntry.TableName);
            onCreate(db);
        }
    }


    public void insert(StockItem item) {
        ContentValues values = new ContentValues();
        values.put(StockContract.StockEntry.Name, item.productName);
        values.put(StockContract.StockEntry.Price, item.Price);
        values.put(StockContract.StockEntry.Quantity, item.quantity);
        values.put(StockContract.StockEntry.SupplierName, item.supplierName);
        values.put(StockContract.StockEntry.SupplierEmail, item.supplierEmail);
        values.put(StockContract.StockEntry.SupplierPhone, item.supplierPhone);
        values.put(StockContract.StockEntry.SupplierImage, item.image);
        getWritableDatabase().insert(StockContract.StockEntry.TableName, null, values);
    }

    public Cursor readall() {

        SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery("SELECT * FROM " + StockContract.StockEntry.TableName, null);
        db.endTransaction();
        return cursor;

    }

    public void update(StockItem item) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StockContract.StockEntry.Quantity, item.quantity);
        String s = StockContract.StockEntry.ID + " =? ";
        String[] Args = {String.valueOf(item.id)};
        db.update(StockContract.StockEntry.TableName, values, s, Args);

    }

    public void delete(int id) {

        SQLiteDatabase db = getWritableDatabase();
        String selectionString = StockContract.StockEntry.ID + "=?";
        String[] Args = {String.valueOf(id)};
        db.delete(StockContract.StockEntry.TableName, selectionString, Args);
    }


}
