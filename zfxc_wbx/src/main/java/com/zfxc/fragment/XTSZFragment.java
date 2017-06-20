package com.zfxc.fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zfxc_wbx.R;
import com.zfxc.adapter.ShezhiAdapter;
import com.zfxc.entity.ShezhiItem;
import com.zfxc.service.DownloadService;
import com.zfxc.ui.FXCQuery;
import com.zfxc.ui.LoginActivity;
import com.zfxc.ui.WGHQuery;
import com.zfxc.ui.WebConfigActivity;
import com.zfxc.ui.XCZFEditActivity;
import com.zfxc.util.DataTable;
import com.zfxc.util.FileUtil;
import com.zfxc.util.FileUtils;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;

public class XTSZFragment extends Fragment {

//	private Object[] activities = { "注销用户", LoginActivity.class, "网络参数设置",
//			LoginActivity.class, "数据上传", LoginActivity.class, "数据下载",
//			LoginActivity.class, };
	private Object[] activities = { "注销用户",
			 "数据上传", "数据下载","放心菜产品查询","无公害产品查询"};
	private Object[] mImageViewArray = { R.drawable.user_icon_zhuxiao,
			R.drawable.user_icon_shangchuan,
			R.drawable.user_icon_xiazai,R.drawable.user_icon_fangxin,R.drawable.user_icon_wuhai};
	MyDbHelper myDbHelper;
	private static int CODE_UPLOAD_SUCCESS = 1;
	private static int CODE_UPLOAD_FAIL = 2;
	private static int CODE_DOWNLOAD_SUCCESS = 3;
	private static int CODE_DOWNLOAD_FAIL = 4;
	String getpath= Environment.getExternalStorageDirectory().getPath()+"/zfxc_wbx/";
	private List<ShezhiItem> iList;
	private ShezhiAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("slide", "JobFragment--onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.xtsz_fragment, container, false);
		Log.i("slide", "JobFragment-rootView=null");
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生IllegalStateException。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
			Log.i("slide", "JobFragment-removeView");
		}
		iList=new ArrayList<>();
		init(view);
		return view;
	}

	private void init(View view) {
		// TODO Auto-generated method stub

		myDbHelper = MyDbHelper.getInstance(getActivity());
//		CharSequence[] list = new CharSequence[activities.length / 2];
//		for (int i = 0; i < list.length; i++) {
//			list[i] = (String) activities[i * 2];
//			list[i] =  (CharSequence) mImageViewArray[i * 2];
//			
//		}
		for(int i=0;i<activities.length;i++){
			ShezhiItem shItem=new ShezhiItem();
			shItem.setItemName((String)activities[i]);
			shItem.setItemImg((Integer)mImageViewArray[i]);
			iList.add(shItem);
		}
//		ArrayAdapter<ShezhiItem> adapter = new ArrayAdapter<ShezhiItem>(
//				getActivity(),R.layout.layout_shezhi_item, iList);
		adapter=new ShezhiAdapter(iList, getActivity());
		ListView listView = (ListView) view.findViewById(R.id.ListView01);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
					logout(position);
					break;
//				case 1:
//					webconfig(position);
//					break;
				case 1:
					upload(position);
					break;
				case 2:
					downloaddata(position);
					break;
				case 3:
					FXCQuery.startActivity(getActivity());
					break;
				case 4:
					WGHQuery.startActivity(getActivity());
					break;
				default:
					break;
				}
			}

		});

	}

	public void downloaddata(int position) {

		Thread t = new Thread(rdownload);
		tempposition = position;
		t.start();

		Toast.makeText(getActivity(), "正在下载请稍后！", Toast.LENGTH_LONG).show();

	}

	public int tempposition = -1;
	Runnable rdownload = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();
			try {
				download(tempposition);
				// download123();

				handler.sendEmptyMessage(CODE_DOWNLOAD_SUCCESS);

			} catch (Exception e) {

				// Log.i("sss",e.getMessage());
				handler.sendEmptyMessage(CODE_DOWNLOAD_FAIL);
			}
			Looper.loop();
		}
	};

	private void download(int position) {

		String enforcementtype = "";
		// TODO Auto-generated method stub
		HashMap<String, String> hm = new HashMap<String, String>();

		String susername = MyDbInfo.account;
		String spassword = MyDbInfo.password;
		WebServiceJC.susername = MyDbInfo.account;
		// String
		// sql="select zpenforcementtype from tbzfPerson where zpmobilephone='"+WebServiceJC.susername+"'";
		// hm.put("username",susername);
		hm.put("zpmobilephone", susername);
		// hm.put("sql",sql);
		WebServiceJC.setUrl("inner", myDbHelper);
		if (hm != null) {

			enforcementtype = WebServiceJC.WorkJC("getUserType", hm);
			Log.e("aaaaa", "==================" + enforcementtype);
			if(enforcementtype=="农产品质量安全监管"){
				enforcementtype="行政执法证";
			}
		}

//		WebServiceJC.susername = "guangzhou";
//		String sql = "select * from tbzfsc where senforcementtype='"
//				+ enforcementtype + "'";
		String sql = "select * from tbzfsc";
		hm.put("username", susername);
		hm.put("password", spassword);
		hm.put("sql", sql);
		WebServiceJC.setUrl("inner", myDbHelper);
		String result = WebServiceJC.WorkJC("downloadinfo", hm);
		DataTable dt = null;
		try {
			dt = WebServiceJC.getChannelListzfxc(result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dt != null) {

			myDbHelper.open();
			Cursor c = null;

			try {
				for (int i = 0; i < dt.getRow().size(); i++) {
					sql = "select * from " + MyDbInfo.getTableNames()[6]
							+ " where sname='"
							+ dt.getRow().get(i).getColumn("sname").toString()
							+ "' and  ddate='"
							+ dt.getRow().get(i).getColumn("ddate").toString()
							+ "'";
					c = myDbHelper.select(sql);
					if (!c.moveToFirst()) {

						String table = "tbzfsc";
						String fields[] = { "sname", "ddate", "sfilepath",
								"sperson", "scomment" };
						String values[] = {
								dt.getRow().get(i).getColumn("sname")
										.toString(),
								dt.getRow().get(i).getColumn("ddate")
										.toString(),
								dt.getRow().get(i).getColumn("sfilepath")
										.toString(),
								dt.getRow().get(i).getColumn("sperson")
										.toString(),
								dt.getRow().get(i).getColumn("scomment")
										.toString() };

						myDbHelper.insert(MyDbInfo.getTableNames()[6], fields,
								values);
					}

					c.close();
				}
				// c.close();
				myDbHelper.close();
			} catch (Exception e) {
				System.out.println(e.toString());
				String s = e.toString();
				System.out.println(s);
			} finally {

				if (c != null) {
					c.close();
				}
				myDbHelper.close();
			}

			// 下载文件
			Intent intent = new Intent();
			// ��Mp3Info������뵽Intent������

			intent.setClass(getActivity(), DownloadService.class);
			// ����Service
			getActivity().startService(intent);
		}

	}

	public void download123() {
		String result = "";

		HashMap<String, String> hm = new HashMap<String, String>();
		String susername = "admin";
		String spassword = "gzncp";
		WebServiceJC.susername = "guangzhou";
		String sql = "select * from tbmission where suser='"
				+ WebServiceJC.susername + "' and sstatus='未接受'";

		hm.put("username", susername);
		hm.put("password", spassword);
		hm.put("sql", sql);
		WebServiceJC.setUrl("inner", myDbHelper);
		if (hm != null) {

			result = WebServiceJC.WorkJC("downloadinfo", hm);
		}
		sql = "update tbmission set sstatus='已接受' where suser='"
				+ WebServiceJC.susername + "'";
		hm.put("username", susername);
		hm.put("password", spassword);
		hm.put("sql", sql);
		WebServiceJC.WorkJC("downloadinfo", hm);// 更新任务状态
		DataTable dt = null;
		try {
			dt = WebServiceJC.getChannelListzfxc(result);

			addrenwu(dt);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean flag = false;

	}
	
	public void addrenwu(DataTable dt) {

		try {
			myDbHelper.open();
			String table = MyDbInfo.getTableNames()[4];
			String fields[] = { "sname", "scontent", "ddate", "sstatus",
					"scomment", "sresult", "sperson", "suser", "serverid" };
			String values[] = { "执法监督", "前往**农场进行执法监督", "2015-09-19", "已接受",
					"", "", "", "guangzhou", "serverid" };
			for (int i = 0; i < dt.getRow().size(); i++) {
				values[0] = dt.getRow().get(i).getColumn("sname").toString();
				values[1] = dt.getRow().get(i).getColumn("scontent").toString();

				SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");

				values[2] = dt.getRow().get(0).getColumn("ddate").toString()
						.split(" ")[0];
				try {

					values[2] = dFormat.format(dFormat.parse(values[2]));
				} catch (Exception e111) {

				}
				values[3] = dt.getRow().get(i).getColumn("sstatus").toString();
				values[4] = dt.getRow().get(i).getColumn("scomment").toString();
				values[5] = dt.getRow().get(i).getColumn("sresult").toString();
				values[6] = dt.getRow().get(i).getColumn("sperson").toString();
				values[7] = dt.getRow().get(i).getColumn("suser").toString();
				values[8] = dt.getRow().get(i).getColumn("id").toString();
				myDbHelper.insert(table, fields, values);
			}

			myDbHelper.close();
			// Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_LONG)
			// .show();

		} catch (Exception e) {
			System.out.println(e.getMessage());

		} finally {
			try {
				myDbHelper.close();

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}

	private void upload(int position) {
		// TODO Auto-generated method stub

		Thread t = new Thread(rupload);
		t.start();

		Toast.makeText(getActivity(), "正在上传请稍后！", Toast.LENGTH_LONG).show();
	}

	Runnable rupload = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();
			try {
				// uploadrwgl();
				if(MyDbInfo.PuResult!=null){
					MyDbInfo.PuResult.clear();
//					uploadxczf();
//					upJcxczf();
//					upDBxczf();
					
				}else{
//					uploadxczf();
//					upJcxczf();
//					upDBxczf();
				}
//				upJcxczf();
				// uploadpicture();
				handler.sendEmptyMessage(CODE_UPLOAD_SUCCESS);

			} catch (Exception e) {

				// Log.i("sss",e.getMessage());
				handler.sendEmptyMessage(CODE_UPLOAD_FAIL);
			}
			Looper.loop();
		}

	};

	private void uploadpicture(String imagepath) {
		// TODO Auto-generated method stub
		try {

			String filename = XCZFEditActivity.cachePath + imagepath;
			byte[] imagebytes = FileUtils.readFileSdcardFile(filename);

			int uploadlength = 500000;
			long filecursor = 0;
			while (true) {

				HashMap<String, String> params = new HashMap<String, String>();
				long end = 0;
				if ((filecursor + uploadlength) > imagebytes.length) {
					end = imagebytes.length;
				} else {

					end = filecursor + uploadlength;
				}

				byte[] buffers = new byte[(int) (end - filecursor)];

				for (int i = 0; i < buffers.length; i++) {
					buffers[i] = imagebytes[(int) (filecursor + i)];

				}

				filecursor = end;
				String uploadBuffer = new String(Base64.encode(buffers));
				params.put("image", uploadBuffer);
				params.put("filename", WebServiceJC.susername + imagepath);
				String method = "uploadzfxcPicture";

				// WebServiceJC.setUrl("outter", dbmanager);
				// WebServiceJC.SERVICE_URL="http://192.168.7.253/gzvpms_webservice/Service.asmx";
				WebServiceJC.setNetWorkCondition();
				WebServiceJC.callWebServiceMethod(method, params);
				if (filecursor >= imagebytes.length) {
					break;
				}

			}

			Toast.makeText(getActivity(), "上传成功！", Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			e.printStackTrace();
			// Toast.makeText(getActivity(), "上传失败，请检查网络！", Toast.LENGTH_SHORT)
			// .show();
		}
	}

	private void uploadsound(String imagepath) {
		// TODO Auto-generated method stub
		try {

			String filename = XCZFEditActivity.cachePathaudio + imagepath;
			byte[] imagebytes = FileUtils.readFileSdcardFile(filename);

			int uploadlength = 500000;
			long filecursor = 0;
			while (true) {

				HashMap<String, String> params = new HashMap<String, String>();
				long end = 0;
				if ((filecursor + uploadlength) > imagebytes.length) {
					end = imagebytes.length;
				} else {

					end = filecursor + uploadlength;
				}

				byte[] buffers = new byte[(int) (end - filecursor)];

				for (int i = 0; i < buffers.length; i++) {
					buffers[i] = imagebytes[(int) (filecursor + i)];

				}

				filecursor = end;
				String uploadBuffer = new String(Base64.encode(buffers));
				params.put("image", uploadBuffer);
				params.put("filename", WebServiceJC.susername + imagepath);
				String method = "uploadzfxcPicture";

				// WebServiceJC.setUrl("outter", dbmanager);
				// WebServiceJC.SERVICE_URL="http://192.168.1.102/gzvpms_webservice/Service.asmx";
				// WebServiceJC.SERVICE_URL="http://192.168.7.253/gzvpms_webservice/Service.asmx";
				WebServiceJC.setNetWorkCondition();
				WebServiceJC.callWebServiceMethod(method, params);
				if (filecursor >= imagebytes.length) {
					break;
				}

			}

			Toast.makeText(getActivity(), "上传成功！", Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			e.printStackTrace();
			// Toast.makeText(getActivity(), "上传失败，请检查网络！",
			// Toast.LENGTH_SHORT).show();
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == CODE_UPLOAD_SUCCESS) {

				Toast.makeText(getActivity(), "数据上传成功", Toast.LENGTH_LONG)
						.show();
			} else if (msg.what == CODE_UPLOAD_FAIL) {

				Toast.makeText(getActivity(), "数据上传失败，请检查网络", Toast.LENGTH_LONG)
						.show();
			} else if (msg.what == CODE_DOWNLOAD_SUCCESS) {

				Toast.makeText(getActivity(), "数据下载成功", Toast.LENGTH_LONG)
						.show();
			} else if (msg.what == CODE_DOWNLOAD_FAIL) {

				Toast.makeText(getActivity(), "数据下载失败，请检查网络", Toast.LENGTH_LONG)
						.show();
			}

		}
	};

	private void uploadrwgl() {
		// TODO Auto-generated method stub
		Cursor c = null;
		try {
			myDbHelper.open();

			String sql = "select * from "
					+ MyDbInfo.getTableNames()[4]
					+ " where sstatus='已完成' and isup<>'1' or isup is null or isup=''";
			c = myDbHelper.select(sql);
			String iserverids = "";
			while (c.moveToNext()) {

				// iserverids=iserverids+c.getString(0)+",";
				// iserverids=iserverids.substring(0,iserverids.length()-1);
				sql = "update tbmission set sstatus='已完成',sresult='"
						+ c.getString(c.getColumnIndex("sresult"))
						+ "' ,sperson='"
						+ c.getString(c.getColumnIndex("sperson"))
						+ "',scomment='"
						+ c.getString(c.getColumnIndex("scomment"))
						+ "' where id ="
						+ c.getString(c.getColumnIndex("serverid"));
				HashMap<String, String> hm = new HashMap<String, String>();
				String susername = "admin";
				String spassword = "gzncp";

				hm.put("username", susername);
				hm.put("password", spassword);
				hm.put("sql", sql);
				WebServiceJC.setUrl("inner", myDbHelper);
				WebServiceJC.WorkJC("downloadinfo", hm);// 更新任务状态
			}
			// if(!iserverids.equals("")){
			//
			// iserverids=iserverids.substring(0,iserverids.length()-1);
			// sql="update tbmission set sstatus='已完成'  where id in ("+iserverids+")";
			// HashMap<String, String>hm=new HashMap<String, String>();
			// String susername="admin";
			// String spassword="gzncp";
			//
			// hm.put("username",susername);
			// hm.put("password",spassword);
			// hm.put("sql",sql);
			// WebServiceJC.setUrl("inner", myDbHelper);
			// WebServiceJC.WorkJC("downloadinfo", hm);//更新任务状态
			//
			// }

			c.close();
			myDbHelper.close();

		} catch (Exception e) {
			e.printStackTrace();
			// Toast.makeText(getActivity(), "上传失败，请检查网络是否连接成功",
			// Toast.LENGTH_LONG)
			// .show();
		} finally {
			try {
				myDbHelper.close();

			} catch (Exception e2) {
				// TODO: handle exception
			}

		}

	}
	/***
	 * 上传农业执法的
	 * @throws Exception
	 */
	private void uploadxczf() throws Exception {
		// TODO Auto-generated method stub
		
		int rezult = 0;
		String sql = "select * from tbnyxczf";
		myDbHelper.open();
		Cursor c = myDbHelper.select(sql);

		// String sqldel = "";
		// sqldel = "delete from tbzfsc where username='"
		// + WebServiceJC.susername + "'";
		// executeSql(sqldel);
		try {

			while (c.moveToNext()) {
				
				int id = c.getInt(c.getColumnIndex("Id"));
				String date = c.getString(c.getColumnIndex("nydate"));
				String companyname = c.getString(c
						.getColumnIndex("nycompany"));
				String address = c.getString(c.getColumnIndex("nyaddress"));
				String contactor = c.getString(c.getColumnIndex("nyperson"));
				String telephone = c.getString(c.getColumnIndex("nytelephone"));
				String nyfull = c.getString(c.getColumnIndex("nyfullperson"));
				String nypart = c.getString(c.getColumnIndex("nypartperson"));
				String nyzhong = c.getString(c.getColumnIndex("nyzhong"));
				String nychu = c.getString(c.getColumnIndex("nychu"));
				String nyyu = c.getString(c.getColumnIndex("nyyu"));
				String nybumen = c
						.getString(c.getColumnIndex("nyrebumen"));
				String nybmproblem = c.getString(c
						.getColumnIndex("nyrebmproblem"));
				String nywork = c
						.getString(c.getColumnIndex("nyrework"));
				String nywprobelm = c.getString(c
						.getColumnIndex("nyreworkproblem"));
				String nyjilu = c.getString(c.getColumnIndex("nyrejilu"));
				String nyjlproblem = c
						.getString(c.getColumnIndex("nyrejlproblem"));
				String nydangan = c
						.getString(c.getColumnIndex("nyredangan"));
				String nydaproblem = c.getString(c
						.getColumnIndex("nyredaproblem"));
				String nyguifan = c.getString(c.getColumnIndex("nymasguifan"));
				String nygfproblem = c
						.getString(c.getColumnIndex("nymasgfproblem"));
				String nycxml = c
						.getString(c.getColumnIndex("nymascxml"));
				String nycxmlproblem = c.getString(c
						.getColumnIndex("nymasmlproblem"));
				String nybeian = c.getString(c.getColumnIndex("nymasbeian"));
				String nybaproblem = c.getString(c.getColumnIndex("nymasbaproblem"));
				String nyguanli = c.getString(c.getColumnIndex("nymasguanli"));
				String nyglproblem = c
						.getString(c.getColumnIndex("nymasglproblem"));
				String nyqiye = c
						.getString(c.getColumnIndex("nymaqiye"));
				String nyqyproblem = c.getString(c
						.getColumnIndex("nymaqyproblem"));
				String nyml = c
						.getString(c.getColumnIndex("maml"));
				String nymlproblem = c.getString(c
						.getColumnIndex("mamlproblem"));
				String nyhuishou = c
						.getString(c.getColumnIndex("mahuishou"));
				String nyhsproblem = c.getString(c
						.getColumnIndex("mahsproblem"));
				String gpsx = c.getString(c.getColumnIndex("nysgpsx"));
				String gpsy = c.getString(c.getColumnIndex("nysgpsy"));
				String images = c.getString(c.getColumnIndex("nysimages"));
				String audio = c.getString(c.getColumnIndex("nysaudio"));
//				String zfadress = c.getString(c.getColumnIndex("sadresset"));
				String svideo = c.getString(c.getColumnIndex("nysvideo"));
				String missId=c.getString(c.getColumnIndex("missionId"));
				String missName=c.getString(c.getColumnIndex("missname"));
				String missPerson=c.getString(c.getColumnIndex("missperson"));
				WebServiceJC.setUrl("inner", myDbHelper);
				uploadpicture(images);
				uploadsound(audio);
				uploadvideo(svideo);
				HashMap<String, String> hm = new HashMap<String, String>();
				String sql2="delete xc_wghzhjc_f2  where missionid="+missId+" and zfPerson='"+missPerson+"'";
				hm.put("username", MyDbInfo.account);
				hm.put("password", MyDbInfo.password);
				hm.put("sql", sql2);
				WebServiceJC.setUrl("inner", myDbHelper);
				WebServiceJC.WorkJC("downloadinfo", hm);
				// String isup = c.getString(c.getColumnIndex("sisup"));
				String sql1 = "insert into xc_wghzhjc_f2 (missionId, missionName, zfPerson, zfDate, v1, v2_1, "
						+ "   v2_2, v2_3, v3, v4, v5, v5_2, "
						+ "  v6, v6_2, v7, v7_2, v8, v8_2, "
						+ "   v9, v9_2, v10, v10_2, v11, v11_2, v12, v12_2, "
						+ "   v13, v13_2, v14, v14_2, v15, v15_2,gpsX,gpsY)values ('"
						+ missId
						+ "','"
						+ missName
						+ "','"
						+ missPerson
						+ "','"
						+ date
						+ "','"
						+ companyname
						+ "','"
						+ nyzhong
						+ "','"
						+ nychu
						+ "','"
						+ nyyu
						+ "','"
						+ nyfull
						+ "','"
						+ nypart
						+ "','"
						+ Conversion(nybumen)
						+ "','"
						+ nybmproblem
						+ "','"
						+Conversion(nywork) 
						+ "','"
						+ nywprobelm
						+ "','"
						+ Conversion(nyjilu) 
						+ "','"
						+ nyjlproblem
						+ "','"
						+ Conversion(nydangan) 
						+ "','"
						+ nydaproblem
						+ "','"
						+ Conversion(nyguifan) 
						+ "','"
						+ nygfproblem
						+ "','"
						+ Conversion(nycxml) 
						+ "','"
						+ nycxmlproblem
						+ "','"
						+Conversion(nybeian)  
						+ "','"
						+ nybaproblem
						+ "','"
						+Conversion(nyguanli)  
						+ "','"
						+ nyglproblem
						+ "','"
						+Conversion(nyqiye)  
						+ "','"
						+ nyqyproblem
						+ "','"
						+ Conversion(nyml) 
						+ "','"
						+ nymlproblem
						+ "','"
						+Conversion(nyhuishou)  
						+ "','"
						+ nyhsproblem
						+"','"
						+gpsx
						+"','"
						+gpsy
						 + "')";
				
				boolean flag_i = executeSql(sql1);
//				sql = "update tbnyxczf set isup=1 where id=" + id;
//
//				myDbHelper.open();
//				myDbHelper.execSQL(sql);
				System.out.println("flag_i=" + flag_i);
				if (flag_i == true) {
					rezult = 1;
					MyDbInfo.PuResult.add(missId);
				} else {
					rezult = 0;
				}
			}

			if (!c.moveToFirst()) {
				rezult = 2;
			}
		} catch (Exception e) {
		e.printStackTrace();

		}
		c.close();
		myDbHelper.close();
		return ;

	}
	/***
	 * 上传检查员
	 * @throws Exception
	 */
	private void upJcxczf() throws Exception {
		// TODO Auto-generated method stub
		
		int rezult = 0;
		String sql = "select * from tbjcxczf";
		myDbHelper.open();
		Cursor c = myDbHelper.select(sql);

		// String sqldel = "";
		// sqldel = "delete from tbzfsc where username='"
		// + WebServiceJC.susername + "'";
		// executeSql(sqldel);
		try {

			while (c.moveToNext()) {
				int id = c.getInt(c.getColumnIndex("Id"));
				String date = c.getString(c.getColumnIndex("edate"));
				String jbjccompany = c.getString(c
						.getColumnIndex("jbjccompany"));
				String jbjcphone = c.getString(c.getColumnIndex("jbjcphone"));
				String jbzuzhang = c.getString(c.getColumnIndex("jbzuzhang"));
				String jbsuitong = c.getString(c.getColumnIndex("jbsuitong"));
				String jbsjcompany = c.getString(c.getColumnIndex("jbsjcompany"));
				String jbsjphone = c.getString(c.getColumnIndex("jbsjphone"));
				String jbfading = c.getString(c.getColumnIndex("jbfading"));
				String jbproduct = c.getString(c.getColumnIndex("jbproduct"));
				String jbaddress = c.getString(c.getColumnIndex("jbaddress"));
				String wtproblem = c
						.getString(c.getColumnIndex("wtproblem"));
				String zgcontent = c.getString(c
						.getColumnIndex("zgcontent"));
				String zgdate = c
						.getString(c.getColumnIndex("zgdate"));
				String bjcon = c.getString(c
						.getColumnIndex("bjcon"));
				String bjdate = c.getString(c.getColumnIndex("bjdate"));
				String fhproblem = c
						.getString(c.getColumnIndex("fhproblem"));
				String fhdate = c
						.getString(c.getColumnIndex("fhdate"));
				String jcsgpsx = c.getString(c
						.getColumnIndex("jcsgpsx"));
				String jcsgpsy = c.getString(c.getColumnIndex("jcsgpsy"));
				String missionId = c
						.getString(c.getColumnIndex("missionId"));
				String missname = c
						.getString(c.getColumnIndex("missname"));
				String missperson = c.getString(c
						.getColumnIndex("missperson"));
				
				WebServiceJC.setUrl("inner", myDbHelper);
				HashMap<String, String> hm = new HashMap<String, String>();
				String sql2="delete xc_wghzhjc_f5  where missionid="+missionId+" and zfPerson='"+missperson+"'";
				hm.put("username", MyDbInfo.account);
				hm.put("password", MyDbInfo.password);
				hm.put("sql", sql2);
				WebServiceJC.setUrl("inner", myDbHelper);
				WebServiceJC.WorkJC("downloadinfo", hm);
				// String isup = c.getString(c.getColumnIndex("sisup"));

				String sql1 = "insert into xc_wghzhjc_f5 (missionId, missionName, zfPerson, zfDate, v1, v2, "
						+ "   v3, v4, v5, v6, v7, v8, "
						+ "  v9, v10, v11, v12, v13, v14, "
						+ "   v15, v16,gpsX,gpsY)values ('"
						+ missionId
						+ "','"
						+ missname
						+ "','"
						+ missperson
						+ "','"
						+ date
						+ "','"
						+ jbjccompany
						+ "','"
						+ jbjcphone
						+ "','"
						+ jbzuzhang
						+ "','"
						+ jbsuitong
						+ "','"
						+ jbsjcompany
						+ "','"
						+ jbsjphone
						+ "','"
						+ jbfading
						+ "','"
						+ jbproduct
						+ "','"
						+jbaddress
						+ "','"
						+ wtproblem
						+ "','"
						+zgcontent
						+ "','"
						+ zgdate
						+ "','"
						+ bjcon
						+ "','"
						+ bjdate
						+ "','"
						+ fhproblem
						+ "','"
						+ fhdate
						+ "','"
						+ jcsgpsx
						+ "','"
						+ jcsgpsy
						 + "')";
				
				boolean flag_i = executeSql(sql1);
//				sql = "update tbnyxczf set isup=1 where id=" + id;
//
//				myDbHelper.open();
//				myDbHelper.execSQL(sql);
				System.out.println("flag_i=" + flag_i);
				if (flag_i == true) {
					rezult = 1;
					MyDbInfo.PuResult.add(missionId);
				} else {
					rezult = 0;
				}
			}

			if (!c.moveToFirst()) {
				rezult = 2;
			}
		} catch (Exception e) {
		e.printStackTrace();

		}
		c.close();
		myDbHelper.close();
		return ;

	}
	/***
	 * 上传地标
	 * @throws Exception
	 */
	private void upDBxczf() throws Exception {
		// TODO Auto-generated method stub
		
		int rezult = 0;
		String sql = "select * from tbdbxczf";
		myDbHelper.open();
		Cursor c = myDbHelper.select(sql);

		// String sqldel = "";
		// sqldel = "delete from tbzfsc where username='"
		// + WebServiceJC.susername + "'";
		// executeSql(sqldel);
		try {

			while (c.moveToNext()) {
			
				int id = c.getInt(c.getColumnIndex("Id"));
				String date = c.getString(c.getColumnIndex("dbdate"));
				String companyname = c.getString(c
						.getColumnIndex("sjcompany"));
				String address = c.getString(c.getColumnIndex("saddress"));
				String jbdj = c.getString(c.getColumnIndex("jbdj"));
				String jbxj = c.getString(c.getColumnIndex("jbxj"));
				String hyzhong = c.getString(c.getColumnIndex("hyzhong"));
				String hychu = c.getString(c.getColumnIndex("hychu"));
				String hyyu = c.getString(c.getColumnIndex("hyyu"));
				String wmlcount = c.getString(c.getColumnIndex("wmlcount"));
				String wproductcount = c.getString(c.getColumnIndex("wproductcount"));
				String wgzcount = c
						.getString(c.getColumnIndex("wgzcount"));
				String wpxcount = c.getString(c
						.getColumnIndex("wpxcount"));
				String wreg = c
						.getString(c.getColumnIndex("wreg"));
				String wdengji = c.getString(c
						.getColumnIndex("wdengji"));
				String wdjproblem = c.getString(c.getColumnIndex("wdjproblem"));
				String wziliao = c
						.getString(c.getColumnIndex("wziliao"));
				String wzlproblem = c
						.getString(c.getColumnIndex("wzlproblem"));
				String jgxize = c.getString(c
						.getColumnIndex("jgxize"));
				String jgxzprobelm = c.getString(c.getColumnIndex("jgxzprobelm"));
				String jgdiyu = c
						.getString(c.getColumnIndex("jgdiyu"));
				String jgdyproblem = c
						.getString(c.getColumnIndex("jgdyproblem"));
				String jgbiaozhi = c.getString(c
						.getColumnIndex("jgbiaozhi"));
				String jgbzproblem = c.getString(c.getColumnIndex("jgbzproblem"));
				String jgbeian = c.getString(c.getColumnIndex("jgbeian"));
				String jgbaproblem = c.getString(c.getColumnIndex("jgbaproblem"));
				String zcyusuan = c
						.getString(c.getColumnIndex("zcyusuan"));
				String zcysproblem = c
						.getString(c.getColumnIndex("zcysproblem"));
				String zcguihua = c.getString(c
						.getColumnIndex("zcguihua"));
				String zcghproblem = c
						.getString(c.getColumnIndex("zcghproblem"));
				String zczhichi = c.getString(c
						.getColumnIndex("zczhichi"));
				String zczcproblem = c
						.getString(c.getColumnIndex("zczcproblem"));
				String zckaohe = c.getString(c
						.getColumnIndex("zckaohe"));
				String zckhproblem = c.getString(c.getColumnIndex("zckhproblem"));
				String zcjingyan = c.getString(c.getColumnIndex("zcjingyan"));
				String yjproblem = c.getString(c.getColumnIndex("yjproblem"));
				String jcsgpsx = c.getString(c.getColumnIndex("jcsgpsx"));
				String jcsgpsy = c.getString(c.getColumnIndex("jcsgpsy"));
				String missId=c.getString(c.getColumnIndex("missionId"));
				String missName=c.getString(c.getColumnIndex("missname"));
				String missPerson=c.getString(c.getColumnIndex("missperson"));
				WebServiceJC.setUrl("inner", myDbHelper);
				HashMap<String, String> hm = new HashMap<String, String>();
				String sql2="delete xc_dlbzzhjc_f3  where missionid="+missId+" and zfPerson='"+missPerson+"'";
				hm.put("username", MyDbInfo.account);
				hm.put("password", MyDbInfo.password);
				hm.put("sql", sql2);
				WebServiceJC.setUrl("inner", myDbHelper);
				WebServiceJC.WorkJC("downloadinfo", hm);
				// String isup = c.getString(c.getColumnIndex("sisup"));
				String sql1 = "insert into xc_dlbzzhjc_f3 (missionId, missionName, zfPerson, zfDate, v1, v2, "
						+ "v3, v4_1, v4_2,v5_1, v5_2, v5_3, "
						+ "v6, v7, v8, v9, v10, v11,"
						+ "v11_2, v12, v12_2, v13, v13_2, v14, v14_2, v15,"
						+ "v15_2, v16, v16_2, v17, v17_2, v18,v18_2,v19,v19_2,v20,v20_2,v21,v22,gpsX,gpsY)values ('"
						+ missId
						+ "','"
						+ missName
						+ "','"
						+ missPerson
						+ "','"
						+ date
						+ "','"
						+ address
						+ "','"
						+ date
						+ "','"
						+ companyname
						+ "','"
						+ jbdj
						+ "','"
						+ jbxj
						+ "','"
						+ hyzhong
						+ "','"
						+ hychu
						+ "','"
						+ hyyu
						+ "','"
						+wmlcount
						+ "','"
						+ wproductcount
						+ "','"
						+ wgzcount
						+ "','"
						+ wpxcount
						+ "','"
						+ wreg
						+ "','"
						+ Conversion(wdengji) 
						+ "','"
						+ wdjproblem 
						+ "','"
						+ Conversion(wziliao) 
						+ "','"
						+ wzlproblem
						+ "','"
						+ Conversion(jgxize) 
						+ "','"
						+jgxzprobelm 
						+ "','"
						+ Conversion(jgdiyu)  
						+ "','"
						+jgdyproblem
						+ "','"
						+ Conversion(jgbiaozhi)  
						+ "','"
						+jgbzproblem
						+ "','"
						+ Conversion(jgbeian)
						+ "','"
						+ jgbaproblem
						+ "','"
						+ Conversion(zcyusuan)  
						+ "','"
						+zcysproblem 
						+ "','"
						+ Conversion(zcguihua)  
						+ "','"
						+zcghproblem 
						+ "','"
						+ Conversion(zczhichi)  
						+ "','"
						+zczcproblem 
						+ "','"
						+ Conversion(zckaohe)  
						+ "','"
						+zckhproblem 
						+ "','"
						+ zcjingyan
						+"','"
						+ yjproblem
						+"','"
						+jcsgpsx
						+"','"
						+jcsgpsy
						 + "')";
				
				boolean flag_i = executeSql(sql1);
//				sql = "update tbnyxczf set isup=1 where id=" + id;
//
//				myDbHelper.open();
//				myDbHelper.execSQL(sql);
				System.out.println("flag_i=" + flag_i);
				if (flag_i == true) {
					rezult = 1;
					MyDbInfo.PuResult.add(missId);
				} else {
					rezult = 0;
				}
			}

			if (!c.moveToFirst()) {
				rezult = 2;
			}
		} catch (Exception e) {
		e.printStackTrace();

		}
		c.close();
		myDbHelper.close();
		return ;

	}
	private void uploadvideo(String svideo) {
		// TODO Auto-generated method stub
		try {

			String filename = XCZFEditActivity.cachePathvideo + svideo;
			byte[] imagebytes = FileUtils.readFileSdcardFile(filename);

			int uploadlength = 500000;
			long filecursor = 0;
			while (true) {

				HashMap<String, String> params = new HashMap<String, String>();
				long end = 0;
				if ((filecursor + uploadlength) > imagebytes.length) {
					end = imagebytes.length;
				} else {

					end = filecursor + uploadlength;
				}

				byte[] buffers = new byte[(int) (end - filecursor)];

				for (int i = 0; i < buffers.length; i++) {
					buffers[i] = imagebytes[(int) (filecursor + i)];

				}

				filecursor = end;
				String uploadBuffer = new String(Base64.encode(buffers));
				params.put("image", uploadBuffer);
				params.put("filename", WebServiceJC.susername + svideo);
				String method = "uploadzfxcPicture";

				// WebServiceJC.setUrl("outter", dbmanager);
				// WebServiceJC.SERVICE_URL="http://192.168.1.102/gzvpms_webservice/Service.asmx";
				// WebServiceJC.SERVICE_URL="http://192.168.7.253/gzvpms_webservice/Service.asmx";
				WebServiceJC.setNetWorkCondition();
				WebServiceJC.callWebServiceMethod(method, params);
				if (filecursor >= imagebytes.length) {
					break;
				}

			}

		} catch (Exception e) {

		}
	}

	public static boolean executeSql(String sql) {
		WebServiceJC.setNetWorkCondition();
		// 命名空间
		String nameSpace = "http://tempuri.org/";
		// 调用的方法名称
		String methodName = "rsqlStr";
		// EndPoint
		// String endPoint = "";
		// SOAP Action
		String soapAction = "http://tempuri.org/rsqlStr";

		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
		rpc.addProperty("sqlStr", sql);
		// rpc.addProperty("userId", "");

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(
				WebServiceJC.SERVICE_URL);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		// 获取返回的结果
		String result = object.getProperty(0).toString();

		// 将WebService返回的结果显示在TextView中
		System.out.println("result===" + result);

		return Boolean.parseBoolean(result);
	}

	public void webconfig(int position) {

		Intent intent = new Intent(getActivity(), WebConfigActivity.class);
		startActivity(intent);

	}

	private void logout(int position) {
		// TODO Auto-generated method stub
		if (position == 0) {

			AlertDialog dialog = new AlertDialog.Builder(getActivity())
					.setTitle("确认注销吗？")

					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 点击“确认”后的操作
									deleteuser();

								}

								private void deleteuser() {
									// TODO Auto-generated method stub
									try {
										myDbHelper.open();

										// String []wherevalue={id};
										myDbHelper.delete(
												MyDbInfo.getTableNames()[2],
												null, null);
										myDbHelper.delete(
												MyDbInfo.getTableNames()[8],
												null, null);
										myDbHelper.delete(
												MyDbInfo.getTableNames()[4],
												null, null);
										myDbHelper.delete(
												MyDbInfo.getTableNames()[9],
												null, null);
										myDbHelper.delete(
												MyDbInfo.getTableNames()[10],
												null, null);
										myDbHelper.delete(
												MyDbInfo.getTableNames()[11],
												null, null);
										myDbHelper.delete(
												MyDbInfo.getTableNames()[12],
												null, null);
										myDbHelper.delete(
												MyDbInfo.getTableNames()[13],
												null, null);

										myDbHelper.close();
										 File file = new File(getpath); 
								            DeleteFile(file); 
										Toast.makeText(getActivity(), "注销成功",
												Toast.LENGTH_LONG).show();
										Intent intent = new Intent(
												getActivity(),
												LoginActivity.class);
										startActivity(intent);

										getActivity().finish();

									} catch (Exception e) {
										Toast.makeText(getActivity(), "删除失败",
												Toast.LENGTH_LONG).show();
									} finally {
										myDbHelper.close();

									}
								}

								private void DeleteFile(File file) {
									 if (file.isFile()) { 
							                file.delete(); 
							                return; 
							            } 
							            if (file.isDirectory()) { 
							                File[] childFile = file.listFiles(); 
							                if (childFile == null || childFile.length == 0) { 
							                    file.delete(); 
							                    return; 
							                } 
							                for (File f : childFile) { 
							                    DeleteFile(f); 
							                } 
							                file.delete(); 
							            } 
									
								}

							})
					.setNegativeButton("返回",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 点击“返回”后的操作,这里不设置没有任何操作
								}
							}).create();
			dialog.show();
			WindowManager.LayoutParams params = dialog.getWindow()
					.getAttributes();
			params.width = 400;
			params.height = 200;
			dialog.getWindow().setAttributes(params);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i("slide", "JobFragment--onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i("slide", "JobFragment--onStop");
	}
	public String Conversion(String s){
		if(s.equals("是")){
			return "1";
		}else {
		return "0";	
		}
	}
	
}
