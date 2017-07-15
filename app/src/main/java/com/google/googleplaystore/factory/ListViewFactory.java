package com.google.googleplaystore.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ListView;

import com.google.googleplaystore.utils.UIUtils;

/**
 * Created by DH on 2017/7/15.
 */

public class ListViewFactory {
    public static ListView createListView() {
        ListView listView = new ListView(UIUtils.getContext());
        listView.setDividerHeight(1);
        listView.setFastScrollEnabled(true);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        return listView;
    }
}
