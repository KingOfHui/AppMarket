package com.google.googleplaystore.fragment;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.googleplaystore.base.BaseFragment;
import com.google.googleplaystore.base.LoadingPagerController;
import com.google.googleplaystore.utils.UIUtils;

import java.util.Random;

/**
 * Created by DH on 2017/7/8.
 */

public class AppFragment extends BaseFragment {

    @Override
    protected LoadingPagerController.LoadedResult initData() {
        SystemClock.sleep(2000);
        Random random = new Random();
        int index = random.nextInt(3);
        LoadingPagerController.LoadedResult[] loadedResults = {LoadingPagerController.LoadedResult.SUCCESS, LoadingPagerController.LoadedResult.EMPTY, LoadingPagerController.LoadedResult.ERROR};
        return loadedResults[index];
    }

    @Override
    public View initSuccessView() {
        TextView tv = new TextView(UIUtils.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.GREEN);
        tv.setText(getClass().getSimpleName());
        return tv;
    }
}
