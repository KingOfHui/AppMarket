package com.google.googleplaystore.protocol;

import com.google.googleplaystore.bean.HomeBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by DH on 2017/7/15.
 */

public class AppProtocol extends BaseProtocol<List<HomeBean.ListBean>> {
    @Override
    protected List<HomeBean.ListBean> handleData(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<HomeBean.ListBean>>() {
        }.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "app";
    }
}
