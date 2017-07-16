package com.google.googleplaystore.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.google.googleplaystore.R;

/**
 *  Created by DH on 2017/7/15.
 */

public class RatioLayout extends FrameLayout {
    public static final int RELATIVE_WIDTH = 0;
    public static final int RELATIVE_HEIGHT  = 1;
    private int mRelative=RELATIVE_WIDTH; //相对谁来计算值
    private float mPicRatio; //宽高比例

    public void setPicRatio(float picRatio) {
        mPicRatio = picRatio;
    }

    public void setRelative(int relative) {
        mRelative = relative;
    }

    public RatioLayout(@NonNull Context context) {
        this(context,null);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatioLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //取出自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mPicRatio = typedArray.getFloat(R.styleable.RatioLayout_picRatio, 1);
        mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //自己测量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY&&mRelative==RELATIVE_WIDTH) {
            //得到当前控件的宽度
            int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
            //图片的宽高比=当前控件的宽/当前控件的高
            int selfHeight = (int) (selfWidth/mPicRatio+.5f);
            //保存测量结果
            setMeasuredDimension(selfWidth,selfHeight);

            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();
            //让孩子测量
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureHeight = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec,childHeightMeasureHeight);
        } else if (heightMode == MeasureSpec.EXACTLY&&mRelative==RELATIVE_HEIGHT) {
            //得到控件的高度
            int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
            int selfWidth= (int) (mPicRatio*selfHeight+.5f);
            //保存测量结果
            setMeasuredDimension(selfWidth,selfHeight);

            int childWidth = selfWidth - getPaddingLeft() - getPaddingRight();
            int childHeight = selfHeight - getPaddingTop() - getPaddingBottom();
            //让孩子自己测量
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec,childHeightMeasureSpec);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
