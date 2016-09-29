package com.vision.yao.test.http;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 网络请求接口
 * Created by owner on 2016/3/18.
 */
public interface API {

    @POST("user/login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("mobile") String mobile, @Field("password") String password);

}
