SwipeMenu for ListView & ExpandableListView
=================
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SwipeMenuListView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/912)

A SwipeMenu of ListView & ExpandableListView. Modified [baoyongzhang's SwipeMenuListView](https://github.com/baoyongzhang/SwipeMenuListView), add support for ExpandableListView

# Demo
<table>
    <tr>
        <th>List View</th>
        <th>Expandable List View with different menus</th>
    </tr>
    <tr>
        <td><img src="https://raw.githubusercontent.com/baoyongzhang/SwipeMenuListView/master/demo.gif" width="320" alt="Screenshot"/></td>
        <td><img src="https://raw.githubusercontent.com/tycallen/SwipeMenuListView/master/swipe_expandable_list.gif" width="320" alt="Screenshot"/></td>
    </tr>
</table>

# Usage

### Step 1

* add SwipeMenuListView or SwipeMenuExpandableListView in layout xml

```xml
<com.baoyz.swipemenulistview.SwipeMenuListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
```xml
<com.baoyz.swipemenulistview.SwipeMenuExpandableListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
### Step 2

* create a SwipeMenuCreator to add items.

```java
SwipeMenuCreator creator = new SwipeMenuCreator() {

	@Override
	public void create(SwipeMenu menu) {
		// create "open" item
		SwipeMenuItem openItem = new SwipeMenuItem(
				getApplicationContext());
		// set item background
		openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
				0xCE)));
		// set item width
		openItem.setWidth(dp2px(90));
		// set item title
		openItem.setTitle("Open");
		// set item title fontsize
		openItem.setTitleSize(18);
		// set item title font color
		openItem.setTitleColor(Color.WHITE);
		// add to menu
		menu.addMenuItem(openItem);

		// create "delete" item
		SwipeMenuItem deleteItem = new SwipeMenuItem(
				getApplicationContext());
		// set item background
		deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
				0x3F, 0x25)));
		// set item width
		deleteItem.setWidth(dp2px(90));
		// set a icon
		deleteItem.setIcon(R.drawable.ic_delete);
		// add to menu
		menu.addMenuItem(deleteItem);
	}
};

// set creator
listView.setMenuCreator(creator);
```

* **For expandable** Create a SwipeMenuExpandableCreator to add items for group and child.

```java
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
```
### Step 3

* listener item click event

```java
listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	@Override
	public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
		switch (index) {
		case 0:
			// open
			break;
		case 1:
			// delete
			break;
		}
		// false : close the menu; true : not close the menu
		return false;
	}
});
```
**For expandable** *Notice that the group and child use the same onMenuClickListener, the difference is that when a group's menu was clicked, the param childPosition will be SwipeMenuExpandableListAdapter.GROUP_INDEX. Otherwise, it's the child index*
```java
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
```
### Control ExpandableListView's swipable
Use those two methods to control
```
@Override
    class AppAdapter extends BaseSwipeMenuExpandableListAdapter {
        public boolean isGroupSwipable(int groupPosition) {
            return true;
        }

        @Override
        public boolean isChildSwipable(int groupPosition, int childPosition) {
            return true;
        }
    }
```


### Create Different Menu

* Use the ViewType of adapter

```java
	class AppAdapter extends BaseAdapter {

		...
		
		@Override
		public int getViewTypeCount() {
			// menu type count
			return 2;
		}
		
		@Override
		public int getItemViewType(int position) {
			// current menu type
			return type;
		}

		...
	}
```

* Create different menus depending on the view type

```java
	SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// Create different menus depending on the view type
				switch (menu.getViewType()) {
				case 0:
					// create menu of type 0
					break;
				case 1:
					// create menu of type 1
					break;
				...
				}
			}

		};
```

* Demo

<p>

<img src="https://raw.githubusercontent.com/baoyongzhang/SwipeMenuListView/master/demo3.gif" width="320" alt="Screenshot"/>
</p>

### Other

* OnSwipeListener

```java
listView.setOnSwipeListener(new OnSwipeListener() {
			
	@Override
	public void onSwipeStart(int position) {
		// swipe start
	}
	
	@Override
	public void onSwipeEnd(int position) {
		// swipe end
	}
});
```

* open menu method for SwipeMenuListView

```java
listView.smoothOpenMenu(position);
```

* Open/Close Animation Interpolator

```java
// Close Interpolator
listView.setCloseInterpolator(new BounceInterpolator());
// Open Interpolator
listView.setOpenInterpolator(...);
```  
  

<p>
   <img src="demo2.gif" width="320" alt="Screenshot"/>
</p>
