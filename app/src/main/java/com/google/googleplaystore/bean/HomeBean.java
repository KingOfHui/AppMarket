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
    }
}
