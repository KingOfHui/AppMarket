package com.google.googleplaystore.protocol;

import android.support.annotation.NonNull;

import com.google.googleplaystore.bean.HomeBean;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DH on 2017/7/17.
 */

public class DetailProtocol extends BaseProtocol<HomeBean.ListBean> {
    private String packageName;

    public DetailProtocol(String packageName) {
        this.packageName=packageName;
    }

    @Override
    protected HomeBean.ListBean handleData(String json) {
        return new Gson().fromJson(json, HomeBean.ListBean.class);
    }

    @Override
    public String getInterfaceKey() {
        return "detail";
    }

    @NonNull
    @Override
    public Map<String, Object> getParamsMap(int index) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("packageName", packageName);
        return paramsMap;
    }

    @Override
    public String generateKey(int index) {
        return getInterfaceKey()+"."+packageName;
    }
}
