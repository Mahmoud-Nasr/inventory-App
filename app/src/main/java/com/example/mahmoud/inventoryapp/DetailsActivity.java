package com.example.mahmoud.inventoryapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    EditText name;
    EditText price;
    EditText quantity;
    EditText supplierName;
    EditText supplierEmail;
    EditText supplierPhone;
    Button save;
    Button delete;
    Button phoneOrder;
    Button EmailOrder;
    Button ImageSelector;
    Button increment;
    Button decrement;
    ImageView imageView;
    Uri actualUri;
    int imageResult = 0;
    int id;
    StockItem item = new StockItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name = (EditText) findViewById(R.id.EditProductName);
        price = (EditText) findViewById(R.id.EditPrice);
        quantity = (EditText) findViewById(R.id.EditQuantity);
        supplierName = (EditText) findViewById(R.id.EditSupplierName);
        supplierEmail = (EditText) findViewById(R.id.EditSupplierEmail);
        supplierPhone = (EditText) findViewById(R.id.EditSupplierPhone);
        save = (Button) findViewById(R.id.detailsButtonSave);
        delete = (Button) findViewById(R.id.detailsButtonDelete);
        phoneOrder = (Button) findViewById(R.id.detailsButtonOrderPhone);
        EmailOrder = (Button) findViewById(R.id.detailsButtonOrderEmail);
        ImageSelector = (Button) findViewById(R.id.detailsButtonImage);
        increment = (Button) findViewById(R.id.increment);
        decrement = (Button) findViewById(R.id.decrement);
        imageView = (ImageView) findViewById(R.id.detailsImageView);
        id = getIntent().getIntExtra("id", -1);
        if (id == -1) {
            delete.setVisibility(View.INVISIBLE);
            phoneOrder.setVisibility(View.INVISIBLE);
            EmailOrder.setVisibility(View.INVISIBLE);
            ImageSelector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });
        } else {
            item = MainActivity.list.get(id);
            actualUri = Uri.parse(item.image);
            delete.setVisibility(View.VISIBLE);
            phoneOrder.setVisibility(View.VISIBLE);
            EmailOrder.setVisibility(View.VISIBLE);
            name.setText(item.productName);
            name.setEnabled(false);
            price.setText(item.Price);
            price.setEnabled(false);
            quantity.setText(String.valueOf(item.quantity));
            quantity.setEnabled(false);
            supplierName.setText(item.supplierName);
            supplierName.setEnabled(false);
            supplierEmail.setText(item.supplierEmail);
            supplierEmail.setEnabled(false);
            supplierPhone.setText(item.supplierPhone);
            supplierPhone.setEnabled(false);
            imageView.setImageURI(Uri.parse(item.image));
            ImageSelector.setEnabled(false);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(DetailsActivity.this).setMessage("confirm the operation").setPositiveButton("delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    MainActivity.dBhelper.delete(item.id);
                                    NavUtils.navigateUpFromSameTask(DetailsActivity.this);

                                }
                            }).create().show();
                }
            });
            phoneOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + item.supplierPhone));
                    startActivity(intent);
                }
            });
            EmailOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + item.supplierEmail));
                    startActivity(intent);
                }
            });
        }
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.equals("") || price.equals("") || quantity.equals("") || supplierName.equals("") || supplierPhone.equals("")
                        || supplierEmail.equals("") || actualUri == null) {
                    Toast.makeText(DetailsActivity.this, "please don't leave any field empty", Toast.LENGTH_LONG).show();
                } else if (item.id == 0) {
                    StockItem itemm = new StockItem(0, name.getText().toString(), price.getText().toString() + " $",
                            Integer.parseInt(quantity.getText().toString()), supplierName.getText().toString(), supplierPhone.getText().toString()
                            , supplierEmail.getText().toString(), actualUri.toString());
                    MainActivity.dBhelper.insert(itemm);
                    NavUtils.navigateUpFromSameTask(DetailsActivity.this);
                } else {
                    StockItem itemm = new StockItem(item.id, name.getText().toString(), price.getText().toString() + " $",
                            Integer.parseInt(quantity.getText().toString()), supplierName.getText().toString(), supplierPhone.getText().toString()
                            , supplierEmail.getText().toString(), actualUri.toString());
                    MainActivity.dBhelper.update(itemm);
                    NavUtils.navigateUpFromSameTask(DetailsActivity.this);

                }
            }
        });
    }


    public void increment() {
        if (id != -1) {
            item.quantity = item.quantity + 1;
            quantity.setText(String.valueOf(item.quantity));
        } else {
            int quan = Integer.parseInt(quantity.getText().toString());
            quan = quan + 1;
            quantity.setText(String.valueOf(quan));
        }
    }

    public void decrement() {
        if (id != -1) {
            if (item.quantity <= 0) {
                item.quantity = 0;
            } else {
                item.quantity = item.quantity - 1;
            }
            quantity.setText(String.valueOf(item.quantity));

        } else {
            int quan = Integer.parseInt(quantity.getText().toString());
            quan = quan - 1;
            quantity.setText(String.valueOf(quan));

        }

    }

    public void selectImage() {

        Intent intent;
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), imageResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imageResult && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                actualUri = data.getData();
                imageView.setImageURI(actualUri);
                imageView.invalidate();
            }
        }
    }
}
