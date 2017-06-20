package com.zfxc.ui;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;
import com.example.zfxc_wbx.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zfxc.entity.Positionet;
import com.zfxc.entity.ZFgePerson;
import com.zfxc.maputil.LocationApplication;
import com.zfxc.util.DatePickerFragment;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;
import com.zfxc.wbx.DisplayZFXCActivity;

public class PFKHEditActivity extends Activity implements LocationListener {
	private static final String TAG = "通知";
	static Uri mUri;
	public static String missId;
	public static String Nyid;
	private static String mName;
	private static String mPerson;
	private static String county;
	private String phone=MyDbInfo.account;
	private TextView etdescrib;
	static String Photofilepath;
	static String videofilepath;
	private ImageView imageView;
	private ImageView imageviewsound;
	private ImageView imageviewvideo;
	int RECORD_CODE = 2;
	int AUDIO_CODE = 3;
	int CAPTURE_CODE = 1;
	int VIDEO_CODE = 4;
	int VIDEO_CAMERA_CODE = 5;
	private Bitmap cameraBitmap;

	BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
	String FILE_PATH = "";
	private boolean isBig = false;
	static Bitmap bitmap;
	int IMAGE_CODE = 0;
	public static DisplayImageOptions mNormalImageOptions;
	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().toString();
	public static final String IMAGES_FOLDER = SDCARD_PATH + File.separator
			+ "zfxc_wbx/images" + File.separator + "images" + File.separator;
	public static String cachePath = SDCARD_PATH + "/zfxc_wbx/images/";
	public static String cachePathaudio = SDCARD_PATH + "/zfxc_wbx/audios/";
	public static String cachePathvideo = SDCARD_PATH + "/zfxc_wbx/video/";
	String filename = "";
	String audiofilename = "";
	String videofilename = "";
	boolean caiji = false;// 判断用户是否点击过照片采集

	// 位置采集

	private double mLongitude;
	private double mLatitude;
	private LocationClient mLocationClient;
	private LocationMode lmode = LocationMode.Hight_Accuracy;
	private String tempcoor = "bd09ll";
	public static String locationStr = "";
	private TextView txtsaddressdes;
	public String sgpsx;
	public String sgpsy;
	public String saddress;
	private String firstsound = "";
	private String firstvideo = "";
	BaiduMap mBaiduMap;
	// 声音采集

	private Button btnsy;

	MyDbHelper myDbHelper;
	private Button buttonSetDate;
	private Button btndeleteftrecord;
	private Button btnsave;
	private Button btnzp;
	private Button btnsp;
	private Button btnposition;
	private Button btnaddress;
	private Button btntotal;

	Integer total;
	Integer total1;
	Integer total2;
	Integer total3;
	Integer total4;
	Integer total5;
	Integer total6;
	Integer total7;
	Integer total8;
	Integer total9;
	Integer total10;
	Integer total11;
	Integer total12;
	Integer total13;
	Integer total14;
	Integer total15;
	Integer total16;
	Integer total17;
	Integer total18;
	Integer total19;
	Integer total20;
	Integer total21;
	Integer total22;
	Integer total23;
	Integer total24;
	Integer total25;
	Integer total26;
	Integer total27;
	Integer total28;
	Integer total29;
	Integer totalNum;

	private EditText etddate;
	private Spinner spJidi;
	private EditText txtexperts;
	private TextView txttotalNum;
	private EditText txtsaddress;
	private EditText txtzhutiscore;
	private EditText txtzhutiproblem;
	private EditText txtleixingscore;
	private EditText txtleixingproblem;
	private EditText txtguimoscore;
	private EditText txtguimoproblem;
	private EditText txtmeiguanscore;
	private EditText txtmeiguanproblem;
	private EditText txtfangbianscore;
	private EditText txtfangbianproblem;
	private EditText txtnongcanscore;
	private EditText txtnongcanproblem;
	private EditText txtfeiqiwuscore;
	private EditText txtfeiqiwuproblem;
	private EditText txtchuliscore;
	private EditText txtchuliproblem;
	private EditText txttongzhiscore;
	private EditText txttongzhiproblem;
	private EditText txtjishuscore;
	private EditText txtjishuproblem;

	private EditText txtkongzhiscore;
	private EditText txtkongzhiproblem;
	private EditText txtfushescore;
	private EditText txtfusheproblem;
	private EditText txtzhengshuscore;
	private EditText txtzhengshuproblem;
	private EditText txtpeixunscore;
	private EditText txtpeixunproblem;
	private EditText txtzhiduscore;
	private EditText txtzhiduproblem;
	private EditText txtglshoucescore;
	private EditText txtglshouceproblem;
	private EditText txtzhanshiscore;
	private EditText txtzhanshiproblem;
	private EditText txtdaozescore;
	private EditText txtdaozeproblem;
	private EditText txtnongziscore;
	private EditText txtnongziproblem;
	private EditText txttianjianscore;
	private EditText txttianjianproblem;
	private EditText txtwangluoscore;
	private EditText txtwangluoproblem;
	private EditText txtshangchuanscore;
	private EditText txtshangchuanproblem;
	private EditText txtzhuisuscore;
	private EditText txtzhuisuproblem;
	private EditText txtbiaozhiscore;
	private EditText txtbiaozhiproblem;
	private EditText txtwendingscore;
	private EditText txtwendingproblem;
	private EditText txttongyiscore;
	private EditText txttongyiproblem;
	private EditText txtjiluscore;
	private EditText txtjiluproblem;
	private EditText txtshangbiaoscore;
	private EditText txtshangbiaoproblem;
	private EditText txtdymoshiscore;
	private EditText txtdymoshiproblem;
	private EditText txtbaodaoscore;
	private EditText txtbaodaoproblem;

	private TextView txtjiditotalscore;
	private EditText txtjiditotalproblem;
	private TextView txthuanjingtotalscore;
	private EditText txthuanjingtotalproblem;
	private TextView txtshengchantotalscore;
	private EditText txtshengchantotalproblem;
	private TextView txtguanlitotalscore;
	private EditText txtguanlitotalproblem;
	private TextView txtjianguantotalscore;
	private EditText txtjianguantotalproblem;
	private TextView txtjingyingtotalscore;
	private EditText txtjingyingtotalproblem;
	private TextView txtxiaoshoutotalscore;
	private EditText txtxiaoshoutotalproblem;
	private List<String> qxList = null;
	private static String codeString = null;
	private TextView ProvinceTxt;

