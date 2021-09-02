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
import com.basic.metablez.models.UtilityModel;
import com.basic.metablez.utility.RechargeActivity;

import java.util.ArrayList;

public class UtilityRecyclVwAdapter extends RecyclerView.Adapter<UtilityRecyclVwAdapter.UtilityVwHolder> {

    Context context;
    ArrayList<UtilityModel> arrUtility;

    public UtilityRecyclVwAdapter(Context context, ArrayList<UtilityModel> arrUtility) {
        this.context = context;
        this.arrUtility = arrUtility;
    }

    @NonNull
    @Override
    public UtilityVwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UtilityVwHolder(LayoutInflater.from(context).inflate(R.layout.home_item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UtilityVwHolder holder, int position) {
            holder.image.setImageResource(arrUtility.get(position).utImage);
            holder.name.setText(arrUtility.get(position).utName);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickEvents(position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return arrUtility.size();
    }

    public class UtilityVwHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public UtilityVwHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.item_img_rcv);
            name = itemView.findViewById(R.id.item_name_rcv);
        }
    }

    //To handle recycler view item click
    public void clickEvents(int position){
        String name = arrUtility.get(position).utName;
        switch (name){
            case "Recharge":
                Intent iRecharge = new Intent(context, RechargeActivity.class); //intent to recharge activity
                context.startActivity(iRecharge);
                //Toast.makeText(context, "Clicked: "+ name, Toast.LENGTH_SHORT).show();
                break;
            case "Gas Refill":
                break;
        }
    }
}
