package com.vision.yao.test.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.vision.yao.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 标题控件
 * Created by xiezihao on 16/6/21.
 */
public class TitleView extends FrameLayout {

    //标题部分
    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.v_divider)
    View v_divider;

    public TitleView(Context context) {
        super(context);
        init();
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        boolean isAlpha = getTag() != null && Boolean.parseBoolean(getTag().toString());
        if (isAlpha) { //如果是透明渐变效果,背景色不能和不渐变时引用一个资源对象,alpha时好像会改变这个色值导致其它标题栏一起变色
            setBackgroundColor(Color.parseColor("#fbfbfb"));
        } else {//父容器设置透明状态栏填充的背景色
            setBackgroundResource(R.color.title_bg);
        }

        //添加标题部分并去除标题背景色
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_title, this, false);
        view.setBackgroundResource(android.R.color.transparent);
        addView(view);

        ButterKnife.bind(this);
    }

    /**
     * 透明标题背景
     */
    public TitleView transBg() {
        setBackgroundResource(android.R.color.transparent);
        return this;
    }

    /**
     * 渐变透明:0-1
     */
    public TitleView alpha(float alpha) {
        //透明背景
        getBackground().setAlpha((int) (alpha * 255));
        //透明标题和线
        tv_title.setAlpha(alpha);
        v_divider.setAlpha(alpha);
        return this;
    }

    /**
     * 设置左边
     *
     * @param left 资源图片
     * @param cl   点击事件
     * @return this
     */
    public TitleView setLeft(int left, OnClickListener cl) {
        iv_left.setImageResource(left);
        iv_left.setOnClickListener(cl);
        return this;
    }

    /**
     * 设置左边返回
     *
     * @return this
     */
    public TitleView setLeftBack(Activity act) {
        iv_left.setImageResource(R.drawable.ic_title_back);
        iv_left.setOnClickListener(v -> act.onBackPressed());
        return this;
    }

    /**
     * 没有左边
     *
     * @return this
     */
    public TitleView setLeftNone() {
        iv_left.setVisibility(View.INVISIBLE);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     * @return this
     */
    public TitleView setTitle(int title) {
        tv_title.setText(title);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     * @return this
     */
    public TitleView setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    /**
     * 设置没有标题
     *
     * @return
     */
    public TitleView setTitleNone() {
        tv_title.setVisibility(View.INVISIBLE);
        return this;
    }

    /**
     * 设置标题颜色
     */
    public TitleView setTitleColor(String color) {
        tv_title.setTextColor(Color.parseColor(color));
        return this;
    }

    /**
     * 设置右边IV
     *
     * @param right 资源图片
     * @param cl    点击事件
     * @return this
     */
    public TitleView setRightIv(int right, OnClickListener cl) {
        iv_right.setImageResource(right);
        iv_right.setOnClickListener(cl);
        return this;
    }

    /**
     * 没有右边IV
     *
     * @return this
     */
    public TitleView setRightIvNone() {
        iv_right.setVisibility(View.INVISIBLE);
        return this;
    }

    /**
     * 设置右边TV
     *
     * @param right 右边
     * @param cl    点击事件
     * @return this
     */
    public TitleView setRightTv(int right, OnClickListener cl) {
        tv_right.setText(right);
        tv_right.setOnClickListener(cl);
        return this;
    }

    /**
     * 右边TV不显示
     *
     * @return this
     */
    public TitleView setRightTvNone() {
        tv_right.setVisibility(View.INVISIBLE);
        return this;
    }

}
