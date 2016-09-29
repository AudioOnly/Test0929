package com.vision.yao.test.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vision.yao.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 内容状态View
 * Created by xzh on 2016/1/4 0004.
 */
public class ContentStatusView extends FrameLayout {

    @BindView(R.id.v_progress)
    ProgressBar v_progress;
    @BindView(R.id.tv_empty)
    TextView tv_empty;

    @BindView(R.id.vg_failed)
    ViewGroup vg_failed;
    @BindView(R.id.tv_failed)
    TextView tv_failed;
    @BindView(R.id.v_failed)
    View v_failed;

    public ContentStatusView(Context context) {
        super(context);
        init();
    }

    public ContentStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContentStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_content_status, this, false);
        addView(view);
        ButterKnife.bind(this, view);
        progress();
    }

    /**
     * 设置失败点击监听
     *
     * @param l 监听
     */
    public void setOnFailedClickListener(OnClickListener l) {
        v_failed.setOnClickListener(l);
    }

    /**
     * 显示进度
     */
    public void progress() {
        setViewVisibility(VISIBLE, GONE, GONE);
    }

    /**
     * 失败
     *
     * @param text 失败消息
     */
    public void failed(String text) {
        setViewVisibility(GONE, GONE, VISIBLE);
        tv_failed.setText(text);
    }

    public boolean isFailed() {
        return vg_failed.getVisibility() == VISIBLE;
    }

    /**
     * 空
     *
     * @param text 空消息
     */
    public void empty(String text) {
        setViewVisibility(GONE, VISIBLE, GONE);
        tv_empty.setText(text);
    }

    /**
     * 成功之后，这个View将不会再显示
     */
    public void success() {
        setVisibility(GONE);
    }

    /**
     * 还原View初始显示状态
     */
    public void resetUI() {
        progress();
        setVisibility(View.VISIBLE);
    }

    /**
     * 设置显示
     *
     * @param progress 进度
     * @param empty    空
     * @param failed   失败
     */
    private void setViewVisibility(int progress, int empty, int failed) {
        v_progress.setVisibility(progress);
        tv_empty.setVisibility(empty);
        vg_failed.setVisibility(failed);
    }

    /**
     * 是否正在显示
     */
    public boolean isShow() {
        return getVisibility() == VISIBLE;
    }
}
