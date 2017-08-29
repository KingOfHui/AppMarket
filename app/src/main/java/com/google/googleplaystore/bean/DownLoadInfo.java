package com.google.googleplaystore.bean;

import com.google.googleplaystore.manager.DownLoadManager;

/**
 * Created by DH on 2017/7/20.
 */

public class DownLoadInfo {
    public String downloadUrl;
    public String savePath;

    public int curState= DownLoadManager.STATE_UNDOWNLOAD;//默认是未下载

    public String packageName;

    public long max;
    public long progress;
}
