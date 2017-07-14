package com.google.googleplaystore.base;

import android.view.View;

/**
 *  Created by DH on 2017/7/11.
 */

public abstract class BaseHolder<T> {
    public View mHolderView;
    public T mData;

    public BaseHolder() {
//        mData.getClass().getSimpleName();
        mHolderView = initHolderView();
        mHolderView.setTag(this);
    }

    public void setDataAndRefreshHolderView(T data) {
        mData=data;
        refreshHolderView(data);
    }

    protected abstract void refreshHolderView(T data);

    public abstract View initHolderView();
}
