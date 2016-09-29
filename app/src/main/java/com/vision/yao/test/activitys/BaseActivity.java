package com.vision.yao.test.activitys;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.vision.yao.test.R;
import com.zhx.statusbar.SmartBarUtils;
import com.zhx.statusbar.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;

import static com.vision.yao.test.utils.Toast.*;


/**
 * 基础父类
 * Created by xzh on 2015/12/31 0031.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    private static final List<Activity> ACTIVITIES = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ACTIVITIES.add(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SmartBarUtils.hide(getWindow().getDecorView());
        setContentView(getLayoutID());
        StatusBarUtils.translucentStatusBar(this, findViewById(R.id.v_fitsSystemWindows));
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //每次显示都关闭分享框
        dismissShareProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ACTIVITIES.remove(this);
    }

    /**
     * 退出所有页面
     */
    protected void exit() {
        for (Activity act : ACTIVITIES) {
            act.finish();
        }
    }

    /**
     * 退出其它页面除了当前页面
     */
    protected void exitWithoutThis() {
        for (Activity act : ACTIVITIES) {
            if (act == this) { //当前页面跳过
                continue;
            }
            act.finish();
        }
    }

    /**
     * 当前页是否是最后一个页面
     */
    protected boolean isLastAct() {
        return ACTIVITIES.size() == 1 && ACTIVITIES.contains(this);
    }


    /**
    * Rx生命周期
    */
    public <T> Observable<T> rxDestroy(Observable<T> observable) {
        //添加最近java版本限制 在app build中
        return observable.compose(bindUntilEvent(ActivityEvent.DESTROY));
    }

    //=============================================

    /**
     * 绑定控件之前必须初始化布局
     *
     * @return 布局ID
     */
    protected abstract int getLayoutID();

    /**
     * 初始化完成，替代{@link #onCreate(Bundle)}
     */
    protected abstract void init(Bundle savedInstanceState);

    //Toast====================================

    /**
     * Toast
     *
     * @param res 资源
     */
    public void toast(int res) {
        show(res);
    }

    /**
     * Toast
     *
     * @param text 文本
     */
    public void toast(String text) {
        show(text);
    }

    //进度框=============================

    private MaterialDialog mPD;

    /**
     * 显示进度框
     */
    public void showProgress() {
        if (mPD == null) {
            mPD = new MaterialDialog.Builder(this).content(R.string.dl_progress)
                    .progress(true, 0).canceledOnTouchOutside(false).build();
        }
        mPD.show();
    }

    /**
     * 关闭进度框
     */
    public void dismissProgress() {
        if (mPD != null)
            mPD.dismiss();
    }

    //分享进度框================

    private MaterialDialog mSharePD;

    /**
     * 显示进度框
     */
    public void showShareProgress() {
        if (mSharePD == null) {
            mSharePD = new MaterialDialog.Builder(this).content(R.string.dl_share_progress)
                    .progress(true, 0).canceledOnTouchOutside(false).build();
        }
        mSharePD.show();
    }

    /**
     * 关闭进度框
     */
    public void dismissShareProgress() {
        if (mSharePD != null)
            mSharePD.dismiss();
    }
}
