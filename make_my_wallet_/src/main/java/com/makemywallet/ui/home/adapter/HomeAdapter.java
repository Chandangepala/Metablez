package com.makemywallet.ui.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.makemywallet.R;
import com.makemywallet.ui.Models.BannerModel;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {
    ArrayList<BannerModel> arrayListBanner = new ArrayList<>();
    Activity mActivity;

    public HomeAdapter(Activity activity, ArrayList<BannerModel> arrayListBanner) {
        this.mActivity = activity;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.row_item_home, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (position==0){
           // holder.tv.setText(String.valueOf(position));
            holder.rLayoutFirst.setVisibility(View.VISIBLE);
            holder.rLayoutSecond.setVisibility(View.GONE);
        }else {
           // holder.tv.setText(String.valueOf(position));
            holder.rLayoutFirst.setVisibility(View.GONE);
            holder.rLayoutSecond.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayListBanner.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        RelativeLayout rLayoutFirst,rLayoutSecond;
        TextView tv;
        public Holder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvPosition);
            rLayoutFirst = itemView.findViewById(R.id.rLayoutFirst);
            rLayoutSecond = itemView.findViewById(R.id.rLayoutSecond);
        }
    }
}

