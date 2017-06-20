/**
 * 
 */
package com.zfxc.adapter;

import java.util.List;

import com.example.zfxc_wbx.R;
import com.zfxc.entity.MissTongJi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author dell
 *
 */
public class TongjiAdapter extends BaseAdapter {

	private List<MissTongJi> mdata;
	private Context mcontext;
	private LayoutInflater lif;
	private int[] colors=new int[]{0x30FF0000,0x300000FF};
	public TongjiAdapter(List<MissTongJi> data,Context context){
		this.mdata=data;
		this.mcontext=context;
		this.lif=lif.from(context);
	}
	public TongjiAdapter(Context context){
		this.mcontext=context;
		this.lif=lif.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdata!=null ? mdata.size():0;
	}

	@Override
	public MissTongJi getItem(int position) {
		// TODO Auto-generated method stub
		return mdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if(v==null){
			viewHolder=new ViewHolder();
			v=lif.inflate(R.layout.layout_tongji_item,null);
			viewHolder.tvMissName=(TextView) v.findViewById(R.id.tj_item_mname);
			viewHolder.tvMissDate=(TextView) v.findViewById(R.id.tj_item_mdate);
			viewHolder.tvMissType=(TextView) v.findViewById(R.id.tj_item_mtype);
			viewHolder.tvMissStatu=(TextView) v.findViewById(R.id.tj_item_mstatu);
			viewHolder.tvMissId=(TextView) v.findViewById(R.id.tj_item_missId);
			v.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) v.getTag();
		}
		MissTongJi tj=getItem(position);
		viewHolder.tvMissName.setText(tj.getMisName());
		viewHolder.tvMissDate.setText(tj.getMisDate());
		viewHolder.tvMissType.setText(tj.getMisType());
		viewHolder.tvMissStatu.setText(tj.getMisStatu());
		viewHolder.tvMissId.setText(tj.getMisId());
		 int colorPos=position%colors.length;
	        if(colorPos==1){
	               v.setBackgroundColor(Color.argb(250, 255, 255, 255)); //颜色设置
	        }else{
	            v.setBackgroundColor(Color.argb(255, 224, 243, 250));//颜色设置
	        }
		return v;
	}
	class ViewHolder{
		TextView tvMissName;
		TextView tvMissDate;
		TextView tvMissType;
		TextView tvMissStatu;
		TextView tvMissId;
	}
	public void addAll(List<MissTongJi> list) {
		mdata.addAll(list);
        notifyDataSetChanged();
    }
}
