package com.google.googleplaystore.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.base.MyApplication;
import com.google.googleplaystore.bean.DownLoadInfo;
import com.google.googleplaystore.bean.HomeBean;
import com.google.googleplaystore.manager.DownLoadManager;
import com.google.googleplaystore.utils.FileUtils;
import com.google.googleplaystore.utils.UIUtils;
import com.google.googleplaystore.views.ProgressBtn;

import java.io.File;

/**
 * Created by DH on 2017/7/17.
 */

public class DetailDownloadHolder extends BaseHolder<HomeBean.ListBean> implements View.OnClickListener,DownLoadManager.DownLoadInfoObserver{

    private android.widget.Button appdetaildownloadbtnfavo;
    private android.widget.Button appdetaildownloadbtnshare;
    private ProgressBtn appdetaildownloadbtndownload;
    private HomeBean.ListBean mListBean;

    @Override
    public View initHolderView() {
        View holderView= LayoutInflater.from(UIUtils.getContext()).inflate( R.layout.item_detail_download,null,false);
        this.appdetaildownloadbtndownload = (ProgressBtn) holderView.findViewById(R.id.app_detail_download_btn_download);
        this.appdetaildownloadbtnshare = (Button) holderView.findViewById(R.id.app_detail_download_btn_share);
        this.appdetaildownloadbtnfavo = (Button) holderView.findViewById(R.id.app_detail_download_btn_favo);
        appdetaildownloadbtndownload.setOnClickListener(this);
        return holderView;
    }
    @Override
    protected void refreshHolderView(HomeBean.ListBean data) {
        mListBean=data;

        /*--------------根据不同的状态给用户提示---------------*/
        DownLoadManager.getInstance().getDownLoadInfo(data);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(UIUtils.getContext(), "下载", Toast.LENGTH_SHORT).show();
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        String dir = FileUtils.getDir("apk");
        String fileName = mListBean.packageName + ".apk";
        File saveApk = new File(dir, fileName);
        downLoadInfo.downloadUrl = mListBean.downloadUrl;
        downLoadInfo.savePath = saveApk.getAbsolutePath();
        DownLoadManager.getInstance().download(downLoadInfo);
    }

    @Override
    public void onDownLoadInfoChanged(final DownLoadInfo downLoadInfo) {
        //针对接收到的DownLoadInfo进行过滤
        if (!mListBean.packageName.equals(downLoadInfo.packageName)) {
            return;
        }
        MyApplication.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                refreshProgressBtnUI(downLoadInfo);
            }
        });
    }

    private void refreshProgressBtnUI(DownLoadInfo downLoadInfo) {
        int curState = downLoadInfo.curState;

        appdetaildownloadbtndownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_normal);
        switch (curState) {
            case DownLoadManager.STATE_UNDOWNLOAD:
                appdetaildownloadbtndownload.setText("下载");
                break;
            case DownLoadManager.STATE_DOWMLOADING:
                appdetaildownloadbtndownload.setIsProgressEnable(true);
                appdetaildownloadbtndownload.setBackgroundResource(R.drawable.selector_app_detail_bottom_downloading);
                int index= (int) (downLoadInfo.progress*1.0f/downLoadInfo.max*100+.5f);
                appdetaildownloadbtndownload.setText(index+"%");
                appdetaildownloadbtndownload.setMax(downLoadInfo.max);
                appdetaildownloadbtndownload.setProgress(downLoadInfo.progress);
                break;
            case DownLoadManager.STATE_PAUSEDOWNLOAD:
                appdetaildownloadbtndownload.setText("继续");
                break;
            case DownLoadManager.STATE_WAITINGDOWNLOAD:
                appdetaildownloadbtndownload.setText("等待");
                break;
            case DownLoadManager.STATE_DOWNLOADFAILED:
                appdetaildownloadbtndownload.setText("重试");
                break;
            case DownLoadManager.STATE_DOWNLOADED:
                appdetaildownloadbtndownload.setIsProgressEnable(false);
                appdetaildownloadbtndownload.setText("安装");
                break;
            case DownLoadManager.STATE_INSTALLED:
                appdetaildownloadbtndownload.setText("打开");
                break;
        }
    }
}
