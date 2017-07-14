package com.google.googleplaystore.protocol;

import com.google.googleplaystore.bean.HomeBean;
import com.google.gson.Gson;

/**
 * Created by DH on 2017/7/12.
 */

public class HomeProtocol extends BaseProtocol{
    @Override
    public String getInterfaceKey() {
        return "home";
    }
    @Override
    protected HomeBean handleData(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, HomeBean.class);
    }
}

