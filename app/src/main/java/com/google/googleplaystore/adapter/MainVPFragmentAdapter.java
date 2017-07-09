package com.google.googleplaystore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.google.googleplaystore.factory.FragmentFactory;

/**
 * Created by DH on 2017/7/8.
 */

public class MainVPFragmentAdapter extends FragmentStatePagerAdapter {
    private String[] titles;

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public MainVPFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.create(position);
    }

    @Override
    public int getCount() {
        if (titles != null) {
            return titles.length;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
