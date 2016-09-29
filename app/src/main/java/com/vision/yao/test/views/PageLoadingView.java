package com.vision.yao.test.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vision.yao.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分页加载显示View，这个View要加到RecyclerView中必须用RelativeLayout，不然填不满Item
 * Created by Hao on 2016/1/4.
 */
public class PageLoadingView extends RelativeLayout {

    @BindView(R.id.v_progress)
    View v_progress;
    @BindView(R.id.tv_empty)
    TextView tv_empty;
    @BindView(R.id.tv_failed)
    TextView tv_failed;

    public PageLoadingView(Context context) {
        super(context);
        init();
    }

    public PageLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_page_loading, this, false);
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
        tv_failed.setOnClickListener(l);
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
     * 是否是空了
     *
     * @return true 空 false不是空
     */
    public boolean isEmptyShow() {
        return tv_empty.getVisibility() == View.VISIBLE;
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
        tv_failed.setVisibility(failed);
    }
}
