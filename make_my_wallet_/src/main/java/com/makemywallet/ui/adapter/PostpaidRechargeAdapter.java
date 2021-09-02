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
import com.makemywallet.ui.Models.PostpaidModel;
import com.makemywallet.ui.customClickListener.OnCustomThreeClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostpaidRechargeAdapter extends RecyclerView.Adapter<PostpaidRechargeAdapter.MyHolder> {
    private List<PostpaidModel> arrayList;
    Context mContext;
    OnCustomThreeClickListener onCustomClickListener;

    public PostpaidRechargeAdapter(Context mContext, OnCustomThreeClickListener onCustomClickListener, List<PostpaidModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.onCustomClickListener =onCustomClickListener;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_postoperator, parent, false);
        return new MyHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvTitleName.setText(arrayList.get(position).getBillerName());
        /*Glide.with(mContext).load(arrayList.get(position).getLogo())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivUserImg);*/
        holder.rLayoutHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomClickListener.onCustomThreeClick(String.valueOf(arrayList.get(position).getBillerId()),arrayList.get(position).getBillerName(),"");
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
        CircleImageView ivUserImg;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleName = itemView.findViewById(R.id.tvTitleName);
            rLayoutHead = itemView.findViewById(R.id.rLayoutHead);
            ivUserImg = itemView.findViewById(R.id.ivUserImg);
        }
    }
}
