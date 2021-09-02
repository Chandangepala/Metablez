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
import com.makemywallet.ui.Models.CircleModel;
import com.makemywallet.ui.customClickListener.OnCustomClickListener;
import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.MyHolder> {
    private List<CircleModel> arrayList;
    Context mContext;
    OnCustomClickListener onCustomClickListener;

    public CircleAdapter(Context mContext, OnCustomClickListener onCustomClickListener, List<CircleModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.onCustomClickListener =onCustomClickListener;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_circle, parent, false);
        return new MyHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvTitleName.setText(arrayList.get(position).getState());

        holder.rLayoutHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCustomClickListener.onCustomClick(String.valueOf(arrayList.get(position).getId()),arrayList.get(position).getState());
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
}
