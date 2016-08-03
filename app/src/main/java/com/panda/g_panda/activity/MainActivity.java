package com.panda.g_panda.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.panda.g_panda.R;
import com.panda.g_panda.adapter.MainViewPagerAdapter;
import com.panda.g_panda.fragment.LiveFragment;
import com.panda.g_panda.fragment.VedioFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.title_main)
    TextView title_main;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.tablayout_main)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_main)
    ViewPager viewPager;
    @BindView(R.id.point_01)
    ImageView point_01;
    @BindView(R.id.point_02)
    ImageView point_02;
    @BindView(R.id.point_03)
    ImageView point_03;
    @BindView(R.id.point_04)
    ImageView point_04;
    List<Fragment> fragments;
    List<ImageView> imageViews;
    List<String> titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        imageViews = new ArrayList<>();
        point_01.setSelected(true);
        imageViews.add(point_01);
        imageViews.add(point_02);
        imageViews.add(point_03);
        imageViews.add(point_04);

        fragments = new ArrayList<>();
        fragments.add(new LiveFragment());
        fragments.add(new VedioFragment());
        //tablayout
       titles = new ArrayList<>();
        titles.add("直播");
        titles.add("视频");
        title_main.setText(titles.get(0));
        for (int i = 0; i < 2; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }
        tabLayout.setSelectedTabIndicatorHeight(3);
        tabLayout.setBackgroundColor(Color.WHITE);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        showPoint(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void showPoint(int position) {
        for (int i = 0; i < imageViews.size(); i++) {
            if (i == position) {
                imageViews.get(i).setSelected(true);
                title_main.setText(titles.get(position));
            } else {
                imageViews.get(i).setSelected(false);
            }
        }
    }
}
