package com.canadapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 */

public class MyAdapter extends MultiItemTypeAdapter<String> {

    public MyAdapter(Context context, List<String> datas) {
        super(context, datas);
    }
}
