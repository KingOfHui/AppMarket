package com.google.googleplaystore.factory;

import android.support.v4.app.Fragment;

import com.google.googleplaystore.base.BaseFragment;
import com.google.googleplaystore.fragment.AppFragment;
import com.google.googleplaystore.fragment.CategoryFragment;
import com.google.googleplaystore.fragment.GameFragment;
import com.google.googleplaystore.fragment.HomeFragment;
import com.google.googleplaystore.fragment.HotFragment;
import com.google.googleplaystore.fragment.RecommendFragment;
import com.google.googleplaystore.fragment.SubjectFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DH on 2017/7/8.
 */

public class FragmentFactory {

    public static final int FRAGMENT_HOME = 0;//首页
    public static final int FRAGMENT_APP = 1;//应用
    public static final int FRAGMENT_GAME = 2;//游戏
    public static final int FRAGMENT_SUBJECT = 3;//专题
    public static final int FRAGMENT_RECOMMEND = 4;//推荐
    public static final int FRAGMENT_CATEGORY = 5;//分类
    public static final int FRAGMENT_HOT = 6;//排行
    public static Map<Integer, BaseFragment> fragmentMap=new HashMap<>();

    public static Fragment create(int position) {
        //定义Fragment对象
        BaseFragment fragment = null;
        if (fragmentMap.containsKey(position)) {
            fragment = fragmentMap.get(position);
            return fragment;
        }
        switch (position) {
            case FRAGMENT_HOME://返回 首页 对应的fragment
                fragment = new HomeFragment();
                break;
            case FRAGMENT_APP://返回 应用 对应的fragment
                fragment = new AppFragment();
                break;
            case FRAGMENT_GAME://返回 游戏 对应的fragment
                fragment = new GameFragment();
                break;
            case FRAGMENT_SUBJECT://返回 专题 对应的fragment
                fragment = new SubjectFragment();
                break;
            case FRAGMENT_RECOMMEND://返回 推荐 对应的fragment
                fragment = new RecommendFragment();
                break;
            case FRAGMENT_CATEGORY://返回 分类 对应的fragment
                fragment = new CategoryFragment();
                break;
            case FRAGMENT_HOT://返回 排行 对应的fragment
                fragment = new HotFragment();
                break;
        }
        fragmentMap.put(position, fragment);
        return fragment;
    }
}
