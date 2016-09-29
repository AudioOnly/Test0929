package com.vision.yao.test.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vision.yao.test.R;
import com.vision.yao.test.adapters.BaseRecyclerViewAdapter;
import com.vision.yao.test.http.HDAPI;
import com.vision.yao.test.utils.PagesHelper;
import com.vision.yao.test.views.ContentStatusView;
import com.vision.yao.test.views.PageLoadingView;
import com.vision.yao.test.views.SwipeRefreshLayout;
import com.vision.yao.test.views.TitleView;

import butterknife.BindView;

/**
 * Created by Magic on 16/9/8.
 */
public class Test1Fragment extends BaseFragment {

    public static final String EXTRA_TITLE="title";

    @BindView(R.id.v_fitsSystemWindows)
    TitleView tv_title;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.rl)
    RecyclerView rl;
    @BindView(R.id.csv)
    ContentStatusView csv;

    private PagesHelper mPagesHelper;
    private HeaderViewHolder hvh;

    private boolean isPrepared=false;


    @Override
    protected int getLayoutID() {
        return R.layout.fragment_test1;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        isPrepared=true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared||!isVisible||isLoad){
            return;
        }
        tv_title.setTitle(getArguments().getString(EXTRA_TITLE));
        rl.setHasFixedSize(true);
        rl.setLayoutManager(new LinearLayoutManager(getActivity()));
        rl.setItemAnimator(null);
        hvh=new HeaderViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_test1_header,rl,false));
        mAdapter.setHeader(hvh.itemView);
        rl.setAdapter(mAdapter);
        //分页
        mPagesHelper=new PagesHelper(csv, new PageLoadingView(getActivity()), srl, page -> Test1Fragment.this.loadData(page), () -> mAdapter.resetUI());
        isLoad=true;
    }


    private void loadData(int page){
        rxDestroy(HDAPI.getPages(page)).subscribe(strings -> {
            mPagesHelper.loadingSuccess(page, strings, plv -> mAdapter.setFooter(plv), new PagesHelper.SetDataListener() {
                @Override
                public void setData() {
                    mAdapter.addAll(strings);
                    mAdapter.notifyDataSetChanged();
                }
            });

        },throwable -> {
            mPagesHelper.loadingFailed(page,"网络异常，加载失败");
        });
    }
    class HeaderViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder{
        @BindView(R.id.fragment_test1_tv)
        TextView tv;

        public HeaderViewHolder(View view) {
            super(view);
        }
    }







    private BaseRecyclerViewAdapter<String ,ViewHolder> mAdapter= new BaseRecyclerViewAdapter<String, ViewHolder>(android.R.layout.test_list_item) {
        @Override
        protected BaseViewHolder getViewHolder(View root, int index) {
            return new ViewHolder(root);
        }

        @Override
        protected void bindView(ViewHolder holder, String obj, int position, int index) {

            holder.tv.setText(obj);
        }

        @Override
        protected void onFooterShow() {
            super.onFooterShow();
            mPagesHelper.loadingNext();
        }
    };


    class ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder{
        @BindView(android.R.id.text1)
        TextView tv;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
