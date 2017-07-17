package com.google.googleplaystore.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by DH on 2017/7/16.
 */

public class HotProtocol extends BaseProtocol<List<String>> {
    @Override
    protected List<String> handleData(String json) {
        return new Gson().fromJson(json,new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public String getInterfaceKey() {
        return "hot";
    }
}
