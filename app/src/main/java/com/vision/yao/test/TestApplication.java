package com.vision.yao.test;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.vision.yao.test.http.HDAPI;
import com.vision.yao.test.utils.EB;

import static com.vision.yao.test.utils.Toast.show;

/**
 * Created by Magic on 16/9/6.
 */
public class TestApplication  extends MultiDexApplication {

        /**
         * 是否是线上模式
         */
        public static final boolean ONLINE = false;
        /**
         * 是否调试？打印Log日志
         */
        public static final boolean DEBUG = true;
    /**
     * APP上下文实例缓存
     */
    public static Context AppCtx;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCtx = getApplicationContext();

        if (!ONLINE)
            show("当前是测试服务器");
        if (DEBUG)
            show("当前是测试模式");

        //初始化网络工具类
        HDAPI.init();
        //初始化工具类
        EB.init();
    }
}
