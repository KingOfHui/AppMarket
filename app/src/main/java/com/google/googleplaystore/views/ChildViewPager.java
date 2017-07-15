package com.google.googleplaystore.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by DH on 2017/7/15.
 */

public class ChildViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录手指按下的坐标
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //记录手指移动的坐标
                float moveX = ev.getRawX();
                float moveY = ev.getRawY();
                //计算手指移动的偏移量
                int diffX= (int) (moveX-mDownX+.5f);
                int diffY= (int) (moveY-mDownY+.5f);
                if(Math.abs(diffX)>Math.abs(diffY)){
                    //横向滚动,孩子处理事件-->申请父控件不要拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    //纵向滚动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            default :

                break;
        }
        return super.onTouchEvent(ev);
    }
}
