package com.baoyz.swipemenulistview;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class BaseSwipeListAdapter extends BaseAdapter {
    private static final String TAG = "BaseSwipeAdapter";

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewAndReusable(position, convertView, parent).view;
    }

    public abstract ContentViewWrapper getViewAndReusable(int position, View convertView, ViewGroup parent);
}
