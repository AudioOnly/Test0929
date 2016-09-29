package com.vision.yao.test.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.vision.yao.test.R;

/**
 * 下拉刷新组件
 * Created by xiezihao on 16/6/22.
 */
public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {

    public SwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.progress));
    }
}
