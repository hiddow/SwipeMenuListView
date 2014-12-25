package com.baoyz.swipemenulistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuView.OnSwipeItemClickListener;

/**
 * 
 * @author baoyz
 * @date 2014-8-24
 * 
 */
public class SwipeMenuAdapter implements WrapperListAdapter,
		OnSwipeItemClickListener {

    private BaseSwipeListAdapter mAdapter;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mList.smoothOpenMenu(msg.arg1);
                    break;
            }
            super.handleMessage(msg);
        }
    };
	private Context mContext;
    private OnMenuItemClickListener onMenuItemClickListener;
    private SwipeMenuListView mList;

    public SwipeMenuAdapter(Context context, BaseSwipeListAdapter adapter, SwipeMenuListView listView) {
		mAdapter = adapter;
		mContext = context;
        mList = listView;
	}

    public void notifyDataSetChanged(boolean ifKeepMenuOpen) {
        int i = -1;
        if (ifKeepMenuOpen) {
            i = mList.getOpenedPosition();
        }
        mAdapter.notifyDataSetChanged();
        Log.i("keep", "posi is:" + i);
        if (ifKeepMenuOpen && i >= 0) {
            Message m = new Message();
            m.what = 0;
            m.arg1 = i;
            mHandler.sendMessageDelayed(m, 0);
        }
    }

	@Override
	public int getCount() {
		return mAdapter.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return mAdapter.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        SwipeMenuLayout layout = null;
        View subConvert = null;
        if (convertView instanceof SwipeMenuLayout) {
            subConvert = ((SwipeMenuLayout) convertView).getContentView();
        }
        ContentViewWrapper contentViewWrapper = mAdapter.getViewAndReusable(position, subConvert, parent);
        if (convertView == null || !contentViewWrapper.ifReUsable) {
			SwipeMenu menu = new SwipeMenu(mContext);
			menu.setViewType(mAdapter.getItemViewType(position));
			createMenu(menu);
			SwipeMenuView menuView = new SwipeMenuView(menu,
					(SwipeMenuListView) parent);
			menuView.setOnSwipeItemClickListener(this);
			SwipeMenuListView listView = (SwipeMenuListView) parent;
            layout = new SwipeMenuLayout(contentViewWrapper.view, menuView,
					listView.getCloseInterpolator(),
                    listView.getOpenInterpolator(), listView.getmMenuStickTo());
			layout.setPosition(position);
		} else {
			layout = (SwipeMenuLayout) convertView;
			layout.closeMenu();
			layout.setPosition(position);
		}
		return layout;
	}

	public void createMenu(SwipeMenu menu) {
		// Test Code
		SwipeMenuItem item = new SwipeMenuItem(mContext);
		item.setTitle("Item 1");
		item.setBackground(new ColorDrawable(Color.GRAY));
		item.setWidth(300);
		menu.addMenuItem(item);

		item = new SwipeMenuItem(mContext);
		item.setTitle("Item 2");
		item.setBackground(new ColorDrawable(Color.RED));
		item.setWidth(300);
		menu.addMenuItem(item);
	}

	@Override
	public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
		if (onMenuItemClickListener != null) {
			onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu,
					index);
		}
	}

	public void setOnMenuItemClickListener(
			OnMenuItemClickListener onMenuItemClickListener) {
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
	public boolean isEnabled(int position) {
		return mAdapter.isEnabled(position);
	}

	@Override
	public boolean hasStableIds() {
		return mAdapter.hasStableIds();
	}

	@Override
	public int getItemViewType(int position) {
		return mAdapter.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return mAdapter.getViewTypeCount();
	}

	@Override
	public boolean isEmpty() {
		return mAdapter.isEmpty();
	}

	@Override
	public ListAdapter getWrappedAdapter() {
		return mAdapter;
	}

}
