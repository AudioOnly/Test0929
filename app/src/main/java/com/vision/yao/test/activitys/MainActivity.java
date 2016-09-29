package com.vision.yao.test.activitys;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.vision.yao.test.R;
import com.vision.yao.test.fragments.Test1Fragment;
import com.vision.yao.test.fragments.Test2Fragment;
import com.vision.yao.test.fragments.Test3Fragment;
import com.vision.yao.test.fragments.Test4Fragment;
import com.vision.yao.test.utils.FinishLogic;
import com.vision.yao.test.views.ContainerViewPager;

import butterknife.BindView;

/**
 * Created by Magic on 16/9/8.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_tabs)
    RadioGroup rg_tabs;
    @BindView(R.id.vp)
    ContainerViewPager vp;



    private final FinishLogic finishLogic=new FinishLogic() {
        @Override
        public void onFinish() {
            exit();
        }

        @Override
        public void touchAgain() {
            toast("再次点击退出应用");
        }
    };


    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Bundle test1 = new Bundle();
        test1.putString(Test1Fragment.EXTRA_TITLE, "首页");
        Bundle test2 = new Bundle();
        test2.putString(Test1Fragment.EXTRA_TITLE, "随便");
        Bundle test3 = new Bundle();
        test3.putString(Test1Fragment.EXTRA_TITLE, "你猜");
        Bundle test4 = new Bundle();
        test4.putString(Test1Fragment.EXTRA_TITLE, "滚蛋");

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("", Test1Fragment.class, test1)
                        .add("", Test2Fragment.class, test2)
                        .add("", Test3Fragment.class, test3)
                        .add("", Test4Fragment.class, test4).create());
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(adapter.getCount());

        rg_tabs.setOnCheckedChangeListener((radioGroup, i) -> {
            int postion = Integer.valueOf(radioGroup.findViewById(i).getTag().toString());
            vp.setCurrentItem(postion,false);
        });


    }

    @Override
    public void onBackPressed() {
        finishLogic.onKeyBack();
    }
}
