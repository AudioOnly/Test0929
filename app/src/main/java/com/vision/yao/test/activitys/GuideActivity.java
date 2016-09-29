package com.vision.yao.test.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.vision.yao.test.R;
import com.vision.yao.test.adapters.ViewPagerAdapter;
import com.vision.yao.test.http.HDAPI;
import com.vision.yao.test.views.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Magic on 16/9/9.
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.stl)
    SmartTabLayout stl;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_guide;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<View> views = new ArrayList<>();
        views.add(getItem(R.drawable.img_guide_1));
        views.add(getItem(R.drawable.img_guide_2));
        views.add(getItem(R.drawable.img_guide_3));
        vp.setAdapter(new ViewPagerAdapter(views));
        vp.setOffscreenPageLimit(views.size());

        stl.setViewPager(vp);

        rxDestroy(HDAPI.test1()).subscribe(s -> {
            if ("OK".equals(s)){
                startActivity(new Intent(this,MainActivity.class));
            }
        },throwable -> {
            toast("数据异常");
        });
    }

    private View getItem(int i) {
        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(i);
        return iv;

    }
}
