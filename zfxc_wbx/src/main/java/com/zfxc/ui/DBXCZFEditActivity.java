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
import java.util.Date;

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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.zfxc.maputil.LocationApplication;
import com.zfxc.util.DatePickerFragment;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.wbx.DisplayZFXCActivity;

public class DBXCZFEditActivity extends Activity implements LocationListener {
	private static final String TAG = "通知";
	static Uri mUri;
	public static String missId;
	public static String Nyid;
	public String jid;
	private static String mName;
	private static String mPerson;
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
			+ "zfxc/images" + File.separator + "images" + File.separator;
	public static String cachePath = SDCARD_PATH + "/zfxc/images/";
	public static String cachePathaudio = SDCARD_PATH + "/zfxc/audios/";
	public static String cachePathvideo = SDCARD_PATH + "/zfxc/video/";
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
	private EditText etddate;

	private EditText txtscompanyname;
	private EditText txtsaddress;
	private EditText txtmlcount;
	private EditText txtproductcount;
	private EditText txtworkcount;
	private EditText txttrainingcount;
	private EditText txtregcount;
	private EditText txtsscjlproblem;
	private EditText txtswzjlproblem;
	private EditText txtstrpproblem;
	private EditText txtsnysyproblem;
	private EditText txtsjgqproblem;
	private EditText txtsbzbsproblem;
	private EditText txtsjcproblem;
	private EditText txtstjjproblem;
	private EditText txtsjdccproblem;
	private EditText txtjxkhproblem;
	private EditText txtxgjy;
	private EditText txtyjcon;

	private CheckBox cbzhong;
	private CheckBox cbchu;
	private CheckBox cbyu;
	private CheckBox cbdiji;
	private CheckBox cbxianji;
	private String zhresult = "";
	private String churesult = "";
	private String yuresult = "";
	private String diji = "";
	private String xianji = "";

	private RadioButton sscjlradiofinish;
	private RadioButton sscjlradiounfinish;
	private RadioButton swzjlradiofinish;
	private RadioButton swzjlradiounfinish;
	private RadioButton strpradiofinish;
	private RadioButton strpradiounfinish;
	private RadioButton snysyradiofinish;
	private RadioButton snysyradiounfinish;
	private RadioButton sjgqrradiofinish;
	private RadioButton sjgqrradiounfinish;
	private RadioButton sbzbradiofinish;
	private RadioButton sbzbradiounfinish;
	private RadioButton sjcradiofinish;
	private RadioButton sjcradiounfinish;
	private RadioButton stjjradiofinish;
	private RadioButton stjjradiounfinish;
	private RadioButton sjdcradiofinish;
	private RadioButton sjdcradiounfinish;
	private RadioButton jxkhradiofinish;
	private RadioButton jxkhradiounfinish;

