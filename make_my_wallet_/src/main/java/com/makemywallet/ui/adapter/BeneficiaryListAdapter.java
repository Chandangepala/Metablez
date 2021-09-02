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
import com.makemywallet.ui.Models.BeneficiaryListModel;
import com.makemywallet.ui.customClickListener.OnCustomSingleClickListener;
import com.makemywallet.ui.customClickListener.OnCustomFourClickListener;
import java.util.List;

public class BeneficiaryListAdapter extends RecyclerView.Adapter<BeneficiaryListAdapter.MyHolder> {
    private List<BeneficiaryListModel> arrayList;
    Context mContext;
    OnCustomSingleClickListener onCustomSingleClickListener;
    OnCustomFourClickListener onCustomFourClickListener;

    public BeneficiaryListAdapter(Context mContext, OnCustomFourClickListener onCustomFourClickListener, OnCustomSingleClickListener onCustomSingleClickListener, List<BeneficiaryListModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.onCustomSingleClickListener = onCustomSingleClickListener;
        this.onCustomFourClickListener = onCustomFourClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_beneficiary, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvName.setText(arrayList.get(position).getRecipientName());
        holder.txtBankName.setText(arrayList.get(position).getBank());
        holder.txtBankAccount.setText(arrayList.get(position).getAccount());
        holder.txtIFSCCode.setText(arrayList.get(position).getIfsc());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomSingleClickListener.onCustomSingleClick(arrayList.get(position).getBeneficiaryId());
            }
        });
        holder.btnTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomFourClickListener.onCustomFourClick(arrayList.get(position).getBeneficiaryId(),arrayList.get(position).getRecipientName(), arrayList.get(position).getAccount(), arrayList.get(position).getIfsc());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvName, txtBankName, txtBankAccount, txtIFSCCode;
        ImageView ivDelete;
        Button btnTransfer;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            txtBankName = itemView.findViewById(R.id.txtBankName);
            txtBankAccount = itemView.findViewById(R.id.txtBankAccount);
            txtIFSCCode = itemView.findViewById(R.id.txtIFSCCode);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            btnTransfer = itemView.findViewById(R.id.btnTransfer);
        }
    }
}
