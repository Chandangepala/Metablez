package com.basic.metablez.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.basic.metablez.R;
import com.basic.metablez.adpaters.ProductRecyclVwAdapter;
import com.basic.metablez.models.ProductModel;
import com.basic.metablez.models.UtilityModel;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<ProductModel> arrProducts = new ArrayList<>();
    RecyclerView recyclerVwProducts;
    ProductRecyclVwAdapter productAdapter;
    ImageView backImg, mapImg;
    TextView toolbarTitleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initMain();

        fixedData();

        setupRecyclerVw();
    }

    //To initialize all the views
    public void initMain(){
        recyclerVwProducts = findViewById(R.id.recycler_view_products);
        toolbarTitleText = findViewById(R.id.toolbar_home_title_text);
        backImg = findViewById(R.id.back_btn_img);
        mapImg = findViewById(R.id.img_map_home);
        backImg.setVisibility(View.VISIBLE);
        mapImg.setVisibility(View.GONE);

        toolbarTitleText.setText(R.string.products);
        backImg.setOnClickListener(this);
    }

    //To setup and add adapeter to recycler view in order to populate
    public void setupRecyclerVw(){
        recyclerVwProducts.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductRecyclVwAdapter(this, arrProducts);
        recyclerVwProducts.setAdapter(productAdapter);
    }

    //To add data to arraylist arrUtility
    public void addProducts(String name, int img, String weight,int weightDigit, String price, int priceDigit, int multiplier){
        ProductModel productModel = new ProductModel(name, weight,weightDigit, price, priceDigit, "", img, multiplier);
        arrProducts.add(productModel);
    }

    //To add static data to arraylist hence to the recycler view
    public void fixedData(){
        addProducts("Chicken Tikka Cut Boneless", R.drawable.farm_chicken_img, "500gms", 500, "RS 250", 250, 1);
        addProducts("Chiken Curry Cut", R.drawable.country_chicken, "500 gms",500, "RS 400", 400, 1);
        addProducts("Chicken Breast Boneless", R.drawable.chicken_a, "1 Kg", 1000,"RS 600", 600, 1);

        addProducts("Chicken Tikka Cut Boneless", R.drawable.farm_chicken_img, "500gms", 500, "RS 250", 250, 1);
        addProducts("Chiken Curry Cut", R.drawable.country_chicken, "500 gms",500, "RS 400", 400, 1);
        addProducts("Chicken Breast Boneless", R.drawable.chicken_a, "1 Kg", 1000,"RS 600", 600, 1);

        addProducts("Chicken Tikka Cut Boneless", R.drawable.farm_chicken_img, "500gms", 500, "RS 250", 250, 1);
        addProducts("Chiken Curry Cut", R.drawable.country_chicken, "500 gms",500, "RS 400", 400, 1);
        addProducts("Chicken Breast Boneless", R.drawable.chicken_a, "1 Kg", 1000,"RS 600", 600, 1);

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