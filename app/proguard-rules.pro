# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/Magic/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings

#ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#项目中的 不混淆类
#Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#lambda
-dontwarn java.lang.invoke.*

#rxjava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#greendao
-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

#EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


#ShareSDK
-keep class com.mob.** { *; }
-keep class cn.sharesdk.** { *; }
-keep class * extends cn.sharesdk.framework.authorize.AuthorizeAdapter { *; }

#Umeng
##Umeng统计
-keepclassmembers class * {
    public <init> (org.json.JSONObject);
}
-keep class **.R$* { *;  }
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
##Umeng推送
-dontwarn com.ut.mini.**
-dontwarn okio.**
-dontwarn com.xiaomi.**
-dontwarn com.squareup.wire.**
-dontwarn android.support.v4.**
-keepattributes *Annotation*
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class okio.** {*;}
-keep class com.squareup.wire.** {*;}
-keep class com.umeng.message.protobuffer.* {
     public <fields>;
     public <methods>;
}
-keep class com.umeng.message.* {
	 public <fields>;
     public <methods>;
}
-keep class org.android.agoo.impl.* {
	 public <fields>;
     public <methods>;
}
-keep class org.android.agoo.service.* {*;}
-keep class org.android.spdy.**{*;}
-keep class **.R$* { *;  }
###如果compileSdkVersion为23，请添加以下混淆代码
-dontwarn org.apache.http.**
-dontwarn android.webkit.**
-keep class org.apache.http.** { *; }
-keep class org.apache.commons.codec.** { *; }
-keep class org.apache.commons.logging.** { *; }
-keep class android.net.compatibility.** { *; }
-keep class android.net.http.** { *; }

#百度地图
-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}
-dontwarn com.baidu.**

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

#nineoldandroids
-keep interface com.nineoldandroids.view.** { *; }
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }

#support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
#support-design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#趣拍
-ignorewarnings
-dontwarn com.alibaba.**
-keep class com.alibaba.**
-keepclassmembers class com.alibaba.** {
    *;
}
-keep class com.taobao.**
-keepclassmembers class com.taobao.** {
    *;
}
-dontwarn com.google.common.**
-dontwarn com.amap.api.**
-dontwarn net.jcip.annotations.**
-keepattributes Annotation,EnclosingMethod,Signature,InnerClasses
-keep class com.duanqu.**
-keepclassmembers class com.duanqu.** {
    *;
}
-dontwarn org.apache.http.**
-keepclassmembers class org.apache.http.** {
    *;
}
-dontwarn com.taobao.update.**
-dontwarn android.util.**
-dontwarn com.google.auto.factory.**
-dontwarn com.taobao.tae.sdk.callback.**
-ignorewarnings
-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**
-keep class org.codehaus.** { *; }
-keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }

#七牛
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings

#Camera360
-keep class us.pinguo.edit.sdk.core.utils.**{*;}
-keep class us.pinguo.androidsdk.*{*;}
-keep class us.pinguo.edit.sdk.core.effect.**{*;}
-keep class us.pinguo.edit.sdk.core.model.PGEftDispInfo{*;}
-keep class us.pinguo.edit.sdk.core.model.PGEftPkgDispInfo{*;}
-keep class us.pinguo.edit.sdk.core.PGEditCoreAPI{*;}
-keep class us.pinguo.edit.sdk.core.IPGEditCallback{*;}
-keep class us.pinguo.edit.sdk.base.PGEditResult{*;}
-keep class us.pinguo.edit.sdk.base.PGEditSDK{*;}
-keep class us.pinguo.edit.sdk.base.controller.PGEditController{*;}
-keep class us.pinguo.edit.sdk.base.utils.ApiHelper{*;}
-keep class us.pinguo.edit.sdk.base.view.IPGEditView{*;}
-keep class us.pinguo.edit.sdk.base.PGEditConstants{*;}
-keep class us.pinguo.edit.sdk.base.bean.PGEditMenuBean$*{*;}
-keep class us.pinguo.edit.sdk.base.view.**{*;}
-keep class us.pinguo.edit.sdk.base.widget.**{*;}
-keep class us.pinguo.edit.sdk.base.PGEditTools{*;}
-keep class us.pinguo.edit.sdk.base.bean.**{*;}
-keep class us.pinguo.common.log.*{*;}
-keep class us.pinguo.common.utils.*{*;}

#极光推送
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-dontwarn com.google.**
-keep class com.google.protobuf.** {*;}

#支付宝


-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}