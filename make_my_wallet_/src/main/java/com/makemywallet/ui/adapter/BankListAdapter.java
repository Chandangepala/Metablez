package com.makemywallet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makemywallet.R;
import com.makemywallet.ui.Models.BankListModel;
import com.makemywallet.ui.customClickListener.OnCustomClickListener;

import java.util.ArrayList;
import java.util.List;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.MyHolder> {
    private List<BankListModel> arrayList;
    Context mContext;
    OnCustomClickListener onCustomClickListener;

    public BankListAdapter(Context mContext, OnCustomClickListener onCustomClickListener, List<BankListModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.onCustomClickListener =onCustomClickListener;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_operator, parent, false);
        return new MyHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvTitleName.setText(arrayList.get(position).getBankName());
        holder.rLayoutHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomClickListener.onCustomClick(arrayList.get(position).getBankId(),arrayList.get(position).getBankName());
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTitleName;
        RelativeLayout rLayoutHead;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleName = itemView.findViewById(R.id.tvTitleName);
            rLayoutHead = itemView.findViewById(R.id.rLayoutHead);
        }
    }

    public void updateList(ArrayList<BankListModel> filterdNames) {
        this.arrayList = filterdNames;
        notifyDataSetChanged();
    }
}
