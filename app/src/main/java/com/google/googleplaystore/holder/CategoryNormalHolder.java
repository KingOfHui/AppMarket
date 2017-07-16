package com.google.googleplaystore.holder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.googleplaystore.Constants.Constants;
import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.bean.CategoryInfoBean;
import com.google.googleplaystore.utils.UIUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by DH on 2017/7/16.
 */

public class CategoryNormalHolder extends BaseHolder<CategoryInfoBean> {
    private android.widget.ImageView itemcategoryicon1;
    private android.widget.TextView itemcategoryname1;
    private android.widget.LinearLayout itemcategoryitem1;
    private android.widget.ImageView itemcategoryicon2;
    private android.widget.TextView itemcategoryname2;
    private android.widget.LinearLayout itemcategoryitem2;
    private android.widget.ImageView itemcategoryicon3;
    private android.widget.TextView itemcategoryname3;
    private android.widget.LinearLayout itemcategoryitem3;

    @Override
    public View initHolderView() {
        View holderView= LayoutInflater.from(UIUtils.getContext()).inflate( R.layout.item_category_normal,null,false);
        this.itemcategoryitem3 = (LinearLayout) holderView.findViewById(R.id.item_category_item_3);
        this.itemcategoryname3 = (TextView) holderView.findViewById(R.id.item_category_name_3);
        this.itemcategoryicon3 = (ImageView) holderView.findViewById(R.id.item_category_icon_3);
        this.itemcategoryitem2 = (LinearLayout) holderView.findViewById(R.id.item_category_item_2);
        this.itemcategoryname2 = (TextView) holderView.findViewById(R.id.item_category_name_2);
        this.itemcategoryicon2 = (ImageView) holderView.findViewById(R.id.item_category_icon_2);
        this.itemcategoryitem1 = (LinearLayout) holderView.findViewById(R.id.item_category_item_1);
        this.itemcategoryname1 = (TextView) holderView.findViewById(R.id.item_category_name_1);
        this.itemcategoryicon1 = (ImageView) holderView.findViewById(R.id.item_category_icon_1);
        return holderView;
    }
    @Override
    protected void refreshHolderView(CategoryInfoBean data) {
        refreshUI(data.name1, data.url1, itemcategoryname1, itemcategoryicon1);
        refreshUI(data.name2, data.url2, itemcategoryname2, itemcategoryicon2);
        refreshUI(data.name3, data.url3, itemcategoryname3, itemcategoryicon3);
    }

    private void refreshUI(String name, String url, TextView tvName, ImageView ivIcon) {
        if(TextUtils.isEmpty(name)&& TextUtils.isEmpty(url)){
            ViewParent parent = tvName.getParent();
            ((ViewGroup)parent).setVisibility(View.INVISIBLE);
        }else{
            tvName.setText(name);
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGURL+url).into(ivIcon);
            ViewParent parent = tvName.getParent();
            ((ViewGroup)parent).setVisibility(View.VISIBLE);
        }

    }

}
