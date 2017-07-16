package com.google.googleplaystore.protocol;

import com.google.googleplaystore.bean.CategoryInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DH on 2017/7/16.
 */

public class CategoryProtocol extends BaseProtocol<List<CategoryInfoBean>>{

    @Override
    protected List<CategoryInfoBean> handleData(String json) {
        ArrayList<CategoryInfoBean> categoryInfoBeans = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            //便利json数组
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //取出title
                String title = jsonObject.getString("title");
                CategoryInfoBean titleCategoryInfoBean = new CategoryInfoBean();
                titleCategoryInfoBean.title=title;
                titleCategoryInfoBean.isTitle=true;
                //加入集合中
                categoryInfoBeans.add(titleCategoryInfoBean);
                JSONArray infosJsonArray = jsonObject.getJSONArray("infos");
                //遍历infosJsonArray
                for (int i1 = 0; i1 < infosJsonArray.length(); i1++) {
                    JSONObject infoJsonObject = infosJsonArray.getJSONObject(i1);
                    String name1 = infoJsonObject.getString("name1");
                    String name2 = infoJsonObject.getString("name2");
                    String name3 = infoJsonObject.getString("name3");
                    String url1 = infoJsonObject.getString("url1");
                    String url2 = infoJsonObject.getString("url2");
                    String url3 = infoJsonObject.getString("url3");

                    CategoryInfoBean categoryInfoBean = new CategoryInfoBean();
                    categoryInfoBean.name1=name1;
                    categoryInfoBean.name2=name2;
                    categoryInfoBean.name3=name3;
                    categoryInfoBean.url1=url1;
                    categoryInfoBean.url2=url2;
                    categoryInfoBean.url3=url3;
                    //加入到集合中
                    categoryInfoBeans.add(categoryInfoBean);
                }
            }
            return categoryInfoBeans;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getInterfaceKey() {
        return "category";
    }
}
