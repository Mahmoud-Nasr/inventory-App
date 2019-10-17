package com.example.mahmoud.inventoryapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mahmoud on 6/16/2017.
 */

public class StockAdapter extends ArrayAdapter<StockItem> {
    MainActivity context;

    public StockAdapter(@NonNull MainActivity context) {
        super(context, 0, new ArrayList<StockItem>());
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final StockItem item = getItem(position);
        StockHolder holder;
        if (convertView == null) {
            holder = new StockHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            holder.productname = (TextView) convertView.findViewById(R.id.Productname);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.Quantity = (TextView) convertView.findViewById(R.id.quantity);
            holder.sale = (Button) convertView.findViewById(R.id.sale);
            convertView.setTag(holder);
        } else {
            holder = (StockHolder) convertView.getTag();
        }
        holder.price.setText(item.Price);
        holder.productname.setText(item.productName);
        holder.Quantity.setText(String.valueOf(item.quantity));
        holder.sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.decrement(item);

            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.viewDetils(position);
            }
        });
        return convertView;
    }

    private class StockHolder {
        TextView productname;
        TextView price;
        TextView Quantity;
        Button sale;
    }
}
