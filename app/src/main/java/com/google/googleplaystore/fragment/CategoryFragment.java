package com.google.googleplaystore.fragment;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.googleplaystore.base.BaseFragment;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.base.LoadingPagerController;
import com.google.googleplaystore.base.SuperBaseAdapter;
import com.google.googleplaystore.bean.CategoryInfoBean;
import com.google.googleplaystore.factory.ListViewFactory;
import com.google.googleplaystore.holder.CategoryNormalHolder;
import com.google.googleplaystore.holder.CategoryTitleHolder;
import com.google.googleplaystore.protocol.CategoryProtocol;

import java.util.List;

/**
 * Created by DH on 2017/7/8.
 */

public class CategoryFragment extends BaseFragment {

    private List<CategoryInfoBean> mDatas;

    @Override
    protected LoadingPagerController.LoadedResult initData() {
        CategoryProtocol protocol = new CategoryProtocol();
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
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new CategoryAdapter(mDatas,listView));
        return listView;
    }

    class CategoryAdapter extends SuperBaseAdapter<CategoryInfoBean> {

        public CategoryAdapter(List<CategoryInfoBean> datas, AbsListView absListView) {
            super(datas, absListView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            CategoryInfoBean itemBean= (CategoryInfoBean) mDatas.get(position);
            if(itemBean.isTitle){
                return new CategoryTitleHolder();
            }else{
                return new CategoryNormalHolder();
            }
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }

        @Override
        public int getNormalItemViewType(int position) {
            CategoryInfoBean itemBean= (CategoryInfoBean) mDatas.get(position);
            if(itemBean.isTitle){
                return 1;
            }else{
                return 2;
            }
        }
    }
}
