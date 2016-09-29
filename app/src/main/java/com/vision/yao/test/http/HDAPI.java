package com.vision.yao.test.http;

import com.vision.yao.test.TestApplication;
import com.vision.yao.test.utils.L;
import com.vision.yao.test.utils.SP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.vision.yao.test.TestApplication.DEBUG;
import static com.vision.yao.test.utils.Toast.show;


/**
 * 网络请求
 * Created by owner on 2016/3/18.
 */
public class HDAPI {
    private static final L L = new L(HDAPI.class);

    /**
     * 分页默认开始页
     */
    public static final int PAGE_DEFAULT = 1;
    /**
     * 一页应该有的数据量
     */
    public static final int PAGE_SIZE = 5;

    /**
     * SP
     */
    private static final SP SP = new SP();
    //初始化================

    /**
     * 基础的URL
     */
    private static String BASE_URL;
    /**
     * api实例
     */
    private static API API;

    /**
     * 初始化网络请求，在Application创建的时候调用,确保只初始化一次
     */
    public static void init() {
        //开发环境:http://aaabbbccc.vifibus.com/,测试环境:http://xxxyyyzzz.vifibus.com/
        BASE_URL = TestApplication.ONLINE ? "http://api.mzshiwan.com/" : "http://aaabbbccc.vifibus.com/";
        Retrofit.Builder retBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        //添加头拦截器
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException { //添加Agent和已登录后自动设置用户信息参数
                Request.Builder rb = chain.request().newBuilder();
                rb.header("HTTP_USER_AGENT", "android");
//                LoginInfo li = loadLoginInfo();
//                if (li.hasLogined()) { //已登录,添加头
//                    rb.addHeader("UID", li.getUid());
//                    rb.addHeader("SESSIONID", li.getSession_id());
//                    rb.addHeader("USECRET", li.getUsecret());
//                    rb.addHeader("APPVERSION", BuildConfig.VERSION_NAME);
//                }
                return chain.proceed(rb.build());
            }
        });
        if (DEBUG) { //DEBUG模式
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okBuilder.addInterceptor(interceptor);
        }
        retBuilder.client(okBuilder.build());
        API = retBuilder.build().create(API.class);
        L.e("网络API初始化完成");
    }

    //接口=======================

    //登陆
    public static Observable<String> test1() {
        return thread(Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("OK");
                subscriber.onCompleted();
            }
        }));
    }

    /**
     * 虚拟加载数据
     * @param page
     * @return
     */
    public static Observable<List<String>> getPages(int page){
        return thread(Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<String> strings=new ArrayList<>();
                if (page<=5){
                    for (int i=0;i<10;i++){
                        strings.add(String.valueOf(System.currentTimeMillis())+page);
                    }
                }
                subscriber.onNext(strings);
                subscriber.onCompleted();
            }

        }));
    }

    //SP======================




    //其它封装===============

    /**
     * 后台网络,前台UI线程
     */
    private static <T> Observable<T> thread(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 错误空拦截
     */
    private static <T> Observable<T> onErrorEmpty(Observable<T> observable) {
        return observable.onErrorResumeNext(Observable.<T>empty());
    }
}
