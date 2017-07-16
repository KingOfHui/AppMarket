package com.google.googleplaystore.holder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.googleplaystore.Constants.Constants;
import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.bean.SubjectBean;
import com.google.googleplaystore.utils.UIUtils;
import com.squareup.picasso.Picasso;

/**
 *  Created by DH on 2017/7/15.
 */

public class SubjectHolder extends BaseHolder<SubjectBean> {
    private ImageView mItemSubjectIvIcon;
    TextView mItemSubjectTvTitle;


    @Override
    public View initHolderView() {
        View holderView= View.inflate(UIUtils.getContext(), R.layout.item_subject,null);
        this.mItemSubjectTvTitle = (TextView) holderView.findViewById(R.id.item_subject_tv_title);
        this.mItemSubjectIvIcon = (ImageView) holderView.findViewById(R.id.item_subject_iv_icon);
        return holderView;
    }
    @Override
    protected void refreshHolderView(SubjectBean data) {
        Log.d("SubjectHolder", data.des);
        mItemSubjectTvTitle.setText(data.des);
        Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGURL+data.url).into(mItemSubjectIvIcon);
    }
}
