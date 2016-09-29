package com.vision.yao.test.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.vision.yao.test.TestApplication;
import com.vision.yao.test.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.vision.yao.test.TestApplication.AppCtx;

/**
 * 工具类
 * Created by xiezihao on 16/6/30.
 */
public class Utils {

    /**
     * 设置部分文本颜色,从头开始匹配第一个
     *
     * @param tv    TextView
     * @param text  文本
     * @param sign  颜色文本
     * @param color 颜色
     */
    public static void setPartTextColor(TextView tv, String text, String sign, String color) {
        setPartTextColor(tv, text, sign, color, true);
    }

    /**
     * 设置部分文本颜色
     *
     * @param tv    TextView
     * @param text  文本
     * @param sign  颜色文本
     * @param color 颜色
     * @param first 是否从头开始匹配
     */
    public static void setPartTextColor(TextView tv, String text, String sign, String color, boolean first) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(sign))
            return;
        int start;
        if (first) { //从头匹配
            start = text.indexOf(sign);
        } else {
            start = text.lastIndexOf(sign);
        }
        int end = start + sign.length();
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ss);
    }

    /**
     * 获取手机IMEI号
     * @return
     */
    public static String getIMEI() {
        String imei = "";
        TelephonyManager telecomManager = (TelephonyManager) TestApplication.AppCtx.getSystemService(Context.TELEPHONY_SERVICE);
        imei = telecomManager.getDeviceId();
        return imei;

    }

    /**
     * 计算时间差
     *
     * @param endTime 秒数
     * @return
     */
    public static String getTimeDistance(long endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = "";
        try {
            Date d1 = new Date();
            Date d2 = df.parse("2006-07-02 11:20:00");
            long diff = d1.getTime() - endTime * 1000;//这样得到的差值是微秒级别

            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            timeStr = "" + days + "天" + hours + "小时" + minutes + "分";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }


    /**
     * 计算当前日期的本手起始日期
     *
     * @return
     */
    public static String getWeekTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format); //设置时间格式
        Calendar cal = Calendar.getInstance();
        Date time = new Date();// sdf.parse("2015-9-4 14:22:47");
        cal.setTime(time);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String start = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        String end = sdf.format(cal.getTime());
        return start + "-" + end;
    }


    /**
     * 获取时间字符串
     */
    public static String getTimeStr(long pt) {
        long pass = System.currentTimeMillis() - pt;
        if (pass < 0) {
            return AppCtx.getString(R.string.time_unknow);
        }
        if (pass < 2 * 60 * 1000) { //2分钟内
            return AppCtx.getString(R.string.time_moment);
        }
        if (pass < 60 * 60 * 1000) { //一小时内
            int min = (int) (pass / 60 / 1000);
            return AppCtx.getString(R.string.time_min_fmt, min);
        }
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String pubDate = ymd.format(new Date(pt));
        if (pubDate.equals(ymd.format(new Date()))) { //年月日一样
            return AppCtx.getString(R.string.time_today);
        }
        if (pubDate.equals(ymd.format(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)))) { //和昨天年月日一样
            return AppCtx.getString(R.string.time_ysd);
        }
        if (pubDate.equals(ymd.format(new Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000)))) { //和前天年月日一样
            return AppCtx.getString(R.string.time_qsd);
        }
        return new SimpleDateFormat(AppCtx.getString(R.string.time_month_fmt), Locale.getDefault()).format(new Date(pt));
    }

    /**
     * 转灰度图
     */
    public static Bitmap convertGreyImage(Bitmap img) {
        //获取宽高
        int width = img.getWidth();
        int height = img.getHeight();

        //通过位图的大小创建像素点数组
        int[] pixels = new int[width * height];

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);

        //回收
        img.recycle();

        return result;
    }

    /**
     * 获取缓存大小,byte单位
     */
    public static double getCacheSize() {
        return getFileSize(getCacheFile());
    }

    /**
     * 清除缓存
     */
    public static void clearCache() {
        deleteFile(getCacheFile());
    }

    /**
     * 获取缓存目录
     */
    private static File getCacheFile() {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        return new File(dataDir, AppCtx.getPackageName());
    }

    /**
     * 获取文件或目录大小
     */
    public static long getFileSize(File file) {
        long fileSize = 0;
        if (file.exists()) { //存在
            if (file.isFile()) { //是文件
                fileSize = file.length();
            } else { //是目录
                File[] files = file.listFiles();
                if (files != null) { //子不为null
                    for (File f : files) {
                        fileSize += getFileSize(f);
                    }
                }
            }
        }
        return fileSize;
    }

    /**
     * 删除文件或文件夹
     */
    public static void deleteFile(File file) {
        if (file.exists()) { //存在
            if (file.isFile()) { //是文件
                file.delete();
            } else { //是目录
                File[] files = file.listFiles();
                if (files != null) { //子不为null
                    for (File f : files) {
                        deleteFile(f);
                    }
                }
                //子文件删除完毕,删除目录
                file.delete();
            }
        }
    }
}
