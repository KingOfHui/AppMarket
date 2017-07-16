package com.google.googleplaystore.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.google.googleplaystore.base.BaseFragment;
import com.google.googleplaystore.base.LoadingPagerController;
import com.google.googleplaystore.protocol.RecommendProtocol;
import com.google.googleplaystore.utils.UIUtils;
import com.google.googleplaystore.views.flyinout.ShakeListener;
import com.google.googleplaystore.views.flyinout.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by DH on 2017/7/8.
 */

public class RecommendFragment extends BaseFragment {

    private static final int PAGESIZE = 15;
    private List<String> mDatas;
    private ShakeListener mShakeListener;
    private RecommendAdapter mAdapter;

    @Override
    protected LoadingPagerController.LoadedResult initData() {
        RecommendProtocol protocol = new RecommendProtocol();
        try {
            mDatas = protocol.loadData(0);
            return checkResult(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPagerController.LoadedResult.ERROR;
        }
    }

    @Override
    public View initSuccessView() {
        final StellarMap stellarMap = new StellarMap(UIUtils.getContext());
        mAdapter = new RecommendAdapter();
        stellarMap.setAdapter(mAdapter);
        //设置展示首页
        stellarMap.setGroup(0,true);
        //解决每一页展示的条目不统一的问题
        stellarMap.setRegularity(15,20);

        //摇一摇切换
        mShakeListener = new ShakeListener(UIUtils.getContext());
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                //切换
                int currentGroup = stellarMap.getCurrentGroup();
                if (currentGroup == mAdapter.getGroupCount() - 1) {
                    currentGroup=0;
                }else {
                    currentGroup++;
                }
                stellarMap.setGroup(currentGroup,true);
            }
        });


        return stellarMap;
    }

    @Override
    public void onResume() {
        if (mShakeListener != null) {
            mShakeListener.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mShakeListener != null) {
            mShakeListener.pause();
        }
        super.onPause();
    }

    class RecommendAdapter implements StellarMap.Adapter{

        @Override
        public int getGroupCount() {//一共多少组
            if(mDatas.size()%PAGESIZE==0){
                return mDatas.size()/PAGESIZE;
            }else{
                return mDatas.size()/PAGESIZE+1;
            }
        }

        @Override
        public int getCount(int group) {//每组多少个孩子
            if(mDatas.size()%PAGESIZE==0){
                return PAGESIZE;
            }else{
                if(group==getGroupCount()-1){
                    return mDatas.size()%PAGESIZE;
                }else{
                    return PAGESIZE;
                }
            }
        }

        @Override
        public View getView(int group, int position, View convertView) {
            //返回具体的样子
            TextView tv = new TextView(UIUtils.getContext());
            int index = group * PAGESIZE + position;
            String data = mDatas.get(index);
            tv.setText(data);
            Random random = new Random();
            tv.setTextSize(random.nextInt(5)+12);
            int alpha=255;
            int red=random.nextInt(170)+30;
            int green=random.nextInt(170)+30;
            int blue=random.nextInt(170)+30;
            int color = Color.argb(alpha, red, green, blue);
            tv.setTextColor(color);
            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            //
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 0;
        }
    }
}
