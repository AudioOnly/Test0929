package com.vision.yao.test.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.vision.yao.test.R;
import com.vision.yao.test.utils.SP;

/**
 * 验证码按钮
 *
 * @author xzh
 */
public class CodeButton extends TextView {

    /**
     * 验证码时间，秒
     */
    private final int TIME = 60;

    private int mLeaveTime; // 剩余的时间
    private String mDefaultText; // 默认文本
    private int mFormatterId; // 剩余时间显示格式资源
    private SP mSP = new SP("code"); // 当前按钮保存数据的SP
    private String mTag;

    public CodeButton(Context context) {
        super(context);
        init();
    }

    public CodeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDefaultText = getText().toString(); // 再次显示文本
    }

    /**
     * 初始化验证码按钮
     *
     * @param tag 标识区别
     */
    public void init(String tag) {
        mFormatterId = R.string.code_time_fmt; // 缓存显示格式
        mTag = tag;
        update();
    }

    /**
     * 开始计时
     */
    public void start() {
        if (mSP == null)
            throw new NullPointerException("是否初始化？");
        mSP.save(mTag, System.currentTimeMillis()); // 缓存获取验证码的时间
        update();
    }

    /**
     * 刷新当前验证码状态
     */
    private void update() {
        long last = mSP.load(mTag, 0L); // 获取上次获取的时间
        int leave = (int) (TIME - (Math.abs(System.currentTimeMillis() - last) / 1000)); // 获取剩余的时间
        if (leave > 0) { // 如果剩余时间超过0秒
            setEnabled(false); // 禁用
            mLeaveTime = leave; // 缓存剩余的时间
            setText(getContext().getString(mFormatterId, mLeaveTime)); // 默认显示当前剩余时间
            postDelayed(mTimeRunnable, 1000);
        }
    }

    /**
     * 计时的Runnable
     */
    private Runnable mTimeRunnable = new Runnable() {

        @Override
        public void run() {
            if (--mLeaveTime > 0) { // 还存在时间，再次计时
                setText(getContext().getString(mFormatterId, mLeaveTime)); // 显示当前剩余时间
                postDelayed(mTimeRunnable, 1000);
            } else {// 不存在计时了
                setText(mDefaultText);
                setEnabled(true); // 启用
            }
        }
    };
}
