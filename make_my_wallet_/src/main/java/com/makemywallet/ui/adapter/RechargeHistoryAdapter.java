package com.makemywallet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makemywallet.R;
import com.makemywallet.ui.Models.AccountStatementModel;

import java.util.List;

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RechargeHistoryAdapter.MyHolder> {
    private List<AccountStatementModel> arrayList;
    Context mContext;

    public RechargeHistoryAdapter(Context mContext, List<AccountStatementModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_recharge_history, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.txtRechargeId.setText(arrayList.get(position).getOrderId());
        holder.txtNumber.setText(arrayList.get(position).getNumber());
        holder.txtAmount.setText(arrayList.get(position).getAmount());
        holder.txtProviderName.setText(arrayList.get(position).getProviderName());
        holder.txtTxnid.setText(arrayList.get(position).getTxnid());
        holder.txtRemark.setText("₹ "+arrayList.get(position).getRemark());
        holder.txtTotalBalance.setText(arrayList.get(position).getTotalBalance());
        holder.txtStatus.setText(arrayList.get(position).getStatus());
        holder.txtDate.setText(arrayList.get(position).getDate());
        if (arrayList.get(position).getStatus().equalsIgnoreCase("Success")){
            holder.txtStatus.setText(arrayList.get(position).getStatus());
            holder.txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        }else {
            holder.txtStatus.setText(arrayList.get(position).getStatus());
            holder.txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txtRechargeId, txtNumber, txtAmount, txtProviderName, txtTxnid, txtRemark, txtTotalBalance, txtStatus, txtDate;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtRechargeId = itemView.findViewById(R.id.txtRechargeId);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtProviderName = itemView.findViewById(R.id.txtProviderName);
            txtTxnid = itemView.findViewById(R.id.txtTxnid);
            txtRemark = itemView.findViewById(R.id.txtRemark);
            txtTotalBalance = itemView.findViewById(R.id.txtTotalBalance);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
