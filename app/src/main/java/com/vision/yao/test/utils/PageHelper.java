package com.vision.yao.test.utils;

import android.support.v4.widget.SwipeRefreshLayout;

import com.vision.yao.test.views.ContentStatusView;

/**
 * 下拉刷新工具类:SwipeRefreshLayout+ContentStatusView
 * Created by xiezihao on 16/7/1.
 */
public class PageHelper {

    private ContentStatusView csv;
    private SwipeRefreshLayout srl;
    private LoadingListener loadingListener;
    private HiddenListener hiddenListener;

    public PageHelper(ContentStatusView csv, SwipeRefreshLayout srl, LoadingListener loadingListener, HiddenListener hiddenListener) {
        this.csv = csv;
        this.srl = srl;
        this.loadingListener = loadingListener;
        this.hiddenListener = hiddenListener;

        csv.setOnFailedClickListener(v -> {
            csv.resetUI();
            loadingListener.loading();
        });
        if (srl != null) {
            srl.setOnRefreshListener(this::refresh);
        }

        //清空UI后加载数据
        hiddenListener.clear();
        loadingListener.loading();
    }

    public void refresh() {
        if (srl != null) {
            srl.setRefreshing(true);
        }
        loadingListener.loading();
    }

    public void loadingFailed(String msg) {
        hiddenListener.clear();
        //显示错误
        csv.resetUI();
        csv.failed(msg);
        if (srl != null) {
            srl.setRefreshing(false);
            srl.setEnabled(false);
        }
    }

    public void loadingSuccess(DisplayListener displayListener) {
        displayListener.display();
        csv.success();
        if (srl != null) {
            srl.setRefreshing(false);
            srl.setEnabled(true);
        }
    }

    //==============

    /**
     * 加载数据监听
     */
    public interface LoadingListener {
        void loading();
    }

    /**
     * 隐藏内容监听
     */
    public interface HiddenListener {
        void clear();
    }

    /**
     * 显示内容监听
     */
    public interface DisplayListener {
        void display();
    }
}
