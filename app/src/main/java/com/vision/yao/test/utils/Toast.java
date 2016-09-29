package com.vision.yao.test.utils;


import android.text.TextUtils;

import static com.vision.yao.test.TestApplication.AppCtx;

/**
 * Toast工具类
 * Created by owner on 2016/4/26.
 */
public class Toast {

    /**
     * Toast
     *
     * @param res 资源ID
     */
    public static void show(int res) {
        show(AppCtx.getString(res));
    }

    /**
     * Toast
     *
     * @param text 文本
     */
    public static void show(String text) {
        if (TextUtils.isEmpty(text)) //空文本不显示
            return;
        android.widget.Toast.makeText(AppCtx, text, android.widget.Toast.LENGTH_SHORT).show();
    }
}