	private RadioGroup rgsscjlresult;
	private RadioGroup rgswzjlresult;
	private RadioGroup rgstrpresult;
	private RadioGroup rgsnysyresult;
	private RadioGroup rgsjgqresult;
	private RadioGroup rgsbzbsresult;
	private RadioGroup rgsjcresult;
	private RadioGroup rgstjjresult;
	private RadioGroup rgsjdccresult;
	private RadioGroup rgjxkhresult;
	private MapView mMapView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_dbxczfedit);
		initmap();
		initImageLoader(this);
		init();

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
						DBXCZFEditActivity.this,
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
		jid = getIntent().getStringExtra("id");
		myDbHelper = MyDbHelper.getInstance(this);
		etddate = (EditText) findViewById(R.id.db_etddate);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		etddate.setText(sf.format(new Date()));
		// 获取控件句柄
		txtscompanyname = (EditText) findViewById(R.id.db_txtscompanyname);
		txtsaddress = (EditText) findViewById(R.id.db_txtsaddress);
		txtmlcount = (EditText) findViewById(R.id.db_mlcount);
		txtproductcount = (EditText) findViewById(R.id.db_productcount);
		txtworkcount = (EditText) findViewById(R.id.db_workpscount);
		txttrainingcount = (EditText) findViewById(R.id.db_trainingpscount);
		txtregcount = (EditText) findViewById(R.id.db_regpscount);
		txtsscjlproblem = (EditText) findViewById(R.id.db_txtsscjlproblem);
		txtswzjlproblem = (EditText) findViewById(R.id.db_txtswzjlproblem);
		txtstrpproblem = (EditText) findViewById(R.id.db_txtstrpproblem);
		txtsnysyproblem = (EditText) findViewById(R.id.db_txtsnysyproblem);
		txtsjgqproblem = (EditText) findViewById(R.id.db_txtsjgqproblem);
		txtsbzbsproblem = (EditText) findViewById(R.id.db_txtsbzbsproblem);
		txtsjcproblem = (EditText) findViewById(R.id.db_txtsjcproblem);
		txtstjjproblem = (EditText) findViewById(R.id.db_txtstjjproblem);
		txtsjdccproblem = (EditText) findViewById(R.id.db_txtsjdccproblem);
		txtjxkhproblem = (EditText) findViewById(R.id.db_txtjxkhproblem);
		txtxgjy = (EditText) findViewById(R.id.db_txtxgjy);
		txtyjcon = (EditText) findViewById(R.id.db_txtyjcon);
		txtsaddressdes = (TextView) findViewById(R.id.txtsaddressdes);

		sscjlradiofinish = (RadioButton) findViewById(R.id.db_sscjlradiofinish);
		sscjlradiounfinish = (RadioButton) findViewById(R.id.db_sscjlradiounfinish);
		swzjlradiofinish = (RadioButton) findViewById(R.id.db_swzjlradiofinish);
		swzjlradiounfinish = (RadioButton) findViewById(R.id.db_swzjlradiounfinish);
		strpradiofinish = (RadioButton) findViewById(R.id.db_strpradiofinish);
		strpradiounfinish = (RadioButton) findViewById(R.id.db_strpradiounfinish);
		snysyradiofinish = (RadioButton) findViewById(R.id.db_snysyradiofinish);
		snysyradiounfinish = (RadioButton) findViewById(R.id.db_snysyradiounfinish);
		sjgqrradiofinish = (RadioButton) findViewById(R.id.db_sjgqrradiofinish);
		sjgqrradiounfinish = (RadioButton) findViewById(R.id.db_sjgqrradiounfinish);
		sbzbradiofinish = (RadioButton) findViewById(R.id.db_sbzbradiofinish);
		sbzbradiounfinish = (RadioButton) findViewById(R.id.db_sbzbradiounfinish);
		sjcradiofinish = (RadioButton) findViewById(R.id.db_sjcradiofinish);
		sjcradiounfinish = (RadioButton) findViewById(R.id.db_sjcradiounfinish);
		stjjradiofinish = (RadioButton) findViewById(R.id.db_stjjradiofinish);
		stjjradiounfinish = (RadioButton) findViewById(R.id.db_stjjradiounfinish);
		sjdcradiofinish = (RadioButton) findViewById(R.id.db_sjdcradiofinish);
		sjdcradiounfinish = (RadioButton) findViewById(R.id.db_sjdcradiounfinish);
		jxkhradiofinish = (RadioButton) findViewById(R.id.db_jxkhradiofinish);
		jxkhradiounfinish = (RadioButton) findViewById(R.id.db_jxkhradiounfinish);

		rgsscjlresult = (RadioGroup) findViewById(R.id.db_rgsscjlresult);
		rgswzjlresult = (RadioGroup) findViewById(R.id.db_rgswzjlresult);
		rgstrpresult = (RadioGroup) findViewById(R.id.db_rgstrpresult);
		rgsnysyresult = (RadioGroup) findViewById(R.id.db_rgsnysyresult);
		rgsjgqresult = (RadioGroup) findViewById(R.id.db_rgsjgqresult);
		rgsbzbsresult = (RadioGroup) findViewById(R.id.db_rgsbzbsresult);
		rgsjcresult = (RadioGroup) findViewById(R.id.db_rgsjcresult);
		rgstjjresult = (RadioGroup) findViewById(R.id.db_rgstjjresult);
		rgsjdccresult = (RadioGroup) findViewById(R.id.db_rgsjdccresult);
		rgjxkhresult = (RadioGroup) findViewById(R.id.db_jxkhresult);

		cbzhong = (CheckBox) findViewById(R.id.db_zhong);
		cbchu = (CheckBox) findViewById(R.id.db_chu);
		cbyu = (CheckBox) findViewById(R.id.db_yu);
		cbdiji = (CheckBox) findViewById(R.id.db_diji);
		cbxianji = (CheckBox) findViewById(R.id.db_xianji);
		cbzhong.setOnCheckedChangeListener(cbLisclick);
		cbchu.setOnCheckedChangeListener(cbLisclick);
		cbyu.setOnCheckedChangeListener(cbLisclick);
		cbdiji.setOnCheckedChangeListener(cbLisclick);
		cbxianji.setOnCheckedChangeListener(cbLisclick);
		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;

		btnposition = (Button) findViewById(R.id.btnposition);
		btnposition.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				getposition();

			}

		});
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

		buttonSetDate = (Button) findViewById(R.id.ButtonSetDate);
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
				if (Nyid == null || Nyid.equals("")) {
					if (jid == null || jid.equals("")) {
						if(txtsaddress.getText().toString().equals("")||txtsaddress.getText().toString()==null){
							Toast.makeText(DBXCZFEditActivity.this,"地址不能为空！", Toast.LENGTH_SHORT).show();
						}else{
						saverecord();
						}
					} else {
						updaterecord();
					}
				} else {
					if (jid == null || jid.equals("")) {
						updaterecord();
					} else {
						updaterecord();
					}
				}

			}

		});

		btndeleteftrecord = (Button) findViewById(R.id.delftrecord);

		if (Nyid == null || Nyid.equals("")) {
			btndeleteftrecord.setVisibility(View.INVISIBLE);
		} else {
			btndeleteftrecord.setVisibility(View.VISIBLE);
		}
		btndeleteftrecord.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				AlertDialog dialog = new AlertDialog.Builder(
						DBXCZFEditActivity.this)
						.setTitle("确认删除吗？")

						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 点击“确认”后的操作
										deletedata();

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

			}

		});

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

				Intent intent = new Intent(DBXCZFEditActivity.this,
						SpaceImageDetailActivity.class);
				// String
				// filename=Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/20151029_102917.jpg";

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

					Toast.makeText(DBXCZFEditActivity.this, "尚未采集照片！",
							Toast.LENGTH_LONG).show();

				}
			}

		});
		imageviewsound = (ImageView) findViewById(R.id.imageviewsound);

		imageviewsound.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {

				//

				// String
				// filename=Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/20151029_102917.jpg";

				File f = new File(cachePathaudio + audiofilename);
				if (audiofilename != null && !audiofilename.equals("")
						&& f.exists()) {

					Intent intent = new Intent(Intent.ACTION_VIEW);
					// intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory()
					// + "/recording323345382.3gpp"), "audio/3gpp");

					intent.setAction(Intent.ACTION_VIEW);
					File file1 = new File(cachePathaudio + audiofilename);
					intent.setDataAndType(Uri.fromFile(file1), "audio/*");
					startActivity(intent);

				} else {

					Toast.makeText(DBXCZFEditActivity.this, "尚未采集声音！",
							Toast.LENGTH_LONG).show();

				}
			}

		});

		imageviewvideo = (ImageView) findViewById(R.id.imageviewvideo);

		imageviewvideo.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {

				//

				// String
				// filename=Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/20151029_102917.jpg";

				File f = new File(cachePathvideo + videofilename);
				if (videofilename != null && !videofilename.equals("")
						&& f.exists()) {

					Intent intent = new Intent(Intent.ACTION_VIEW);
					// intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory()
					// + "/recording323345382.3gpp"), "audio/3gpp");

					intent.setAction(Intent.ACTION_VIEW);
					File file1 = new File(cachePathvideo + videofilename);
					intent.setDataAndType(Uri.fromFile(file1), "video/*");
					startActivity(intent);

				} else {

					Toast.makeText(DBXCZFEditActivity.this, "尚未采集视频！",
							Toast.LENGTH_LONG).show();

				}
			}

		});

		bindziduan();

	}

	private OnCheckedChangeListener cbLisclick = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.id.db_zhong:
				if (isChecked) {
					zhresult = "1";
				} else {
					zhresult = "0";
				}
				break;
			case R.id.db_chu:
				if (isChecked) {
					churesult = "1";
				} else {
					churesult = "0";
				}
				break;
			case R.id.db_yu:
				if (isChecked) {
					yuresult = "1";
				} else {
					yuresult = "0";
				}
				break;
			case R.id.db_diji:
				if (isChecked) {
					diji = "1";
				} else {
					diji = "0";
				}
				break;
			case R.id.db_xianji:
				if (isChecked) {
					xianji = "1";
				} else {
					xianji = "0";
				}
				break;
			}
		}
	};

	// 获取实时位置
	private void getposition() {
		// TODO Auto-generated method stub

		// InitLocation();
		// mLocationClient.start();
		txtsaddressdes.setText("正在获取请稍后...");

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

				txtsaddressdes.setText("地址：" + Positionet.saddress + "\r\n经度："
						+ Positionet.sgpsx + "\r\n纬度：" + Positionet.sgpsy);

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
				// 构建Marker图标
				BitmapDescriptor bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.maker);
				// 构建MarkerOption，用于在地图上添加Marker
				mBaiduMap.clear();
				OverlayOptions option = new MarkerOptions().position(point)
						.icon(bitmap);

				// 在地图上添加Marker，并显示
				mBaiduMap.addOverlay(option);
				MapStatus mMapStatus = new MapStatus.Builder().target(point)
						.zoom(13).build();
				MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
						.newMapStatus(mMapStatus);
				// 改变地图状态
				mBaiduMap.setMapStatus(mMapStatusUpdate);

			} else {
				txtsaddressdes.setText("未获取到地址，请保持网络畅通！");
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
		mLocationClient.stop();// ֹͣ����
		((LocationApplication) getApplication()).mLocationresult = null;// �ȳ�ʼ��Ϊnull
	}

	// 采集照片
	private void savazp() {
		// TODO Auto-generated method stub

		final CharSequence[] items = { "相册", "拍照" };
		AlertDialog dlg = new AlertDialog.Builder(DBXCZFEditActivity.this)
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
										+ "/zfxc/images/temps");

								if (!appDir.exists()) {
									appDir.mkdir();
								}
								Photofilepath = Environment
										.getExternalStorageDirectory()
										+ "/zfxc/images/temps"
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
		AlertDialog dlg = new AlertDialog.Builder(DBXCZFEditActivity.this)
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
										+ "/zfxc/video/temps");

								if (!appDir.exists()) {
									appDir.mkdir();
								}
								videofilepath = Environment
										.getExternalStorageDirectory()
										+ "/zfxc/video/temps"
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

								Intent album = new Intent(
										Intent.ACTION_GET_CONTENT);

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
		AlertDialog dlg = new AlertDialog.Builder(DBXCZFEditActivity.this)
				.setTitle("选择图片")
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
			if (Nyid == null || Nyid.equals("")) {
				if (jid == null || jid.equals("")) {
					wherevalue = new String[] { "0" };
				} else {
					wherevalue = new String[] { jid };
				}

			} else if (jid == null || jid.equals("")) {
				if (Nyid == null || Nyid.equals("")) {
					wherevalue = new String[] { "0" };
				} else {
					wherevalue = new String[] { Nyid };
				}

			} else {
				wherevalue = new String[] { jid };
			}
			File f = new File(cachePath + filename);
			if (!filename.equals("") && f.exists()) {
				f.delete();
			}
			myDbHelper.delete(MyDbInfo.getTableNames()[11], "ID=?", wherevalue);

			myDbHelper.close();
			Toast.makeText(DBXCZFEditActivity.this, "删除成功", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(DBXCZFEditActivity.this, "删除失败", Toast.LENGTH_LONG)
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
			if (Nyid == null || Nyid.equals("") && jid != null) {
				if (jid == null || jid.equals("")) {
					d = "0";
				} else {
					d = jid;
				}
			} else if (jid == null || jid.equals("") && Nyid != null) {
				if (Nyid == null || Nyid.equals("")) {
					d = "0";
				} else {
					d = Nyid;
				}
			} else {
				d = jid;
			}
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			String sql = "select * from " + MyDbInfo.getTableNames()[11]
					+ " where Id=" + d;
			c = myDbHelper.select(sql);
			if (c.getCount() == 0) {
				return;
			} else {
				c.moveToFirst();
				String result = "";
				etddate.setText(c.getString(2));

				txtscompanyname.setText(c.getString(1));
				txtsaddress.setText(c.getString(3));
				if (c.getString(4).equals("0") || c.getString(4).equals("")) {

					cbdiji.setChecked(false);
				} else {
					cbdiji.setChecked(true);
				}
				if (c.getString(5).equals("0") || c.getString(5).equals("")) {

					cbxianji.setChecked(false);
				} else {
					cbxianji.setChecked(true);
				}
				if (c.getString(6).equals("0") || c.getString(6).equals("")) {

					cbzhong.setChecked(false);
				} else {
					cbzhong.setChecked(true);
				}
				if (c.getString(7).equals("0") || c.getString(7).equals("")) {

					cbchu.setChecked(false);
				} else {
					cbchu.setChecked(true);
				}
				if (c.getString(8).equals("0") || c.getString(8).equals("")) {

					cbyu.setChecked(false);
				} else {
					cbyu.setChecked(true);
				}
				txtmlcount.setText(c.getString(9));
				txtproductcount.setText(c.getString(10));
				txtworkcount.setText(c.getString(11));
				txttrainingcount.setText(c.getString(12));
				txtregcount.setText(c.getString(13));
				txtsscjlproblem.setText(c.getString(15));
				txtswzjlproblem.setText(c.getString(17));
				txtstrpproblem.setText(c.getString(19));
				txtsnysyproblem.setText(c.getString(21));
				txtsjgqproblem.setText(c.getString(23));
				txtsbzbsproblem.setText(c.getString(25));
				txtsjcproblem.setText(c.getString(27));
				txtstjjproblem.setText(c.getString(29));
				txtsjdccproblem.setText(c.getString(31));
				txtjxkhproblem.setText(c.getString(33));
				txtxgjy.setText(c.getString(34));
				txtyjcon.setText(c.getString(35));
				this.filename = c.getString(38);
				audiofilename = c.getString(39);

				videofilename = c.getString(c.getColumnIndex("jcsvideo"));
				firstvideo = videofilename;

				if (c.getString(14) == null || c.getString(14).equals("")
						|| c.getString(14).equals("null")
						|| c.getString(14).equals("是")) {

					sscjlradiounfinish.setChecked(false);
					sscjlradiofinish.setChecked(true);
				} else {
					sscjlradiounfinish.setChecked(true);
					sscjlradiofinish.setChecked(false);

				}

				if (c.getString(16) == null || c.getString(16).equals("")
						|| c.getString(16).equals("null")
						|| c.getString(16).equals("是")) {

					swzjlradiounfinish.setChecked(false);
					swzjlradiofinish.setChecked(true);
				} else {
					swzjlradiounfinish.setChecked(true);
					swzjlradiofinish.setChecked(false);

				}

				if (c.getString(18) == null || c.getString(18).equals("")
						|| c.getString(18).equals("null")
						|| c.getString(18).equals("是")) {

					strpradiounfinish.setChecked(false);
					strpradiofinish.setChecked(true);
				} else {
					strpradiounfinish.setChecked(true);
					strpradiofinish.setChecked(false);

				}

				if (c.getString(20) == null || c.getString(20).equals("")
						|| c.getString(20).equals("null")
						|| c.getString(20).equals("是")) {

					snysyradiounfinish.setChecked(false);
					snysyradiofinish.setChecked(true);
				} else {
					snysyradiounfinish.setChecked(true);
					snysyradiofinish.setChecked(false);

				}

				if (c.getString(22) == null || c.getString(22).equals("")
						|| c.getString(22).equals("null")
						|| c.getString(22).equals("是")) {

					sjgqrradiounfinish.setChecked(false);
					sjgqrradiofinish.setChecked(true);
				} else {
					sjgqrradiounfinish.setChecked(true);
					sjgqrradiofinish.setChecked(false);

				}

				if (c.getString(24) == null || c.getString(24).equals("")
						|| c.getString(24).equals("null")
						|| c.getString(24).equals("是")) {

					sbzbradiounfinish.setChecked(false);
					sbzbradiofinish.setChecked(true);
				} else {
					sbzbradiounfinish.setChecked(true);
					sbzbradiofinish.setChecked(false);

				}
				if (c.getString(26) == null || c.getString(26).equals("")
						|| c.getString(26).equals("null")
						|| c.getString(26).equals("是")) {

					sjcradiounfinish.setChecked(false);
					sjcradiofinish.setChecked(true);
				} else {
					sjcradiounfinish.setChecked(true);
					sjcradiofinish.setChecked(false);

				}

				if (c.getString(28) == null || c.getString(28).equals("")
						|| c.getString(28).equals("null")
						|| c.getString(28).equals("是")) {

					stjjradiounfinish.setChecked(false);
					stjjradiofinish.setChecked(true);
				} else {
					stjjradiounfinish.setChecked(true);
					stjjradiofinish.setChecked(false);

				}
				if (c.getString(30) == null || c.getString(30).equals("")
						|| c.getString(30).equals("null")
						|| c.getString(30).equals("是")) {

					sjdcradiounfinish.setChecked(false);
					sjdcradiofinish.setChecked(true);
				} else {
					sjdcradiounfinish.setChecked(true);
					sjdcradiofinish.setChecked(false);

				}
				if (c.getString(32) == null || c.getString(32).equals("")
						|| c.getString(32).equals("null")
						|| c.getString(32).equals("是")) {

					jxkhradiounfinish.setChecked(false);
					jxkhradiofinish.setChecked(true);
				} else {
					jxkhradiounfinish.setChecked(true);
					jxkhradiofinish.setChecked(false);

				}
				InitLocation();
				// 绑定位置
				sgpsy = c.getString(c.getColumnIndex("jcsgpsy"));
				sgpsx = c.getString(c.getColumnIndex("jcsgpsx"));
				LatLng point = new LatLng(23.150725, 113.257469);
				try {

					point = new LatLng(Double.parseDouble(sgpsy),
							Double.parseDouble(sgpsx));
				} catch (Exception e) {

				}
				// 构建Marker图标
				BitmapDescriptor bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.maker);
				// 构建MarkerOption，用于在地图上添加Marker
				mBaiduMap.clear();
				OverlayOptions option = new MarkerOptions().position(point)
						.icon(bitmap);

				// 在地图上添加Marker，并显示
				mBaiduMap.addOverlay(option);
				MapStatus mMapStatus = new MapStatus.Builder().target(point)
						.zoom(13).build();
				MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
						.newMapStatus(mMapStatus);
				// 改变地图状态
				mBaiduMap.setMapStatus(mMapStatusUpdate);

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
			String table = "tbdbxczf";
			String fields[] = { "sjcompany", "dbdate", "saddress", "jbdj",
					"jbxj", "hyzhong", "hychu", "hyyu", "wmlcount",
					"wproductcount", "wgzcount", "wpxcount", "wreg", "wdengji",
					"wdjproblem", "wziliao", "wzlproblem", "jgxize",
					"jgxzprobelm", "jgdiyu", "jgdyproblem", "jgbiaozhi",
					"jgbzproblem", "jgbeian", "jgbaproblem", "zcyusuan",
					"zcysproblem", "zcguihua", "zcghproblem", "zczhichi",
					"zczcproblem", "zckaohe", "zckhproblem", "zcjingyan",
					"yjproblem", "jcsgpsx", "jcsgpsy", "jcsimages", "jcsaudio",
					"jcsvideo" };
			String values[] = { "sjcompany", "dbdate", "saddress", "jbdj",
					"jbxj", "hyzhong", "hychu", "hyyu", "wmlcount",
					"wproductcount", "wgzcount", "wpxcount", "wreg", "wdengji",
					"wdjproblem", "wziliao", "wzlproblem", "jgxize",
					"jgxzprobelm", "jgdiyu", "jgdyproblem", "jgbiaozhi",
					"jgbzproblem", "jgbeian", "jgbaproblem", "zcyusuan",
					"zcysproblem", "zcguihua", "zcghproblem", "zczhichi",
					"zczcproblem", "zckaohe", "zckhproblem", "zcjingyan",
					"yjproblem", "jcsgpsx", "jcsgpsy", "jcsimages", "jcsaudio",
					"jcsvideo" };
			for (int i = 0; i < values.length; i++) {
				values[i] = "";
			}
			values[0] = txtscompanyname.getText().toString();
			values[1] = etddate.getText().toString();
			values[2] = txtsaddress.getText().toString();
			values[3] = diji;
			values[4] = xianji;
			values[5] = zhresult;
			values[6] = churesult;
			values[7] = yuresult;
			values[8] = txtmlcount.getText().toString();
			values[9] = txtproductcount.getText().toString();
			values[10] = txtworkcount.getText().toString();
			values[11] = txttrainingcount.getText().toString();
			values[12] = txtregcount.getText().toString();
			values[13] = ((RadioButton) findViewById(rgsscjlresult
					.getCheckedRadioButtonId())).getText().toString();
			values[14] = txtsscjlproblem.getText().toString();

			values[15] = ((RadioButton) findViewById(rgswzjlresult
					.getCheckedRadioButtonId())).getText().toString();
			values[16] = txtswzjlproblem.getText().toString();

			values[17] = ((RadioButton) findViewById(rgstrpresult
					.getCheckedRadioButtonId())).getText().toString();
			values[18] = txtstrpproblem.getText().toString();

			values[19] = ((RadioButton) findViewById(rgsnysyresult
					.getCheckedRadioButtonId())).getText().toString();
			values[20] = txtsnysyproblem.getText().toString();

			values[21] = ((RadioButton) findViewById(rgsjgqresult
					.getCheckedRadioButtonId())).getText().toString();
			values[22] = txtsjgqproblem.getText().toString();

			values[23] = ((RadioButton) findViewById(rgsbzbsresult
					.getCheckedRadioButtonId())).getText().toString();
			values[24] = txtsbzbsproblem.getText().toString();

			values[25] = ((RadioButton) findViewById(rgsjcresult
					.getCheckedRadioButtonId())).getText().toString();
			values[26] = txtsjcproblem.getText().toString();

			values[27] = ((RadioButton) findViewById(rgstjjresult
					.getCheckedRadioButtonId())).getText().toString();
			values[28] = txtstjjproblem.getText().toString();

			values[29] = ((RadioButton) findViewById(rgsjdccresult
					.getCheckedRadioButtonId())).getText().toString();
			values[30] = txtsjdccproblem.getText().toString();
			values[31] = ((RadioButton) findViewById(rgjxkhresult
					.getCheckedRadioButtonId())).getText().toString();
			values[32] = txtjxkhproblem.getText().toString();
			values[33] = txtxgjy.getText().toString();
			values[34] = txtyjcon.getText().toString();

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
			values[35] = sgpsx;
			values[36] = sgpsy;
			values[37] = filename;
			values[38] = audiofilename;
			values[39] = videofilename;
			String[] wherevalue = null;
			if (Nyid == null || Nyid.equals("")) {
				wherevalue = new String[] { jid };
			} else if (jid == null || jid.equals("")) {
				wherevalue = new String[] { Nyid };
			} else {
				wherevalue = new String[] { jid };
			}
			myDbHelper.update(MyDbInfo.getTableNames()[11], fields, values,
					"id=?", wherevalue);
			myDbHelper.close();
			Toast.makeText(DBXCZFEditActivity.this, "更新成功", Toast.LENGTH_LONG)
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
			String table = "tbdbxczf";
			String fields[] = { "sjcompany", "dbdate", "saddress", "jbdj",
					"jbxj", "hyzhong", "hychu", "hyyu", "wmlcount",
					"wproductcount", "wgzcount", "wpxcount", "wreg", "wdengji",
					"wdjproblem", "wziliao", "wzlproblem", "jgxize",
					"jgxzprobelm", "jgdiyu", "jgdyproblem", "jgbiaozhi",
					"jgbzproblem", "jgbeian", "jgbaproblem", "zcyusuan",
					"zcysproblem", "zcguihua", "zcghproblem", "zczhichi",
					"zczcproblem", "zckaohe", "zckhproblem", "zcjingyan",
					"yjproblem", "jcsgpsx", "jcsgpsy", "jcsimages", "jcsaudio",
					"jcsvideo", "missionId", "missname", "missperson" };
			String values[] = { "sjcompany", "dbdate", "saddress", "jbdj",
					"jbxj", "hyzhong", "hychu", "hyyu", "wmlcount",
					"wproductcount", "wgzcount", "wpxcount", "wreg", "wdengji",
					"wdjproblem", "wziliao", "wzlproblem", "jgxize",
					"jgxzprobelm", "jgdiyu", "jgdyproblem", "jgbiaozhi",
					"jgbzproblem", "jgbeian", "jgbaproblem", "zcyusuan",
					"zcysproblem", "zcguihua", "zcghproblem", "zczhichi",
					"zczcproblem", "zckaohe", "zckhproblem", "zcjingyan",
					"yjproblem", "jcsgpsx", "jcsgpsy", "jcsimages", "jcsaudio",
					"jcsvideo", "missionId", "missname", "missperson" };
			for (int i = 0; i < values.length; i++) {
				values[i] = "";
			}
			values[0] = txtscompanyname.getText().toString();
			values[1] = etddate.getText().toString();
			values[2] = txtsaddress.getText().toString();
			values[3] = diji;
			values[4] = xianji;
			values[5] = zhresult;
			values[6] = churesult;
			values[7] = yuresult;
			values[8] = txtmlcount.getText().toString();
			values[9] = txtproductcount.getText().toString();
			values[10] = txtworkcount.getText().toString();
			values[11] = txttrainingcount.getText().toString();
			values[12] = txtregcount.getText().toString();
			values[13] = ((RadioButton) findViewById(rgsscjlresult
					.getCheckedRadioButtonId())).getText().toString();
			values[14] = txtsscjlproblem.getText().toString();

			values[15] = ((RadioButton) findViewById(rgswzjlresult
					.getCheckedRadioButtonId())).getText().toString();
			values[16] = txtswzjlproblem.getText().toString();

			values[17] = ((RadioButton) findViewById(rgstrpresult
					.getCheckedRadioButtonId())).getText().toString();
			values[18] = txtstrpproblem.getText().toString();

			values[19] = ((RadioButton) findViewById(rgsnysyresult
					.getCheckedRadioButtonId())).getText().toString();
			values[20] = txtsnysyproblem.getText().toString();

			values[21] = ((RadioButton) findViewById(rgsjgqresult
					.getCheckedRadioButtonId())).getText().toString();
			values[22] = txtsjgqproblem.getText().toString();

			values[23] = ((RadioButton) findViewById(rgsbzbsresult
					.getCheckedRadioButtonId())).getText().toString();
			values[24] = txtsbzbsproblem.getText().toString();

			values[25] = ((RadioButton) findViewById(rgsjcresult
					.getCheckedRadioButtonId())).getText().toString();
			values[26] = txtsjcproblem.getText().toString();

			values[27] = ((RadioButton) findViewById(rgstjjresult
					.getCheckedRadioButtonId())).getText().toString();
			values[28] = txtstjjproblem.getText().toString();

			values[29] = ((RadioButton) findViewById(rgsjdccresult
					.getCheckedRadioButtonId())).getText().toString();
			values[30] = txtsjdccproblem.getText().toString();
			values[31] = ((RadioButton) findViewById(rgjxkhresult
					.getCheckedRadioButtonId())).getText().toString();
			values[32] = txtjxkhproblem.getText().toString();
			values[33] = txtxgjy.getText().toString();
			values[34] = txtyjcon.getText().toString();
			String filename = "";
			try {
				filename = saveimage();
			} catch (Exception e1) {

			}
			values[35] = sgpsx;
			values[36] = sgpsy;
			values[37] = filename;
			values[38] = audiofilename;
			values[39] = videofilename;
			values[40] = missId;
			values[41] = mName;
			values[42] = mPerson;
			myDbHelper.insert(MyDbInfo.getTableNames()[11], fields, values);
			myDbHelper.close();
			Toast.makeText(DBXCZFEditActivity.this, "保存成功", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(DBXCZFEditActivity.this, "保存失败", Toast.LENGTH_LONG)
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
					bitmap = BitmapFactory.decodeFile(Photofilepath,
							bitmapOptions);
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
					.getPath() + "/" + "zfxc/audios");
			if (!f.exists()) {
				f.mkdir();
			}
			audiofilename = getAudioFileName();
			copyFile(filesourcepath, Environment.getExternalStorageDirectory()
					.getPath() + "/" + "zfxc/audios/" + audiofilename);
		} catch (Exception e) {

		}

	}

	public void savevideo(String filesourcepath) {
		try {

			File f = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/" + "zfxc/video");
			if (!f.exists()) {
				f.mkdir();
			}
			videofilename = getvideoFileName();
			copyFile(filesourcepath, Environment.getExternalStorageDirectory()
					.getPath() + "/" + "zfxc/video/" + videofilename);
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
		mMapView.onDestroy();
		mMapView = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
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
			String name, String person) {
		Intent in = new Intent(context, DBXCZFEditActivity.class);
		missId = id;
		Nyid = nid;
		mName = name;
		mPerson = person;
		Log.e("aaaa", "==============" + id);
		context.startActivity(in);
	}
}
