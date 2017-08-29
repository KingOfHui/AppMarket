package com.google.googleplaystore.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.bean.HomeBean;
import com.google.googleplaystore.utils.UIUtils;

/**
 * Created by DH on 2017/7/17.
 */

public class DetailDesHolder extends BaseHolder<HomeBean.ListBean> implements View.OnClickListener {

    private android.widget.TextView appdetaildestvdes;
    private android.widget.TextView appdetaildestvauthor;
    private android.widget.ImageView appdetaildesivarrow;
    private int mAppDetailDesTvTvDesHeight;
    private HomeBean.ListBean mItemBean;

    @Override
    public View initHolderView() {
        View holderView = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_detail_des, null, false);
        this.appdetaildesivarrow = (ImageView) holderView.findViewById(R.id.app_detail_des_iv_arrow);
        this.appdetaildestvauthor = (TextView) holderView.findViewById(R.id.app_detail_des_tv_author);
        this.appdetaildestvdes = (TextView) holderView.findViewById(R.id.app_detail_des_tv_des);
        holderView.setOnClickListener(this);
        return holderView;
    }

    @Override
    protected void refreshHolderView(HomeBean.ListBean data) {
        //保存数据为成员变量
        mItemBean = data;
        appdetaildestvdes.setText(data.des);
        appdetaildestvauthor.setText(data.author);
        appdetaildestvdes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //默认折叠appdetaildestvdes
                appdetaildestvdes.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        changeDetailDesHeight(true);
    }

    private boolean isOpen = true;

    private void changeDetailDesHeight(boolean isAnimation) {
        if (mAppDetailDesTvTvDesHeight == 0) {
            mAppDetailDesTvTvDesHeight = appdetaildestvdes.getMeasuredHeight();
        }
        if (isOpen) {
            //折叠appdetaildestvdes高度,应有的高度-->7行的高度
            int start = mAppDetailDesTvTvDesHeight;
            int end = getShortLineHeight(7, mItemBean.des);//7行的高度
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                appdetaildestvdes.setHeight(end);
            }
        } else {
            //展开appdetaildestvdes高度,7行的高度-->应有的高度
            int start = getShortLineHeight(7, mItemBean.des);
            int end = mAppDetailDesTvTvDesHeight;
            if (isAnimation) {
                doAnimation(start, end);
            } else {
                appdetaildestvdes.setHeight(end);
            }
        }
        isOpen = !isOpen;
    }

    private void doAnimation(int start, int end) {
        ObjectAnimator animator = ObjectAnimator.ofInt(appdetaildestvdes, "height", start, end);
        animator.start();
        //降头跟着转
        if(isOpen){
            ObjectAnimator.ofFloat(appdetaildesivarrow,"rotation",180,0).start();
        }else{
            ObjectAnimator.ofFloat(appdetaildesivarrow,"rotation",0,180).start();
        }
        //监听动画执行完成
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束-->找到外层scrollView-->完成自动滚动
                ViewParent parent = appdetaildestvdes.getParent();//得到父容器
                while (true) {
                    parent=parent.getParent();//父容器的父容器
                    if (parent instanceof ScrollView) {
                        ((ScrollView) parent).fullScroll(View.FOCUS_DOWN);
                        break;
                    }
                    if (parent == null) {
                        break;
                    }
                }
            }
        });
    }

    /**
     * 得到指定行高,指定内容的textView的高度
     */
    private int getShortLineHeight(int lineHeight, String content) {
        TextView tempTextView = new TextView(UIUtils.getContext());
        tempTextView.setLines(lineHeight);
        tempTextView.setText(content);
        tempTextView.measure(0,0);
        return tempTextView.getMeasuredHeight();
    }
}
