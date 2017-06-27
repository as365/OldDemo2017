package com.canadapter.empty;

import android.content.Context;

import com.canadapter.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/27.
 */

public class EmptyAdapter extends CommonAdapter<String> {
    public EmptyAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.tv, s);
    }
}
