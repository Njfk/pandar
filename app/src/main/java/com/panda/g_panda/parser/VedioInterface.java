package com.panda.g_panda.parser;

import com.panda.g_panda.bean.VedioEntity;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ASUS on 2016/8/4.
 */
public interface VedioInterface {
    @GET(value = "trs/xwzd/shipin/list.json")
    public Call<VedioEntity> getData();
}
