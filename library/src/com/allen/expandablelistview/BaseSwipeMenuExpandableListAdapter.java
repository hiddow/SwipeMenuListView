package com.allen.expandablelistview; 

import android.widget.BaseExpandableListAdapter;

/**
 * @author yuchentang A sub class of BaseExpandableListAdapter , add controll to
 *         swipable
 */
public abstract class BaseSwipeMenuExpandableListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "BaseSwipeMenuExpandableListAdapter";

    public abstract boolean isGroupSwipable(int groupPosition);

    public abstract boolean isChildSwipable(int groupPosition, int childPosition);
}
