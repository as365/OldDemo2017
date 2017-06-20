/**
 * 
 */
package com.zfxc.adapter;

import java.util.List;

import com.example.zfxc_wbx.R;
import com.zfxc.entity.Taskentity;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author dell
 *
 */
public class TaskAdapter extends BaseAdapter {

	private List<Taskentity> tList;
	private Context mContext;
	private LayoutInflater lif;
	private MyDbHelper myDbHelper;
	private String Type;
	public TaskAdapter(List<Taskentity> list,Context context,String TaskType){
		this.tList=list;
		this.mContext=context;
		this.lif=lif.from(context);
		this.Type=TaskType;
		myDbHelper = MyDbHelper.getInstance(context);
	}
	@Override
	public int getCount() {
		return tList!=null ? tList.size():0;
	}

	@Override
	public Taskentity getItem(int position) {
		return tList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		ViewHolder holer=null;
		if(v==null){
			holer=new ViewHolder();
			v=lif.inflate(R.layout.layout_task_perform_list_item, null);
			holer.tvUnit=(TextView) v.findViewById(R.id.task_item_unit);
			holer.tvAddress=(TextView) v.findViewById(R.id.task_item_address);
			holer.tvTime=(TextView) v.findViewById(R.id.task_item_time);
			holer.tvId=(TextView) v.findViewById(R.id.task_item_id);
			holer.btDelete=(Button) v.findViewById(R.id.task_item_delete);
			v.setTag(holer);
		}else{
			holer=(ViewHolder) v.getTag();
		}
		Taskentity t=getItem(position);
		holer.tvUnit.setText(t.getTunit());
		holer.tvAddress.setText(t.getTaddress());
		holer.tvTime.setText(t.getTdate());
		holer.tvId.setText(t.getTid());
		holer.btDelete.setOnClickListener(new lvButtonListener(position,holer.tvId.getText().toString()));
		return v;
	}
	class ViewHolder{
		TextView tvUnit;
		TextView tvAddress;
		TextView tvTime;
		TextView tvId;
		Button   btDelete;
	}
	class lvButtonListener implements OnClickListener {
		private int position;
		private int cun=0;
		private String tId;

		lvButtonListener(int pos,String id) {
			position = pos;
			tId=id;
		}

		@Override
		public void onClick(View v) {
			myDbHelper.open();
			String [] values={tId};
			if(Type.equals("农产品质量安全监管")){
				
				myDbHelper.delete(MyDbInfo.getTableNames()[12],"Id=?", values);
			}else if(Type.equals("放心菜基地监督考评")){
				myDbHelper.delete(MyDbInfo.getTableNames()[12],"Id=?", values);
			}
			myDbHelper.close();
			tList.remove(position);
			notifyDataSetChanged();
			
		}
	}
}
