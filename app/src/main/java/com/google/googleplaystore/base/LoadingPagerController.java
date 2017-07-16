package com.google.googleplaystore.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import com.google.googleplaystore.R;
import com.google.googleplaystore.factory.ThreadPoolProxyFactory;
import com.google.googleplaystore.utils.UIUtils;
/**
 *  Created by DH on 2017/7/9.
 */

public abstract class LoadingPagerController extends FrameLayout {

    public static final int LOADING_STATE = 0;
    public static final int EMPTY_STATE = 1;
    public static final int ERROR_STATE = 2;
    public static final int SUCCESS_STATE = 3;

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mSuccessView;
    private LoadDataTask mLoadDataTask;

    public LoadingPagerController(@NonNull Context context) {
        super(context);
        initContentView();
    }

    private void initContentView() {
        //加载中视图
        mLoadingView = View.inflate(UIUtils.getContext(), R.layout.pager_loading, null);
        addView(mLoadingView);
        //空视图
        mEmptyView = View.inflate(UIUtils.getContext(), R.layout.pager_empty, null);
        addView(mEmptyView);
        //错误视图
        mErrorView = View.inflate(UIUtils.getContext(), R.layout.pager_error, null);
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerData();
            }
        });
        addView(mErrorView);
        //通过状态更新ui
        refreshViewByState();
    }

    protected abstract View initSuccessView();

    private int state = LOADING_STATE;

    private void refreshViewByState() {
        if (state == LOADING_STATE) {
            mLoadingView.setVisibility(VISIBLE);
        } else {
            mLoadingView.setVisibility(GONE);
        }
        if (state == EMPTY_STATE) {
            mEmptyView.setVisibility(VISIBLE);
        } else {
            mEmptyView.setVisibility(GONE);
        }
        if (state == ERROR_STATE) {
            mErrorView.setVisibility(VISIBLE);
        } else {
            mErrorView.setVisibility(GONE);
        }
        if (mSuccessView == null && state == SUCCESS_STATE) {
            mSuccessView = initSuccessView();
            addView(mSuccessView);
        }
        if (mSuccessView != null) {
            if (state == SUCCESS_STATE) {
                mSuccessView.setVisibility(VISIBLE);
            } else {
                mSuccessView.setVisibility(GONE);
            }
        }
    }
    public void triggerData() {
        if (state == SUCCESS_STATE) {
            return;
        }
        if(mLoadDataTask==null) {
            state = LOADING_STATE;
            refreshViewByState();
            mLoadDataTask = new LoadDataTask();
//            new Thread(mLoadDataTask).start();
            ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadDataTask);
        }
    }
    private class LoadDataTask implements Runnable {
        @Override
        public void run() {
            LoadedResult result = initData();
            state = result.getState();
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    refreshViewByState();
                }
            });
            mLoadDataTask=null;
        }
    }

    public abstract LoadedResult initData();

    public enum LoadedResult{
        SUCCESS(SUCCESS_STATE),ERROR(ERROR_STATE),EMPTY(EMPTY_STATE);

        private int state;

        public int getState() {
            return state;
        }

        LoadedResult(int state) {
            this.state= state;
        }
    }
}
