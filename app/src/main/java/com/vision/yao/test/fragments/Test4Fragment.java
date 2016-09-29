package com.vision.yao.test.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vision.yao.test.R;
import com.vision.yao.test.views.ContentStatusView;
import com.vision.yao.test.views.PageLoadingView;
import com.vision.yao.test.views.SwipeRefreshLayout;
import com.vision.yao.test.views.TitleView;

import butterknife.BindView;

/**
 * Created by Magic on 16/9/12.
 */
public class Test4Fragment extends BaseFragment {


    @BindView(R.id.v_fitsSystemWindows)
    TitleView tv_title;
    @BindView(R.id.csv)
    ContentStatusView csv;
    @BindView(R.id.rcv)
    RecyclerView rcv;
    @BindView(R.id.srl)
    SwipeRefreshLayout rl;

    private PageLoadingView plv;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_test4;
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        tv_title.setTitle(getArguments().getString(Test1Fragment.EXTRA_TITLE));
        plv=new PageLoadingView(getActivity());

    }

    @Override
    protected void lazyLoad() {

    }
}
