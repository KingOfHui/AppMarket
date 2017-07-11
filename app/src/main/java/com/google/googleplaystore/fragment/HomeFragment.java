package com.google.googleplaystore.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import com.google.googleplaystore.base.BaseFragment;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.base.LoadingPagerController;
import com.google.googleplaystore.base.SuperBaseAdapter;
import com.google.googleplaystore.holder.HomeHolder;
import com.google.googleplaystore.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DH on 2017/7/8.
 */

public class HomeFragment extends BaseFragment {

    private ArrayList<String> mDatas;

    @Override
    protected LoadingPagerController.LoadedResult initData() {
        SystemClock.sleep(2000);
//        Random random = new Random();
//        int index = random.nextInt(3);
//        LoadingPagerController.LoadedResult[] loadedResults = {LoadingPagerController.LoadedResult.SUCCESS, LoadingPagerController.LoadedResult.EMPTY, LoadingPagerController.LoadedResult.ERROR};
        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDatas.add("+i");
        }
        return LoadingPagerController.LoadedResult.SUCCESS;
    }

    @Override
    public View initSuccessView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setAdapter(new HomeAdapter(mDatas));
        return listView;
    }
    private class HomeAdapter extends SuperBaseAdapter<String>{
        private HomeAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public BaseHolder getSpecialBaseHolder() {
            return new HomeHolder();
        }
    }
}
