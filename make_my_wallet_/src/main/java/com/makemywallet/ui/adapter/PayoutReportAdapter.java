package com.makemywallet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.makemywallet.R;
import com.makemywallet.ui.Models.PayoutReportModel;
import java.util.List;

public class PayoutReportAdapter extends RecyclerView.Adapter<PayoutReportAdapter.MyHolder> {
    private List<PayoutReportModel> arrayList;
    Context mContext;

    public PayoutReportAdapter(Context mContext, List<PayoutReportModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_payout_report, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.txtSerialNo.setText(arrayList.get(position).getOrderId());
        holder.txtNumber.setText(arrayList.get(position).getNumber());
        holder.txtAmount.setText(arrayList.get(position).getAmount());
        holder.txtProviderName.setText(arrayList.get(position).getProviderName());
        holder.txtTxnid.setText(arrayList.get(position).getTxnid());
        holder.txtSurcharge.setText(arrayList.get(position).getSurcharge());
        holder.txtTotalBalance.setText(arrayList.get(position).getTotalBalance());
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
        TextView txtSerialNo, txtNumber, txtAmount, txtProviderName, txtTxnid, txtSurcharge, txtTotalBalance, txtStatus, txtDate;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txtSerialNo = itemView.findViewById(R.id.txtSerialNo);
            txtNumber = itemView.findViewById(R.id.txtAmount);
            txtAmount = itemView.findViewById(R.id.txtAccount);
            txtProviderName = itemView.findViewById(R.id.txtProviderName);
            txtTxnid = itemView.findViewById(R.id.txtTxnid);
            txtSurcharge = itemView.findViewById(R.id.txtSurcharge);
            txtTotalBalance = itemView.findViewById(R.id.txtTotalBalance);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
