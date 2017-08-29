package com.google.googleplaystore.bean;

import java.util.List;

/**
 *  Created by DH on 2017/7/11.
 */

public class HomeBean {

    public List<String> picture;
    public List<ListBean> list;

    public static class ListBean {
        public long id;
        public String name;
        public String packageName;
        public String iconUrl;
        public float stars;
        public long size;
        public String downloadUrl;
        public String des;

        /*--------------添加详情页面里面的额外字段---------------*/
        public String author;// 黑马程序员
        public String date;//  2015-06-10
        public String downloadNum;//   40万+
        public String version;// 1.1.0605.0
        public List<ItemSafeBean> safe;// Array
        public List<String> screen;//Array

        public class ItemSafeBean {
            public String safeDes;// 已通过安智市场安全检测，请放心使用
            public int safeDesColor;// 0
            public String safeDesUrl;//    app/com.itheima.www/safeDesUrl0.jpg
            public String safeUrl;// app/com.itheima.www/safeIcon0.jpg


            @Override
            public String toString() {
                return "ItemSafeBean{" +
                        "safeDes='" + safeDes + '\'' +
                        ", safeDesColor=" + safeDesColor +
                        ", safeDesUrl='" + safeDesUrl + '\'' +
                        ", safeUrl='" + safeUrl + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", stars=" + stars +
                    ", size=" + size +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    ", des='" + des + '\'' +
                    ", author='" + author + '\'' +
                    ", date='" + date + '\'' +
                    ", dowmloadNum='" + downloadNum + '\'' +
                    ", version='" + version + '\'' +
                    ", safe=" + safe +
                    ", screen=" + screen +
                    '}';
        }
    }
}
