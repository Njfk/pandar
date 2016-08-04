package com.panda.g_panda.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.g_panda.R;
import com.panda.g_panda.adapter.MainViewPagerAdapter;
import com.panda.g_panda.fragment.LiveFragment;
import com.panda.g_panda.fragment.MenuFragment;
import com.panda.g_panda.fragment.VedioFragment;
import com.panda.g_panda.parser.OnDrawerStateChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener ,OnDrawerStateChangeListener {
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
    @BindView(R.id.floading_but)
    Button floading_but;
    @BindView(R.id.drawerlayout_main)
    DrawerLayout drawerLayout;
    List<Fragment> fragments;
    List<ImageView> imageViews;
    List<String> titles;
    long time1;
    long time2;
    boolean isFirst = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setDrawerlayout();
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
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final int screenWidth = dm.widthPixels;
        final int screenHeight = dm.heightPixels - 50;

        floading_but.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;// 记录移动的最后的位置
            private int btnHeight;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int ea = event.getAction();

                switch (ea) {
                    case MotionEvent.ACTION_DOWN:
                        //按下
                        lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                        lastY = (int) event.getRawY();
                        btnHeight = floading_but.getHeight();
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
                        v.layout(20, top1, btnHeight+18, bottom1);
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
    @OnClick(R.id.floading_but)
    public void onclick(View v){
            onDrawerOpen();
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
    public void setDrawerlayout(){
        FragmentManager sm = getSupportFragmentManager();
        FragmentTransaction ft = sm.beginTransaction();
        ft.add(R.id.menu_container,new MenuFragment());
        ft.commit();
    }

    @Override
    public void onDrawerOpen() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onDrawerClose() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else if(isFirst){
           time1 = System.currentTimeMillis();
            isFirst = false;
            Toast.makeText(this,"再点一次退出",Toast.LENGTH_SHORT).show();
        } else {
            time2 = System.currentTimeMillis();
            if((time2 - time1)<2000){
                super.onBackPressed();
            }else{
                isFirst = true;
            }
        }
    }
}
