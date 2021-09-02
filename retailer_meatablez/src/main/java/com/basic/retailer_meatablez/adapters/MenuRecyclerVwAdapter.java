package com.basic.retailer_meatablez.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basic.retailer_meatablez.R;
import com.basic.retailer_meatablez.models.MenuProductModel;

import java.util.ArrayList;

public class MenuRecyclerVwAdapter extends RecyclerView.Adapter<MenuRecyclerVwAdapter.MenuViewHolder> {

    Context context;
    ArrayList<MenuProductModel> arrMenuProducts;

    public MenuRecyclerVwAdapter(Context context, ArrayList<MenuProductModel> arrMenuProduct) {
        this.context = context;
        this.arrMenuProducts = arrMenuProduct;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(context).inflate(R.layout.menu_item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtProductPrice;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.product_img);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            txtProductPrice = itemView.findViewById(R.id.txt_product_price);
        }
    }
}
