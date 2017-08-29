package com.google.googleplaystore.manager;

import com.google.googleplaystore.Constants.Constants;
import com.google.googleplaystore.bean.DownLoadInfo;
import com.google.googleplaystore.bean.HomeBean;
import com.google.googleplaystore.factory.ThreadPoolProxyFactory;
import com.google.googleplaystore.utils.CommonUtils;
import com.google.googleplaystore.utils.FileUtils;
import com.google.googleplaystore.utils.HttpUtils;
import com.google.googleplaystore.utils.IOUtils;
import com.google.googleplaystore.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载管理器,负责和下载相关的逻辑
 */

public class DownLoadManager {

    public static final int STATE_UNDOWNLOAD=0;//未下载
    public static final int STATE_DOWMLOADING=1;//下载中
    public static final int STATE_PAUSEDOWNLOAD=2;//暂停下载
    public static final int STATE_WAITINGDOWNLOAD=3;//等待下载
    public static final int STATE_DOWNLOADFAILED=4;//下载失败
    public static final int STATE_DOWNLOADED=5;//下载完成
    public static final int STATE_INSTALLED=6;//已安装

    /**
     * 用于缓存当前正在下载的DownLoadInfo
     */
    private Map<String, DownLoadInfo> mCacheDownLoadInfoMap = new HashMap<>();
    private static DownLoadManager instance;

    private DownLoadManager() {
    }

    public static DownLoadManager getInstance() {
        if (instance == null) {
            synchronized (DownLoadManager.class) {
                if (instance == null) {
                    instance = new DownLoadManager();
                }
            }
        }
        return instance;
    }

    /**
     * 异步下载apk
     */
    public void download(DownLoadInfo downLoadInfo) {
        mCacheDownLoadInfoMap.put(downLoadInfo.packageName, downLoadInfo);

        /*--------------当前状态:未下载---------------*/
        downLoadInfo.curState=STATE_UNDOWNLOAD;
        notifyObservers(downLoadInfo);
        /*--------------当前状态:等待中---------------*/
        downLoadInfo.curState=STATE_WAITINGDOWNLOAD;
        notifyObservers(downLoadInfo);

        DownLoadTask downLoadTask = new DownLoadTask(downLoadInfo);
        ThreadPoolProxyFactory.getDownloadThreadPoolProxy().submit(downLoadTask);
    }

    public DownLoadInfo getDownLoadInfo(HomeBean.ListBean data) {
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        //常规赋值
        String dir = FileUtils.getDir("apk");
        String fileName = data.packageName + "apk";
        File saveFile = new File(dir, fileName);

        downLoadInfo.savePath= saveFile.getAbsolutePath();
        downLoadInfo.packageName=data.packageName;
        downLoadInfo.downloadUrl=data.downloadUrl;

        downLoadInfo.max=data.size;
        downLoadInfo.progress=0;
        //重点赋值
        //已安装-->用户即使不惦记下载操作,也有可能已经安装
        if (CommonUtils.isInstalled(UIUtils.getContext(), downLoadInfo.packageName)) {
            downLoadInfo.curState=STATE_INSTALLED;
            return downLoadInfo;
        }
        //下载完成-->用户即使不点击操作,也有可能已经下载完成
        if (saveFile.exists() && saveFile.length() == data.size) {
            downLoadInfo.curState=STATE_DOWNLOADED;
            return downLoadInfo;
        }
        //
        if (mCacheDownLoadInfoMap.containsKey(data.packageName)) {
            //用户肯定点击了itemBean所对应详情界面里面的下载按钮
            DownLoadInfo tempDownLoadInfo = mCacheDownLoadInfoMap.get(data.packageName);
            return tempDownLoadInfo;
//            DownLoadInfo tempDownLoadInfo = mCacheDownLoadInfoMap.get(data.packageName);
//            int curState = tempDownLoadInfo.curState;
//
//            downLoadInfo.curState = curState;
//            return downLoadInfo;

        }
        //默认情况:未下载
        downLoadInfo.curState=STATE_UNDOWNLOAD;
        return downLoadInfo;
    }

    private class DownLoadTask implements Runnable {
        private DownLoadInfo downLoadInfo;

        public DownLoadTask(DownLoadInfo downLoadInfo) {
            this.downLoadInfo = downLoadInfo;
        }

        @Override
        public void run() {
            /*--------------当前状态:下载中---------------*/
            downLoadInfo.curState=STATE_DOWMLOADING;
            notifyObservers(downLoadInfo);

            InputStream mIs=null;
            FileOutputStream fos=null;
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                String url = Constants.URLS.BASEURL + "download";
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("name", downLoadInfo.downloadUrl);
                paramsMap.put("range", "0");
                //转换参数为字符串
                String urlParamsByMap = HttpUtils.getUrlParamsByMap(paramsMap);
                url += "?" + urlParamsByMap;
                Request request = new Request.Builder().get().url(url).build();
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    mIs = response.body().byteStream();
                    File saveApk = new File(downLoadInfo.savePath);
                    fos = new FileOutputStream(saveApk);
                    int len=0;
                    byte[] buffer = new byte[1024];

                    boolean isPause=false;

                    while ((len = mIs.read(buffer)) != -1) {
                        if (downLoadInfo.curState == STATE_PAUSEDOWNLOAD) {
                            isPause=true;
                            break;
                        }
                        fos.write(buffer,0,len);
                        //处理进度
                        downLoadInfo.progress+=len;
                        /*--------------当前状态:下载中---------------*/
                        downLoadInfo.curState=STATE_DOWMLOADING;
                        notifyObservers(downLoadInfo);
                    }
                    if (isPause) {
                        //说明用户点击了暂停下载,来到这个地方
                    }else {
                        //说明下载完成
                         /*--------------当前状态:下载完成---------------*/
                        downLoadInfo.curState=STATE_DOWNLOADED;
                        notifyObservers(downLoadInfo);
                    }
                }else {
                    /*--------------当前状态:下载失败---------------*/
                    downLoadInfo.curState=STATE_DOWNLOADFAILED;
                    notifyObservers(downLoadInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
                /*--------------当前状态:下载错误---------------*/
                downLoadInfo.curState=STATE_DOWNLOADFAILED;
                notifyObservers(downLoadInfo);
            } finally {
                IOUtils.close(fos);
                IOUtils.close(mIs);
            }
        }
    }

    //1.定义接口以及接口方法
    public interface DownLoadInfoObserver{
        void onDownLoadInfoChanged(DownLoadInfo downLoadInfo);
    }

    //2.定义集合保存接口对象
    public List<DownLoadInfoObserver> mObservers = new ArrayList<>();

    //3.常见方法-->添加观察者到观察者集合中
    public synchronized void addObserver(DownLoadInfoObserver observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    //3.常见方法-->从观察者集合中移除观察者
    public synchronized void removeObserver(DownLoadInfoObserver observer) {
        mObservers.remove(observer);
    }

    //3.常见方法-->通知所有的观察者消息已经发生改变
    public void notifyObservers(DownLoadInfo downLoadInfo) {
        if(mObservers!=null) {
            for (DownLoadInfoObserver observer : mObservers) {
                observer.onDownLoadInfoChanged(downLoadInfo);
            }
        }
    }

    public void pause(DownLoadInfo downLoadInfo) {
        /*--------------当前状态:暂停下载---------------*/
        downLoadInfo.curState=STATE_PAUSEDOWNLOAD;
        notifyObservers(downLoadInfo);
    }
}
