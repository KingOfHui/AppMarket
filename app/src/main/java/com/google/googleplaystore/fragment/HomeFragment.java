package com.google.googleplaystore.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.googleplaystore.base.BaseFragment;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.base.LoadingPagerController;
import com.google.googleplaystore.base.SuperBaseAdapter;
import com.google.googleplaystore.bean.HomeBean;
import com.google.googleplaystore.holder.HomeHolder;
import com.google.googleplaystore.protocol.HomeProtocol;
import com.google.googleplaystore.utils.UIUtils;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *  Created by DH on 2017/7/8.
 */

public class HomeFragment extends BaseFragment {

    private List<String> mDatas;
    private OkHttpClient mOkHttpClient;
    private Request mGetRequest;
    private List<HomeBean.ListBean> mMItemBeans;
    private List<String> mMPictures;

    @Override
    protected LoadingPagerController.LoadedResult initData() {
        try {
            HomeProtocol homeProtocol = new HomeProtocol();
            HomeBean homeBean = (HomeBean) homeProtocol.loadData(0);
            LoadingPagerController.LoadedResult state = checkResult(homeBean);
                if (state != LoadingPagerController.LoadedResult.SUCCESS) {
                    return state;
                }
                state = checkResult(homeBean.list);
                if (state != LoadingPagerController.LoadedResult.SUCCESS) {
                    return state;
                }
                //走到这里来说明是成功的
                //保存数据到成员变量
                mMItemBeans = homeBean.list;
                mMPictures = homeBean.picture;
                return state;

        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPagerController.LoadedResult.ERROR;
        }
    }

    @Override
    public View initSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setAdapter(new HomeAdapter(mMItemBeans));
        return listView;
    }
    private class HomeAdapter extends SuperBaseAdapter<HomeBean.ListBean>{
        private HomeAdapter(List<HomeBean.ListBean> datas) {
            super(datas);
        }

        @Override
        public BaseHolder getSpecialBaseHolder() {
            return new HomeHolder();
        }
    }
}
