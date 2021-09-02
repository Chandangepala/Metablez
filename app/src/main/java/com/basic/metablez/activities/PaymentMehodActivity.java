package com.basic.metablez.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.basic.metablez.R;
import com.basic.metablez.adpaters.ProductRecyclVwAdapter;
import com.basic.metablez.models.ProductModel;

import java.util.ArrayList;

public class PaymentMehodActivity extends AppCompatActivity {

    String selectedDateTime = "Standard Delivery: 11-May-2021, 5 PM -7 PM";
    TextView txtSelectedDateTime, txtApplyCoupon;
    RecyclerView recyclerViewCart;
    ProductRecyclVwAdapter productRecyclVwAdapter;
    ArrayList<ProductModel> arrayProductCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mehod);

        //To initialize all the views
        initUI();

        //To get data received with intent
        getIntentData();

        txtSelectedDateTime.setText(selectedDateTime);

        //To setup recyclerview and populate it...
        setupRecyclerVw();

        txtApplyCoupon.setOnClickListener(v -> {
            Toast.makeText(this, "Apply Coupon Clicked", Toast.LENGTH_SHORT).show();
            Intent iCoupon = new Intent(PaymentMehodActivity.this, CouponActivity.class);
            startActivity(iCoupon);
        });
    }

    //To initialize all the views
    private void initUI() {
        txtSelectedDateTime = findViewById(R.id.txt_select_time_slot);
        txtApplyCoupon = findViewById(R.id.txt_apply_coupon);
        recyclerViewCart = findViewById(R.id.recycler_vw_cart_payment);
    }

    //To get data received with the intent...
    public void getIntentData(){
        if(getIntent().hasExtra("deliveryDate")){
            selectedDateTime = "Standard Delivery: " + getIntent().getStringExtra("deliveryDate")
                    + ", "
                    +getIntent().getStringExtra("deliveryTime");
        }
        if(getIntent().hasExtra("arrProductCart")){
            arrayProductCart = (ArrayList<ProductModel>) getIntent().getSerializableExtra("arrProductCart");
            //Toast.makeText(this, "Del Opt : " + arrayProductCart.get(0), Toast.LENGTH_SHORT).show();
        }
    }

    //To setup recycler view and populate recycler view...
    void setupRecyclerVw(){
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        productRecyclVwAdapter = new ProductRecyclVwAdapter(this, arrayProductCart);
        recyclerViewCart.setAdapter(productRecyclVwAdapter);
    }
}