package com.basic.retailer_meatablez.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.basic.retailer_meatablez.R;

import java.util.ArrayList;

public class SetupShopActivity extends AppCompatActivity {

    Spinner shopCategorySpinner;
    ArrayList<String> arrShopCategory = new ArrayList<>();
    Button btnStartShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_shop);

        initMain();

        setupSpinner();

        btnStartShop.setOnClickListener(v -> {
            startHomeActivity();
        });
    }

    public void initMain(){
        shopCategorySpinner = findViewById(R.id.shop_category_spinner);
        btnStartShop = findViewById(R.id.start_shop_btn);
    }


    //To setup spinner and put data into it
    public void setupSpinner(){
        arrShopCategory.add(getResources().getString(R.string.shop_category));
        arrShopCategory.add("Fish Shop");
        arrShopCategory.add("Chicken Shop");
        arrShopCategory.add("Mutton Shop");
        arrShopCategory.add("All Non-Veg");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrShopCategory);
        shopCategorySpinner.setAdapter(adapter);
    }

    public void startHomeActivity(){
        Intent iHome = new Intent(SetupShopActivity.this, HomeActivity.class);
        startActivity(iHome);
    }
}