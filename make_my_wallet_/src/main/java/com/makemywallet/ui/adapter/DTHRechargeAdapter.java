package com.makemywallet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makemywallet.R;
import com.makemywallet.ui.Models.PrepaidResponseModel;
import com.makemywallet.ui.customClickListener.OnCustomThreeClickListener;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class DTHRechargeAdapter extends RecyclerView.Adapter<DTHRechargeAdapter.MyHolder> {
    private List<PrepaidResponseModel> arrayList;
    Context mContext;
    OnCustomThreeClickListener customThreeClickListener;

    public DTHRechargeAdapter(Context mContext, OnCustomThreeClickListener customThreeClickListener, List<PrepaidResponseModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.customThreeClickListener =customThreeClickListener;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_operator, parent, false);
        return new MyHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvTitleName.setText(arrayList.get(position).getProviderName());
        Glide.with(mContext).load(arrayList.get(position).getLogo())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivUserImg);
        holder.rLayoutHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customThreeClickListener.onCustomThreeClick(String.valueOf(arrayList.get(position).getId()),arrayList.get(position).getProviderName(),arrayList.get(position).getLogo());
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
