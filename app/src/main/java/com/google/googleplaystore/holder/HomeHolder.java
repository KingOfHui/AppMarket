package com.google.googleplaystore.holder;

import android.view.View;
import android.widget.TextView;

import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.utils.UIUtils;

/**
 *  Created by DH on 2017/7/11.
 */

public class HomeHolder extends BaseHolder<String>{
    private TextView mTv2;
    private TextView mTv1;

    /**
     * @return 当前item对应的根视图View
     * 初始化holderView,决定所能提供的视图长什么样子
     */
    public View initHolderView() {
        View itemView = View.inflate(UIUtils.getContext(), R.layout.item_temp, null);
        //初始化孩子控件
        mTv1 = (TextView) itemView.findViewById(R.id.tv1);
        mTv2 = (TextView) itemView.findViewById(R.id.tv2);
        return itemView;
    }
    @Override
    protected void refreshHolderView(String data) {
        mTv1.setText("我是头"+data);
        mTv2.setText("我是尾"+data);
    }
}
