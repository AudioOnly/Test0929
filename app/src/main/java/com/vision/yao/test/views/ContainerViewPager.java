package com.vision.yao.test.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 容器ViewPager
 * Created by Hao on 2016/1/1.
 */
public class ContainerViewPager extends ViewPager {

    public ContainerViewPager(Context context) {
        super(context);
    }

    public ContainerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}