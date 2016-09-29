package com.vision.yao.test.utils;

import android.support.v4.widget.SwipeRefreshLayout;

import com.vision.yao.test.R;
import com.vision.yao.test.views.ContentStatusView;
import com.vision.yao.test.views.PageLoadingView;

import java.util.List;

import static com.vision.yao.test.TestApplication.AppCtx;
import static com.vision.yao.test.http.HDAPI.PAGE_DEFAULT;
import static com.vision.yao.test.http.HDAPI.PAGE_SIZE;

/**
 * 分页工具类:SwipeRefreshLayout+ContentStatusView+PageLoadingView
 * Created by xiezihao on 16/6/30.
 */
public class PagesHelper {

    private ContentStatusView csv;
    private PageLoadingView plv;
    private SwipeRefreshLayout srl;
    private LoadingListener loadingListener;
    private ClearDataListener clearDataListener;

    //当前页标
    private int mCurrentPage;
    //是否加载中
    private boolean isLoading = false;

    public PagesHelper(ContentStatusView csv, PageLoadingView plv, SwipeRefreshLayout srl
            , LoadingListener loadingListener, ClearDataListener clearDataListener) {
        this(csv, plv, srl, loadingListener, clearDataListener, true);
    }

    public PagesHelper(ContentStatusView csv, PageLoadingView plv, SwipeRefreshLayout srl
            , LoadingListener loadingListener, ClearDataListener clearDataListener, boolean autoLoading) {
        this.csv = csv;
        this.plv = plv;
        this.srl = srl;
        this.loadingListener = loadingListener;
        this.clearDataListener = clearDataListener;

        //设置失败监听,这里只可能加载第一页数据
        csv.setOnFailedClickListener(v -> {
            csv.resetUI();
            loading(PAGE_DEFAULT);
        });
        //上拉失败点击监听,还是加载下一页数据
        plv.setOnFailedClickListener(v -> loadingNext());
        //刷新操作监听
        if (srl != null) {
            srl.setOnRefreshListener(this::refresh);
        }

        clearDataListener.clearData();
        if (autoLoading) { //自动加载数据
            loading(PAGE_DEFAULT);
        }
    }

    /**
     * 加载下一页
     */
    public void loadingNext() {
        if (plv.isEmptyShow()) { //没有数据了
            return;
        }
        plv.resetUI();
        loading(mCurrentPage + 1);
    }

    /**
     * 刷新
     */
    public void refresh() {
        if (srl != null) {
            srl.setRefreshing(true);
        }
        loading(PAGE_DEFAULT);
    }

    /**
     * 加载
     *
     * @param page 加载的页
     */
    private void loading(int page) {
        if (isLoading)
            return;
        isLoading = true;
        loadingListener.loading(page);
    }

    /**
     * 加载失败
     */
    public void loadingFailed(int page, String msg) {
        if (page == PAGE_DEFAULT) { //第一页加载失败,初始进入加载或下拉刷新,清空数据-上拉View
            clearDataListener.clearData();
            csv.resetUI();
            csv.failed(msg);
        } else { //上拉
            plv.failed(msg);
        }
        //固定恢复
        if (srl != null) {
            srl.setRefreshing(false);
            srl.setEnabled(false);
        }
        isLoading = false;
    }

    /**
     * 加载成功
     *
     * @param list 加载到的数据
     */
    public void loadingSuccess(int page, List list, SetFooterListener sfl, SetDataListener sdl) {
        if (page == PAGE_DEFAULT) { //第一页,清空列表
            clearDataListener.clearData();
            if (list.isEmpty()) { //第一页数据为空
                csv.resetUI();
                csv.empty(AppCtx.getString(R.string.csv_empty));
            } else { //第一页不为空
                csv.success();
                plv.resetUI();
                sfl.setFooter(plv);
            }
        }
        //不管第几页都根据最大条数来判断Footer是否为空
        if (list.size() < PAGE_SIZE) { //数据不足
            plv.empty(AppCtx.getString(R.string.plv_no_data));
        }
        //刷新数据显示
        sdl.setData();
        //固定恢复和设置
        mCurrentPage = page;
        if (srl != null) {
            srl.setRefreshing(false);
            srl.setEnabled(true);
        }
        isLoading = false;
    }

    //============

    /**
     * 操作监听
     */
    public interface LoadingListener {
        void loading(int page);
    }

    /**
     * 清空列表数据
     */
    public interface ClearDataListener {
        void clearData();
    }

    /**
     * 设置显示列表数据
     */
    public interface SetDataListener {
        void setData();
    }

    /**
     * 设置分页Footer
     */
    public interface SetFooterListener {
        void setFooter(PageLoadingView plv);
    }
}
