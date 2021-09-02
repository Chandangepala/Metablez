package com.basic.metablez.adpaters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basic.metablez.R;
import com.basic.metablez.activities.ProductDetailsActivity;
import com.basic.metablez.clickListeners.ProductAlsoListener;
import com.basic.metablez.models.ProductAlsoModel;
import com.basic.metablez.models.ProductModel;

import java.util.ArrayList;

public class ProductAlsoRecyclVwAdapter extends RecyclerView.Adapter<ProductAlsoRecyclVwAdapter.ProductAlsoVwHolder> {

    Context context;
    ArrayList<ProductModel> arrProductsAlso;
    ProductAlsoListener productAlsoListener;


    public ProductAlsoRecyclVwAdapter(Context context, ArrayList<ProductModel> arrProductsAlso, ProductAlsoListener productAlsoListener) {
        this.context = context;
        this.arrProductsAlso = arrProductsAlso;
        this.productAlsoListener = productAlsoListener;
    }

    @NonNull
    @Override
    public ProductAlsoVwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductAlsoVwHolder(LayoutInflater.from(context).inflate(R.layout.product_also_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAlsoVwHolder holder, int position) {
            holder.produtImg.setImageResource(arrProductsAlso.get(position).ptImg);
            holder.productName.setText(arrProductsAlso.get(position).productName);
            holder.productWeight.setText(arrProductsAlso.get(position).productWeight);
            holder.productPrice.setText(arrProductsAlso.get(position).productPrice);
            holder.productBtnTxt.setText("ADD");
            holder.productPlus.setVisibility(View.GONE);
            holder.productMinus.setVisibility(View.GONE);

            holder.linlayAddBtn.setOnClickListener(v -> {
               // clickEvents(position);
               productAlsoListener.addProductAlso(arrProductsAlso.get(position), position);
                Log.d("added", "Product Added");
            });
    }

    @Override
    public int getItemCount() {
        return arrProductsAlso.size();
    }

    public class ProductAlsoVwHolder extends RecyclerView.ViewHolder {
        ImageView produtImg;
        TextView productName, productWeight, productPrice, productBtnTxt, productPlus, productMinus;
        LinearLayout linlayAddBtn;
        public ProductAlsoVwHolder(@NonNull View itemView) {
            super(itemView);

            produtImg = itemView.findViewById(R.id.product_img);
            productName = itemView.findViewById(R.id.product_name_txt);
            productWeight = itemView.findViewById(R.id.product_weight_txt);
            productPrice = itemView.findViewById(R.id.product_price_txt);
            linlayAddBtn = itemView.findViewById(R.id.linlay_add_button);
            productPlus = itemView.findViewById(R.id.plus_txt);
            productMinus = itemView.findViewById(R.id.minus_sign_txt);
            productBtnTxt = itemView.findViewById(R.id.add_button_center_txt);
        }
    }

    //To handle recycler view item click
    public void clickEvents(int position){
        String name = arrProductsAlso.get(position).productName;
        switch (name){
            case "Utility Services":
                break;
            default:
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                context.startActivity(intent);
        }
    }
}
