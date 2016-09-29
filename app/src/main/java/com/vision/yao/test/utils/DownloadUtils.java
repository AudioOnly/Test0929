package com.vision.yao.test.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.liulishuo.filedownloader.util.FileDownloadUtils.generateId;
import static com.vision.yao.test.TestApplication.AppCtx;

/**
 * 下载工具类
 * Created by owner on 2016/4/11.
 */
public class DownloadUtils {

    private static final L L = new L(DownloadUtils.class);

    /**
     * 下载监听
     */
    private final static Map<Integer, DownloadListener> LISTENERS = new HashMap<>();

    /**
     * 初始化
     */
    public static void init(Context ctx) {
        FileDownloader.init(ctx.getApplicationContext());
    }

    /**
     * 下载APK
     */
    public static int downloadApk(String url, String name, DownloadListener l) {
        L.e("下载:" + url + "-" + name);
        LISTENERS.put(getID(url, name), l);
        return FileDownloader.getImpl().create(url).setCallbackProgressTimes(10).setAutoRetryTimes(3).setPath(getApkFilePath(name)).setListener(LISTENER).start();
    }

    /**
     * 唯一的监听
     */
    private final static FileDownloadListener LISTENER = new FileDownloadListener() {

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            LISTENERS.get(task.getId()).pending(task, soFarBytes, totalBytes);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            LISTENERS.get(task.getId()).progress(task, soFarBytes, totalBytes);
        }

        @Override
        protected void blockComplete(BaseDownloadTask task) {
            LISTENERS.get(task.getId()).blockComplete(task);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            LISTENERS.get(task.getId()).completed(task);
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            LISTENERS.get(task.getId()).paused(task, soFarBytes, totalBytes);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            LISTENERS.get(task.getId()).error(task, e);
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            LISTENERS.get(task.getId()).warn(task);
        }
    };

    /**
     * 获取ID
     */
    public static int getID(String url, String name) {
        return generateId(url, getApkFilePath(name));
    }

    /**
     * 获取进度
     */
    public static int getProgress(int id) {
        return (int) FileDownloader.getImpl().getSoFar(id);
    }

    /**
     * 获取总进度
     */
    public static int getMaxProgress(int id) {
        return (int) FileDownloader.getImpl().getTotal(id);
    }

    /**
     * 获取APK文件路径
     *
     * @param name 文件名
     * @return 文件完整路径
     */
    private static String getApkFilePath(String name) {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + name + ".apk";
    }

    /**
     * 获取安装apk的Intent
     *
     * @param path apk路径
     */
    public static Intent getInstallAPKIntent(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 安装APK
     *
     * @param path apk路径
     */
    public static void installAPK(String path) {
        AppCtx.startActivity(getInstallAPKIntent(path));
    }

    /**
     * 下载监听
     */
    public static class DownloadListener extends FileDownloadListener {

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            L.e("pending:" + soFarBytes + "-" + totalBytes + ";" + task.getDownloadId());
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            L.e("progress:" + soFarBytes + "-" + totalBytes + ";" + task.getDownloadId());
        }

        @Override
        protected void blockComplete(BaseDownloadTask task) {
            L.e("blockComplete" + ";" + task.getDownloadId());
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            L.e("completed" + ";" + task.getDownloadId());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            L.e("paused:" + soFarBytes + "-" + totalBytes + ";" + task.getDownloadId());
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            L.e("error:" + e.getMessage() + ";" + task.getDownloadId());
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            L.e("warn:" + ";" + task.getDownloadId());
        }

        @Override
        protected void started(BaseDownloadTask task) {
            L.e("started" + ";" + task.getDownloadId());
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            L.e("connected:" + etag + "-" + isContinue + "-" + soFarBytes + "-" + totalBytes + ";" + task.getDownloadId());
        }

        @Override
        protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
            L.e("retry:" + retryingTimes + "-" + soFarBytes + "-" + ex.getMessage() + ";" + task.getDownloadId());
        }
    }
}
