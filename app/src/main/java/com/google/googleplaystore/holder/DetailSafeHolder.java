package com.google.googleplaystore.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.googleplaystore.Constants.Constants;
import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.bean.HomeBean;
import com.google.googleplaystore.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 *  Created by DH on 2017/7/17.
 */

public class DetailSafeHolder extends BaseHolder<HomeBean.ListBean> {

    private android.widget.ImageView appdetailsafeivarrow;
    private android.widget.LinearLayout appdetailsafepiccontainer;
    private android.widget.LinearLayout appdetailsafedescontainer;
    private boolean isOpen=true;
    @Override
    public View initHolderView() {
        View holderView= LayoutInflater.from(UIUtils.getContext()).inflate( R.layout.item_detail_safe,null,false);
        this.appdetailsafedescontainer = (LinearLayout) holderView.findViewById(R.id.app_detail_safe_des_container);
        this.appdetailsafepiccontainer = (LinearLayout) holderView.findViewById(R.id.app_detail_safe_pic_container);
        this.appdetailsafeivarrow = (ImageView) holderView.findViewById(R.id.app_detail_safe_iv_arrow);

        holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSafeDesContainerHeight(true);
            }
        });
        return holderView;
    }

    private void changeSafeDesContainerHeight(boolean isAnimation) {
        if (isOpen) {
            //折叠 appdetailsafedescontainer高度,应有的高度-->0
            int start=appdetailsafedescontainer.getMeasuredHeight();
            int end=0;
            if (isAnimation) {
                doAnimation(start, end);
            }else {
                ViewGroup.LayoutParams layoutParams = appdetailsafedescontainer.getLayoutParams();
                layoutParams.height=end;
                //重新设置layoutParams
                appdetailsafedescontainer.setLayoutParams(layoutParams);
            }
        }else {
            //展开  appdetailsafedescontainer  高度  0-->应有的高度
            appdetailsafedescontainer.measure(0,0);
            int start=0;
            int end=appdetailsafedescontainer.getMeasuredHeight();
            if (isAnimation) {
                doAnimation(start,end);
            }else {
                ViewGroup.LayoutParams layoutParams = appdetailsafedescontainer.getLayoutParams();
                layoutParams.height=end;
                //重新设置layoutParams
                appdetailsafedescontainer.setLayoutParams(layoutParams);
            }
        }
        isOpen=!isOpen;
    }

    private void doAnimation(float start, float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.start();

        //得到动画执行过程中的渐变值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float tempHeight = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = appdetailsafedescontainer.getLayoutParams();
                layoutParams.height= (int) tempHeight;
                appdetailsafedescontainer.setLayoutParams(layoutParams);
            }
        });
        //箭头跟着旋转
        if (isOpen) {
            ObjectAnimator.ofFloat(appdetailsafeivarrow,"rotation",180,0).start();
        }else {
            ObjectAnimator.ofFloat(appdetailsafeivarrow, "rotation", 0, 180).start();
        }
    }

    @Override
    protected void refreshHolderView(HomeBean.ListBean data) {
        List<HomeBean.ListBean.ItemSafeBean> itemSafeBeans = data.safe;
        for (int i = 0; i < itemSafeBeans.size(); i++) {
            HomeBean.ListBean.ItemSafeBean itemSafeBean = itemSafeBeans.get(i);
            String safeDes = itemSafeBean.safeDes;
            int safeDesColor = itemSafeBean.safeDesColor;
            String safeDesUrl = itemSafeBean.safeDesUrl;
            String safeUrl = itemSafeBean.safeUrl;

            /*-------------往appdetailsafePicContainer容器中动态加载孩子---------------*/
            ImageView ivIcon = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGURL+safeUrl).into(ivIcon);
            appdetailsafepiccontainer.addView(ivIcon);

            /*--------------往appdetailsafedescontainer容器中动态加载孩子---------------*/
            LinearLayout line = new LinearLayout(UIUtils.getContext());
            //构建描述文本
            TextView tvDes = new TextView(UIUtils.getContext());
            //设置数据
            tvDes.setText(safeDes);
            //设置文字颜色
            if (safeDesColor == 0) {
                tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_normal));
            }else {
                tvDes.setTextColor(UIUtils.getColor(R.color.app_detail_safe_warning));
            }
            //构建描述图标
            ImageView ivDesIcon = new ImageView(UIUtils.getContext());
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGURL+safeDesUrl).into(ivDesIcon);

            line.addView(tvDes);
            line.addView(ivDesIcon);

            appdetailsafedescontainer.addView(line);
        }
    }
}
