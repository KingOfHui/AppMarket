package com.google.googleplaystore.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


/**
 * Created by DH on 2017/7/20.
 */

public class ProgressBtn extends AppCompatButton {
    private boolean isProgressEnable=true;
    private long mMax=100;
    private long mProgress;
    private ColorDrawable mBlueBg;
    private boolean mIsProgressEnable;

//    public void setProgressEnable(boolean progressEnable) {
//        isProgressEnable = progressEnable;
//    }

    public void setMax(long max) {
        mMax = max;
    }

    public void setProgress(long progress) {
        mProgress = progress;
        invalidate();
    }

    public void setBlueBg(ColorDrawable blueBg) {
        mBlueBg = blueBg;
    }

    public ProgressBtn(Context context) {
        this(context,null);
    }

    public ProgressBtn(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isProgressEnable) {
            if (mBlueBg == null) {
                mBlueBg = new ColorDrawable(Color.BLUE);
            }
            int left=0;
            int top=0;
            int right= (int) (mProgress*1.0f/mMax*getMeasuredWidth()+.5f);
            int bottom=getBottom();
            mBlueBg.setBounds(left,top,right,bottom);
            mBlueBg.draw(canvas);
        }
        super.onDraw(canvas);//默认绘制
    }

    public void setIsProgressEnable(boolean isProgressEnable) {
        mIsProgressEnable = isProgressEnable;
    }
}
