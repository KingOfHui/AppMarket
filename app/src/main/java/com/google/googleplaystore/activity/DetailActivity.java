package com.google.googleplaystore.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.googleplaystore.R;
import com.google.googleplaystore.base.LoadingPagerController;
import com.google.googleplaystore.bean.HomeBean;
import com.google.googleplaystore.holder.DetailDesHolder;
import com.google.googleplaystore.holder.DetailDownloadHolder;
import com.google.googleplaystore.holder.DetailInfoHolder;
import com.google.googleplaystore.holder.DetailPicHolder;
import com.google.googleplaystore.holder.DetailSafeHolder;
import com.google.googleplaystore.manager.DownLoadManager;
import com.google.googleplaystore.protocol.DetailProtocol;
import com.google.googleplaystore.utils.UIUtils;

public class DetailActivity extends AppCompatActivity {

    private LoadingPagerController mLoadingPagerController;
    private String mPackageName;
    private android.widget.FrameLayout detailfldownload;
    private android.widget.FrameLayout detailflinfo;
    private android.widget.FrameLayout detailflsafe;
    private android.widget.FrameLayout detailflpic;
    private android.widget.FrameLayout detailfldes;
    private HomeBean.ListBean mListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingPagerController = new LoadingPagerController(this) {

            @Override
            protected View initSuccessView() {
                return DetailActivity.this.initSuccessView();
            }

            @Override
            public LoadedResult initData() {
                return DetailActivity.this.initData();
            }
        };
        setContentView(mLoadingPagerController);
        init();
        triggerLoadData();
        initActionBar();
    }

    private void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default :

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void triggerLoadData() {
        mLoadingPagerController.triggerData();
    }

    /**
     * 在子线程中真正的加载数据
     * @return
     */
    private LoadingPagerController.LoadedResult initData() {
        SystemClock.sleep(1000);
        DetailProtocol protocol = new DetailProtocol(mPackageName);
        try {
            mListBean = protocol.loadData(0);
            if (mListBean != null) {
                return LoadingPagerController.LoadedResult.SUCCESS;
            }else {
                return LoadingPagerController.LoadedResult.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPagerController.LoadedResult.ERROR;
        }
    }

    /**
     * 决定成功视图是什么
     * 成功视图和数据的绑定
     * @return
     */
    private View initSuccessView() {
        View successView= LayoutInflater.from(UIUtils.getContext()).inflate( R.layout.activity_base_detail,null,false);
        this.detailfldes = (FrameLayout) successView.findViewById(R.id.detail_fl_des);
        this.detailflpic = (FrameLayout) successView.findViewById(R.id.detail_fl_pic);
        this.detailflsafe = (FrameLayout) successView.findViewById(R.id.detail_fl_safe);
        this.detailflinfo = (FrameLayout) successView.findViewById(R.id.detail_fl_info);
        this.detailfldownload = (FrameLayout) successView.findViewById(R.id.detail_fl_download);

        //往应用的信息部分这个容器里面 添加内容
        DetailInfoHolder detailInfoHolder = new DetailInfoHolder();
        View holderView = detailInfoHolder.mHolderView;

        //为基本信息部分添加一个反转的属性动画
        ViewCompat.animate(holderView).rotationX(360).setInterpolator(new OvershootInterpolator(4))
                .setDuration(1000).start();

        detailflinfo.addView(holderView);
        detailInfoHolder.setDataAndRefreshHolderView(mListBean);
        //往应用的安全部分容器 里面添加内容
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        detailflsafe.addView(detailSafeHolder.mHolderView);
        detailSafeHolder.setDataAndRefreshHolderView(mListBean);

        //往应用的截图部分容器 里面添加内容
        DetailPicHolder detailPicHolder = new DetailPicHolder();
        detailflpic.addView(detailPicHolder.mHolderView);
        detailPicHolder.setDataAndRefreshHolderView(mListBean);

        //往应用的描述部分添加内容
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        detailfldes.addView(detailDesHolder.mHolderView);
        detailDesHolder.setDataAndRefreshHolderView(mListBean);

        //往应用的下载部分 容器内 添加内容
        DetailDownloadHolder detailDownloadHolder = new DetailDownloadHolder();
        detailfldownload.addView(detailDownloadHolder.mHolderView);
        detailDownloadHolder.setDataAndRefreshHolderView(mListBean);

        //添加观察者到观察者集合中
        DownLoadManager.getInstance().addObserver(detailDownloadHolder);
        return successView;
    }

    private void init() {
        mPackageName = getIntent().getStringExtra("packageName");
        Toast.makeText(this, mPackageName, Toast.LENGTH_SHORT).show();
    }
}
