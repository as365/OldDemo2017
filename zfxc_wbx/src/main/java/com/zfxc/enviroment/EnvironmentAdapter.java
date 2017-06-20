package com.zfxc.enviroment;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zfxc_wbx.R;

public class EnvironmentAdapter extends BaseAdapter {
	private List<Environment> list;
	private LayoutInflater inflater;

	public EnvironmentAdapter(Context context, List<Environment> list) {
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
	if (observer != null) {
	       super.unregisterDataSetObserver(observer);
	   }
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Environment environment = (Environment) this.getItem(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listview_environment, null);
			viewHolder.ddate = (TextView) convertView
					.findViewById(R.id.text_ddate);
			viewHolder.ip = (TextView) convertView.findViewById(R.id.text_ip);
			viewHolder.kqwd = (TextView) convertView
					.findViewById(R.id.text_kqwd);
			viewHolder.kqsd = (TextView) convertView
					.findViewById(R.id.text_kqsd);
			viewHolder.gz = (TextView) convertView.findViewById(R.id.text_gz);
			viewHolder.trwd = (TextView) convertView
					.findViewById(R.id.text_trwd);
			viewHolder.trsd = (TextView) convertView
					.findViewById(R.id.text_trsd);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.ddate.setText(environment.getddate());
		viewHolder.ip.setText(environment.getip());
		viewHolder.kqwd.setText(environment.getkqwd());
		viewHolder.kqsd.setText(environment.getkqsd());
		viewHolder.gz.setText(environment.getgz());
		viewHolder.trwd.setText(environment.gettrwd());
		viewHolder.trsd.setText(environment.gettrsd());

		return convertView;
	}
	
	public static class ViewHolder {// String ddate, String ip, String kqwd,String
								// kqsd,String gz,String trwd,String trsd
		public TextView ddate;
		public TextView ip;
		public TextView kqwd;
		public TextView kqsd;
		public TextView gz;
		public TextView trwd;
		public TextView trsd;

	}

}
