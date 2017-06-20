package com.zfxc.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.zfxc_wbx.R;
import com.zfxc.enviroment.EnvironmentActivity;
import com.zfxc.ui.LoginActivity;


public class FriendFragment extends Fragment{
	private View view;//缓存页面
	private Object[] activities = {
			"在线监测",LoginActivity.class,"视频监控",LoginActivity.class,
		};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("slide","FriendFragment-onCreateView");
		if(view==null){
			view=inflater.inflate(R.layout.friend_fragment,container, false);
		}
		ViewGroup parent = (ViewGroup) view.getParent();
		if(parent!=null){
			parent.removeView(view);//先移除
		}

		init(view);
		return view;
	}

	private void init(View view ) {
		// TODO Auto-generated method stub
		
	
		CharSequence[] list = new CharSequence[activities.length / 2];
		for (int i = 0; i < list.length; i++) {
			list[i] = (String)activities[i * 2];
		}
	
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getActivity(), android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView)view.findViewById(R.id.ListView01);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {		
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
			
				
			switch (position) {
			case 0:
				zxjc(position);
				break;
			case 1:
				spjk(position);
				break;
			default:
				break;
			}
			}

			

		
				
		});
	
	}
	//视频监控
	private void spjk(int position) {
		// TODO Auto-generated method stub
		  Intent  intentVideo=new Intent(getActivity(),com.login.LoginActivity.class);
		    startActivity(intentVideo);
	}
	//在线监测
	private void zxjc(int position) {
		// TODO Auto-generated method stub
		Intent mi = new Intent(getActivity(),EnvironmentActivity.class);
		startActivity(mi); 
	}

	@Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        Log.i("slide","FriendFragment--onPause");
    }
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("slide","FriendFragment--onStop");
	}
}
