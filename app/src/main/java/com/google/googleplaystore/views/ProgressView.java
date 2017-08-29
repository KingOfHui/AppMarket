package com.google.googleplaystore.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.googleplaystore.R;
import com.google.googleplaystore.utils.UIUtils;

/**
 * Created by DH on 2017/7/20.
 */

public class ProgressView extends LinearLayout {

    private boolean isProgressEnable=true;
    private long mMax=100;
    private long mProgress;
    private Paint mPaint;

    private android.widget.ImageView ivIcon;
    private android.widget.TextView tvNote;

    /**
     * 设置图标
     * @param resId
     */
    public void setIvIcon(int resId) {
        ivIcon.setImageResource(resId);
    }

    /**
     * 设置提示文本
     * @param content
     */
    public void setTvNote(String content) {
        this.tvNote.setText(content);
    }

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view= LayoutInflater.from(UIUtils.getContext()).inflate( R.layout.inflate_progressview,null,false);
        this.tvNote = (TextView) view.findViewById(R.id.tvNote);
        this.ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//绘制背景
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);//绘制图标文本
        if (isProgressEnable) {
            if (mPaint == null) {
                mPaint = new Paint();
                mPaint.setColor(Color.BLUE);
                mPaint.setStrokeWidth(3);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setAntiAlias(true);
            }
            //构建绘制范围:由于是贴着图片绘制进度,因此范围即圆形图片的左上右下
            int left=ivIcon.getLeft();
            int top=ivIcon.getTop();
            int right=ivIcon.getRight();
            int bottom=ivIcon.getBottom();
            RectF oval = new RectF(left, top, right, bottom);
            //绘制起始角度
            float startAngle=-90;
            //弧形 的角度
            float sweepAngle=mProgress*1.0f/mMax*360;//动态计算
            //是否显示两条中心连线
            canvas.drawArc(oval,startAngle,sweepAngle,false,mPaint);
        }
    }
}
