package com.basic.metablez.adpaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basic.metablez.R;
import com.basic.metablez.activities.ProductActivity;
import com.basic.metablez.activities.ShopsActivity;
import com.basic.metablez.activities.UtilityServicesActivity;
import com.basic.metablez.models.HomeModel;

import java.util.ArrayList;

public class HomeRecyclVwAdapter extends RecyclerView.Adapter<HomeRecyclVwAdapter.HomeVwHolder> {

    Context context;
    ArrayList<HomeModel> arrHome;


    public HomeRecyclVwAdapter(Context context, ArrayList<HomeModel> arrHome) {
        this.context = context;
        this.arrHome = arrHome;
    }

    @NonNull
    @Override
    public HomeVwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeVwHolder(LayoutInflater.from(context).inflate(R.layout.home_item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVwHolder holder, int position) {
            holder.image.setImageResource(arrHome.get(position).image);
            holder.name.setText(arrHome.get(position).name);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickEvents(position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return arrHome.size();
    }

    public class HomeVwHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public HomeVwHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.item_img_rcv);
            name = itemView.findViewById(R.id.item_name_rcv);
        }
    }

    //To handle recycler view item click
    public void clickEvents(int position){
        String name = arrHome.get(position).name;
        switch (name){
            case "Utility Services":
                //Intent intent = new Intent(context, DashboardActivity.class);
                Intent intent = new Intent(context, UtilityServicesActivity.class);
                context.startActivity(intent);
                break;
            case "Chicken":
                Intent iFChic = new Intent(context, ShopsActivity.class);
                context.startActivity(iFChic);
                break;
        }
    }
}
