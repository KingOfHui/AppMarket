package com.google.googleplaystore.factory;

import com.google.googleplaystore.proxy.ThreadPoolProxy;

/**
 * Created by DH on 2017/7/11.
 * ThreadPoolProxy工厂类,封装对ThreadPoolProxy创建
 */

public class ThreadPoolProxyFactory {
    //普通类型的线程池代理
    private static ThreadPoolProxy mNormalThreadPoolProxy;

    //下载类型的线程池代理
    private static ThreadPoolProxy mDownloadThreadPoolProxy;

    /*
     *得到普通类型的线程池代理
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(5, 5);

                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 得到下载类型的线程池代理
     * @return
     */
    public static ThreadPoolProxy getDownloadThreadPoolProxy() {
        if (mDownloadThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mDownloadThreadPoolProxy==null) {
                    mDownloadThreadPoolProxy = new ThreadPoolProxy(3, 3);
                }
            }
        }
        return mDownloadThreadPoolProxy;
    }
}
