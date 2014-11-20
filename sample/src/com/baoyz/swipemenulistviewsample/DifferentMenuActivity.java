package com.baoyz.swipemenulistviewsample;

import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.BaseSwipeMenuExpandableListAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuExpandableCreator;
import com.baoyz.swipemenulistview.SwipeMenuExpandableListAdapter;
import com.baoyz.swipemenulistview.SwipeMenuExpandableListView;
import com.baoyz.swipemenulistview.SwipeMenuExpandableListView.OnMenuItemClickListenerForExpandable;
import com.baoyz.swipemenulistview.SwipeMenuItem;

public class DifferentMenuActivity extends Activity {

    private List<ApplicationInfo> mAppList;
    private AppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mAppList = getPackageManager().getInstalledApplications(0);

        SwipeMenuExpandableListView listView = (SwipeMenuExpandableListView) findViewById(R.id.listView);
        mAdapter = new AppAdapter();
        listView.setAdapter(mAdapter);

        // step 1. create a SwipeMenuExpandableCreator
        SwipeMenuExpandableCreator creator = new SwipeMenuExpandableCreator() {
            @Override
            public void createGroup(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                    case 2:
                        createMenu3(menu);
                        break;
                }
            }

            @Override
            public void createChild(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {
                    case 0:
                        createMenu1(menu);
                        break;
                    case 1:
                        createMenu2(menu);
                        break;
                    case 2:
                        createMenu3(menu);
                        break;
                }
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18, 0x5E)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.ic_action_favorite);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.drawable.ic_action_good);
                menu.addMenuItem(item2);
            }

            private void createMenu2(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0, 0x3F)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.ic_action_important);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.drawable.ic_action_discard);
                menu.addMenuItem(item2);
            }

            private void createMenu3(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1, 0xF5)));
                item1.setWidth(dp2px(90));
                item1.setIcon(R.drawable.ic_action_about);
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                item2.setWidth(dp2px(90));
                item2.setIcon(R.drawable.ic_action_share);
                menu.addMenuItem(item2);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new OnMenuItemClickListenerForExpandable() {
            @Override
            public boolean onMenuItemClick(int groupPostion, int childPostion, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(groupPostion);
                // childPostion == -1991 means it was the group's swipe menu was
                // clicked
                String s = "Group " + groupPostion;
                if (childPostion != SwipeMenuExpandableListAdapter.GROUP_INDEX) {
                    s += " , child " + childPostion;
                }
                s += " , menu index:" + index + " was clicked";
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        // delete(item);
                        mAppList.remove(groupPostion);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                Toast.makeText(DifferentMenuActivity.this, s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    class AppAdapter extends BaseSwipeMenuExpandableListAdapter {

        @Override
        public int getChildType(int groupPosition, int childPosition) {
            return childPosition % 3;
        }

        @Override
        public int getChildTypeCount() {
            return 3;
        }

        @Override
        public int getGroupType(int groupPosition) {
            return groupPosition % 3;
        }

        @Override
        public int getGroupTypeCount() {
            return 3;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }

        @Override
        public int getGroupCount() {
            return mAppList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 3;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mAppList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return "The " + childPosition + "'th child in " + groupPosition + "'th group.";
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
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_list_app, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (null == holder) {
                holder = new ViewHolder(convertView);
            }
            ApplicationInfo item = (ApplicationInfo) getGroup(groupPosition);
            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.tv_name.setText(item.loadLabel(getPackageManager()));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_list_app, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (null == holder) {
                holder = new ViewHolder(convertView);
            }
            ApplicationInfo item = (ApplicationInfo) getGroup(groupPosition);
            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.tv_name.setText("this is child");
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public boolean isGroupSwipable(int groupPosition) {
            return true;
        }

        @Override
        public boolean isChildSwipable(int groupPosition, int childPosition) {
            return true;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
