package com.google.googleplaystore.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.googleplaystore.Constants.Constants;
import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.bean.HomeBean;
import com.google.googleplaystore.utils.StringUtils;
import com.google.googleplaystore.utils.UIUtils;
import com.squareup.picasso.Picasso;

/**
 *  Created by DH on 2017/7/11.
 */

public class HomeHolder extends BaseHolder<HomeBean.ListBean>{
    private ImageView mItemAppInfoIvIcon;
    private TextView mItemAppInfoTvTitle;
    private RatingBar mItemAppInfoRbStars;
    private TextView mItemAppInfoTvSize;
    private TextView mItemAppInfoTvDes;

    /**
     * @return 当前item对应的根视图View
     * 初始化holderView,决定所能提供的视图长什么样子
     */
    public View initHolderView() {
        View itemView = View.inflate(UIUtils.getContext(), R.layout.item_home, null);
        //初始化孩子控件
        mItemAppInfoIvIcon = (ImageView) itemView.findViewById(R.id.item_appinfo_iv_icon);
        mItemAppInfoTvTitle = (TextView) itemView.findViewById(R.id.item_appinfo_tv_title);
        mItemAppInfoRbStars = (RatingBar) itemView.findViewById(R.id.item_appinfo_rb_stars);
        mItemAppInfoTvSize = (TextView) itemView.findViewById(R.id.item_appinfo_tv_size);
        mItemAppInfoTvDes = (TextView) itemView.findViewById(R.id.item_appinfo_tv_des);
        return itemView;
    }
    @Override
    protected void refreshHolderView(HomeBean.ListBean data) {
        mItemAppInfoTvTitle.setText(data.name);
        mItemAppInfoRbStars.setRating(data.stars);
        mItemAppInfoTvSize.setText(StringUtils.formatFileSize(data.size));
        mItemAppInfoTvDes.setText(data.des);
        String url= Constants.URLS.IMGURL+data.iconUrl;
        Picasso.with(UIUtils.getContext()).load(url).into(mItemAppInfoIvIcon);
    }
}
