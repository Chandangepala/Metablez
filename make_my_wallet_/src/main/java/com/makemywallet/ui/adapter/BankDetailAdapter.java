package com.makemywallet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.makemywallet.R;
import com.makemywallet.ui.Models.BankDetailDemoModel;
import java.util.List;

public class BankDetailAdapter extends RecyclerView.Adapter<BankDetailAdapter.MyHolder> {
    private List<BankDetailDemoModel> arrayList;
    Context mContext;

    public BankDetailAdapter(Context mContext,List<BankDetailDemoModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_bank_details, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvHolderName.setText(arrayList.get(position).getHolder_name());
        holder.txtBankName.setText(arrayList.get(position).getBankName());
        holder.txtBankAccount.setText(arrayList.get(position).getBankAccount());
        holder.txtIFSCCode.setText(arrayList.get(position).getBankIfsc());
        holder.txtBranch.setText(arrayList.get(position).getBankBranch());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvHolderName, txtBankName, txtBankAccount, txtIFSCCode,txtBranch;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvHolderName = itemView.findViewById(R.id.tvHolderName);
            txtBankName = itemView.findViewById(R.id.txtBankName);
            txtBankAccount = itemView.findViewById(R.id.txtBankAccount);
            txtIFSCCode = itemView.findViewById(R.id.txtIFSCCode);
            txtBranch = itemView.findViewById(R.id.txtBranch);
        }
    }
}
