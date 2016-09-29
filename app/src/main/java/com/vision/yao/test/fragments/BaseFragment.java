package com.vision.yao.test.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.vision.yao.test.R;
import com.vision.yao.test.activitys.BaseActivity;
import com.zhx.statusbar.StatusBarUtils;

import butterknife.ButterKnife;
import rx.Observable;

/**
 * 基础的Fragment
 * Created by xzh on 2016/1/4 0004.
 */
public abstract class BaseFragment extends RxFragment {
    //懒加载 界面可见
    protected boolean isVisible;
    //判断数据是否加载过，防止重复加载数据
    protected boolean isLoad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtils.translucentStatusBar(this, view.findViewById(R.id.v_fitsSystemWindows));
        ButterKnife.bind(this, view);
        init(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible=true;
            onVisible();
        }else{
            isVisible=false;
            onInvisible();
        }
    }


    protected void onVisible(){
        lazyLoad();
    }
    protected void onInvisible(){

    }


    /**
     * Rx生命周期
     */
    public <T> Observable<T> rxDestroy(Observable<T> observable) {
        return observable.compose(bindUntilEvent(FragmentEvent.DESTROY));
    }

    //=======================================

    /**
     * 获取Fragment布局ID
     *
     * @return 布局ID
     */
    protected abstract int getLayoutID();

    /**
     * 初始化完成，替代{@link #onViewCreated(View, Bundle)}
     */
    protected abstract void init(View view, Bundle savedInstanceState);

    //Toast====================================

    /**
     * Toast
     *
     * @param res 资源
     */
    public void toast(int res) {
        ((BaseActivity) getActivity()).toast(res);
    }

    /**
     * Toast
     *
     * @param text 文本
     */
    public void toast(String text) {
        ((BaseActivity) getActivity()).toast(text);
    }

    //进度框=============================

    /**
     * 显示进度框
     */
    public void showProgress() {
        ((BaseActivity) getActivity()).showProgress();
    }

    /**
     * 关闭进度框
     */
    public void dismissProgress() {
        ((BaseActivity) getActivity()).dismissProgress();
    }

    protected abstract void lazyLoad();
}
