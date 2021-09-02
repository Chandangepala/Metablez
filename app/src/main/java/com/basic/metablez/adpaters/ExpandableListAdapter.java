package com.basic.metablez.adpaters;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.basic.metablez.R;
import com.basic.metablez.models.ExpandedMenuModel;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    List<ExpandedMenuModel> mListDataHeader; //Header titles
    private HashMap<ExpandedMenuModel, List<String>> mListDataChild;
    ExpandableListView expandList;

    public ExpandableListAdapter(Context context, List<ExpandedMenuModel> mListDataHeader, HashMap<ExpandedMenuModel, List<String>> mListDataChild, ExpandableListView expandList) {
        this.context = context;
        this.mListDataHeader = mListDataHeader;
        this.mListDataChild = mListDataChild;
        this.expandList = expandList;
    }

    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;

        try {
            if(groupPosition != 2){
                childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).size();
            }
        }catch (Exception e){

        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandedMenuModel headerTitle = (ExpandedMenuModel) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listheader, null);
        }

        TextView lbListHeader = (TextView) convertView.findViewById(R.id.submenu);
        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
        ImageView indicatorIcon = (ImageView) convertView.findViewById(R.id.indicator_img);
        //lbListHeader.setTypeface(null, Typeface.BOLD);
        lbListHeader.setText(headerTitle.menuName);
        headerIcon.setImageResource(headerTitle.menuImage);

        if(getChildrenCount(groupPosition) == 0){
            indicatorIcon.setVisibility(View.GONE);
        }else {
            indicatorIcon.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final  String childText = (String) getChild(groupPosition, childPosition);

        if(convertView == null){
            LayoutInflater layoutInflater =  (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_submenu, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.submenu);

        txtListChild.setText(childText);

        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
