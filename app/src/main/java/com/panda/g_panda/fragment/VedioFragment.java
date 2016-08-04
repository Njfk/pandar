package com.panda.g_panda.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.panda.g_panda.R;
import com.panda.g_panda.activity.VideoItemActivity;
import com.panda.g_panda.adapter.VedioAdapter;
import com.panda.g_panda.bean.VedioEntity;
import com.panda.g_panda.parser.VedioInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class VedioFragment extends Fragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.vedio_recyceler)
    RecyclerView recyclerView;
    List<VedioEntity.DataBean.NewsListBean> newsList;
    VedioEntity vedioEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vedio, container, false);
        ButterKnife.bind(this, rootView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(manager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gpfile.sctv.com:2225/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final VedioInterface vedioInterface = retrofit.create(VedioInterface.class);
        Call<VedioEntity> call = vedioInterface.getData();
        call.enqueue(new Callback<VedioEntity>() {
            @Override
            public void onResponse(Call<VedioEntity> call, Response<VedioEntity> response) {
                vedioEntity = response.body();
                recyclerView.setAdapter(new VedioAdapter(newsList, getActivity()));

            }

            @Override
            public void onFailure(Call<VedioEntity> call, Throwable t) {

            }
        });

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), VideoItemActivity.class);
        intent.putExtra("id", newsList.get(position).getNewsid());
        startActivity(intent);

    }
}
