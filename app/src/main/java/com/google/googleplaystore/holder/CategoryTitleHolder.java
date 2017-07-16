package com.google.googleplaystore.holder;

import android.view.View;
import android.widget.TextView;

import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.bean.CategoryInfoBean;
import com.google.googleplaystore.utils.UIUtils;

/**
 * Created by DH on 2017/7/16.
 */

public class CategoryTitleHolder extends BaseHolder<CategoryInfoBean> {

    private TextView mTvTitle;

    @Override
    protected void refreshHolderView(CategoryInfoBean data) {
        mTvTitle.setText(data.title);
    }

    @Override
    public View initHolderView() {
        mTvTitle = new TextView(UIUtils.getContext());
        int padding = UIUtils.dip2Px(5);
        mTvTitle.setPadding(padding,padding,padding,padding);
        return mTvTitle;
    }
}
