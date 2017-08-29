package com.google.googleplaystore.base;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.googleplaystore.factory.ThreadPoolProxyFactory;
import com.google.googleplaystore.holder.LoadMoreHolder;

import java.util.List;

/**
 * Created by DH on 2017/7/11.
 */

public abstract class SuperBaseAdapter<T> extends MyBaseAdapter implements AdapterView.OnItemClickListener {
    private static final int VIEWTYPE_NORMAL = 1;
    private static final int VIEWTYPE_LOADMORE = 0;
    private AbsListView mAbsListView;
    private LoadMoreHolder mLoadMoreHolder;
    private LoadMoreTask mLoadMoreTask;
    private int mState;


    public SuperBaseAdapter(List<T> datas, AbsListView absListView) {
        super(datas);
        mAbsListView=absListView;
        mAbsListView.setOnItemClickListener(this);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return VIEWTYPE_LOADMORE;
        }else {
//            return VIEWTYPE_NORMAL;
            return getNormalItemViewType(position);
        }
    }

    public int getNormalItemViewType(int position) {
        return VIEWTYPE_NORMAL;
    }

    @Override
    public int getCount() {
        return super.getCount()+1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder=null;
        int curViewType = getItemViewType(position);
        if (convertView == null) {
            if(curViewType==VIEWTYPE_LOADMORE){
                holder = getLoadMoreHolder();
            }else{
                //创建holder对象
                holder = getSpecialBaseHolder(position);
            }
        }else {
            holder= (BaseHolder) convertView.getTag();
        }
                /*--------------得到数据,然后绑定数据---------------*/
        if(curViewType==VIEWTYPE_LOADMORE){
            if(hasLoadMore()){
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_LOADING);
                //触发加载更多的数据
                triggerLoadMoreData();

            }else{
                //隐藏加载更多的视图,以及重试视图
                mLoadMoreHolder.setDataAndRefreshHolderView(LoadMoreHolder.LOADMORE_NONE);
            }
        }else{
            //得到数据,然后绑定数据
            Object data = mDatas.get(position);
            holder.setDataAndRefreshHolderView(data);
        }

        View holderView = holder.mHolderView;
        holderView.setScaleX(0.6f);
        holderView.setScaleY(0.5f);
        ViewCompat.animate(holderView).scaleX(1).scaleY(1).setDuration(400).setInterpolator(new OvershootInterpolator(4)).start();
        return holderView;
    }

    private void triggerLoadMoreData() {
        if(mLoadMoreTask==null) {
            //加载之前显示正在加载更多
            int state=LoadMoreHolder.LOADMORE_LOADING;
            mLoadMoreHolder.setDataAndRefreshHolderView(state);
            //异步加载
            mLoadMoreTask = new LoadMoreTask();
            ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadMoreTask);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mAbsListView instanceof ListView) {
            position=position-((ListView)mAbsListView).getHeaderViewsCount();
        }
        int curViewType = getItemViewType(position);
        if(curViewType==VIEWTYPE_LOADMORE){
            if(mState==LoadMoreHolder.LOADMORE_ERROR){
                triggerLoadMoreData();
            }else{

            }
        }else{
            onNormalItemClick(parent, view, position, id);
        }
    }

    public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class LoadMoreTask implements Runnable {
        private static final int PAGESIZE=20;

        @Override
        public void run() {
                /*--------------定义刷新UI需要用到的两个值---------------*/
            List loadMoreList=null;
            int state;
            try{
                loadMoreList = onLoadMore();
                //处理数据
                if(loadMoreList==null){
                    state=LoadMoreHolder.LOADMORE_NONE;
                }else{
                    if(loadMoreList.size()==PAGESIZE){
                        state=LoadMoreHolder.LOADMORE_LOADING;
                    }else{
                        state=LoadMoreHolder.LOADMORE_NONE;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                state=LoadMoreHolder.LOADMORE_ERROR;
            }
                /*--------------生成了两个临时变量---------------*/
            final List finalLoadMoreList=loadMoreList;
            final int finalState=state;
            /*--------------具体刷新ui---------------*/
            MyApplication.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    //刷新ui-->ListView-->修改数据集
                    if (finalLoadMoreList != null) {
                        mDatas.addAll(finalLoadMoreList);
                        notifyDataSetChanged();
                    }
                    //刷新ui-->mLoadMoreHolder-->mLoadMoreHolder.setDataAndRefreshHolderView(curState)
                    mLoadMoreHolder.setDataAndRefreshHolderView(finalState);
                }
            });
            mLoadMoreTask=null;
        }
    }
    /**
     * 是否有加载更多,默认没有.子类可以覆写该方法,可以决定有加载更多
     * @return
     */
    public boolean hasLoadMore() {
        return false;//默认没有加载更多
    }

    public List onLoadMore() throws Exception{
        return null;
    }
    private BaseHolder getLoadMoreHolder() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }

    public abstract BaseHolder getSpecialBaseHolder(int position);

}
