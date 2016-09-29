package com.vision.yao.test.views;

import android.content.Context;
import android.util.AttributeSet;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * 联动控件
 * Created by xiezihao on 16/8/16.
 */
public class ScrollableLayout extends com.scrollablelayout.ScrollableLayout {

    public ScrollableLayout(Context context) {
        super(context);
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 滑动到底部
     */
    public void scrollToBottom() {
        Observable.timer(10, TimeUnit.MILLISECONDS).subscribe(l -> {
            if (getMaxY() <= 0) { //没有获取到MaxY
                scrollToBottom();
            } else {
                scrollTo(0, getMaxY());
            }
        });
    }
}
