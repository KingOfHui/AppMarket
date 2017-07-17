package com.google.googleplaystore.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.googleplaystore.base.BaseFragment;
import com.google.googleplaystore.base.LoadingPagerController;
import com.google.googleplaystore.protocol.HotProtocol;
import com.google.googleplaystore.utils.UIUtils;
import com.google.googleplaystore.views.FlowLayout;

import java.util.List;
import java.util.Random;

/**
 * Created by DH on 2017/7/8.
 */

public class HotFragment extends BaseFragment {

    private List<String> mDatas;

    @Override
    protected LoadingPagerController.LoadedResult initData() {
        HotProtocol hotProtocol = new HotProtocol();
        try {
            mDatas = hotProtocol.loadData(0);
            return checkResult(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPagerController.LoadedResult.ERROR;
        }
    }

    @Override
    public View initSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
        for (int i = 0; i < mDatas.size(); i++) {
            final String data = mDatas.get(i);
            TextView tv = new TextView(UIUtils.getContext());
            tv.setText(data);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.WHITE);
            int padding = UIUtils.dip2Px(5);
            tv.setPadding(padding,padding,padding,padding);
            //创建了一个默认情况的背景
            GradientDrawable normalBg = new GradientDrawable();
            normalBg.setCornerRadius(10);

            Random random = new Random();
            int alpha=255;
            int red=random.nextInt(170)+30;
            int green=random.nextInt(170)+30;
            int blue=random.nextInt(170)+30;
            int argb = Color.argb(alpha, red, green, blue);
            normalBg.setColor(argb);

            GradientDrawable pressedBg = new GradientDrawable();
            pressedBg.setCornerRadius(10);
            pressedBg.setColor(Color.DKGRAY);

            //创建一个带有状态的背景
            StateListDrawable selectorBg = new StateListDrawable();
            selectorBg.addState(new int[]{-android.R.attr.state_pressed}, normalBg);
            selectorBg.addState(new int[]{android.R.attr.state_pressed},pressedBg);

            tv.setBackground(selectorBg);
            tv.setClickable(true);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), data, Toast.LENGTH_SHORT).show();
                }
            });
            flowLayout.addView(tv);
        }
        scrollView.addView(flowLayout);
        return scrollView;
    }
}
