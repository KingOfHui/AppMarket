package com.google.googleplaystore.proxy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by DH on 2017/7/11.
 * 线程池代理类,替线程池完成相关操作
 * 1.代理模式就是多一个代理类出来,替原对象进行一些操作
 * 2.只需提供使用原对象时候真正关心的方法(提交任务,执行任务,移除任务)
 * 3.可以对原对象方法进行增强
 */

public class ThreadPoolProxy {
    //核心池的大小
    private int mCorePoolSize;
    //线程最大线程数
    private int mMaximumPoolSize;
    private ThreadPoolExecutor mExecutor;

    public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mMaximumPoolSize = maximumPoolSize;
    }

    public void initThreadPoolExecutor() {
        //双重检查加锁:只有第一次实例化的时候才启用同步机制,提高了性能
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            synchronized (ThreadPoolProxy.class) {
                if (mExecutor == null || mExecutor.isTerminated() || mExecutor.isShutdown()) {
                    long keepAliveTime = 0;
                    TimeUnit unit = TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
                    ThreadFactory threadFactory = Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                    mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
                }
            }
        }
    }

    /**提交任务
     * @param task
     */
    public void submit(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.submit(task);
    }

    /**执行任务
     * @param task
     */
    public void executor(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.execute(task);
    }

    /**移除任务
     * @param task
     */
    public void remove(Runnable task) {
        initThreadPoolExecutor();
        mExecutor.remove(task);
    }
}
