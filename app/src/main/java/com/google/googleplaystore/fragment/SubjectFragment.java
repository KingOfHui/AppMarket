package com.google.googleplaystore.fragment;

import android.os.SystemClock;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.googleplaystore.base.BaseFragment;
import com.google.googleplaystore.base.BaseHolder;
import com.google.googleplaystore.base.LoadingPagerController;
import com.google.googleplaystore.base.SuperBaseAdapter;
import com.google.googleplaystore.bean.SubjectBean;
import com.google.googleplaystore.factory.ListViewFactory;
import com.google.googleplaystore.holder.SubjectHolder;
import com.google.googleplaystore.protocol.SubjectProtocol;
import com.google.googleplaystore.utils.UIUtils;

import java.util.List;

/**
 * Created by DH on 2017/7/8.
 */

public class SubjectFragment extends BaseFragment {

    private List<SubjectBean> mDatas;
    private SubjectProtocol mProtocol;

    @Override
    protected LoadingPagerController.LoadedResult initData() {
        mProtocol = new SubjectProtocol();
        try {
            mDatas = mProtocol.loadData(0);
            return checkResult(mDatas);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPagerController.LoadedResult.ERROR;
        }
    }

    @Override
    public View initSuccessView() {
        ListView listView = ListViewFactory.createListView();
        listView.setAdapter(new SubjectAdapter(mDatas,listView));
        return listView;
    }
    class SubjectAdapter extends SuperBaseAdapter<SubjectBean>{

        public SubjectAdapter(List<SubjectBean> datas, AbsListView absListView) {
            super(datas, absListView);
        }

        @Override
        public BaseHolder getSpecialBaseHolder(int position) {
            return new SubjectHolder();
        }

        @Override
        public boolean hasLoadMore() {
            return true;
        }

        @Override
        public List onLoadMore() throws Exception {
            SystemClock.sleep(1000);
            List<SubjectBean> subjectBeans = mProtocol.loadData(mDatas.size());
            return subjectBeans;
        }

        @Override
        public void onNormalItemClick(AdapterView<?> parent, View view, int position, long id) {
            SubjectBean subjectBean= (SubjectBean) mDatas.get(position);
            Toast.makeText(UIUtils.getContext(), subjectBean.des, Toast.LENGTH_SHORT).show();
        }
    }
}
