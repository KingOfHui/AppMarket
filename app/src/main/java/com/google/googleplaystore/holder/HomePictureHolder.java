package com.google.googleplaystore.holder;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.googleplaystore.R;
import com.google.googleplaystore.base.BaseHolder;
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
    @Override
    protected void refreshHolderView(List<String> pictureUrls) {
        mPictureUrls=pictureUrls;
        itemhomepicturepager.setAdapter(new HomePicturePagerAdapter());
    }

    @Override
    public View initHolderView() {
        View view= LayoutInflater.from(UIUtils.getContext()).inflate( R.layout.item_home_pictures, null,false);
        this.itemhomepicturecontainerindicator = (LinearLayout) view.findViewById(R.id.item_home_picture_container_indicator);
        this.itemhomepicturepager = (ChildViewPager) view.findViewById(R.id.item_home_picture_pager);

        return view;
    }
    class HomePicturePagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            if (mPictureUrls != null) {
                return mPictureUrls.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            String url = mPictureUrls.get(position);
            Picasso.with(UIUtils.getContext()).load(url).into(iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
