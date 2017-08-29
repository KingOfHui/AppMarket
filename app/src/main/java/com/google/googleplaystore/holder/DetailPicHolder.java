package com.google.googleplaystore.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.googleplaystore.Constants.Constants;
import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.bean.HomeBean;
import com.google.googleplaystore.utils.UIUtils;
import com.google.googleplaystore.views.RatioLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DH on 2017/7/17.
 */

public class DetailPicHolder extends BaseHolder<HomeBean.ListBean> {

    private android.widget.LinearLayout appdetailpicivcontainer;

    @Override
    public View initHolderView() {
        View holderView= LayoutInflater.from(UIUtils.getContext()).inflate( R.layout.item_detail_pic,null,false);
        this.appdetailpicivcontainer = (LinearLayout) holderView.findViewById(R.id.app_detail_pic_iv_container);
        return holderView;
    }
    @Override
    protected void refreshHolderView(HomeBean.ListBean data) {
        //往appdetailpicivcontainer容器中添加内容
        List<String> screenUrls = data.screen;
        for (int i = 0; i < screenUrls.size(); i++) {
            String url = screenUrls.get(i);
            ImageView iv = new ImageView(UIUtils.getContext());

            //创建ratioLayout
            RatioLayout ratioLayout = new RatioLayout(UIUtils.getContext());
            ratioLayout.setRelative(RatioLayout.RELATIVE_WIDTH);
            ratioLayout.setPicRatio((float)150/250);
            //添加图片到ratioLayout中
            ratioLayout.addView(iv);

            //宽度已知-->屏幕的1/3
            int screenWidth = UIUtils.getResources().getDisplayMetrics().widthPixels;
            screenWidth = screenWidth - UIUtils.dip2Px(20);
            int width=screenWidth/3;

            int height=LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            if (i != 0) {
                params.leftMargin = UIUtils.dip2Px(4);
            }
            appdetailpicivcontainer.addView(ratioLayout,params);
            //图片的加载
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGURL+url).into(iv);
        }
    }
}
