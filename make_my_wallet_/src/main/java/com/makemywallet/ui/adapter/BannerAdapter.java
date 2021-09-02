package com.makemywallet.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makemywallet.R;
import com.makemywallet.ui.Models.BannerModel;

import java.util.ArrayList;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.Holder> {
    ArrayList<BannerModel> arrayListBanner = new ArrayList<>();
    Activity mActivity;

    public BannerAdapter(Activity activity, ArrayList<BannerModel> arrayListBanner) {
        this.mActivity = activity;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_row_banner, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
       // holder.tv.setText(arrayListBanner.get(position).getBannerName());

        Glide.with(mActivity).load(arrayListBanner.get(position).getBannerName())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivBanner);
    }

    @Override
    public int getItemCount() {
        return arrayListBanner.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        RelativeLayout lLayBanner;
        ImageView ivBanner;
        TextView tv;

        public Holder(View itemView) {
            super(itemView);
            lLayBanner = itemView.findViewById(R.id.lLayBanner);
            lLayBanner.setVisibility(View.VISIBLE);
            ivBanner = itemView.findViewById(R.id.ivBanner);

        }
    }
}

