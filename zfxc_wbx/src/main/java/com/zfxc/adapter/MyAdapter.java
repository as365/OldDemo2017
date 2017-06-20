/**
 * 
 */
package com.zfxc.adapter;

import java.util.List;

import com.example.zfxc_wbx.R;
import com.zfxc.entity.ZfscDB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author dell
 *
 */
public class MyAdapter extends BaseAdapter {

	private Context mContext;
	private List<ZfscDB> mdata;
	private LayoutInflater mlif;
	public MyAdapter(List<ZfscDB> data,Context context){
		this.mdata=data;
		this.mContext=context;
		this.mlif=mlif.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mdata!=null ? mdata.size():0;
	}

	@Override
	public ZfscDB getItem(int position) {
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
		ViewHolder holder=null;
		if(v==null){
			holder=new ViewHolder();
			v=mlif.inflate(R.layout.layout_zfsc_db, null);
			holder.tvSCtitle=(TextView) v.findViewById(R.id.zfdb_title);
			holder.tvSCcontent=(TextView) v.findViewById(R.id.zfdb_content);
			v.setTag(holder);
		}else{
			holder=(ViewHolder) v.getTag();
		}
		ZfscDB zdb=getItem(position);
		holder.tvSCtitle.setText(zdb.getTitle());
		holder.tvSCcontent.setText(zdb.getContext());
		return v;
	}
	class  ViewHolder{
		TextView tvSCtitle;
		TextView tvSCcontent;
	}
}
