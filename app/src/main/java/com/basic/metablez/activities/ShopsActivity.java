package com.basic.metablez.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.basic.metablez.R;
import com.basic.metablez.adpaters.ShopRecyclVwAdapter;
import com.basic.metablez.models.HomeModel;
import com.basic.metablez.models.ShopModel;

import java.util.ArrayList;

public class ShopsActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerViewShop;
    ArrayList<ShopModel> arrShops = new ArrayList<>();
    ShopRecyclVwAdapter shopAdapter;
    ImageView backImg, mapImg;
    TextView toolbarTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        initMain();

        fixedShops();

        setupRecyclerView();
    }

    public void initMain(){
        recyclerViewShop = findViewById(R.id.recycler_view_shops);
        toolbarTitleText = findViewById(R.id.toolbar_home_title_text);
        backImg = findViewById(R.id.back_btn_img);
        mapImg = findViewById(R.id.img_map_home);
        backImg.setVisibility(View.VISIBLE);
        mapImg.setVisibility(View.GONE);

        toolbarTitleText.setText(R.string.shops);
        backImg.setOnClickListener(this);
    }

    public void setupRecyclerView(){
        recyclerViewShop.setLayoutManager(new LinearLayoutManager(this));
        shopAdapter = new ShopRecyclVwAdapter(this, arrShops);
        recyclerViewShop.setAdapter(shopAdapter);
    }

    public void addShops(String name, String address, int img, int rating){
        ShopModel shopModel = new ShopModel("", name, address, rating, img);
        arrShops.add(shopModel);
    }

    public void fixedShops(){
        addShops("Royal Meat", "Sector 3 Malviya Nagar Jaipur", R.drawable.ic_baseline_shopping_cart_, 4);
        addShops("Red Planet", "C Scheme Jaipur", R.drawable.ic_baseline_shopping_cart_, 5);
        addShops("VegNot Cafe", "Shyam Nagar, Sodala Jaipur", R.drawable.ic_baseline_shopping_cart_, 3);

        addShops("Royal Meat", "Sector 3 Malviya Nagar Jaipur", R.drawable.ic_baseline_shopping_cart_, 4);
        addShops("Red Planet", "C Scheme Jaipur", R.drawable.ic_baseline_shopping_cart_, 5);
        addShops("VegNot Cafe", "Shyam Nagar, Sodala Jaipur", R.drawable.ic_baseline_shopping_cart_, 3);

        addShops("Royal Meat", "Sector 3 Malviya Nagar Jaipur", R.drawable.ic_baseline_shopping_cart_, 4);
        addShops("Red Planet", "C Scheme Jaipur", R.drawable.ic_baseline_shopping_cart_, 5);
        addShops("VegNot Cafe", "Shyam Nagar, Sodala Jaipur", R.drawable.ic_baseline_shopping_cart_, 3);
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