	private MapView mMapView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_zfpfedit);
		// initmap();
		// initImageLoader(this);
		qxList = new ArrayList<>();
		init();
		bingSp();
	}

	private void bingSp() {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("qx", county);
		WebServiceJC.setUrl("inner", myDbHelper);
		if (hm != null) {
			String result1 = WebServiceJC.WorkJC("Down_Base_json", hm);
			try {
				JSONObject json = new JSONObject(result1);
				JSONArray jsonArray = json.getJSONArray("root");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
					String bName = jsonObjectSon.getString("sBaseName");
					qxList.add(bName);
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						R.layout.myspinner, qxList);
				spJidi.setAdapter(adapter);
				spJidi.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						ProvinceTxt = (TextView) spJidi.getSelectedView()
								.findViewById(R.id.sp_base); // 得到选中的选项Id
						codeString = ProvinceTxt.getText().toString();
						Log.e("aaaaa", "===========codeString============"
								+ codeString);
						TextView tv = (TextView) view;
						tv.setTextColor(getResources().getColor(R.color.black)); // 设置颜色
						tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL); // 设置居中
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {

					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void initmap() {
		// TODO Auto-generated method stub
		mMapView = (MapView) findViewById(R.id.id_bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启交通图

		// mBaiduMap.setTrafficEnabled(true); open the traffic information

		LatLng point = new LatLng(23.150725, 113.257469);
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.maker);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);

		// 在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);
		MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(13)
				.build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				// Toast.makeText(DisplayZFXCActivity.this,
				// arg0.latitude+":"+arg0.longitude, Toast.LENGTH_SHORT).show();

			}
		});
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(
						PFKHEditActivity.this,
						arg0.getPosition().latitude + "sssss:"
								+ arg0.getPosition().longitude,
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	private void initImageLoader(Context context) {
		int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);
		MemoryCacheAware<String, Bitmap> memoryCache;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			memoryCache = new LruMemoryCache(memoryCacheSize);
		} else {
			memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
		}

		mNormalImageOptions = new DisplayImageOptions.Builder()
				.bitmapConfig(Config.RGB_565).cacheInMemory(true)
				.cacheOnDisc(true).resetViewBeforeLoading(true).build();

		// This
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.defaultDisplayImageOptions(mNormalImageOptions)
				.denyCacheImageMultipleSizesInMemory()
				.discCache(new UnlimitedDiscCache(new File(IMAGES_FOLDER)))
				// .discCacheFileNameGenerator(new Md5FileNameGenerator())
				.memoryCache(memoryCache)
				// .memoryCacheSize(memoryCacheSize)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3)
				.build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	private void init() {
		// TODO Auto-generated method stub
		// jid = getIntent().getStringExtra("id");
		myDbHelper = MyDbHelper.getInstance(this);
		etddate = (EditText) findViewById(R.id.pfetddate);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		etddate.setText(sf.format(new Date()));
		// 获取控件句柄
		spJidi = (Spinner) findViewById(R.id.pftxtjidi);
		txtexperts = (EditText) findViewById(R.id.pfexperts);
		txttotalNum = (TextView) findViewById(R.id.pftotal_num);
		txtsaddress = (EditText) findViewById(R.id.pftxtadress);
		txtzhutiscore = (EditText) findViewById(R.id.pftxtzhutiscore);
		txtzhutiproblem = (EditText) findViewById(R.id.pftxtzhutiproblem);
		txtleixingscore = (EditText) findViewById(R.id.pftxtleixingscore);
		txtleixingproblem = (EditText) findViewById(R.id.pftxtleixingproblem);
		txtguimoscore = (EditText) findViewById(R.id.pftxtguimoscore);
		txtguimoproblem = (EditText) findViewById(R.id.pftxtguimoproblem);
		txtmeiguanscore = (EditText) findViewById(R.id.pftxtmeiguanscore);
		txtmeiguanproblem = (EditText) findViewById(R.id.pftxtmeiguanproblem);
		txtfangbianscore = (EditText) findViewById(R.id.pftxtfangbianscore);
		txtfangbianproblem = (EditText) findViewById(R.id.pftxtfangbianproblem);
		txtnongcanscore = (EditText) findViewById(R.id.pftxtnongcanscore);
		txtnongcanproblem = (EditText) findViewById(R.id.pftxtnongcanproblem);
		txtfeiqiwuscore = (EditText) findViewById(R.id.pftxtfeiqiwuscore);
		txtfeiqiwuproblem = (EditText) findViewById(R.id.pftxtfeiqiwuproblem);
		txtchuliscore = (EditText) findViewById(R.id.pftxtchuliscore);
		txtchuliproblem = (EditText) findViewById(R.id.pftxtchuliproblem);
		txttongzhiscore = (EditText) findViewById(R.id.pftxttongzhiscore);
		txttongzhiproblem = (EditText) findViewById(R.id.pftxttongzhiproblem);
		txtjishuscore = (EditText) findViewById(R.id.pftxtjishuscore);
		txtjishuproblem = (EditText) findViewById(R.id.pftxtjishuproblem);
		txtkongzhiscore = (EditText) findViewById(R.id.pftxtkongzhiscore);
		txtkongzhiproblem = (EditText) findViewById(R.id.pftxtkongzhiproblem);
		txtfushescore = (EditText) findViewById(R.id.pftxtfushescore);
		txtfusheproblem = (EditText) findViewById(R.id.pftxtfusheproblem);
		txtzhengshuscore = (EditText) findViewById(R.id.pftxtzhengshuscore);
		txtzhengshuproblem = (EditText) findViewById(R.id.pftxtzhengshuproblem);
		txtpeixunscore = (EditText) findViewById(R.id.pftxtpeixunscore);
		txtpeixunproblem = (EditText) findViewById(R.id.pftxtpeixunproblem);
		txtzhiduscore = (EditText) findViewById(R.id.pftxtzhiduscore);
		txtzhiduproblem = (EditText) findViewById(R.id.pftxtzhiduproblem);
		txtglshoucescore = (EditText) findViewById(R.id.pftxtglshoucescore);
		txtglshouceproblem = (EditText) findViewById(R.id.pftxtglshouceproblem);
		txtzhanshiscore = (EditText) findViewById(R.id.pftxtzhanshiscore);
		txtzhanshiproblem = (EditText) findViewById(R.id.pftxtzhanshiproblem);
		txtdaozescore = (EditText) findViewById(R.id.pftxtdaozescore);
		txtdaozeproblem = (EditText) findViewById(R.id.pftxtdaozeproblem);
		txtnongziscore = (EditText) findViewById(R.id.pftxtnongziscore);
		txtnongziproblem = (EditText) findViewById(R.id.pftxtnongziproblem);
		txttianjianscore = (EditText) findViewById(R.id.pftxttianjianscore);
		txttianjianproblem = (EditText) findViewById(R.id.pftxttianjianproblem);
		txtwangluoscore = (EditText) findViewById(R.id.pftxtwangluoscore);
		txtwangluoproblem = (EditText) findViewById(R.id.pftxtwangluoproblem);
		txtshangchuanscore = (EditText) findViewById(R.id.pftxtshangchuanscore);
		txtshangchuanproblem = (EditText) findViewById(R.id.pftxtshangchuanproblem);
		txtzhuisuscore = (EditText) findViewById(R.id.pftxtzhuisuscore);
		txtzhuisuproblem = (EditText) findViewById(R.id.pftxtzhuisuproblem);
		txtbiaozhiscore = (EditText) findViewById(R.id.pftxtbiaozhiscore);
		txtbiaozhiproblem = (EditText) findViewById(R.id.pftxtbiaozhiproblem);
		txtwendingscore = (EditText) findViewById(R.id.pftxtwendingscore);
		txtwendingproblem = (EditText) findViewById(R.id.pftxtwendingproblem);
		txttongyiscore = (EditText) findViewById(R.id.pftxttongyiscore);
		txttongyiproblem = (EditText) findViewById(R.id.pftxttongyiproblem);
		txtjiluscore = (EditText) findViewById(R.id.pftxtjiluscore);
		txtjiluproblem = (EditText) findViewById(R.id.pftxtjiluproblem);
		txtshangbiaoscore = (EditText) findViewById(R.id.pftxtshangbiaoscore);
		txtshangbiaoproblem = (EditText) findViewById(R.id.pftxtshangbiaoproblem);
		txtdymoshiscore = (EditText) findViewById(R.id.pftxtdymoshiscore);
		txtdymoshiproblem = (EditText) findViewById(R.id.pftxtdymoshiproblem);
		txtbaodaoscore = (EditText) findViewById(R.id.pftxtbaodaoscore);
		txtbaodaoproblem = (EditText) findViewById(R.id.pftxtbaodaoproblem);

		txtjiditotalscore =  (TextView) findViewById(R.id.pftxtjiditotalscore);
		txtjiditotalproblem = (EditText) findViewById(R.id.pftxtjiditotalproblem);
		txthuanjingtotalscore =  (TextView) findViewById(R.id.pftxthuanjingtotalscore);
		txthuanjingtotalproblem = (EditText) findViewById(R.id.pftxthuanjingtotalproblem);
		txtshengchantotalscore =  (TextView) findViewById(R.id.pftxtshengchantotalscore);
		txtshengchantotalproblem = (EditText) findViewById(R.id.pftxtshengchantotalproblem);
		txtguanlitotalscore =  (TextView) findViewById(R.id.pftxtguanlitotalscore);
		txtguanlitotalproblem = (EditText) findViewById(R.id.pftxtguanlitotalproblem);
		txtjianguantotalscore =  (TextView) findViewById(R.id.pftxtjianguantotalscore);
		txtjianguantotalproblem = (EditText) findViewById(R.id.pftxtjianguantotalproblem);
		txtjingyingtotalscore =  (TextView) findViewById(R.id.pftxtjingyingtotalscore);
		txtjingyingtotalproblem = (EditText) findViewById(R.id.pftxtjingyingtotalproblem);
		txtxiaoshoutotalscore = (TextView) findViewById(R.id.pftxtxiaoshoutotalscore);
		txtxiaoshoutotalproblem = (EditText) findViewById(R.id.pftxtxiaoshoutotalproblem);

		// txtsaddressdes = (TextView) findViewById(R.id.nctxtsaddressdes);

		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
		btnaddress = (Button) findViewById(R.id.pfaddress_bt);
		btnaddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getposition();
			}
		});
		btntotal = (Button) findViewById(R.id.pftotal_bt);
		btntotal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TotalOrValidation();
				txttotalNum.setText(totalNum.toString());
				Integer guimohua=total+total1+total2;
				Integer wuhaihua=total3+total4+total5+total6+total7;
				Integer biaozhunhua=total8+total9+total10+total11+total12+total13;
				Integer zhiduhua=total14+total15+total16+total17+total18;
				Integer xinxihua=total19+total20+total21+total22+total23;
				Integer chanyehua=total24+total25+total26;
				Integer pinpaihua=total27+total28+total29;
				txtjiditotalscore.setText(guimohua.toString()+"（分）");
				txthuanjingtotalscore.setText(wuhaihua.toString()+"（分）");
				txtshengchantotalscore.setText(biaozhunhua.toString()+"（分）");
				txtguanlitotalscore.setText(zhiduhua.toString()+"（分）");
				txtjianguantotalscore.setText(xinxihua.toString()+"（分）");
				txtjingyingtotalscore.setText(chanyehua.toString()+"（分）");
				txtxiaoshoutotalscore.setText(pinpaihua.toString()+"（分）");
			}
		});
		// btnposition = (Button) findViewById(R.id.btnposition);
		// btnposition.setOnClickListener(new View.OnClickListener() {
		// @SuppressLint("NewApi")
		// @Override
		// public void onClick(View v) {
		// getposition();
		//
		// }
		//
		// });
		InitLocation();

		btnzp = (Button) findViewById(R.id.btnzp);
		btnzp.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				savazp();

			}

		});

		btnsp = (Button) findViewById(R.id.btnsp);
		btnsp.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				savasp();

			}

		});

		btnsy = (Button) findViewById(R.id.btnsy);
		btnsy.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				savasy();

			}

		});

		buttonSetDate = (Button) findViewById(R.id.pfButtonSetDate);
		buttonSetDate.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				DatePickerFragment datePicker = new DatePickerFragment();
				datePicker.setEtdate(etddate);
				datePicker.setDateinitial(etddate.getText().toString());
				datePicker.show(getFragmentManager(), "etdate");

			}
		});

		btnsave = (Button) findViewById(R.id.save);
		btnsave.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				TotalOrValidation();
				
