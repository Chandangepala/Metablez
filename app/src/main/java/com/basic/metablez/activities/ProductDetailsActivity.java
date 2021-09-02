  package com.basic.metablez.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basic.metablez.R;
import com.basic.metablez.adpaters.ProductAlsoRecyclVwAdapter;
import com.basic.metablez.clickListeners.ProductAlsoListener;
import com.basic.metablez.models.ProductAlsoModel;
import com.basic.metablez.models.ProductModel;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener, ProductAlsoListener {

    LinearLayout linlayAddButton;
    RelativeLayout relLayCart;
    TextView plusTxt, minusText, centerTxt, productNameTitleTxt, productWtTxt,
            productPriceTxt, itemsNtotalTxt;
    ImageView productImg;
    RecyclerView recycVwAlsoLike;
    ArrayList<ProductModel> arrProductAlso = new ArrayList<>();
    ArrayList<ProductModel> arrProductCart = new ArrayList<>();
    ProductAlsoRecyclVwAdapter productAlsoAdapter;
    ProductModel productModel;
    double productPriceDigit, basePrice;
    double productWeightDigit, baseWeight;
    int productMultiplier= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initMain();

        fixedData();

        setupRecyclerview();
    }

    public void initMain(){
        linlayAddButton = findViewById(R.id.linlay_add_button);
        relLayCart = findViewById(R.id.rellay_cart);
        centerTxt = findViewById(R.id.add_button_center_txt);
        plusTxt = findViewById(R.id.plus_txt);
        minusText = findViewById(R.id.minus_sign_txt);
        recycVwAlsoLike = findViewById(R.id.recycler_view_may_also_like);
        productImg = findViewById(R.id.product_img);
        productNameTitleTxt = findViewById(R.id.product_name_title_txt);
        productPriceTxt = findViewById(R.id.product_price_txt);
        productWtTxt = findViewById(R.id.product_wt_txt);
        itemsNtotalTxt = findViewById(R.id.no_of_item_txt);

        //get intent values and set it into product interface
        setProductDetailedData();

        centerTxt.setText("ADD");
        plusTxt.setVisibility(View.GONE);
        minusText.setVisibility(View.GONE);

        relLayCart.setVisibility(View.GONE);
        linlayAddButton.setOnClickListener(this);
        plusTxt.setOnClickListener(this);
        minusText.setOnClickListener(this);
        relLayCart.setOnClickListener(this);
    }

    //To setup recycler view
    public void setupRecyclerview(){
        recycVwAlsoLike.setLayoutManager(new LinearLayoutManager(this));
        productAlsoAdapter = new ProductAlsoRecyclVwAdapter(this, arrProductAlso, this);
        recycVwAlsoLike.setAdapter(productAlsoAdapter);
    }

    public void addProductsAlso(String name, String price, int priceDigit, String weight, int weightDigit, int img){
        ProductModel productAlsoModel = new ProductModel(name, weight, weightDigit, price, priceDigit, "", img, 1);
        arrProductAlso.add(productAlsoModel);
    }

    public void fixedData(){
        addProductsAlso("Chicken Tikka Cut Boneless", "RS 250", 250 ,"500gms", 500 , R.drawable.farm_chicken_img);
        addProductsAlso("Chiken Curry Cut","RS 400", 400,"500gms", 500,R.drawable.country_chicken);
        addProductsAlso("Chicken Breast Boneless","RS 500", 500,"1Kg" , 1000,R.drawable.chicken_a);

        addProductsAlso("Chicken Tikka Cut Boneless", "RS 250", 250 ,"500gms", 500 , R.drawable.farm_chicken_img);
        addProductsAlso("Chiken Curry Cut","RS 400", 400,"500gms", 500,R.drawable.country_chicken);
        addProductsAlso("Chicken Breast Boneless","RS 500", 500,"1Kg" , 1000,R.drawable.chicken_a);

        addProductsAlso("Chicken Tikka Cut Boneless", "RS 250", 250 ,"500gms", 500 , R.drawable.farm_chicken_img);
        addProductsAlso("Chiken Curry Cut","RS 400", 400,"500gms", 500,R.drawable.country_chicken);
        addProductsAlso("Chicken Breast Boneless","RS 500", 500,"1Kg" , 1000,R.drawable.chicken_a);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linlay_add_button:
                centerTxt.setText("0.5kg/500g");
                relLayCart.setVisibility(View.VISIBLE);
                plusTxt.setVisibility(View.VISIBLE);
                minusText.setVisibility(View.VISIBLE);

                String itemNtotal = productMultiplier + " item(s) | RS " + productPriceDigit;
                itemsNtotalTxt.setText(itemNtotal);
                break;
            case R.id.minus_sign_txt:
                productMultiplier--;
                renewProductDetails(productMultiplier);
                break;
            case R.id.plus_txt:
                productMultiplier++;
                renewProductDetails(productMultiplier);
                break;
            case R.id.rellay_cart:
                Log.d("multiplier", "" + productMultiplier );
                startHomeActivity();
                break;
        }
    }

    //Intent for home activity
    public void startHomeActivity(){

        //updating the product multiplier value
        productModel.multiplier = productMultiplier;
        arrProductCart.set(0, productModel); //To update array-list obj value

        Intent iHome = new Intent(this, HomeActivity.class);
        iHome.putExtra("navigPage", "cart");
        iHome.putExtra("arrCart",arrProductCart);
        iHome.putExtra("multiplier", productMultiplier);
        startActivity(iHome);

    }

    //To set the product detailed values which gets received from intent
    public void setProductDetailedData(){

        productModel = getIntent().getParcelableExtra("product");
        arrProductCart.add(productModel);

        productNameTitleTxt.setText(productModel.productName);
        productPriceTxt.setText(productModel.productPrice);
        productWtTxt.setText(productModel.productWeight);
        productImg.setImageResource(productModel.ptImg);
        productPriceDigit = productModel.productPriceDigit;
        productWeightDigit = productModel.productWeightDigit;

        basePrice = productModel.productPriceDigit;
        baseWeight = productModel.productWeightDigit;
    }

    //To renew product details when product added or removed
    public void renewProductDetails(int productMultiplier){


        productPriceDigit =  productMultiplier*basePrice;
        productWeightDigit = productMultiplier*baseWeight/1000;

        String newWeight =  productWeightDigit + "Kgs";
        productWtTxt.setText(newWeight);

        String newPrice = "RS " + productPriceDigit;
        productPriceTxt.setText(newPrice);

        String itemNtotal = productMultiplier + " item(s) | RS " + productPriceDigit;
        itemsNtotalTxt.setText(itemNtotal);
    }

    @Override
    public void addProductAlso(ProductModel productModel, int position) {
        Log.d("addedAlso", "product also added" + position);

        int finalMultiplier = productMultiplier + productModel.multiplier;

        double finalPrice = productPriceDigit + productModel.productPriceDigit;
        String itemNtotal = finalMultiplier + " item(s) | RS " + finalPrice;
        itemsNtotalTxt.setText(itemNtotal);

        arrProductCart.add(productModel);

        productModel.multiplier++;
    }

    @Override
    public void subtractProductAlso(ProductModel productModel, int position) {

    }
}