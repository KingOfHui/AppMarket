package com.google.googleplaystore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.googleplaystore.utils.UIUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by DH on 2017/7/9.
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPagerController mLoadingPager;

    public LoadingPagerController getLoadingPager() {
        return mLoadingPager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPager = new LoadingPagerController(UIUtils.getContext()) {
            @Override
            protected View initSuccessView() {
                return BaseFragment.this.initSuccessView();
            }

            @Override
            public LoadedResult initData() {
                return BaseFragment.this.initData();
            }
        };
        return mLoadingPager;
    }

    protected abstract LoadingPagerController.LoadedResult initData();

    public abstract View initSuccessView();

    /**
     * @param resObj
     * @return 校验请求回来的数据
     */
    public LoadingPagerController.LoadedResult checkResult(Object resObj) {
        if (resObj==null) {
            return LoadingPagerController.LoadedResult.EMPTY;
        }
        if (resObj instanceof List) {
            if (((List) resObj).size() == 0) {
                return LoadingPagerController.LoadedResult.EMPTY;
            }
        }
        if (resObj instanceof Map) {
            if (((Map) resObj).size() == 0) {
                return LoadingPagerController.LoadedResult.EMPTY;
            }
        }
        return LoadingPagerController.LoadedResult.SUCCESS;
    }
}
