package com.google.googleplaystore.base;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by DH on 2017/7/11.
 * 针对BaseAdapter简单封装,针对的是其中的3个方法
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> mDatas;

    public MyBaseAdapter(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
