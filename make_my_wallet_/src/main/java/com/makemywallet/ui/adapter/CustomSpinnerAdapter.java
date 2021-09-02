package com.makemywallet.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makemywallet.R;
import com.makemywallet.ui.Models.PrepaidResponseModel;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<PrepaidResponseModel> arrayList = new ArrayList<>();
    LayoutInflater inflter;

    public CustomSpinnerAdapter(Context mContext, ArrayList<PrepaidResponseModel> arrayList) {
        this.context = mContext;
        this.arrayList = arrayList;
        inflter = (LayoutInflater.from(mContext));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public PrepaidResponseModel getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_row_mode_type, null);
        TextView tvTitleName = (TextView) view.findViewById(R.id.tvTitleName);
        tvTitleName.setText(arrayList.get(i).getProviderName());
        return view;
    }
}
