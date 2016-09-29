package com.vision.yao.test.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * ImageLoader的各种项目需要用到的显示方式
 * Created by owner on 2016/3/23.
 */
public class DisplayOptions {

    /**
     * 创建一个新的图片显示Builder
     *
     * @return 默认的图片显示Builder
     */
    private static DisplayImageOptions.Builder createDefaultBuilder() {
        return new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .displayer(new SimpleBitmapDisplayer());
    }

    /**
     * 默认显示方式,不拉伸
     */
    public static final DisplayImageOptions DEFAULT = createDefaultBuilder().build();

    /**
     * 重置显示效果
     */
    public static final DisplayImageOptions RESET = createDefaultBuilder().resetViewBeforeLoading(true).build();

    /**
     * 重置并有动画效果
     */
    public static final DisplayImageOptions RESET_FADE = createDefaultBuilder().resetViewBeforeLoading(true)
            .displayer(new FadeInBitmapDisplayer(400)).build();


}
