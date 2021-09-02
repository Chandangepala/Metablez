package com.basic.metablez.adpaters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basic.metablez.R;
import com.basic.metablez.activities.ProductDetailsActivity;
import com.basic.metablez.activities.UtilityServicesActivity;
import com.basic.metablez.models.ProductModel;

import java.util.ArrayList;

public class ProductRecyclVwAdapter extends RecyclerView.Adapter<ProductRecyclVwAdapter.ProductVwHolder> {

    Context context;
    ArrayList<ProductModel> arrProducts;


    public ProductRecyclVwAdapter(Context context, ArrayList<ProductModel> arrProducts) {
        this.context = context;
        this.arrProducts = arrProducts;
    }

    @NonNull
    @Override
    public ProductVwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductVwHolder(LayoutInflater.from(context).inflate(R.layout.product_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVwHolder holder, int position) {
            holder.produtImg.setImageResource(arrProducts.get(position).ptImg);
            holder.productName.setText(arrProducts.get(position).productName);
            holder.productWeight.setText(arrProducts.get(position).productWeight);
            holder.productPrice.setText(arrProducts.get(position).productPrice);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickEvents(position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return arrProducts.size();
    }

    public class ProductVwHolder extends RecyclerView.ViewHolder {
        ImageView produtImg;
        TextView productName, productWeight, productPrice;
        public ProductVwHolder(@NonNull View itemView) {
            super(itemView);

            produtImg = itemView.findViewById(R.id.product_img);
            productName = itemView.findViewById(R.id.product_name_txt);
            productWeight = itemView.findViewById(R.id.product_weight_txt);
            productPrice = itemView.findViewById(R.id.product_price_txt);
        }
    }

    //To handle recycler view item click
    public void clickEvents(int position){
        String name = arrProducts.get(position).productName;
        switch (name){
            case "Utility Services":
                break;
            default:
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product", (Parcelable) arrProducts.get(position));
                context.startActivity(intent);
        }
    }
}
