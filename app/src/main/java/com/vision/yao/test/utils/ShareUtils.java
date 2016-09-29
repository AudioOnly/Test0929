package com.vision.yao.test.utils;

import android.os.Handler;

/**
 * 分享工具类
 * Created by owner on 2016/4/7.
 */
public class ShareUtils {

    private static Handler H;

    /**
     * 初始化
     */
    public static void init() {
        H = new Handler();
    }

    /**
     * 分享
     *
     * @param act      页面
     * @param data     分享参数,0:icon,1:标题,2:内容,3:url
     * @param platform 分享的平台
     * @param l
     */
//    public static void share(BaseActivity act, String icon, String title, String content, String url,
//                             String platform, PlatformActionListener l) {
//        act.showShareProgress();
//        ImageLoader il = ImageLoader.getInstance();
//        act.rxDestroy(Observable.create(subscriber -> {
//            //不回收图片,缓存在内存中,其它地方可能用到
//            il.loadImageSync(icon);
//            subscriber.onNext(null);
//            subscriber.onCompleted();
//        })).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(obj -> {
//            //开始分享
//            OnekeyShare oks = new OnekeyShare();
//            oks.setTitle(title);
//            oks.setTitleUrl(url);
//            oks.setText(content);
//            oks.setImagePath(il.getDiskCache().get(icon).getAbsolutePath());
//            oks.setUrl(url);
//            oks.setPlatform(platform);
//            oks.setCallback(new PlatformActionListener() {
//                @Override
//                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                    H.post(() -> {
//                        act.dismissShareProgress();
//                        l.onComplete(platform, i, hashMap);
//                    });
//                }
//
//                @Override
//                public void onError(Platform platform, int i, Throwable throwable) {
//                    H.post(() -> {
//                        act.dismissShareProgress();
//                        l.onError(platform, i, throwable);
//                    });
//                }
//
//                @Override
//                public void onCancel(Platform platform, int i) {
//                    H.post(() -> {
//                        act.dismissShareProgress();
//                        l.onCancel(platform, i);
//                    });
//                }
//            });
//            oks.setSilent(true);
//            oks.show(act);
//        }, e -> { //Load图片的时候错误
//            act.dismissShareProgress();
//            l.onError(null, 0, null);
//        });
//    }
}
