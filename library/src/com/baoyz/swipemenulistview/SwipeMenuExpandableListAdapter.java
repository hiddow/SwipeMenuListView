package com.baoyz.swipemenulistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;

import com.baoyz.swipemenulistview.SwipeMenuExpandableListView.OnMenuItemClickListenerForExpandable;
import com.baoyz.swipemenulistview.SwipeMenuViewForExpandable.OnSwipeItemClickListenerForExpandable;

/**
 * 
 * @author yuchentang
 * 
 */
public class SwipeMenuExpandableListAdapter implements ExpandableListAdapter, OnSwipeItemClickListenerForExpandable {

    public static final int GROUP_INDEX = -1991;// when a group's swipe menu was
                                             // clicked, it fires an onclick
                                                // event which childPostion is
                                                // -1991

    private BaseSwipeMenuExpandableListAdapter mAdapter;
    private Context mContext;
    private OnMenuItemClickListenerForExpandable onMenuItemClickListener;

    public SwipeMenuExpandableListAdapter(Context context, BaseSwipeMenuExpandableListAdapter adapter) {
        mAdapter = adapter;
        mContext = context;
    }

    public void createGroupMenu(SwipeMenu menu) {

    }

    public void createChildMenu(SwipeMenu menu) {

    }

    @Override
    public void onItemClick(SwipeMenuViewForExpandable view, SwipeMenu menu, int index) {
        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onMenuItemClick(view.getGroupPosition(), view.getChildPostion(), menu, index);
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListenerForExpandable onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean hasStableIds() {
        return mAdapter.hasStableIds();
    }

    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    public BaseSwipeMenuExpandableListAdapter getWrappedAdapter() {
        return mAdapter;
    }

    @Override
    public int getGroupCount() {
        return mAdapter.getGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mAdapter.getChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mAdapter.getGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mAdapter.getChild(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mAdapter.getGroupId(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mAdapter.getChildId(groupPosition, childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (!mAdapter.isGroupSwipable(groupPosition))
            return mAdapter.getGroupView(groupPosition, isExpanded, convertView, parent);
        SwipeMenuLayout layout = null;
        if (convertView == null) {
            View contentView = mAdapter.getGroupView(groupPosition, isExpanded, convertView, parent);
            SwipeMenu menu = new SwipeMenu(mContext);
            menu.setViewType(mAdapter.getGroupType(groupPosition));
            createGroupMenu(menu);
            SwipeMenuViewForExpandable menuView = new SwipeMenuViewForExpandable(menu,
                    (SwipeMenuExpandableListView) parent, groupPosition, GROUP_INDEX);
            menuView.setOnSwipeItemClickListenerForExpandable(this);
            SwipeMenuExpandableListView listView = (SwipeMenuExpandableListView) parent;
            layout = new SwipeMenuLayout(contentView, menuView, listView.getCloseInterpolator(),
                    listView.getOpenInterpolator());
            layout.setPosition(groupPosition);
        } else {
            layout = (SwipeMenuLayout) convertView;
            layout.closeMenu();
            layout.setPosition(groupPosition);
            View view = mAdapter.getGroupView(groupPosition, isExpanded, convertView, parent);
        }
        return layout;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
            ViewGroup parent) {
        if (!mAdapter.isChildSwipable(groupPosition, childPosition)) {
            return mAdapter.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        }
        SwipeMenuLayout layout = null;
        if (convertView == null) {
            View contentView = mAdapter.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
            SwipeMenu menu = new SwipeMenu(mContext);
            menu.setViewType(mAdapter.getChildType(groupPosition, childPosition));
            createChildMenu(menu);
            SwipeMenuViewForExpandable menuView = new SwipeMenuViewForExpandable(menu,
                    (SwipeMenuExpandableListView) parent, groupPosition, childPosition);
            menuView.setOnSwipeItemClickListenerForExpandable(this);
            Log.i("ChildViewType", mAdapter.getChildType(groupPosition, childPosition) + "");
            SwipeMenuExpandableListView listView = (SwipeMenuExpandableListView) parent;
            layout = new SwipeMenuLayout(contentView, menuView, listView.getCloseInterpolator(),
                    listView.getOpenInterpolator());
            layout.setPosition(groupPosition);
        } else {
            layout = (SwipeMenuLayout) convertView;
            layout.closeMenu();
            layout.setPosition(groupPosition);
            View view = mAdapter.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        }
        return layout;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return mAdapter.isChildSelectable(groupPosition, childPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        mAdapter.onGroupExpanded(groupPosition);
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        mAdapter.onGroupCollapsed(groupPosition);
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return mAdapter.getCombinedChildId(groupId, childId);
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return mAdapter.getCombinedGroupId(groupId);
    }

}
