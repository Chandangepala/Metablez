package com.basic.metablez.adpaters;

import android.content.Context;
import android.content.Intent;
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
import com.basic.metablez.clickListeners.CartProductListener;
import com.basic.metablez.models.ProductModel;

import java.util.ArrayList;

public class CartRecyclVwAdapter extends RecyclerView.Adapter<CartRecyclVwAdapter.ProductAlsoVwHolder> {

    Context context;
    ArrayList<ProductModel> arrProducts;
    CartProductListener cartProductListener;
    int multiplier = 1;

    public CartRecyclVwAdapter(Context context, ArrayList<ProductModel> arrProducts, CartProductListener cartProductListener, int multiplier) {
        this.context = context;
        this.arrProducts = arrProducts;
        this.cartProductListener = cartProductListener;
        this.multiplier = multiplier;
    }

    @NonNull
    @Override
    public ProductAlsoVwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductAlsoVwHolder(LayoutInflater.from(context).inflate(R.layout.cart_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAlsoVwHolder holder, int position) {
            holder.produtImg.setImageResource(arrProducts.get(position).ptImg);
            holder.productName.setText(arrProducts.get(position).productName);
            holder.productWeight.setText(arrProducts.get(position).productWeight);
            holder.productPrice.setText(arrProducts.get(position).productPrice);
            String items = "" + arrProducts.get(position).multiplier;
            holder.productBtnTxt.setText(items);
            holder.productPlus.setVisibility(View.VISIBLE);
            holder.productMinus.setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(v -> {
               // clickEvents(position);
            });

            holder.deletePdtCart.setOnClickListener(v -> {
                cartProductListener.deleteProduct(position);
            });

            holder.productPlus.setOnClickListener(v -> {
                cartProductListener.addProduct(arrProducts.get(position), position);
            });

            holder.productMinus.setOnClickListener(v -> {
                cartProductListener.subtractProduct(arrProducts.get(position), position);
            });

    }

    @Override
    public int getItemCount() {
        if(arrProducts != null)
            return arrProducts.size();
        else
            return 0;
    }

    public class ProductAlsoVwHolder extends RecyclerView.ViewHolder {
        ImageView produtImg;
        TextView productName, productWeight, productPrice, productBtnTxt, productPlus, productMinus, deletePdtCart;
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
            deletePdtCart = itemView.findViewById(R.id.delete_txt_cart);

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
                context.startActivity(intent);
        }
    }
}
