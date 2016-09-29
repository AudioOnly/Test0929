package com.vision.yao.test.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.vision.yao.test.TestApplication.DEBUG;


/**
 * Log封装类
 * Created by owner on 2016/3/2.
 */
public class L {

    private String tag;

    public L(Class<?> clz) {
        this(clz.getSimpleName());
    }

    public L(String tag) {
        this.tag = tag;
    }

    public void e(String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public void e(String msg, Throwable ex) {
        if (DEBUG) {
            Log.e(tag, msg + ":\n" + getExceptionString(ex));
        }
    }

    /**
     * 获取异常字符串
     *
     * @param ex 异常
     * @return 异常字符串
     */
    private String getExceptionString(Throwable ex) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e) {
            return "获取异常信息时发生异常";
        }
    }
}
