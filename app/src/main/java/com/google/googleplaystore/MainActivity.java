package com.google.googleplaystore;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStripExtend;
import com.google.googleplaystore.adapter.MainVPFragmentAdapter;
import com.google.googleplaystore.base.BaseFragment;
import com.google.googleplaystore.base.LoadingPagerController;
import com.google.googleplaystore.factory.FragmentFactory;
import com.google.googleplaystore.utils.UIUtils;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ViewPager mViewPager;
    private PagerSlidingTabStripExtend mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabs = (PagerSlidingTabStripExtend) findViewById(R.id.main_tabs);
        initActionBar();
        initActionBarDrawerToggle();
        initViewPager();
        initListener();
    }

    private void initListener() {

        final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.fragmentMap.get(position);
                LoadingPagerController loadingPager = fragment.getLoadingPager();
                loadingPager.triggerData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        mTabs.setOnPageChangeListener(onPageChangeListener);
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                onPageChangeListener.onPageSelected(0);
                mViewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    private void initViewPager() {
        MainVPFragmentAdapter mainVPFragmentAdapter = new MainVPFragmentAdapter(getSupportFragmentManager());
        String[] titles = UIUtils.getStrings(R.array.main_titles);
        mainVPFragmentAdapter.setTitles(titles);
        mViewPager.setAdapter(mainVPFragmentAdapter);
        mTabs.setViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(6);
    }

    /**
     * 初始化ActionBarDrawerToggle
     */
    private void initActionBarDrawerToggle() {
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        //同步状态方法-->替换默认回退部分的UI效果
        mToggle.syncState();
        //设置DrawerLayout的监听-->DrawerLayout拖动的时候,toggle可以跟着改变ui
        mDrawerLayout.addDrawerListener(mToggle);
    }

    private void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("GooglePlay");
        supportActionBar.setSubtitle("store");
        //设置图标
        supportActionBar.setIcon(R.drawable.arrow_down);
        supportActionBar.setLogo(R.drawable.arrow_up);
        //显示logo/icon图标,默认是false,默认是隐藏图标
        supportActionBar.setDisplayShowHomeEnabled(true);
        //修改icon和logo显示的优先级,默认是false,默认是没用logo用的是icon
        supportActionBar.setDisplayUseLogoEnabled(true);
        //显示回退部分,默认是false,默认隐藏了回退部分
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "点击了回退部分", Toast.LENGTH_SHORT).show();
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