//				if (txtjiditotalscore.getText().toString() == null
//						|| txtjiditotalscore.getText().toString().equals("")) {
//					Toast.makeText(PFKHEditActivity.this, "基地规模化总分数不能为空！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				} else if (Integer.parseInt(txtjiditotalscore.getText()
//						.toString()) > 7
//						|| Integer.parseInt(txtjiditotalscore.getText()
//								.toString()) <= 0) {
//					Toast.makeText(PFKHEditActivity.this, "基地规模化总分数不正确！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				if (txthuanjingtotalscore.getText().toString() == null
//						|| txthuanjingtotalscore.getText().toString()
//								.equals("")) {
//					Toast.makeText(PFKHEditActivity.this, "环境无公害化总分数不能为空！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				} else if (Integer.parseInt(txthuanjingtotalscore.getText()
//						.toString()) > 12
//						|| Integer.parseInt(txthuanjingtotalscore.getText()
//								.toString()) <= 0) {
//					Toast.makeText(PFKHEditActivity.this, "环境无公害化总分数不正确！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				if (txtshengchantotalscore.getText().toString() == null
//						|| txtshengchantotalscore.getText().toString()
//								.equals("")) {
//					Toast.makeText(PFKHEditActivity.this, "生产标准化总分数不能为空！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				} else if (Integer.parseInt(txtshengchantotalscore.getText()
//						.toString()) > 15
//						|| Integer.parseInt(txtshengchantotalscore.getText()
//								.toString()) <= 0) {
//					Toast.makeText(PFKHEditActivity.this, "生产标准化总分数不正确！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				if (txtguanlitotalscore.getText().toString() == null
//						|| txtguanlitotalscore.getText().toString().equals("")) {
//					Toast.makeText(PFKHEditActivity.this, "管理制度化总分数不能为空！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				} else if (Integer.parseInt(txtguanlitotalscore.getText()
//						.toString()) > 25
//						|| Integer.parseInt(txtguanlitotalscore.getText()
//								.toString()) < 0) {
//					Toast.makeText(PFKHEditActivity.this, "管理制度化总分数不正确！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				if (txtjianguantotalscore.getText().toString() == null
//						|| txtjianguantotalscore.getText().toString()
//								.equals("")) {
//					Toast.makeText(PFKHEditActivity.this, "监管信息化总分数不能为空！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				} else if (Integer.parseInt(txtjianguantotalscore.getText()
//						.toString()) > 25
//						|| Integer.parseInt(txtjianguantotalscore.getText()
//								.toString()) < 0) {
//					Toast.makeText(PFKHEditActivity.this, "监管信息化总分数不正确！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				if (txtjingyingtotalscore.getText().toString() == null
//						|| txtjingyingtotalscore.getText().toString()
//								.equals("")) {
//					Toast.makeText(PFKHEditActivity.this, "经营产业化总分数不能为空！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				} else if (Integer.parseInt(txtjingyingtotalscore.getText()
//						.toString()) > 8
//						|| Integer.parseInt(txtjingyingtotalscore.getText()
//								.toString()) < 0) {
//					Toast.makeText(PFKHEditActivity.this, "经营产业化总分数不正确！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				if (txtxiaoshoutotalscore.getText().toString() == null
//						|| txtxiaoshoutotalscore.getText().toString()
//								.equals("")) {
//					Toast.makeText(PFKHEditActivity.this, "销售品牌化总分数不能为空！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				} else if (Integer.parseInt(txtxiaoshoutotalscore.getText()
//						.toString()) > 8
//						|| Integer.parseInt(txtxiaoshoutotalscore.getText()
//								.toString()) < 0) {
//					Toast.makeText(PFKHEditActivity.this, "销售品牌化总分数不正确！",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (txttotalNum.getText().toString() == null || txttotalNum.getText().toString().equals("")) {
					Toast.makeText(PFKHEditActivity.this, "请获取总分数！",
							Toast.LENGTH_SHORT).show();
				} else {
					Integer to = Integer.parseInt(txttotalNum.getText().toString());
					if (totalNum != to) {
						Toast.makeText(PFKHEditActivity.this, "总分数不正确请重新获取！",
								Toast.LENGTH_SHORT).show();
					} else {
						if (to > 100) {
							Toast.makeText(PFKHEditActivity.this,
									"总分数不能大于100，请重新输入！", Toast.LENGTH_SHORT)
									.show();
						} else {
							
							if (Nyid == null || Nyid.equals("")
									|| Nyid.equals("0")) {
								if(txtsaddress.getText().toString()==null||txtsaddress.getText().toString().equals("")){
								Toast.makeText(PFKHEditActivity.this,"地址不能为空！", Toast.LENGTH_SHORT).show();
							}else{
								saverecord();
							}
							} else {
								updaterecord();
							}
						}
					}
				}

			}

		});

		// btndeleteftrecord = (Button) findViewById(R.id.delftrecord);

		// if (Nyid == null || Nyid.equals("")) {
		// btndeleteftrecord.setVisibility(View.INVISIBLE);
		// } else {
		// btndeleteftrecord.setVisibility(View.VISIBLE);
		// }
		// btndeleteftrecord.setOnClickListener(new View.OnClickListener() {
		// @SuppressLint("NewApi")
		// @Override
		// public void onClick(View v) {
		// AlertDialog dialog = new AlertDialog.Builder(
		// PFKHEditActivity.this)
		// .setTitle("确认删除吗？")
		//
		// .setPositiveButton("确定",
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog,
		// int which) {
		// // 点击“确认”后的操作
		// deletedata();
		//
		// }
		//
		// })
		// .setNegativeButton("返回",
		// new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog,
		// int which) {
		// // 点击“返回”后的操作,这里不设置没有任何操作
		// }
		// }).create();
		// dialog.show();
		//
		// }
		//
		// });

		imageView = (ImageView) findViewById(R.id.imageView1);

		imageView.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// LayoutParams para;
				// para = imageView.getLayoutParams();
				//
				// Log.d(TAG, "layout height0: " + para.height);
				// Log.d(TAG, "layout width0: " + para.width);
				//
				// para.height = para.height*2;
				// para.width = para.width*2;
				// imageView.setLayoutParams(para);
				//

				Intent intent = new Intent(PFKHEditActivity.this,
						SpaceImageDetailActivity.class);
				// String
				//
//				filename = Environment.getExternalStorageDirectory().getPath()
//						+ "/DCIM/Camera/20151029_102917.jpg";

				File f = new File(cachePath + filename);
				if (filename != null && !filename.equals("") && f.exists()) {

					BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
					Bitmap bitmap = BitmapFactory.decodeFile(cachePath
							+ filename, bitmapOptions);
					SpaceImageDetailActivity.bitmap = bitmap;
					intent.putExtra("position", 1);
					int[] location = new int[2];
					imageView.getLocationOnScreen(location);
					intent.putExtra("locationX", location[0]);
					intent.putExtra("locationY", location[1]);

					intent.putExtra("width", imageView.getWidth());
					intent.putExtra("height", imageView.getHeight());
					startActivity(intent);
					overridePendingTransition(0, 0);
				} else {

					Toast.makeText(PFKHEditActivity.this, "尚未采集照片！",
							Toast.LENGTH_LONG).show();

				}
			}

		});
		imageviewsound = (ImageView) findViewById(R.id.imageviewsound);

		imageviewsound.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {

//				filename = Environment.getExternalStorageDirectory().getPath()
//						+ "/DCIM/Camera/20151029_102917.jpg";

				File f = new File(cachePathaudio + audiofilename);
				if (audiofilename != null && !audiofilename.equals("")
						&& f.exists()) {

					Intent intent = new Intent(Intent.ACTION_VIEW);
					//
					// intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory()
					// + "/recording323345382.3gpp"), "audio/3gpp");

					intent.setAction(Intent.ACTION_VIEW);
					File file1 = new File(cachePathaudio + audiofilename);
					intent.setDataAndType(Uri.fromFile(file1), "audio/*");
					startActivity(intent);

				} else {

					Toast.makeText(PFKHEditActivity.this, "尚未采集声音！",
							Toast.LENGTH_LONG).show();

				}
			}

		});

		imageviewvideo = (ImageView) findViewById(R.id.imageviewvideo);

		imageviewvideo.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
