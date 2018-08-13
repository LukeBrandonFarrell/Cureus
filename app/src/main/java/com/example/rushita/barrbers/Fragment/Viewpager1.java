package com.example.rushita.barrbers.Fragment;

import android.support.v4.view.ViewPager;



class Viewpager1 {
    private static int offscreenPageLimit;
    private static int currentItem;
    private static ViewPager.OnPageChangeListener onPageChangeListener;

    public static void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        Viewpager1.onPageChangeListener = onPageChangeListener;
    }

    public static void setCurrentItem(int currentItem) {
        Viewpager1.currentItem = currentItem;
    }

    public static void setOffscreenPageLimit(int offscreenPageLimit) {
        Viewpager1.offscreenPageLimit = offscreenPageLimit;
    }

}
