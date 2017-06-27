package com.canadapter.mulitem;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 */

public class MyAdapter extends MultiItemTypeAdapter<String> {

    public MyAdapter(Context context, List<String> datas) {
        super(context, datas);
    }
}
