package com.google.googleplaystore.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.utils.UIUtils;

/**
 * Created by DH on 2017/7/14.
 */

public class LoadMoreHolder extends BaseHolder<Integer> {

    public static final int LOADMORE_LOADING=0;
    public static final int LOADMORE_ERROR=1;
    public static final int LOADMORE_NONE=2;
    private LinearLayout mItemLoadmoreContainerLoading;
    private TextView mItemLoadmoreTvRetry;
    private LinearLayout mItemLoadmoreContainerRetry;

    /**
     * 刷新UI  传递进来的数据类型有什么用?--->决定ui的具体展现
     * @param curState
     */
    @Override
    protected void refreshHolderView(Integer curState) {
        //首先隐藏所有的视图
        mItemLoadmoreContainerLoading.setVisibility(View.GONE);
        mItemLoadmoreContainerRetry.setVisibility(View.GONE);
        switch (curState) {
            case LOADMORE_LOADING:
                mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
                break;
            case LOADMORE_ERROR:
                mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
                break;
            default :
                break;
        }
    }

    @Override
    public View initHolderView() {
        View holderView = LayoutInflater.from(UIUtils.getContext()).inflate( R.layout.item_loadmore, null,false);
        mItemLoadmoreContainerLoading = (LinearLayout) holderView.findViewById(R.id.item_loadmore_container_loading);
        mItemLoadmoreTvRetry = (TextView) holderView.findViewById(R.id.item_loadmore_tv_retry);
        mItemLoadmoreContainerRetry = (LinearLayout) holderView.findViewById(R.id.item_loadmore_container_retry);
        return holderView;
    }
}
