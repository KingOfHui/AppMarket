package com.google.googleplaystore.protocol;

import com.google.googleplaystore.bean.SubjectBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 *  Created by DH on 2017/7/15.
 */

public class SubjectProtocol extends BaseProtocol<List<SubjectBean>> {
    @Override
    protected List<SubjectBean> handleData(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<SubjectBean>>() {
        }.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "subject";
    }
}
