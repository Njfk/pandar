package com.panda.g_panda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.panda.g_panda.R;
import com.panda.g_panda.bean.VedioEntity;

import java.util.List;

/**
 * Created by ASUS on 2016/8/4.
 */
public class VedioAdapter extends RecyclerView.Adapter<VedioAdapter.MyViewHolder> {
    List<VedioEntity.DataBean.NewsListBean> newsList;
    Context context;

    public VedioAdapter(List<VedioEntity.DataBean.NewsListBean> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vedio_item, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(newsList.get(position).getPubTime());
        holder.homeChannelName.setText(newsList.get(position).getHomeChannelName());
        holder.newsTitle.setText(newsList.get(position).getNewsTitle());
        String imagUrl = newsList.get(position).getNewsImage();
        Glide.with(context).load(imagUrl).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView, homeChannelName,newsTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.vedio_time_text);
            homeChannelName = (TextView) itemView.findViewById(R.id.vedio_name);
            newsTitle= (TextView) itemView.findViewById(R.id.newsTitle);
            imageView = (ImageView) itemView.findViewById(R.id.vedio_newsImage);


        }
    }
}
