package com.google.googleplaystore.utils;

import android.content.Context;
import android.content.res.Resources;

import com.google.googleplaystore.base.MyApplication;

/**
 * Created by DH on 2017/7/8.
 * 得到和UI相关的操作
 */

public class UIUtils {
    /**
     * @return 得到上下文
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * @return 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * @param resId 资源id
     * @return 得到String.xml中的字符串信息
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * @param resId 资源id
     * @return 得到String.xml中 字符串数组信息
     */
    public static String[] getStrings(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
       @param resId 资源id
     * @return 得到Color.xml中的颜色信息
     */
    public static int getColor(int resId){
        return getResources().getColor(resId);
    }

    /**
     * @return 得到包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * @param dip
     * @return dip-->px
     */
    public static int dip2Px(int dip) {
        //px/dp=density
        //取得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;
        int px= (int) (dip*density+.5f);
        return px;
    }

    /**
     * @param px
     * @return px-->dip
     */
    public static int px2Dip(int px) {
        // px/dp=density
        //取得当前手机px和dp的倍数关系
        float density = getResources().getDisplayMetrics().density;
        int dip= (int) (px/density+.5f);
        return dip;
    }
}
