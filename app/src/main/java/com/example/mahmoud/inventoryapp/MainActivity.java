package com.example.mahmoud.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    StockAdapter adapter;
    public static List<StockItem> list;
    public static DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView) findViewById(R.id.empty);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", -1);
                startActivity(intent);
            }
        });

        ListView Listview = (ListView) findViewById(R.id.ListView);
        Listview.setEmptyView(textView);
        adapter = new StockAdapter(this);
        Listview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    public void viewDetils(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("id", position);
        startActivity(intent);
    }

    public void decrement(StockItem item) {
        if (item.quantity <= 0) {
            item.quantity = 0;
        } else {
            item.quantity = item.quantity - 1;
        }
        dBhelper.update(item);
        updateUi();
    }

    public void updateUi() {
        dBhelper = new DBhelper(this);
        Cursor cursor = dBhelper.readall();
        list = new ArrayList();
        StockItem item;
        while (cursor.moveToNext()) {
            item = new StockItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(6), cursor.getString(5), cursor.getString(7));
            list.add(item);
        }
        adapter.clear();
        adapter.addAll(list);
    }
}
