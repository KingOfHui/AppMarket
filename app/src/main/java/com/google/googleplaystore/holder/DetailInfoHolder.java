package com.google.googleplaystore.holder;

import android.view.LayoutInflater;
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
 * Created by DH on 2017/7/17.
 */

public class DetailInfoHolder extends BaseHolder<HomeBean.ListBean> {

    private android.widget.ImageView appdetailinfoivicon;
    private android.widget.TextView appdetailinfotvname;
    private android.widget.RatingBar appdetailinforbstar;
    private android.widget.TextView appdetailinfotvdownloadnum;
    private android.widget.TextView appdetailinfotvversion;
    private android.widget.TextView appdetailinfotvtime;
    private android.widget.TextView appdetailinfotvsize;

    @Override
    public View initHolderView() {
        View holderView= LayoutInflater.from(UIUtils.getContext()).inflate( R.layout.item_detail_info,null,false);
        this.appdetailinfotvsize = (TextView) holderView.findViewById(R.id.app_detail_info_tv_size);
        this.appdetailinfotvtime = (TextView) holderView.findViewById(R.id.app_detail_info_tv_time);
        this.appdetailinfotvversion = (TextView) holderView.findViewById(R.id.app_detail_info_tv_version);
        this.appdetailinfotvdownloadnum = (TextView) holderView.findViewById(R.id.app_detail_info_tv_downloadnum);
        this.appdetailinforbstar = (RatingBar) holderView.findViewById(R.id.app_detail_info_rb_star);
        this.appdetailinfotvname = (TextView) holderView.findViewById(R.id.app_detail_info_tv_name);
        this.appdetailinfoivicon = (ImageView) holderView.findViewById(R.id.app_detail_info_iv_icon);

        return holderView;
    }
    @Override
    protected void refreshHolderView(HomeBean.ListBean data) {
        String date = UIUtils.getContext().getString(R.string.detail_date, data.date);
        String downloadNum = UIUtils.getContext().getString(R.string.detail_downloadnum, data.downloadNum);
        String size = UIUtils.getContext().getString(R.string.detail_size, StringUtils.formatFileSize(data.size));
        String version = UIUtils.getContext().getString(R.string.detail_version, data.version);

        appdetailinfotvname.setText(data.name);
        appdetailinfotvversion.setText(version);
        appdetailinfotvtime.setText(date);
        appdetailinfotvsize.setText(size);
        appdetailinfotvdownloadnum.setText(downloadNum);

        //retingbar
        appdetailinforbstar.setRating(data.stars);

        //图标
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGURL+data.iconUrl).into(appdetailinfoivicon);
    }

}
