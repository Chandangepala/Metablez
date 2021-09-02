package com.basic.metablez.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basic.metablez.R;
import com.basic.metablez.adpaters.UtilityRecyclVwAdapter;
import com.basic.metablez.models.UtilityModel;

import java.util.ArrayList;

public class UtilityServicesActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<UtilityModel> arrUtility = new ArrayList<>();
    RecyclerView recyclerVwUtility;
    UtilityRecyclVwAdapter utilityAdapter;
    ImageView backImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_services);

        //To initialize all the views
        initMain();

        //To add static data to arraylist hence to the recycler view
        fixedData();

        //To setup and add adapeter to recycler view in order to populate
        setupRecyclerVw();
    }

    //To initialize all the views
    public void initMain(){
        recyclerVwUtility = findViewById(R.id.recycler_view_utilityservices);
        backImg = findViewById(R.id.back_btn_img);
        backImg.setOnClickListener(this);
    }

    //To setup and add adapeter to recycler view in order to populate
    public void setupRecyclerVw(){
        recyclerVwUtility.setLayoutManager(new GridLayoutManager(this, 3));
        utilityAdapter = new UtilityRecyclVwAdapter(this, arrUtility);
        recyclerVwUtility.setAdapter(utilityAdapter);
    }

    //To add data to arraylist arrUtility
    public void addUtility(String name, int img){
        UtilityModel utilityModel = new UtilityModel(name, img);
        arrUtility.add(utilityModel);
    }

    //To add static data to arraylist hence to the recycler view
    public void fixedData(){
        addUtility("Recharge", R.drawable.mobile_recharge_img);
        addUtility("Post Paid Recharge", R.drawable.postpaid_recharge_img);
        addUtility("Gas Refill", R.drawable.gas_refill_img);
        addUtility("Landline", R.drawable.landline_img);
        //addUtility("UPI", R.drawable.utility_d);
        addUtility("DTH Recharge", R.drawable.dth_recharge_img);
        addUtility("Broadband", R.drawable.broadband_img);
        addUtility("Health Insurance", R.drawable.health_img_x);
        addUtility("FastTag", R.drawable.fast_tag_img);
        addUtility("Cable TV", R.drawable.cable_tv_img);
        addUtility("Loan", R.drawable.loan_img);
        addUtility("Tax", R.drawable.tax_img);
        addUtility("Credit Card Bill", R.drawable.credit_card_img);
        addUtility("Subscribe", R.drawable.subscribe_img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn_img:
                finish();
                break;
        }
    }
}