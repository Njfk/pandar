package com.panda.g_panda.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
    @BindView(R.id.float_btn)
    FloatingActionButton floatingActionButton;
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
        DisplayMetrics dm = new DisplayMetrics();
        final int screenWidth = dm.widthPixels;
        final int screenHeight = dm.heightPixels;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        floatingActionButton.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY; // 记录移动的最后的位置
            private int btnHeight;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取Action
                int ea = event.getAction();
                switch (ea) {
                    case MotionEvent.ACTION_DOWN://按下
                        lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:

                        // 移动中动态设置位置
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;
                        Log.d("1608","dx="+dx);
                        Log.d("1608","dy="+dy);
                        Log.d("1608","left="+left);
                        Log.d("1608","top="+top);
                        if (left < 0) {
                            left = 0;
                            right = left + v.getWidth();
                        }
                        if (right > screenWidth) {
                            right = screenWidth;
                            left = right - v.getWidth();
                        }
                        if (top < 0) {
                            top = 0;
                            bottom = top + v.getHeight();
                        }
                        if (bottom > screenHeight) {
                            bottom = screenHeight;
                            top = bottom - v.getHeight();
                        }
                        v.layout(left, top, right, bottom);
                        // Toast.makeText(getActivity(), "position：" + left + ", " +
                        // top + ", " + right + ", " + bottom, 0)
                        // .show();
                        // 将当前的位置再次设置
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                }

                return false;
            }
        });

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
