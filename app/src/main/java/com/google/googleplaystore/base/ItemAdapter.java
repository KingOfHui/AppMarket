package com.google.googleplaystore.base;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.google.googleplaystore.activity.DetailActivity;
import com.google.googleplaystore.bean.HomeBean;
import com.google.googleplaystore.holder.ItemHolder;
import com.google.googleplaystore.utils.UIUtils;

import java.util.List;

/**
 * Created by DH on 2017/7/17.
 */

public class ItemAdapter extends SuperBaseAdapter<HomeBean.ListBean> {
    public ItemAdapter(List<HomeBean.ListBean> datas, AbsListView absListView) {
        super(datas, absListView);
    }

    @Override
    public BaseHolder getSpecialBaseHolder(int position) {
        return new ItemHolder();
    }
    @Override
    public boolean hasLoadMore() {
        return true;
    }

    @Override
    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
        HomeBean.ListBean listBean = (HomeBean.ListBean) mDatas.get(position);
        Intent intent=new Intent(UIUtils.getContext(),DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", listBean.packageName);
        UIUtils.getContext().startActivity(intent);
    }
}
