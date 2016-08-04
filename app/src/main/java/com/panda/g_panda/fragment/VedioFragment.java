package com.panda.g_panda.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.panda.g_panda.R;
import com.panda.g_panda.bean.VedioEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class VedioFragment extends Fragment {
    @BindView(R.id.vedio_recyceler)
    RecyclerView recyclerView;
    List<VedioEntity.DataBean.NewsListBean> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vedio, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gpfile.sctv.com:2225/")
                .build();


        return rootView;
    }

}
