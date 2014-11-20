package com.baoyz.swipemenulistview;


/**
 * 
 * @author yuchentang seperate the group and the child's menu creator
 * 
 */
public interface SwipeMenuExpandableCreator {

    void createGroup(SwipeMenu menu);

    void createChild(SwipeMenu menu);
}
