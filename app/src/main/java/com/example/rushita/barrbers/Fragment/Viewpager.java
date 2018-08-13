package com.example.rushita.barrbers.Fragment;

import android.support.v4.view.ViewPager;

class Viewpager {
    private static int offscreenPageLimit;
    private static int currentItem;
    private static ViewPager.OnPageChangeListener onPageChangeListener;

    public static void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
Viewpager.onPageChangeListener = onPageChangeListener;
    }

    public static void setCurrentItem(int currentItem) {
     Viewpager.currentItem = currentItem;
    }

    public static void setOffscreenPageLimit(int offscreenPageLimit) {
    Viewpager.offscreenPageLimit = offscreenPageLimit;
    }
}
