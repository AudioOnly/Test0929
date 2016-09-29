package com.vision.yao.test.utils;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;

/**
 * 监听空实现
 * Created by xiezihao on 16/6/22.
 */
public class Listeners {

    public static class OnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public static class ImageLoadingListener implements com.nostra13.universalimageloader.core.listener.ImageLoadingListener {

        @Override
        public void onLoadingStarted(String imageUri, View view) {

        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {

        }
    }
}
