package com.vision.yao.test.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.vision.yao.test.R;

/**
 * 重置MaterialProgressBar细节
 * Created by xiezihao on 16/7/19.
 */
public class MaterialProgressBar extends me.zhanghai.android.materialprogressbar.MaterialProgressBar {
    public MaterialProgressBar(Context context) {
        super(context);
        init();
    }

    public MaterialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setProgressTintList(ContextCompat.getColorStateList(getContext(), R.color.progress));
//        ContextCompat.getColorStateList(getContext(),R.color.progress);
//        getResources().getColorStateList(R.color.progress,getContext().getTheme());
//        getContext().getResources().getColorStateList(R.color.progress,null));

    }
}
