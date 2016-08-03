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
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final int screenWidth = dm.widthPixels;
        final int screenHeight = dm.heightPixels - 50;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        floatingActionButton.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;// 记录移动的最后的位置
            private int btnHeight;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int ea = event.getAction();

                switch (ea) {
                    case MotionEvent.ACTION_DOWN://按下
                        lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                        lastY = (int) event.getRawY();
                        btnHeight = floatingActionButton.getHeight();
                        break;
                    case MotionEvent.ACTION_MOVE://移动
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        int l = v.getLeft() + dx;
                        int b = v.getBottom() + dy;
                        int r = v.getRight() + dx;
                        int t = v.getTop() + dy;
                        // 下面判断移动是否超出屏幕
                        if (l < 0) {
                            l = 0;
                            r = l + v.getWidth();
                        }
                        if (t < 0) {
                            t = 0;
                            b = t + v.getHeight();
                        }
                        if (r > screenWidth) {
                            r = screenWidth;
                            l = r - v.getWidth();
                        }
                        if (b > screenHeight) {
                            b = screenHeight;
                            t = b - v.getHeight();
                        }
                        v.layout(l, t, r, b);
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
//                        Toast.makeText(MainActivity.this,
//                                "当前位置：" + l + "," + t + "," + r + "," + b,
//                                Toast.LENGTH_SHORT).show();
                        v.postInvalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        // 向四周吸附
                        int dx1 = (int) event.getRawX() - lastX;
                        int dy1 = (int) event.getRawY() - lastY;
                        int left1 = v.getLeft() + dx1;
                        int top1 = v.getTop() + dy1;
                        int right1 = v.getRight() + dx1;
                        int bottom1 = v.getBottom() + dy1;
                        if (left1 < (screenWidth / 2)) {
                            if (top1 < 100) {

                                v.layout(left1, 0, right1, btnHeight);
                            } else if (bottom1 > (screenHeight - 200)) {
                                v.layout(left1, (screenHeight - btnHeight), right1, screenHeight);
                            } else {
                                v.layout(0, top1, btnHeight, bottom1);
                            }
                        } else {
                            if (top1 < 100) {
                                v.layout(left1, 0, right1, btnHeight);
                            } else if (bottom1 > (screenHeight - 200)) {
                                v.layout(left1, (screenHeight - btnHeight), right1, screenHeight);
                            } else {
                                v.layout((screenWidth - btnHeight), top1, screenWidth, bottom1);
                            }
                        }

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
