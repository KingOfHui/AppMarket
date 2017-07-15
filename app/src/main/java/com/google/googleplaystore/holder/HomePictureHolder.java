package com.google.googleplaystore.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.googleplaystore.Constants.Constants;
import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.base.MyApplication;
import com.google.googleplaystore.utils.UIUtils;
import com.google.googleplaystore.views.ChildViewPager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DH on 2017/7/14.
 */

public class HomePictureHolder extends BaseHolder<List<String>> {

    List<String> mPictureUrls;
    private com.google.googleplaystore.views.ChildViewPager itemhomepicturepager;
    private LinearLayout itemhomepicturecontainerindicator;
    private AutoSwitchTask mAutoSwitchTask;

    class AutoSwitchTask implements Runnable {
        public void start() {
            MyApplication.getMainThreadHandler().postDelayed(this, 1500);
        }

        public void stop() {
            MyApplication.getMainThreadHandler().removeCallbacks(this);
        }

        @Override
        public void run() {
            int currentItem = itemhomepicturepager.getCurrentItem();
            currentItem++;
            itemhomepicturepager.setCurrentItem(currentItem);
            start();
        }
    }

    @Override
    public View initHolderView() {
        View view = LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_home_pictures, null, false);
        this.itemhomepicturecontainerindicator = (LinearLayout) view.findViewById(R.id.item_home_picture_container_indicator);
        this.itemhomepicturepager = (ChildViewPager) view.findViewById(R.id.item_home_picture_pager);
        return view;
    }

    @Override
    protected void refreshHolderView(List<String> pictureUrls) {
        mPictureUrls = pictureUrls;
        itemhomepicturepager.setAdapter(new HomePicturePagerAdapter());
        for (int i = 0; i < mPictureUrls.size(); i++) {
            ImageView ivIndicator = new ImageView(UIUtils.getContext());
            //设置默认时候的点的src
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            //选择默认选中的第一个点
            if (i == 0) {
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
            int width = UIUtils.px2Dip(6);
            int height = UIUtils.px2Dip(6);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = width;
            params.bottomMargin = width;
            itemhomepicturecontainerindicator.addView(ivIndicator, params);
        }
        //监听viewPager的页面切换操作
        itemhomepicturepager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //处理添加无限轮播后的position
                position = position % mPictureUrls.size();

                //控制Indicator选中效果
                for (int i = 0; i < mPictureUrls.size(); i++) {
                    ImageView ivIndicator = (ImageView) itemhomepicturecontainerindicator.getChildAt(i);
                    //还原默认效果
                    ivIndicator.setImageResource(R.drawable.indicator_normal);
                    if (position == i) {
                        ivIndicator.setImageResource(R.drawable.indicator_selected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置ViewPager页面的初始位置
        int curItem = Integer.MAX_VALUE / 2;
        //对curItem做偏差处理
        int diff = Integer.MAX_VALUE / 2 % mPictureUrls.size();
        curItem = curItem - diff;
        itemhomepicturepager.setCurrentItem(curItem);
        if (mAutoSwitchTask == null) {
            mAutoSwitchTask = new AutoSwitchTask();
            mAutoSwitchTask.start();
        }
        //按下去的时候停止轮播
        itemhomepicturepager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAutoSwitchTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mAutoSwitchTask.stop();
                        break;
                    case  MotionEvent.ACTION_UP:
                        mAutoSwitchTask.start();
                        break;
                }
                return false;
            }
        });
    }

    class HomePicturePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mPictureUrls != null) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //处理添加无限轮播后的position
            position = position % mPictureUrls.size();

            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            String url = mPictureUrls.get(position);
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGURL + url).into(iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