//				filename = Environment.getExternalStorageDirectory().getPath()
//						+ "/DCIM/Camera/20151029_102917.jpg";

				File f = new File(cachePathvideo + videofilename);
				if (videofilename != null && !videofilename.equals("")
						&& f.exists()) {

					Intent intent = new Intent(Intent.ACTION_VIEW);
					//
					// intent.setDataAndType(
					// Uri.parse(Environment.getExternalStorageDirectory()
					// + "/recording323345382.3gpp"), "audio/3gpp");

					intent.setAction(Intent.ACTION_VIEW);
					File file1 = new File(cachePathvideo + videofilename);
					intent.setDataAndType(Uri.fromFile(file1), "video/*");
					startActivity(intent);

				} else {

					Toast.makeText(PFKHEditActivity.this, "尚未采集视频！",
							Toast.LENGTH_LONG).show();

				}
			}

		});

		bindziduan();

	}

	// 获取实时位置
	private void getposition() {
		// TODO Auto-generated method stub

		// InitLocation();
		// mLocationClient.start();
		// txtsaddressdes.setText("正在获取请稍后...");

		Thread t = new Thread(rpostion);
		t.start();

	}

	Runnable rpostion = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Positionet.saddress = "";
			Positionet.sgpsx = "";
			Positionet.sgpsy = "";
			int count = 0;
			boolean hasadd = false;
			while (true) {
				if (count > 5) {
					break;
				}
				count++;
				InitLocation();
				mLocationClient.start();
				if (Positionet.saddress != null
						&& !Positionet.saddress.equals("")) {
					handler.sendEmptyMessage(1);
					hasadd = true;
					break;
				} else {

				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (!hasadd) {

				handler.sendEmptyMessage(0);
			}

		}
	};
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) // handler接收到相关的消息后
			{
				// Toast.makeText(XCZFEditActivity.this,
				// "地址："+Positionet.saddress+"\r\n经度："+Positionet.sgpsx+"\r\n纬度："+Positionet.sgpsy,
				// Toast.LENGTH_LONG).show();

				// txtsaddressdes.setText("地址：" + Positionet.saddress +
				// "\r\n经度："
				// + Positionet.sgpsx + "\r\n纬度：" + Positionet.sgpsy);

				sgpsx = Positionet.sgpsx;
				sgpsy = Positionet.sgpsy;
				saddress = Positionet.saddress;
				txtsaddress.setText(saddress);

				LatLng point = new LatLng(23.150725, 113.257469);
				try {

					point = new LatLng(Double.parseDouble(sgpsy),
							Double.parseDouble(sgpsx));
					;
				} catch (Exception e) {

				}
				// // 构建Marker图标
				// BitmapDescriptor bitmap = BitmapDescriptorFactory
				// .fromResource(R.drawable.maker);
				// // 构建MarkerOption，用于在地图上添加Marker
				// // mBaiduMap.clear();
				// OverlayOptions option = new MarkerOptions().position(point)
				// .icon(bitmap);
				//
				// // 在地图上添加Marker，并显示
				// mBaiduMap.addOverlay(option);
				// MapStatus mMapStatus = new MapStatus.Builder().target(point)
				// .zoom(13).build();
				// MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				// .newMapStatus(mMapStatus);
				// // 改变地图状态
				// mBaiduMap.setMapStatus(mMapStatusUpdate);

			} else {
				// txtsaddressdes.setText("未获取到地址，请保持网络畅通！");
				// txtsaddress.setText(saddress);
			}

		}
	};

	private void InitLocation() {
		// TODO Auto-generated method stub
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(lmode);//
		option.setCoorType(tempcoor);//
		// option.setOpenGps(true);
		option.setIsNeedAddress(true);// ַ
		option.setScanSpan(1000);//
		mLocationClient.setLocOption(option);//
		// mLocationClient.start();//启动定位服务
		mLocationClient.requestLocation();// 请求定位

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("slide", "JCGLFragment--onStop");
		// mLocationClient.stop();// ֹͣ����
		((LocationApplication) getApplication()).mLocationresult = null;// �ȳ�ʼ��Ϊnull
	}

	// 采集照片
	private void savazp() {
		// TODO Auto-generated method stub

		final CharSequence[] items = { "相册", "拍照" };
		AlertDialog dlg = new AlertDialog.Builder(PFKHEditActivity.this)
				.setTitle("选择图片")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 这里item是根据选择的方式，
						// 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
						if (item == 1) {
							try {

								// Intent cameraIntent = new Intent(
								// android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								Intent cameraIntent = new Intent(
										android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								File appDir = new File(Environment
										.getExternalStorageDirectory()
										+ "/zfxc_wbx/images/temps");

								if (!appDir.exists()) {
									appDir.mkdir();
								}
								Photofilepath = Environment
										.getExternalStorageDirectory()
										+ "/zfxc_wbx/images/temps/"
										+ String.valueOf(System
												.currentTimeMillis()) + ".jpg";
								mUri = Uri.fromFile(new File(Photofilepath));
								cameraIntent
										.putExtra(
												android.provider.MediaStore.EXTRA_OUTPUT,
												mUri);

								try {
									cameraIntent.putExtra("return-data", true);
									startActivityForResult(cameraIntent, 1);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} catch (Exception e) {
								Log.i(TAG, "exception is " + e.toString());
							}

						} else {
							try {

								Intent album = new Intent(
										Intent.ACTION_GET_CONTENT);

								album.setType("image/*");
								album = new Intent(
										Intent.ACTION_PICK,
										android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

								//
								// //album.addCategory(Intent.CATEGORY_OPENABLE);
								// album.setType("image/*");
								// album.putExtra("crop", "false");
								//
								// album.putExtra("aspectX", 1000);
								// album.putExtra("aspectY", 1000);
								// album.putExtra("outputX", 1000);
								// album.putExtra("outputY", 1000);
								// album.putExtra("return-data", true);
								startActivityForResult(album, 0);
							} catch (Exception e) {
								Log.i(TAG, "2Exception is " + e.toString());
							}
						}
					}
				}).create();
		dlg.show();

	}

	// 采集照片
	private void savasp() {
		// TODO Auto-generated method stub

		final CharSequence[] items = { "视频", "摄像" };
		AlertDialog dlg = new AlertDialog.Builder(PFKHEditActivity.this)
				.setTitle("选择视频")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 这里item是根据选择的方式，
						// 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
						if (item == 1) {
							try {

								// Intent cameraIntent = new Intent(
								// android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								Intent cameraIntent = new Intent(
										android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
								File appDir = new File(Environment
										.getExternalStorageDirectory()
										+ "/zfxc_wbx/video/");

								if (!appDir.exists()) {
									appDir.mkdir();
								}
								videofilepath = Environment
										.getExternalStorageDirectory()
										+ "/zfxc_wbx/video/"
										+ String.valueOf(System
												.currentTimeMillis()) + ".avi";
								mUri = Uri.fromFile(new File(videofilepath));
								cameraIntent
										.putExtra(
												android.provider.MediaStore.EXTRA_OUTPUT,
												mUri);

								try {
									cameraIntent.putExtra("return-data", true);
									startActivityForResult(cameraIntent,
											VIDEO_CAMERA_CODE);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} catch (Exception e) {
								Log.i(TAG, "exception is " + e.toString());
							}

						} else {
							try {
//								 Intent album = new  Intent(MediaStore.ACTION_VIDEO_CAPTURE);   
//								    startActivityForResult(intent, CASE_VIDEO); 
								Intent album = new Intent(
										Intent.ACTION_GET_CONTENT);
//
								album.setType("video/*");
								album = new Intent(
										Intent.ACTION_PICK,
										android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

								//
								// //album.addCategory(Intent.CATEGORY_OPENABLE);
								// album.setType("image/*");
								// album.putExtra("crop", "false");
								//
								// album.putExtra("aspectX", 1000);
								// album.putExtra("aspectY", 1000);
								// album.putExtra("outputX", 1000);
								// album.putExtra("outputY", 1000);
								// album.putExtra("return-data", true);
								startActivityForResult(album, VIDEO_CODE);
							} catch (Exception e) {
								Log.i(TAG, "2Exception is " + e.toString());
							}
						}
					}
				}).create();
		dlg.show();

	}

	// 采集声音
	private void savasy() {
		// TODO Auto-generated method stub

		final CharSequence[] items = { "已有音频", "录音" };
		AlertDialog dlg = new AlertDialog.Builder(PFKHEditActivity.this)
				.setTitle("选择音频")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 这里item是根据选择的方式，
						// 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
						if (item == 1) {
							Intent intent = new Intent(
									MediaStore.Audio.Media.RECORD_SOUND_ACTION);
							startActivityForResult(intent, AUDIO_CODE);

						} else {
							try {

								Intent album = new Intent(
										Intent.ACTION_GET_CONTENT);

								album.setType("audio/*");
								album = new Intent(
										Intent.ACTION_PICK,
										android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);

								//
								// //album.addCategory(Intent.CATEGORY_OPENABLE);
								// album.setType("image/*");
								// album.putExtra("crop", "false");
								//
								// album.putExtra("aspectX", 1000);
								// album.putExtra("aspectY", 1000);
								// album.putExtra("outputX", 1000);
								// album.putExtra("outputY", 1000);
								// album.putExtra("return-data", true);
								startActivityForResult(album, 2);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}).create();
		dlg.show();

	}

	private void deletedata() {
		// TODO Auto-generated method stub
		String[] wherevalue = null;
		try {
			myDbHelper.open();
			// if (Nyid == null || Nyid.equals("")) {
			// if (jid == null || jid.equals("")) {
			// wherevalue = new String[] { "0" };
			// } else {
			// wherevalue = new String[] { jid };
			// }
			//
			// } else if (jid == null || jid.equals("")) {
			// if (Nyid == null || Nyid.equals("")) {
			// wherevalue = new String[] { "0" };
			// } else {
			// wherevalue = new String[] { Nyid };
			// }
			//
			// } else {
			// wherevalue = new String[] { jid };
			// }
			wherevalue = new String[] { Nyid };
			File f = new File(cachePath + filename);
			if (!filename.equals("") && f.exists()) {
				f.delete();
			}
			myDbHelper.delete(MyDbInfo.getTableNames()[12], "ID=?", wherevalue);

			myDbHelper.close();
			Toast.makeText(PFKHEditActivity.this, "删除成功", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(PFKHEditActivity.this, "删除失败", Toast.LENGTH_LONG)
					.show();
		} finally {
			myDbHelper.close();

		}

		finish();

	}

	private void bindziduan() {
		// TODO Auto-generated method stub
		Cursor c = null;
		myDbHelper.open();
		String d = null;
		try {

			if (Nyid == null || Nyid.equals("")) {
				Nyid = "0";

			}
			String sql = "select * from " + MyDbInfo.getTableNames()[13]
					+ " where Id=" + Nyid;
			c = myDbHelper.select(sql);
			if (c.getCount() == 0) {
				return;
			} else {
				c.moveToFirst();
				String result = "";

				etddate.setText(c.getString(2));
				qxList.add(c.getString(1));
				// spJidi.setText(c.getString(1));
				txtexperts.setText(c.getString(3));
				txttotalNum.setText(c.getString(4));
				txtsaddress.setText(c.getString(5));
				txtzhutiscore.setText(c.getString(6));
				txtzhutiproblem.setText(c.getString(7));
				txtleixingscore.setText(c.getString(8));
				txtleixingproblem.setText(c.getString(9));
				txtguimoscore.setText(c.getString(10));
				txtguimoproblem.setText(c.getString(11));
				txtmeiguanscore.setText(c.getString(12));
				txtmeiguanproblem.setText(c.getString(13));
				txtfangbianscore.setText(c.getString(14));
				txtfangbianproblem.setText(c.getString(15));
				txtnongcanscore.setText(c.getString(16));
				txtnongcanproblem.setText(c.getString(17));
				txtfeiqiwuscore.setText(c.getString(18));
				txtfeiqiwuproblem.setText(c.getString(19));
				txtchuliscore.setText(c.getString(20));
				txtchuliproblem.setText(c.getString(21));
				txttongzhiscore.setText(c.getString(22));
				txttongzhiproblem.setText(c.getString(23));
				txtjishuscore.setText(c.getString(24));
				txtjishuproblem.setText(c.getString(25));
				txtkongzhiscore.setText(c.getString(26));
				txtkongzhiproblem.setText(c.getString(27));
				txtfushescore.setText(c.getString(28));
				txtfusheproblem.setText(c.getString(29));
				txtzhengshuscore.setText(c.getString(30));
				txtzhengshuproblem.setText(c.getString(31));
				txtpeixunscore.setText(c.getString(32));
				txtpeixunproblem.setText(c.getString(33));
				txtzhiduscore.setText(c.getString(34));
				txtzhiduproblem.setText(c.getString(35));
				txtglshoucescore.setText(c.getString(36));
				txtglshouceproblem.setText(c.getString(37));
				txtzhanshiscore.setText(c.getString(38));
				txtzhanshiproblem.setText(c.getString(39));
				txtdaozescore.setText(c.getString(40));
				txtdaozeproblem.setText(c.getString(41));
				txtnongziscore.setText(c.getString(42));
				txtnongziproblem.setText(c.getString(43));
				txttianjianscore.setText(c.getString(44));
				txttianjianproblem.setText(c.getString(45));
				txtwangluoscore.setText(c.getString(46));
				txtwangluoproblem.setText(c.getString(47));
				txtshangchuanscore.setText(c.getString(48));
				txtshangchuanproblem.setText(c.getString(49));
				txtzhuisuscore.setText(c.getString(50));
				txtzhuisuproblem.setText(c.getString(51));
				txtbiaozhiscore.setText(c.getString(52));
				txtbiaozhiproblem.setText(c.getString(53));
				txtwendingscore.setText(c.getString(54));
				txtwendingproblem.setText(c.getString(55));
				txttongyiscore.setText(c.getString(56));
				txttongyiproblem.setText(c.getString(57));
				txtjiluscore.setText(c.getString(58));
				txtjiluproblem.setText(c.getString(59));
				txtshangbiaoscore.setText(c.getString(60));
				txtshangbiaoproblem.setText(c.getString(61));
				txtdymoshiscore.setText(c.getString(62));
				txtdymoshiproblem.setText(c.getString(63));
				txtbaodaoscore.setText(c.getString(64));
				txtbaodaoproblem.setText(c.getString(65));

				this.filename = c.getString(68);
				audiofilename = c.getString(69);
				sgpsy = c.getString(c.getColumnIndex("pfsgpsy"));
				sgpsx = c.getString(c.getColumnIndex("pfgpsx"));
				videofilename = c.getString(c.getColumnIndex("pfsvideo"));
				firstvideo = videofilename;
				txtjiditotalscore.setText(c.getString(74));
				txtjiditotalproblem.setText(c.getString(75));
				txthuanjingtotalscore.setText(c.getString(76));
				txthuanjingtotalproblem.setText(c.getString(77));
				txtshengchantotalscore.setText(c.getString(78));
				txtshengchantotalproblem.setText(c.getString(79));
				txtguanlitotalscore.setText(c.getString(80));
				txtguanlitotalproblem.setText(c.getString(81));
				txtjianguantotalscore.setText(c.getString(82));
				txtjianguantotalproblem.setText(c.getString(83));
				txtjingyingtotalscore.setText(c.getString(84));
				txtjingyingtotalproblem.setText(c.getString(85));
				txtxiaoshoutotalscore.setText(c.getString(86));
				txtxiaoshoutotalproblem.setText(c.getString(87));
			}
			c.close();
			myDbHelper.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
			myDbHelper.close();
		}
		if (etddate.getText().equals("")) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String ddatestring = sf.format(new Date());
			etddate.setText(ddatestring);

		}

	}

	private void updaterecord() {
		// TODO Auto-generated method stub

		try {

			myDbHelper.open();
			String table = "tbnyxczf";
			String fields[] = { "pfcompany", "pfdate", "pfexperts",
					"pftotalNum", "pftxtadress", "pfzhutiscore",
					"pfzhutiproblem", "pfleixingscore", "pfleixingproblem",
					"pfguimoscore", "pfguimoproblem", "pfmeiguanscore",
					"pfmeiguanproblem", "pffangbianscore", "pffangbianproblem",
					"pfnongcanscore", "pfnongcanproblem", "pffeiqiwuscore",
					"pffeiqiwuproblem", "pfchuliscore", "pfchuliproblem",
					"pftongzhiscore", "pftongzhiproblem", "pfjishuscore",
					"pfjishuproblem", "pfkongzhiscore", "pfkongzhiproblem",
					"pffushescore", "pffusheproblem", "pfzhengshuscore",
					"pfzhengshuproblem", "pfpeixunscore", "pfpeixunproblem",
					"pfzhiduscore", "pfzhiduproblem", "pfglshoucescore",
					"pfglshouceproblem", "pfzhanshiscore", "pfzhanshiproblem",
					"pfdaozescore", "pfdaozeproblem", "pfnongziscore",
					"pfnongziproblem", "pftianjianscore", "pftianjianproblem",
					"pfwangluoscore", "pfwangluoproblem", "pfshangchuanscore",
					"pfshangchuanproblem", "pfzhuisuscore", "pfzhuisuproblem",
					"pfbiaozhiscore", "pfbiaozhiproblem", "pfwendingscore",
					"pfwendingproblem", "pftongyiscore", "pftongyiproblem",
					"pfjiluscore", "pfjiluproblem", "pfshangbiaoscore",
					"pfshangbiaoproblem", "pfdymoshiscore", "pfdymoshiproblem",
					"pfbaodaoscore", "pfbaodaoproblem", "pfgpsx", "pfsgpsy",
					"pfsimages", "pfsaudio", "pfsvideo", "pftxtjiditotalscore",
					"pftxtjiditotalproblem", "pftxthuanjingtotalscore",
					"pftxthuanjingtotalproblem", "pftxtshengchantotalscore",
					"pftxtshengchantotalproblem", "pftxtguanlitotalscore",
					"pftxtguanlitotalproblem", "pftxtjianguantotalscore",
					"pftxtjianguantotalproblem", "pftxtjingyingtotalscore",
					"pftxtjingyingtotalproblem", "pftxtxiaoshoutotalscore",
					"pftxtxiaoshoutotalproblem" };
			String values[] = { "pfcompany", "pfdate", "pfexperts",
					"pftotalNum", "pftxtadress", "pfzhutiscore",
					"pfzhutiproblem", "pfleixingscore", "pfleixingproblem",
					"pfguimoscore", "pfguimoproblem", "pfmeiguanscore",
					"pfmeiguanproblem", "pffangbianscore", "pffangbianproblem",
					"pfnongcanscore", "pfnongcanproblem", "pffeiqiwuscore",
					"pffeiqiwuproblem", "pfchuliscore", "pfchuliproblem",
					"pftongzhiscore", "pftongzhiproblem", "pfjishuscore",
					"pfjishuproblem", "pfkongzhiscore", "pfkongzhiproblem",
					"pffushescore", "pffusheproblem", "pfzhengshuscore",
					"pfzhengshuproblem", "pfpeixunscore", "pfpeixunproblem",
					"pfzhiduscore", "pfzhiduproblem", "pfglshoucescore",
					"pfglshouceproblem", "pfzhanshiscore", "pfzhanshiproblem",
					"pfdaozescore", "pfdaozeproblem", "pfnongziscore",
					"pfnongziproblem", "pftianjianscore", "pftianjianproblem",
					"pfwangluoscore", "pfwangluoproblem", "pfshangchuanscore",
					"pfshangchuanproblem", "pfzhuisuscore", "pfzhuisuproblem",
					"pfbiaozhiscore", "pfbiaozhiproblem", "pfwendingscore",
					"pfwendingproblem", "pftongyiscore", "pftongyiproblem",
					"pfjiluscore", "pfjiluproblem", "pfshangbiaoscore",
					"pfshangbiaoproblem", "pfdymoshiscore", "pfdymoshiproblem",
					"pfbaodaoscore", "pfbaodaoproblem", "pfgpsx", "pfsgpsy",
					"pfsimages", "pfsaudio", "pfsvideo", "pftxtjiditotalscore",
					"pftxtjiditotalproblem", "pftxthuanjingtotalscore",
					"pftxthuanjingtotalproblem", "pftxtshengchantotalscore",
					"pftxtshengchantotalproblem", "pftxtguanlitotalscore",
					"pftxtguanlitotalproblem", "pftxtjianguantotalscore",
					"pftxtjianguantotalproblem", "pftxtjingyingtotalscore",
					"pftxtjingyingtotalproblem", "pftxtxiaoshoutotalscore",
					"pftxtxiaoshoutotalproblem" };
			for (int i = 0; i < values.length; i++) {
				values[i] = "";
			}
			values[0] = codeString;
			values[1] = etddate.getText().toString();
			values[2] = txtexperts.getText().toString();
			values[3] = txttotalNum.getText().toString();
			values[4] = txtsaddress.getText().toString();
			values[5] = txtzhutiscore.getText().toString();
			values[6] = txtzhutiproblem.getText().toString();
			values[7] = txtleixingscore.getText().toString();
			values[8] = txtleixingproblem.getText().toString();
			values[9] = txtguimoscore.getText().toString();
			values[10] = txtguimoproblem.getText().toString();
			values[11] = txtmeiguanscore.getText().toString();
			values[12] = txtmeiguanproblem.getText().toString();
			values[13] = txtfangbianscore.getText().toString();
			values[14] = txtfangbianproblem.getText().toString();
			values[15] = txtnongcanscore.getText().toString();
			values[16] = txtnongcanproblem.getText().toString();
			values[17] = txtfeiqiwuscore.getText().toString();
			values[18] = txtfeiqiwuproblem.getText().toString();
			values[19] = txtchuliscore.getText().toString();
			values[20] = txtchuliproblem.getText().toString();
			values[21] = txttongzhiscore.getText().toString();
			values[22] = txttongzhiproblem.getText().toString();
			values[23] = txtjishuscore.getText().toString();
			values[24] = txtjishuproblem.getText().toString();
			values[25] = txtkongzhiscore.getText().toString();
			values[26] = txtkongzhiproblem.getText().toString();
			values[27] = txtfushescore.getText().toString();
			values[28] = txtfusheproblem.getText().toString();
			values[29] = txtzhengshuscore.getText().toString();
			values[30] = txtzhengshuproblem.getText().toString();
			values[31] = txtpeixunscore.getText().toString();
			values[32] = txtpeixunproblem.getText().toString();
			values[33] = txtzhiduscore.getText().toString();
			values[34] = txtzhiduproblem.getText().toString();
			values[35] = txtglshoucescore.getText().toString();
			values[36] = txtglshouceproblem.getText().toString();
			values[37] = txtzhanshiscore.getText().toString();
			values[38] = txtzhanshiproblem.getText().toString();
			values[39] = txtdaozescore.getText().toString();
			values[40] = txtdaozeproblem.getText().toString();
			values[41] = txtnongziscore.getText().toString();
			values[42] = txtnongziproblem.getText().toString();
			values[43] = txttianjianscore.getText().toString();
			values[44] = txttianjianproblem.getText().toString();
			values[45] = txtwangluoscore.getText().toString();
			values[46] = txtwangluoproblem.getText().toString();
			values[47] = txtshangchuanscore.getText().toString();
			values[48] = txtshangchuanproblem.getText().toString();
			values[49] = txtzhuisuscore.getText().toString();
			values[50] = txtzhuisuproblem.getText().toString();
			values[51] = txtbiaozhiscore.getText().toString();
			values[52] = txtbiaozhiproblem.getText().toString();
			values[53] = txtwendingscore.getText().toString();
			values[54] = txtwendingproblem.getText().toString();
			values[55] = txttongyiscore.getText().toString();
			values[56] = txttongyiproblem.getText().toString();
			values[57] = txtjiluscore.getText().toString();
			values[58] = txtjiluproblem.getText().toString();
			values[59] = txtshangbiaoscore.getText().toString();
			values[60] = txtshangbiaoproblem.getText().toString();
			values[61] = txtdymoshiscore.getText().toString();
			values[62] = txtdymoshiproblem.getText().toString();
			values[63] = txtbaodaoscore.getText().toString();
			values[64] = txtbaodaoproblem.getText().toString();

			if (caiji) {// 如果采集过就删除然后存储一次，否则不管

				File f = new File(cachePath + filename);
				if (!filename.equals("") && f.exists()) {
					f.delete();
				}
				filename = saveimage();

			}
			if (!firstsound.equals(audiofilename)) {
				File f = new File(cachePathaudio + firstsound);
				if (f.exists()) {
					f.delete();
				}

			}

			if (!firstvideo.equals(videofilename)) {
				File f = new File(cachePathvideo + firstvideo);
				if (f.exists()) {
					f.delete();
				}

			}
			values[65] = sgpsx;
			Log.e("aaaaaa", "==========================" + sgpsx);
			values[66] = sgpsy;
			values[67] = filename;
			values[68] = audiofilename;
			values[69] = videofilename;
			values[70] = txtjiditotalscore.getText().toString();
			values[71] = txtjiditotalproblem.getText().toString();
			values[72] = txthuanjingtotalscore.getText().toString();
			values[73] = txthuanjingtotalproblem.getText().toString();
			values[74] = txtshengchantotalscore.getText().toString();
			values[75] = txtshengchantotalproblem.getText().toString();
			values[76] = txtguanlitotalscore.getText().toString();
			values[77] = txtguanlitotalproblem.getText().toString();
			values[78] = txtjianguantotalscore.getText().toString();
			values[79] = txtjianguantotalproblem.getText().toString();
			values[80] = txtjingyingtotalscore.getText().toString();
			values[81] = txtjingyingtotalproblem.getText().toString();
			values[82] = txtxiaoshoutotalscore.getText().toString();
			values[83] = txtxiaoshoutotalproblem.getText().toString();
			String[] wherevalue = null;
			wherevalue = new String[] { Nyid };
			myDbHelper.update(MyDbInfo.getTableNames()[13], fields, values,
					"id=?", wherevalue);
			myDbHelper.close();
			Toast.makeText(PFKHEditActivity.this, "更新成功", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			myDbHelper.close();
		}
		finish();
	}

	// 保存执法记录
	private void saverecord() {
		// TODO Auto-generated method stub

		try {

			myDbHelper.open();
			String table = "tbnyxczf";
			String fields[] = { "pfcompany", "pfdate", "pfexperts",
					"pftotalNum", "pftxtadress", "pfzhutiscore",
					"pfzhutiproblem", "pfleixingscore", "pfleixingproblem",
					"pfguimoscore", "pfguimoproblem", "pfmeiguanscore",
					"pfmeiguanproblem", "pffangbianscore", "pffangbianproblem",
					"pfnongcanscore", "pfnongcanproblem", "pffeiqiwuscore",
					"pffeiqiwuproblem", "pfchuliscore", "pfchuliproblem",
					"pftongzhiscore", "pftongzhiproblem", "pfjishuscore",
					"pfjishuproblem", "pfkongzhiscore", "pfkongzhiproblem",
					"pffushescore", "pffusheproblem", "pfzhengshuscore",
					"pfzhengshuproblem", "pfpeixunscore", "pfpeixunproblem",
					"pfzhiduscore", "pfzhiduproblem", "pfglshoucescore",
					"pfglshouceproblem", "pfzhanshiscore", "pfzhanshiproblem",
					"pfdaozescore", "pfdaozeproblem", "pfnongziscore",
					"pfnongziproblem", "pftianjianscore", "pftianjianproblem",
					"pfwangluoscore", "pfwangluoproblem", "pfshangchuanscore",
					"pfshangchuanproblem", "pfzhuisuscore", "pfzhuisuproblem",
					"pfbiaozhiscore", "pfbiaozhiproblem", "pfwendingscore",
					"pfwendingproblem", "pftongyiscore", "pftongyiproblem",
					"pfjiluscore", "pfjiluproblem", "pfshangbiaoscore",
					"pfshangbiaoproblem", "pfdymoshiscore", "pfdymoshiproblem",
					"pfbaodaoscore", "pfbaodaoproblem", "pfgpsx", "pfsgpsy",
					"pfsimages", "pfsaudio", "pfsvideo", "missionId",
					"missname", "missperson", "pftxtjiditotalscore",
					"pftxtjiditotalproblem", "pftxthuanjingtotalscore",
					"pftxthuanjingtotalproblem", "pftxtshengchantotalscore",
					"pftxtshengchantotalproblem", "pftxtguanlitotalscore",
					"pftxtguanlitotalproblem", "pftxtjianguantotalscore",
					"pftxtjianguantotalproblem", "pftxtjingyingtotalscore",
					"pftxtjingyingtotalproblem", "pftxtxiaoshoutotalscore",
					"pftxtxiaoshoutotalproblem" };
			String values[] = { "pfcompany", "pfdate", "pfexperts",
					"pftotalNum", "pftxtadress", "pfzhutiscore",
					"pfzhutiproblem", "pfleixingscore", "pfleixingproblem",
					"pfguimoscore", "pfguimoproblem", "pfmeiguanscore",
					"pfmeiguanproblem", "pffangbianscore", "pffangbianproblem",
					"pfnongcanscore", "pfnongcanproblem", "pffeiqiwuscore",
					"pffeiqiwuproblem", "pfchuliscore", "pfchuliproblem",
					"pftongzhiscore", "pftongzhiproblem", "pfjishuscore",
					"pfjishuproblem", "pfkongzhiscore", "pfkongzhiproblem",
					"pffushescore", "pffusheproblem", "pfzhengshuscore",
					"pfzhengshuproblem", "pfpeixunscore", "pfpeixunproblem",
					"pfzhiduscore", "pfzhiduproblem", "pfglshoucescore",
					"pfglshouceproblem", "pfzhanshiscore", "pfzhanshiproblem",
					"pfdaozescore", "pfdaozeproblem", "pfnongziscore",
					"pfnongziproblem", "pftianjianscore", "pftianjianproblem",
					"pfwangluoscore", "pfwangluoproblem", "pfshangchuanscore",
					"pfshangchuanproblem", "pfzhuisuscore", "pfzhuisuproblem",
					"pfbiaozhiscore", "pfbiaozhiproblem", "pfwendingscore",
					"pfwendingproblem", "pftongyiscore", "pftongyiproblem",
					"pfjiluscore", "pfjiluproblem", "pfshangbiaoscore",
					"pfshangbiaoproblem", "pfdymoshiscore", "pfdymoshiproblem",
					"pfbaodaoscore", "pfbaodaoproblem", "pfgpsx", "pfsgpsy",
					"pfsimages", "pfsaudio", "pfsvideo", "missionId",
					"missname", "missperson", "pftxtjiditotalscore",
					"pftxtjiditotalproblem", "pftxthuanjingtotalscore",
					"pftxthuanjingtotalproblem", "pftxtshengchantotalscore",
					"pftxtshengchantotalproblem", "pftxtguanlitotalscore",
					"pftxtguanlitotalproblem", "pftxtjianguantotalscore",
					"pftxtjianguantotalproblem", "pftxtjingyingtotalscore",
					"pftxtjingyingtotalproblem", "pftxtxiaoshoutotalscore",
					"pftxtxiaoshoutotalproblem" };

			for (int i = 0; i < values.length; i++) {
				values[i] = "";
			}
			values[0] = codeString;
			values[1] = etddate.getText().toString();
			values[2] = txtexperts.getText().toString();
			values[3] = txttotalNum.getText().toString();
			values[4] = txtsaddress.getText().toString();
			values[5] = txtzhutiscore.getText().toString();
			values[6] = txtzhutiproblem.getText().toString();
			values[7] = txtleixingscore.getText().toString();
			values[8] = txtleixingproblem.getText().toString();
			values[9] = txtguimoscore.getText().toString();
			values[10] = txtguimoproblem.getText().toString();
			values[11] = txtmeiguanscore.getText().toString();
			values[12] = txtmeiguanproblem.getText().toString();
			values[13] = txtfangbianscore.getText().toString();
			values[14] = txtfangbianproblem.getText().toString();
			values[15] = txtnongcanscore.getText().toString();
			values[16] = txtnongcanproblem.getText().toString();
			values[17] = txtfeiqiwuscore.getText().toString();
			values[18] = txtfeiqiwuproblem.getText().toString();
			values[19] = txtchuliscore.getText().toString();
			values[20] = txtchuliproblem.getText().toString();
			values[21] = txttongzhiscore.getText().toString();
			values[22] = txttongzhiproblem.getText().toString();
			values[23] = txtjishuscore.getText().toString();
			values[24] = txtjishuproblem.getText().toString();
			values[25] = txtkongzhiscore.getText().toString();
			values[26] = txtkongzhiproblem.getText().toString();
			values[27] = txtfushescore.getText().toString();
			values[28] = txtfusheproblem.getText().toString();
			values[29] = txtzhengshuscore.getText().toString();
			values[30] = txtzhengshuproblem.getText().toString();
			values[31] = txtpeixunscore.getText().toString();
			values[32] = txtpeixunproblem.getText().toString();
			values[33] = txtzhiduscore.getText().toString();
			values[34] = txtzhiduproblem.getText().toString();
			values[35] = txtglshoucescore.getText().toString();
			values[36] = txtglshouceproblem.getText().toString();
			values[37] = txtzhanshiscore.getText().toString();
			values[38] = txtzhanshiproblem.getText().toString();
			values[39] = txtdaozescore.getText().toString();
			values[40] = txtdaozeproblem.getText().toString();
			values[41] = txtnongziscore.getText().toString();
			values[42] = txtnongziproblem.getText().toString();
			values[43] = txttianjianscore.getText().toString();
			values[44] = txttianjianproblem.getText().toString();
			values[45] = txtwangluoscore.getText().toString();
			values[46] = txtwangluoproblem.getText().toString();
			values[47] = txtshangchuanscore.getText().toString();
			values[48] = txtshangchuanproblem.getText().toString();
			values[49] = txtzhuisuscore.getText().toString();
			values[50] = txtzhuisuproblem.getText().toString();
			values[51] = txtbiaozhiscore.getText().toString();
			values[52] = txtbiaozhiproblem.getText().toString();
			values[53] = txtwendingscore.getText().toString();
			values[54] = txtwendingproblem.getText().toString();
			values[55] = txttongyiscore.getText().toString();
			values[56] = txttongyiproblem.getText().toString();
			values[57] = txtjiluscore.getText().toString();
			values[58] = txtjiluproblem.getText().toString();
			values[59] = txtshangbiaoscore.getText().toString();
			values[60] = txtshangbiaoproblem.getText().toString();
			values[61] = txtdymoshiscore.getText().toString();
			values[62] = txtdymoshiproblem.getText().toString();
			values[63] = txtbaodaoscore.getText().toString();
			values[64] = txtbaodaoproblem.getText().toString();

			String filename = "";
			try {
				filename = saveimage();
			} catch (Exception e1) {

			}
			values[65] = sgpsx;
			Log.e("aaaaaa", "==========================" + sgpsx);
			values[66] = sgpsy;
			values[67] = filename;
			values[68] = audiofilename;
			values[69] = videofilename;
			values[70] = missId;
			values[71] = mName;
			values[72] = mPerson;
			values[73] = txtjiditotalscore.getText().toString();
			values[74] = txtjiditotalproblem.getText().toString();
			values[75] = txthuanjingtotalscore.getText().toString();
			values[76] = txthuanjingtotalproblem.getText().toString();
			values[77] = txtshengchantotalscore.getText().toString();
			values[78] = txtshengchantotalproblem.getText().toString();
			values[79] = txtguanlitotalscore.getText().toString();
			values[80] = txtguanlitotalproblem.getText().toString();
			values[81] = txtjianguantotalscore.getText().toString();
			values[82] = txtjianguantotalproblem.getText().toString();
			values[83] = txtjingyingtotalscore.getText().toString();
			values[84] = txtjingyingtotalproblem.getText().toString();
			values[85] = txtxiaoshoutotalscore.getText().toString();
			values[86] = txtxiaoshoutotalproblem.getText().toString();
			myDbHelper.insert(MyDbInfo.getTableNames()[13], fields, values);
			myDbHelper.close();
			Toast.makeText(PFKHEditActivity.this, "保存成功", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(PFKHEditActivity.this, "保存失败", Toast.LENGTH_LONG)
					.show();
		} finally {
			myDbHelper.close();
		}

		finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xczfedit, menu);
		return true;
	}

	// 回调函数照片选择
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == IMAGE_CODE && resultCode == Activity.RESULT_OK
				&& null != data) {

			Uri selectedImage = data.getData();
			String[] filePathColumns = { MediaStore.Images.Media.DATA };
			Cursor c = this.getContentResolver().query(selectedImage,
					filePathColumns, null, null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePathColumns[0]);
			String picturePath = c.getString(columnIndex);
			c.close();
			// 获取图片并显示

			bitmap = BitmapFactory.decodeFile(picturePath, bitmapOptions);
			bitmap = autoRotate(bitmap, picturePath);
			caiji = true;
			// imageView.setImageBitmap(bitmap);
		}

		if (requestCode == VIDEO_CODE && resultCode == Activity.RESULT_OK
				&& null != data) {

			Uri selectedImage = data.getData();
			String[] filePathColumns = { MediaStore.Video.Media.DATA };
			Cursor c = this.getContentResolver().query(selectedImage,
					filePathColumns, null, null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePathColumns[0]);
			String picturePath = c.getString(columnIndex);
			c.close();
			// 获取图片并显示

			savevideo(picturePath);
			// imageView.setImageBitmap(bitmap);
		}
		if (requestCode == VIDEO_CAMERA_CODE) {
			try {
				// Uri uri = data.getData();
				// String filePath = getVideoFilePathFromUri(uri);
				// Log.d(TAG, filePath);
				//
				// savevideo(filePath);、
				videofilename = videofilepath.substring(videofilepath
						.lastIndexOf("/") + 1);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			// imageView.setImageBitmap(bitmap);
		}

		if (requestCode == RECORD_CODE && resultCode == Activity.RESULT_OK
				&& null != data) {

			Uri selectedAudio = data.getData();
			String[] filePathColumns = { MediaStore.Audio.Media.DATA };
			Cursor c = this.getContentResolver().query(selectedAudio,
					filePathColumns, null, null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePathColumns[0]);
			String picturePath = c.getString(columnIndex);
			saveaudio(picturePath);

		}

		if (requestCode == AUDIO_CODE && resultCode == Activity.RESULT_OK
				&& null != data) {

			try {
				Uri uri = data.getData();
				String filePath = getAudioFilePathFromUri(uri);
				Log.d(TAG, filePath);

				saveaudio(filePath);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

		try {

			if (resultCode != RESULT_OK) {
				return;
			} else if (requestCode == CAPTURE_CODE && resultCode == RESULT_OK) {
				ContentResolver cr = this.getContentResolver();
				try {
					if (bitmap != null)// 如果不释放的话，不断取图片，将会内存不够
						bitmap.recycle();
					// bitmap = BitmapFactory.decodeStream(cr
					// .openInputStream(mUri));

					// String filename = cachePath + "/test.jpg";
					// FileOutputStream out = new FileOutputStream(filename);
					// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//					bitmap = BitmapFactory.decodeFile(Photofilepath,
//							bitmapOptions);
					String f=Photofilepath;
					 BitmapFactory.Options options = new BitmapFactory.Options(); 
					 options.inJustDecodeBounds = true; 
					 //获取这个图片的宽和高 
					 bitmap = BitmapFactory.decodeFile(f + "",options);//此时返回bm为空 
					 options.inJustDecodeBounds =false; 
					 //计算缩放比 
					 int be = (int)(options.outHeight / (float)400);//配置图片分辨率 
					 if(be <= 0){
					 be =1; 
					 } 
					 options.inSampleSize =be; 
					 //重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦 
					 bitmap = BitmapFactory.decodeFile(f + "",options);
					int digree = 0;

					bitmap = autoRotate(bitmap, Photofilepath);

					// imageView.setImageBitmap(bitmap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				caiji = true;
			}

			Log.i(TAG, "图片生成");
		} catch (Exception e) {
			// Toast.makeText(this, "选择图片错误，图片只能为jpg格式", Toast.LENGTH_SHORT)
			// .show();
			Log.i(TAG, "Exception is " + e.toString());
		}

	}

	private String getAudioFilePathFromUri(Uri uri) {
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
		return cursor.getString(index);
	}

	private String getVideoFilePathFromUri(Uri uri) {
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		int index = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA);
		return cursor.getString(index);
	}

	public void saveaudio(String filesourcepath) {
		try {

			File f = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/" + "zfxc_wbx/audios");
			if (!f.exists()) {
				f.mkdir();
			}
			audiofilename = getAudioFileName();
			copyFile(filesourcepath, Environment.getExternalStorageDirectory()
					.getPath() + "/" + "zfxc_wbx/audios/" + audiofilename);
		} catch (Exception e) {

		}

	}

	public void savevideo(String filesourcepath) {
		try {

			File f = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/" + "zfxc_wbx/video");
			if (!f.exists()) {
				f.mkdir();
			}
			videofilename = getvideoFileName();
			copyFile(filesourcepath, Environment.getExternalStorageDirectory()
					.getPath() + "/" + "zfxc_wbx/video/" + videofilename);
		} catch (Exception e) {

		}

	}

	public void copyFile(String formPath, String toPath) {
		File file1 = new File(formPath);// 源文件路径
		File file2 = new File(toPath);// 目标文件路径

		String fileName = file1.getName();
		double fileSize = (file1.length()) / 1024 / 1024;

		System.out
				.println("***********************文件属性×××××××××××××××××××××××××");
		System.out.println("源文件路径：" + formPath);
		System.out.println("目标文件路径：" + toPath);
		System.out.println("文件名：" + fileName);
		System.out.println("文件大小:" + fileSize + "MB");

		try {
			FileInputStream fin = new FileInputStream(file1);
			FileOutputStream fout = new FileOutputStream(file2);

			BufferedInputStream bis = new BufferedInputStream(fin);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			byte[] buf = new byte[2048];

			int i;
			while ((i = bis.read(buf)) != -1) {
				bos.write(buf, 0, i);

			}
			// int len = fin.read();
			//
			// System.out.println("开始复制.....");
			// long startTime = System.currentTimeMillis();//开始时间
			// while(len != -1){
			// fout.write(len);
			// len = fin.read();
			// }
			// long endTime = System.currentTimeMillis();//结束时间
			// System.out.println("文件复制完成，公耗时："+(endTime-startTime)+"ms");

			// 关闭流
			bis.close();
			bos.close();
			fin.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Bitmap autoRotate(Bitmap bitmap, String filepath) {

		int digree = 0;

		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException e) {
			e.printStackTrace();
			exif = null;
		}

		if (exif != null) {

			int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_UNDEFINED);

			switch (ori) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				digree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				digree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				digree = 270;
				break;
			default:
				digree = 0;
				break;
			}
		}
		if (digree != 0) {

			Matrix m = new Matrix();
			m.postRotate(digree);
			 DisplayMetrics dm = new DisplayMetrics();

			 getWindowManager().getDefaultDisplay().getMetrics(dm);

			 System.out.println("heigth : " + dm.heightPixels);

			 System.out.println("width : " + dm.widthPixels);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), m, true);
		}
		return bitmap;
	}

	private String saveimage() {
		// TODO Auto-generated method stub
		String fileName = "";
		try {

			File file = new File(cachePath);
			if (!file.exists()) {
				file.mkdirs();// 创建文件夹
			}
			fileName = getPhotoFileName();

			FileOutputStream outStream = new FileOutputStream(cachePath
					+ fileName);
			byte[] bs = Bitmap2Bytes(bitmap);
			outStream.write(bs);
			outStream.close();

		} catch (FileNotFoundException e) {
			return "";
		} catch (IOException e) {
			return "";
		}
		bitmap.recycle();

		return fileName;
	}

	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		FILE_PATH = dateFormat.format(date) + ".jpg";
		return FILE_PATH;

	}

	private String getAudioFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		FILE_PATH = dateFormat.format(date) + ".3gpp";
		return FILE_PATH;

	}

	private String getvideoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		FILE_PATH = dateFormat.format(date) + ".avi";
		return FILE_PATH;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		// mMapView.onDestroy();
		mMapView = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		// mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		// mMapView.onPause();
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	private byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	// 跳转
	public static void startActivity(Context context, String id, String nid,
			String name, String person, String qx) {
		Intent in = new Intent(context, PFKHEditActivity.class);
		missId = id;
		Nyid = nid;
		mName = name;
		mPerson = person;
		county = qx;
		Log.e("aaaa", "==============" + id);
		context.startActivity(in);
	}

	public void TotalOrValidation() {
		if (txtzhutiscore.getText().toString().equals("")
				|| txtzhutiscore.getText().toString() == null) {
			total = 0;
		} else {
			total = Integer.parseInt(txtzhutiscore.getText().toString());
		}
		if (txtleixingscore.getText().toString().equals("")
				|| txtleixingscore.getText().toString() == null) {
			total1 = 0;
		} else {
			total1 = Integer.parseInt(txtleixingscore.getText().toString());
		}
		if (txtguimoscore.getText().toString().equals("")
				|| txtguimoscore.getText().toString() == null) {
			total2 = 0;
		} else {
			total2 = Integer.parseInt(txtguimoscore.getText().toString());
		}
		if (txtmeiguanscore.getText().toString().equals("")
				|| txtmeiguanscore.getText().toString() == null) {
			total3 = 0;
		} else {
			total3 = Integer.parseInt(txtmeiguanscore.getText().toString());
		}
		if (txtfangbianscore.getText().toString().equals("")
				|| txtfangbianscore.getText().toString() == null) {
			total4 = 0;
		} else {
			total4 = Integer.parseInt(txtfangbianscore.getText().toString());
		}
		if (txtnongcanscore.getText().toString().equals("")
				|| txtnongcanscore.getText().toString() == null) {
			total5 = 0;
		} else {
			total5 = Integer.parseInt(txtnongcanscore.getText().toString());
		}
		if (txtfeiqiwuscore.getText().toString().equals("")
				|| txtfeiqiwuscore.getText().toString() == null) {
			total6 = 0;
		} else {
			total6 = Integer.parseInt(txtfeiqiwuscore.getText().toString());
		}
		if (txtchuliscore.getText().toString().equals("")
				|| txtchuliscore.getText().toString() == null) {
			total7 = 0;
		} else {
			total7 = Integer.parseInt(txtchuliscore.getText().toString());
		}
		if (txttongzhiscore.getText().toString().equals("")
				|| txttongzhiscore.getText().toString() == null) {
			total8 = 0;
		} else {
			total8 = Integer.parseInt(txttongzhiscore.getText().toString());
		}
		if (txtjishuscore.getText().toString().equals("")
				|| txtjishuscore.getText().toString() == null) {
			total9 = 0;
		} else {
			total9 = Integer.parseInt(txtjishuscore.getText().toString());
		}
		if (txtkongzhiscore.getText().toString().equals("")
				|| txtkongzhiscore.getText().toString() == null) {
			total10 = 0;
		} else {
			total10 = Integer.parseInt(txtkongzhiscore.getText().toString());
		}
		if (txtfushescore.getText().toString().equals("")
				|| txtfushescore.getText().toString() == null) {
			total11 = 0;
		} else {
			total11 = Integer.parseInt(txtfushescore.getText().toString());
		}
		if (txtzhengshuscore.getText().toString().equals("")
				|| txtzhengshuscore.getText().toString() == null) {
			total12 = 0;
		} else {
			total12 = Integer.parseInt(txtzhengshuscore.getText().toString());
		}
		if (txtpeixunscore.getText().toString().equals("")
				|| txtpeixunscore.getText().toString() == null) {
			total13 = 0;
		} else {
			total13 = Integer.parseInt(txtpeixunscore.getText().toString());
		}
		if (txtzhiduscore.getText().toString().equals("")
				|| txtzhiduscore.getText().toString() == null) {
			total14 = 0;
		} else {
			total14 = Integer.parseInt(txtzhiduscore.getText().toString());
		}
		if (txtglshoucescore.getText().toString().equals("")
				|| txtglshoucescore.getText().toString() == null) {
			total15 = 0;
		} else {
			total15 = Integer.parseInt(txtglshoucescore.getText().toString());
		}
		if (txtzhanshiscore.getText().toString().equals("")
				|| txtzhanshiscore.getText().toString() == null) {
			total16 = 0;
		} else {
			total16 = Integer.parseInt(txtzhanshiscore.getText().toString());
		}
		if (txtdaozescore.getText().toString().equals("")
				|| txtdaozescore.getText().toString() == null) {
			total17 = 0;
		} else {
			total17 = Integer.parseInt(txtdaozescore.getText().toString());
		}
		if (txtnongziscore.getText().toString().equals("")
				|| txtnongziscore.getText().toString() == null) {
			total18 = 0;
		} else {
			total18 = Integer.parseInt(txtnongziscore.getText().toString());
		}
		if (txttianjianscore.getText().toString().equals("")
				|| txttianjianscore.getText().toString() == null) {
			total19 = 0;
		} else {
			total19 = Integer.parseInt(txttianjianscore.getText().toString());
		}
		if (txtwangluoscore.getText().toString().equals("")
				|| txtwangluoscore.getText().toString() == null) {
			total20 = 0;
		} else {
			total20 = Integer.parseInt(txtwangluoscore.getText().toString());
		}
		if (txtshangchuanscore.getText().toString().equals("")
				|| txtshangchuanscore.getText().toString() == null) {
			total21 = 0;
		} else {
			total21 = Integer.parseInt(txtshangchuanscore.getText().toString());
		}
		if (txtzhuisuscore.getText().toString().equals("")
				|| txtzhuisuscore.getText().toString() == null) {
			total22 = 0;
		} else {
			total22 = Integer.parseInt(txtzhuisuscore.getText().toString());
		}
		if (txtbiaozhiscore.getText().toString().equals("")
				|| txtbiaozhiscore.getText().toString() == null) {
			total23 = 0;
		} else {
			total23 = Integer.parseInt(txtbiaozhiscore.getText().toString());
		}
		if (txtwendingscore.getText().toString().equals("")
				|| txtwendingscore.getText().toString() == null) {
			total24 = 0;
		} else {
			total24 = Integer.parseInt(txtwendingscore.getText().toString());
		}
		if (txttongyiscore.getText().toString().equals("")
				|| txttongyiscore.getText().toString() == null) {
			total25 = 0;
		} else {
			total25 = Integer.parseInt(txttongyiscore.getText().toString());
		}
		if (txtjiluscore.getText().toString().equals("")
				|| txtjiluscore.getText().toString() == null) {
			total26 = 0;
		} else {
			total26 = Integer.parseInt(txtjiluscore.getText().toString());
		}
		if (txtshangbiaoscore.getText().toString().equals("")
				|| txtshangbiaoscore.getText().toString() == null) {
			total27 = 0;
		} else {
			total27 = Integer.parseInt(txtshangbiaoscore.getText().toString());
		}
		if (txtdymoshiscore.getText().toString().equals("")
				|| txtdymoshiscore.getText().toString() == null) {
			total28 = 0;
		} else {
			total28 = Integer.parseInt(txtdymoshiscore.getText().toString());
		}
		if (txtbaodaoscore.getText().toString().equals("")
				|| txtbaodaoscore.getText().toString() == null) {
			total29 = 0;
		} else {
			total29 = Integer.parseInt(txtbaodaoscore.getText().toString());
		}

		totalNum = total + total1 + total2 + total3 + total4 + total5 + total6
				+ total7 + total8 + total9 + total10 + total11 + total12
				+ total13 + total14 + total15 + total16 + total17 + total18
				+ total19 + total20 + total21 + total22 + total23 + total24
				+ total25 + total26 + total27 + total28 + total29;
	}
	private void Updata(String missId,String uname,String status){
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("MissionId", missId);
		hm.put("Mobilephone", uname);
		hm.put("Status", status);
		WebServiceJC.WorkJC("changeStatus", hm);// 更新任务状态
	}
}
