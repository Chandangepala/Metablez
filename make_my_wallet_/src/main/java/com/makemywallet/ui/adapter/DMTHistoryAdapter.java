package com.makemywallet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makemywallet.R;
import com.makemywallet.ui.Models.DMTHistoryModel;

import java.util.List;

public class DMTHistoryAdapter extends RecyclerView.Adapter<DMTHistoryAdapter.MyHolder> {
    private List<DMTHistoryModel> arrayList;
    Context mContext;

    public DMTHistoryAdapter(Context mContext, List<DMTHistoryModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_dmr_report, parent, false);
        return new MyHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.txtSerialNo.setText(arrayList.get(position).getOrderId());
        holder.txtAccount.setText(arrayList.get(position).getNumber());
        holder.txtAmount.setText(arrayList.get(position).getAmount());
       // holder.txtBranch.setText(arrayList.get(position).getProfit());
        holder.txtTxnid.setText(arrayList.get(position).getTxnid());
        holder.txtTotalBalance.setText(arrayList.get(position).getTotalBalance());
      //  holder.txtStatus.setText(arrayList.get(position).getStatus());
        holder.txtDate.setText(arrayList.get(position).getDate());
        holder.txtSurcharge.setText(arrayList.get(position).getSurcharge());
        holder.txtGST.setText(arrayList.get(position).getGst());
        holder.txtTDS.setText(arrayList.get(position).getTds());
        holder.txtCommission.setText("-");
        holder.txtBranch.setText("-");
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
        TextView txtSerialNo,txtAccount,txtBranch,txtAmount,txtStatus,txtDate,txtTxnid,txtTotalBalance,txtSurcharge,txtCommission,txtGST,txtTDS;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtSerialNo = itemView.findViewById(R.id.txtSerialNo);
            txtAccount = itemView.findViewById(R.id.txtAccount);
            txtBranch = itemView.findViewById(R.id.txtBranch);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTxnid = itemView.findViewById(R.id.txtTxnid);
            txtTotalBalance = itemView.findViewById(R.id.txtTotalBalance);
            txtSurcharge = itemView.findViewById(R.id.txtSurcharge);
            txtCommission = itemView.findViewById(R.id.txtCommission);
            txtGST = itemView.findViewById(R.id.txtGst);
            txtTDS = itemView.findViewById(R.id.txtTds);

        }
    }
}
