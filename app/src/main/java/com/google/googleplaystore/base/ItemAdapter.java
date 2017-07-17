package com.google.googleplaystore.base;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

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
        Toast.makeText(UIUtils.getContext(), "条目被点击了", Toast.LENGTH_SHORT).show();
    }
}
