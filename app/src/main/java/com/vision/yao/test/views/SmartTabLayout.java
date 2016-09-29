package com.vision.yao.test.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 继承SmartTabLayout,修改一些东西
 * Created by owner on 2016/4/1.
 */
public class SmartTabLayout extends com.ogaclejapan.smarttablayout.SmartTabLayout {

    public SmartTabLayout(Context context) {
        super(context);
    }

    public SmartTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected TextView createDefaultTabView(CharSequence title) {
        TextView defaultTabView = super.createDefaultTabView(title);
        //去除默认tab的粗体
        defaultTabView.setTypeface(Typeface.DEFAULT);
        return defaultTabView;
    }
}
