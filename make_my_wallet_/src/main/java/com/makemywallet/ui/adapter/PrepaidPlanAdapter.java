package com.makemywallet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.makemywallet.R;
import com.makemywallet.ui.Models.PrepaidPlanModel;
import com.makemywallet.ui.customClickListener.OnCustomClickListener;
import com.makemywallet.ui.customClickListener.OnCustomSingleClickListener;

import java.util.ArrayList;

public class PrepaidPlanAdapter extends RecyclerView.Adapter<PrepaidPlanAdapter.ViewHolder> {
    Context context;
    ArrayList<PrepaidPlanModel> arrayList;
    OnCustomSingleClickListener onCustomSingleClickListener;

    public PrepaidPlanAdapter(Context context, OnCustomSingleClickListener onCustomSingleClickListener, ArrayList<PrepaidPlanModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.onCustomSingleClickListener = onCustomSingleClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_prepaid_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PrepaidPlanModel model = arrayList.get(position);
        holder.txtTalkTime.setText(model.getRechargeLongDesc());
        holder.txtValidity.setText(model.getRechargeValidity());
        holder.txtTopUp.setText(model.getRechargeType());
        holder.txtAmount.setText(model.getRechargeAmount());
        holder.txtLongDecTittle.setText(model.getRechargeLongDesc());
        holder.txtShortDecTittle.setText(model.getRechargeShortDesc());
        holder.txtOperatorTittle.setText(model.getOperatorId());
        holder.txtCircleTittle.setText(model.getCircleId());
        holder.txtUpdatedDateTittle.setText("Updated At : "+model.getUpdatedAt());
        holder.lLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomSingleClickListener.onCustomSingleClick(model.getRechargeAmount());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTalkTime, txtValidity, txtTopUp, txtAmount, txtLongDecTittle, txtShortDecTittle, txtOperatorTittle, txtCircleTittle, txtUpdatedDateTittle;
        CardView lLayoutRoot;

        ViewHolder(View itemView) {
            super(itemView);
            txtTalkTime = itemView.findViewById(R.id.txtTalkTime);
            txtValidity = itemView.findViewById(R.id.txtValidity);
            txtTopUp = itemView.findViewById(R.id.txtTopUp);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtLongDecTittle = itemView.findViewById(R.id.txtLongDecTittle);
            txtShortDecTittle = itemView.findViewById(R.id.txtShortDecTittle);
            txtOperatorTittle = itemView.findViewById(R.id.txtOperatorTittle);
            txtCircleTittle = itemView.findViewById(R.id.txtCircleTittle);
            txtUpdatedDateTittle = itemView.findViewById(R.id.txtUpdatedDateTittle);
            lLayoutRoot = itemView.findViewById(R.id.lLayoutRoot);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
