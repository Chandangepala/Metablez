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
import com.basic.retailer_meatablez.models.OrderModel;

import java.util.ArrayList;

public class OrderRecyclerVwAdapter extends RecyclerView.Adapter<OrderRecyclerVwAdapter.OrderViewHolder> {

    Context context;
    ArrayList<OrderModel> arrOrders;

    public OrderRecyclerVwAdapter(Context context, ArrayList<OrderModel> arrOrders) {
        this.context = context;
        this.arrOrders = arrOrders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.order_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.imgProduct.setImageResource(R.drawable.farm_chicken_img);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtProductName, txtDateTime, txtOrderId;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.product_img);
            txtProductName = itemView.findViewById(R.id.product_name_txt);
            txtDateTime = itemView.findViewById(R.id.dateTime_txt);
            txtOrderId = itemView.findViewById(R.id.order_id_txt);
        }
    }
}
