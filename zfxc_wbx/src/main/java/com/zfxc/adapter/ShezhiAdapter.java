/**
 * 
 */
package com.zfxc.adapter;

import java.util.List;

import com.example.zfxc_wbx.R;
import com.zfxc.entity.ShezhiItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author dell
 *
 */
public class ShezhiAdapter extends BaseAdapter {

	private List<ShezhiItem> iList;
	private Context mcontext;
	private LayoutInflater lif;
	public ShezhiAdapter(List<ShezhiItem> data,Context context){
		this.iList=data;
		this.mcontext=context;
		this.lif=lif.from(context);
	}
	@Override
	public int getCount() {
		return iList!=null?iList.size():0;
	}

	@Override
	public ShezhiItem getItem(int position) {
		// TODO Auto-generated method stub
		return iList.get(position);
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
			v=lif.inflate(R.layout.layout_shezhi_item,null);
			holder.itemName=(TextView) v.findViewById(R.id.shezhi_name);
			holder.itemImg= (ImageView) v.findViewById(R.id.shezhi_img);
			v.setTag(holder);
		}else
		{
			holder=(ViewHolder) v.getTag();
		}
		ShezhiItem szi=getItem(position);
		holder.itemName.setText(szi.getItemName());
		holder.itemImg.setImageResource(szi.getItemImg());
		return v;
	}
	class ViewHolder{
		private ImageView itemImg;
		private TextView itemName;
	}

}
