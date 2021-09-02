package com.basic.metablez.adpaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basic.metablez.R;
import com.basic.metablez.activities.ProductActivity;
import com.basic.metablez.models.ShopModel;

import java.util.ArrayList;

public class ShopRecyclVwAdapter extends RecyclerView.Adapter<ShopRecyclVwAdapter.ShopVwHolder> {

    Context context;
    ArrayList<ShopModel> arrShops;


    public ShopRecyclVwAdapter(Context context, ArrayList<ShopModel> arrShops) {
        this.context = context;
        this.arrShops = arrShops;
    }

    @NonNull
    @Override
    public ShopVwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopVwHolder(LayoutInflater.from(context).inflate(R.layout.shop_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopVwHolder holder, int position) {

            ShopModel shopModel = arrShops.get(position);
            holder.shopName.setText(shopModel.shopName);
            holder.shopAddress.setText(shopModel.shopAddress);
            holder.ratingBarShop.setMax(5);
            holder.ratingBarShop.setNumStars(shopModel.shopRating);
            holder.shopImg.setImageResource(shopModel.shopImg);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickEvents(position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return arrShops.size();
    }

    public class ShopVwHolder extends RecyclerView.ViewHolder {
        ImageView shopImg;
        TextView shopName, shopAddress;
        RatingBar ratingBarShop;
        public ShopVwHolder(@NonNull View itemView) {
            super(itemView);

            shopImg = itemView.findViewById(R.id.shop_img);
            shopAddress = itemView.findViewById(R.id.shop_address_txt);
            shopName = itemView.findViewById(R.id.shop_name_txt);
            ratingBarShop = itemView.findViewById(R.id.shop_rating);
        }
    }

    //To handle recycler view item click
    public void clickEvents(int position){
        String name = arrShops.get(position).shopName;
        switch (name){
            case "Utility Services":
                break;
            default:
                Intent intent = new Intent(context, ProductActivity.class);
                context.startActivity(intent);
        }
    }
}
