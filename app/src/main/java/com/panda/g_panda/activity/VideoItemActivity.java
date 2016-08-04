package com.panda.g_panda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.panda.g_panda.R;
import com.panda.g_panda.bean.VedioEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class VideoItemActivity extends AppCompatActivity {
    @BindView(R.id.videoController)
    JCVideoPlayer jcVideoPlayer;
    @BindView(R.id.newsTopTitle_text)
    TextView titleTop;
    @BindView(R.id.newsTime_text)
    TextView timeText;

    List<VedioEntity.DataBean.NewsListBean>newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_item);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        newsList=new ArrayList<>();
        Intent intent=this.getIntent();
        String id=intent.getStringExtra("id");



    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
