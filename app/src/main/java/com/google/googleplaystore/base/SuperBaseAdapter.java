package com.google.googleplaystore.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by DH on 2017/7/11.
 */

public abstract class SuperBaseAdapter<T> extends MyBaseAdapter {
    public SuperBaseAdapter(List datas) {
        super(datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder=null;
        if (convertView == null) {
            //创建holder对象
            holder = getSpecialBaseHolder();
        }else {
            holder= (BaseHolder) convertView.getTag();
        }
        //得到数据,然后绑定数据
        Object data = mDatas.get(position);
        holder.setDataAndRefreshHolderView(data);
        return holder.mHolderView;
    }

    public abstract BaseHolder getSpecialBaseHolder();
}
