package com.google.googleplaystore.Constants;

import com.google.googleplaystore.utils.LogUtils;

/**
 * Created by DH on 2017/7/8.
 */

public class Constants {
    /**
     * LogUtils.LEVEL_ALL : 打开日志
     * LogUtils.LEVEL_OFF : 关闭日志
     */
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;
    public static final long PROTOCOLTIMEOUT = 5 * 60 * 1000;

    public static class URLS {

        public static final String BASEURL = "http://10.0.2.2:8080/GooglePlayServer/";
        public static final String IMGURL = BASEURL+"image?name=";
    }
}
