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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.zfxc.maputil.LocationApplication;
import com.zfxc.util.DatePickerFragment;
import com.zfxc.util.MyDbHelper;
import com.zfxc.util.MyDbInfo;
import com.zfxc.util.WebServiceJC;
import com.zfxc.wbx.DisplayZFXCActivity;

public class NCPZLZFEditActivity extends Activity implements LocationListener {
	private static final String TAG = "通知";
	static Uri mUri;
	public static String missId;
	public static String Nyid;
	private static String mName;
	private static String mPerson;
	private static String county;
	private String phone = MyDbInfo.account;
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
	private EditText etddate;

	private Spinner spSJdanwei;
	private TextView tvUname;
	private EditText txtOther;
	private EditText txtsaddress;
	private EditText txtsscjlproblem;
	private EditText txtswzjlproblem;
	private EditText txtstrpproblem;
	private EditText txtsnysyproblem;
	private EditText txtsjgqproblem;
	private EditText txtsbzbsproblem;
	private EditText txtsjcproblem;
	private EditText txtstjjproblem;
	private EditText txtsjdccproblem;
	private EditText txtwanzhengproblem;
	private EditText txtzhengqiproblem;
	private EditText txtshixingproblem;
	private EditText txtdananproblem;
	private EditText txtzijianproblem;
	private EditText txtcundangproblem;
	private EditText txtcunfangproblem;
	private EditText txtshangshiproblem;
	private EditText txtguifanproblem;
	private EditText txtzlproblem;

	private EditText txtsjyear;
	private EditText txtsjmonth;
	private EditText txtsjday;
	private EditText txtxcyear;
	private EditText txtxcmonth;
	private EditText txtxcday;

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

	private RadioButton ncwanzhengradiounfinish;
	private RadioButton ncwanzhengradiofinish;
	private RadioButton nczhengqiradiounfinish;
	private RadioButton nczhengqiradiofinish;
	private RadioButton ncshixingradiounfinish;
	private RadioButton ncshixingradiofinish;
	private RadioButton ncdananradiounfinish;
	private RadioButton ncdananradiofinish;
	private RadioButton nczijianradiounfinish;
	private RadioButton nczijianradiofinish;
	private RadioButton nccundangradiounfinish;
	private RadioButton nccundangradiofinish;
	private RadioButton nccunfangradiounfinish;
	private RadioButton nccunfangradiofinish;
	private RadioButton ncshangshiradiounfinish;
	private RadioButton ncshangshiradiofinish;
	private RadioButton ncguifanradiounfinish;
	private RadioButton ncguifanradiofinish;

	private RadioGroup rgsscjlresult;
	private RadioGroup rgswzjlresult;
	private RadioGroup rgstrpresult;
	private RadioGroup rgsnysyresult;
	private RadioGroup rgsjgqresult;
	private RadioGroup rgsbzbsresult;
	private RadioGroup rgsjcresult;
	private RadioGroup rgstjjresult;

	private RadioGroup ncwanzhengresult;
	private RadioGroup nczhengqiresult;
	private RadioGroup ncshixingresult;
	private RadioGroup ncdananresult;
	private RadioGroup nczijianresult;
	private RadioGroup nccundangresult;
	private RadioGroup nccunfangresult;
	private RadioGroup ncshangshiresult;
	private RadioGroup ncguifanresult;
	
	private ImageView imgNcssPhoto;
	private ImageView ImaNcssVideo;
	private ImageView imgNcswPhoto;
	private ImageView ImaNcswVideo;
	private ImageView imgNcstPhoto;
	private ImageView ImaNcstVideo;
	private ImageView imgNcsnPhoto;
	private ImageView ImaNcsnVideo;
	private ImageView imgNcsjPhoto;
	private ImageView ImaNcsjVideo;
	private ImageView imgNcsbPhoto;
	private ImageView ImaNcsbVideo;
	private ImageView imgNccrPhoto;
	private ImageView ImaNccrVideo;
	private ImageView imgNcjjPhoto;
	private ImageView ImaNcjjVideo;
	private ImageView imgNcwaPhoto;
	private ImageView ImaNcwaVideo;
	private ImageView imgNczhengPhoto;
	private ImageView ImaNczhengVideo;
	private ImageView imgNcshiPhoto;
	private ImageView ImaNcshiVideo;
	private ImageView imgNcdanPhoto;
	private ImageView ImaNcdanVideo;
	private ImageView imgNcziPhoto;
	private ImageView ImaNcziVideo;
	private ImageView imgNcdangPhoto;
	private ImageView ImaNcdangVideo;
	private ImageView imgNcfangPhoto;
	private ImageView ImaNcfangVideo;
	private ImageView imgNcshangPhoto;
	private ImageView ImaNcshangVideo;
	private ImageView imgNcguiPhoto;
	private ImageView ImaNcguiVideo;
	
	private MapView mMapView = null;

	private List<String> qxList = null;
	private static String codeString = null;
	private TextView ProvinceTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_zfncpzledit);
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
				spSJdanwei.setAdapter(adapter);
				spSJdanwei
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								ProvinceTxt = (TextView) spSJdanwei
										.getSelectedView().findViewById(
												R.id.sp_base); // 得到选中的选项Id
								codeString = ProvinceTxt.getText().toString();
								Log.e("aaaaa",
										"===========codeString============"
												+ codeString);
								// curyid = position;
								// showPrice(position);
								TextView tv = (TextView) view;
								tv.setTextColor(getResources().getColor(
										R.color.black)); // 设置颜色

								// tv.setTextSize(12.0f); //设置大小

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
						NCPZLZFEditActivity.this,
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
		etddate = (EditText) findViewById(R.id.ncetddate);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		etddate.setText(sf.format(new Date()));
		// 获取控件句柄
		spSJdanwei = (Spinner) findViewById(R.id.nctxtsjdanwei);
		tvUname = (TextView) findViewById(R.id.ncpzl_user);
		txtOther = (EditText) findViewById(R.id.nctxtother);
		txtsaddress = (EditText) findViewById(R.id.nctxtadress);
		txtsscjlproblem = (EditText) findViewById(R.id.nctxtsscjlproblem);
		txtswzjlproblem = (EditText) findViewById(R.id.nctxtswzjlproblem);
		txtsnysyproblem = (EditText) findViewById(R.id.nctxtsnysyproblem);
		txtstrpproblem = (EditText) findViewById(R.id.nctxtstrpproblem);
		txtsjgqproblem = (EditText) findViewById(R.id.nctxtsjgqproblem);
		txtsbzbsproblem = (EditText) findViewById(R.id.nctxtsbzbsproblem);
		txtsjcproblem = (EditText) findViewById(R.id.nctxtsjcproblem);
		txtstjjproblem = (EditText) findViewById(R.id.nctxtstjjproblem);
		txtwanzhengproblem = (EditText) findViewById(R.id.ncwanzhengproblem);
		txtzhengqiproblem = (EditText) findViewById(R.id.nczhengqiproblem);
		txtshixingproblem = (EditText) findViewById(R.id.ncshixingproblem);
		txtdananproblem = (EditText) findViewById(R.id.ncdananproblem);
		txtzijianproblem = (EditText) findViewById(R.id.nczijianproblem);
		txtcundangproblem = (EditText) findViewById(R.id.nccundangproblem);
		txtcunfangproblem = (EditText) findViewById(R.id.nccunfangproblem);
		txtshangshiproblem = (EditText) findViewById(R.id.ncshangshiproblem);
		txtguifanproblem = (EditText) findViewById(R.id.ncguifanproblem);
		txtzlproblem = (EditText) findViewById(R.id.nczlproblem);
		txtsjyear = (EditText) findViewById(R.id.ncfuzerenyear);
		txtsjmonth = (EditText) findViewById(R.id.ncfuzerenmonth);
		txtsjday = (EditText) findViewById(R.id.ncfuzerenday);
		txtxcyear = (EditText) findViewById(R.id.nczuzhangyear);
		txtxcmonth = (EditText) findViewById(R.id.nczuzhangmonth);
		txtxcday = (EditText) findViewById(R.id.nczuzhangday);

		// txtsaddressdes = (TextView) findViewById(R.id.nctxtsaddressdes);

		sscjlradiofinish = (RadioButton) findViewById(R.id.ncsscjlradiofinish);
		sscjlradiounfinish = (RadioButton) findViewById(R.id.ncsscjlradiounfinish);
		swzjlradiofinish = (RadioButton) findViewById(R.id.ncswzjlradiofinish);
		swzjlradiounfinish = (RadioButton) findViewById(R.id.ncswzjlradiounfinish);
		strpradiofinish = (RadioButton) findViewById(R.id.ncstrpradiofinish);
		strpradiounfinish = (RadioButton) findViewById(R.id.ncstrpradiounfinish);
		snysyradiofinish = (RadioButton) findViewById(R.id.ncsnysyradiofinish);
		snysyradiounfinish = (RadioButton) findViewById(R.id.ncsnysyradiounfinish);
		sjgqrradiofinish = (RadioButton) findViewById(R.id.ncsjgqrradiofinish);
		sjgqrradiounfinish = (RadioButton) findViewById(R.id.ncsjgqrradiounfinish);
		sbzbradiofinish = (RadioButton) findViewById(R.id.ncsbzbradiofinish);
		sbzbradiounfinish = (RadioButton) findViewById(R.id.ncsbzbradiounfinish);
		sjcradiofinish = (RadioButton) findViewById(R.id.ncsjcradiofinish);
		sjcradiounfinish = (RadioButton) findViewById(R.id.ncsjcradiounfinish);
		stjjradiofinish = (RadioButton) findViewById(R.id.ncstjjradiofinish);
		stjjradiounfinish = (RadioButton) findViewById(R.id.ncstjjradiounfinish);
		ncwanzhengradiofinish = (RadioButton) findViewById(R.id.ncwanzhengradiofinish);
		ncwanzhengradiounfinish = (RadioButton) findViewById(R.id.ncwanzhengradiounfinish);
		nczhengqiradiofinish = (RadioButton) findViewById(R.id.nczhengqiradiofinish);
		nczhengqiradiounfinish = (RadioButton) findViewById(R.id.nczhengqiradiounfinish);
		ncshixingradiofinish = (RadioButton) findViewById(R.id.ncshixingradiofinish);
		ncshixingradiounfinish = (RadioButton) findViewById(R.id.ncshixingradiounfinish);
		ncdananradiofinish = (RadioButton) findViewById(R.id.ncdananradiofinish);
		ncdananradiounfinish = (RadioButton) findViewById(R.id.ncdananradiounfinish);
		nczijianradiofinish = (RadioButton) findViewById(R.id.nczijianradiofinish);
		nczijianradiounfinish = (RadioButton) findViewById(R.id.nczijianradiounfinish);
		nccundangradiofinish = (RadioButton) findViewById(R.id.nccundangradiofinish);
		nccundangradiounfinish = (RadioButton) findViewById(R.id.nccundangradiounfinish);
		nccunfangradiofinish = (RadioButton) findViewById(R.id.nccunfangradiofinish);
		nccunfangradiounfinish = (RadioButton) findViewById(R.id.nccunfangradiounfinish);
		ncshangshiradiofinish = (RadioButton) findViewById(R.id.ncshangshiradiofinish);
		ncshangshiradiounfinish = (RadioButton) findViewById(R.id.ncshangshiradiounfinish);
		ncguifanradiofinish = (RadioButton) findViewById(R.id.ncguifanradiofinish);
		ncguifanradiounfinish = (RadioButton) findViewById(R.id.ncguifanradiounfinish);

		rgsscjlresult = (RadioGroup) findViewById(R.id.ncrgsscjlresult);
		rgswzjlresult = (RadioGroup) findViewById(R.id.ncrgswzjlresult);
		rgstrpresult = (RadioGroup) findViewById(R.id.ncrgstrpresult);
		rgsnysyresult = (RadioGroup) findViewById(R.id.ncrgsnysyresult);
		rgsjgqresult = (RadioGroup) findViewById(R.id.ncrgsjgqresult);
		rgsbzbsresult = (RadioGroup) findViewById(R.id.ncrgsbzbsresult);
		rgsjcresult = (RadioGroup) findViewById(R.id.ncrgsjcresult);
		rgstjjresult = (RadioGroup) findViewById(R.id.ncrgstjjresult);
		ncwanzhengresult = (RadioGroup) findViewById(R.id.ncwanzhengresult);
		nczhengqiresult = (RadioGroup) findViewById(R.id.nczhengqiresult);
		ncshixingresult = (RadioGroup) findViewById(R.id.ncshixingresult);
		ncdananresult = (RadioGroup) findViewById(R.id.ncdananresult);
		nczijianresult = (RadioGroup) findViewById(R.id.nczijianresult);
		nccundangresult = (RadioGroup) findViewById(R.id.nccundangresult);
		nccunfangresult = (RadioGroup) findViewById(R.id.nccunfangresult);
		ncshangshiresult = (RadioGroup) findViewById(R.id.ncshangshiresult);
		ncguifanresult = (RadioGroup) findViewById(R.id.ncguifanresult);
		
		
		imgNcssPhoto=(ImageView) findViewById(R.id.ncss_getImage);
		ImaNcssVideo=(ImageView) findViewById(R.id.ncss_getvideo);
		imgNcswPhoto=(ImageView) findViewById(R.id.ncsw_getImage);
		ImaNcswVideo=(ImageView) findViewById(R.id.ncsw_getvideo);
		imgNcstPhoto=(ImageView) findViewById(R.id.ncst_getImage);
		ImaNcstVideo=(ImageView) findViewById(R.id.ncst_getvideo);
		imgNcsnPhoto=(ImageView) findViewById(R.id.ncsn_getImage);
		ImaNcsnVideo=(ImageView) findViewById(R.id.ncsn_getvideo);
		imgNcsjPhoto=(ImageView) findViewById(R.id.ncsj_getImage);
		ImaNcsjVideo=(ImageView) findViewById(R.id.ncsj_getvideo);
		imgNcsbPhoto=(ImageView) findViewById(R.id.ncsb_getImage);
		ImaNcsbVideo=(ImageView) findViewById(R.id.ncsb_getvideo);
		imgNccrPhoto=(ImageView) findViewById(R.id.nccr_getImage);
		ImaNccrVideo=(ImageView) findViewById(R.id.nccr_getvideo);
		imgNcjjPhoto=(ImageView) findViewById(R.id.ncjj_getImage);
		ImaNcjjVideo=(ImageView) findViewById(R.id.ncjj_getvideo);
		imgNcwaPhoto=(ImageView) findViewById(R.id.ncwa_getImage);
		ImaNcwaVideo=(ImageView) findViewById(R.id.ncwa_getvideo);
		imgNczhengPhoto=(ImageView) findViewById(R.id.nczheng_getImage);
		ImaNczhengVideo=(ImageView) findViewById(R.id.nczheng_getvideo);
		imgNcshiPhoto=(ImageView) findViewById(R.id.ncshi_getImage);
		ImaNcshiVideo=(ImageView) findViewById(R.id.ncshi_getvideo);
		imgNcdanPhoto=(ImageView) findViewById(R.id.ncdan_getImage);
		ImaNcdanVideo=(ImageView) findViewById(R.id.ncdan_getvideo);
		imgNcziPhoto=(ImageView) findViewById(R.id.nczi_getImage);
		ImaNcziVideo=(ImageView) findViewById(R.id.nczi_getvideo);
		imgNcdangPhoto=(ImageView) findViewById(R.id.ncdang_getImage);
		ImaNcdangVideo=(ImageView) findViewById(R.id.ncdang_getvideo);
		imgNcfangPhoto=(ImageView) findViewById(R.id.ncfang_getImage);
		ImaNcfangVideo=(ImageView) findViewById(R.id.ncfang_getvideo);
		imgNcshangPhoto=(ImageView) findViewById(R.id.ncshang_getImage);
		ImaNcshangVideo=(ImageView) findViewById(R.id.ncshang_getvideo);
		imgNcguiPhoto=(ImageView) findViewById(R.id.ncgui_getImage);
		ImaNcguiVideo=(ImageView) findViewById(R.id.ncgui_getvideo);
		

		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
		btnaddress = (Button) findViewById(R.id.ncaddress_bt);
		btnaddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getposition();
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
		// InitLocation();
		//
		btnzp = (Button) findViewById(R.id.btnzp);
		btnzp.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				savazp();

			}

		});
		//
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

		buttonSetDate = (Button) findViewById(R.id.ncButtonSetDate);
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
				if (Nyid == null || Nyid.equals("") || Nyid.equals("0")) {
					if (txtsaddress.getText().toString() == null
							|| txtsaddress.getText().toString().equals("")) {
						Toast.makeText(NCPZLZFEditActivity.this, "地址不能为空！",
								Toast.LENGTH_SHORT).show();
					} else {
						saverecord();
					}
					// Updata(missId,phone, "正在执行");
				} else {
					updaterecord();
				}
			}

		});

		// btndeleteftrecord = (Button) findViewById(R.id.delftrecord);
		//
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
		// NCPZLZFEditActivity.this)
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
		//
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

				Intent intent = new Intent(NCPZLZFEditActivity.this,
						SpaceImageDetailActivity.class);
				// String
				// filename=Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/20151029_102917.jpg";
//				String cache=Environment
//						.getExternalStorageDirectory()
//						+ "/zfxc_wbzx/images/temp";
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

					Toast.makeText(NCPZLZFEditActivity.this, "尚未采集照片！",
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
					// Intent intent = new Intent(
					// MediaStore.Audio.Media.RECORD_SOUND_ACTION);
					// intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory()
					// + "/recording323345382.3gpp"), "audio/3gpp");

					intent.setAction(Intent.ACTION_VIEW);
					File file1 = new File(cachePathaudio + audiofilename);
					intent.setDataAndType(Uri.fromFile(file1), "audio/*");
					// intent.setDataAndType(Uri.parse("file://"+Environment.getExternalStorageDirectory()
					// + "/zfxc_wbx/audios/"+audiofilename), "audio/3gpp");
					startActivity(intent);
					// startActivityForResult(intent, 2);
				} else {

					Toast.makeText(NCPZLZFEditActivity.this, "尚未采集声音！",
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

					Toast.makeText(NCPZLZFEditActivity.this, "尚未采集视频！",
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
		AlertDialog dlg = new AlertDialog.Builder(NCPZLZFEditActivity.this)
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
										+ "/zfxc_wbzx/images/temp");

								if (!appDir.exists()) {
									appDir.mkdir();
								}
								Photofilepath = Environment
										.getExternalStorageDirectory()
										+ "/zfxc_wbx/images/temp/"
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

//	// 采集照片
//	private void savasp() {
//		// TODO Auto-generated method stub
//
//		final CharSequence[] items = { "视频", "摄像" };
//		AlertDialog dlg = new AlertDialog.Builder(NCPZLZFEditActivity.this)
//				.setTitle("选择视频")
//				.setItems(items, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int item) {
//						// 这里item是根据选择的方式，
//						// 在items数组里面定义了两种方式，拍照的下标为1所以就调用拍照方法
//						if (item == 1) {
//							try {
//
//								// Intent cameraIntent = new Intent(
//								// android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//								Intent cameraIntent = new Intent(
//										android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
//								File appDir = new File(Environment
//										.getExternalStorageDirectory()
//										+ "/zfxc_wbx/video");
//
//								if (!appDir.exists()) {
//									appDir.mkdir();
//								}
//								videofilepath = Environment
//										.getExternalStorageDirectory()
//										+ "/zfxc_wbx/video/"
//										+ String.valueOf(System
//												.currentTimeMillis()) + ".avi";
//								mUri = Uri.fromFile(new File(videofilepath));
//								cameraIntent
//										.putExtra(
//												android.provider.MediaStore.EXTRA_OUTPUT,
//												mUri);
//
//								try {
//									cameraIntent.putExtra("return-data", true);
//									startActivityForResult(cameraIntent,
//											VIDEO_CAMERA_CODE);
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//							} catch (Exception e) {
//								Log.i(TAG, "exception is " + e.toString());
//							}
//
//						} else {
//							try {
//
////								 Intent album = new
////								 Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//								Intent album = new Intent(
//										Intent.ACTION_GET_CONTENT);
//
//								album.setType("video/*");
//								album = new Intent(
//										Intent.ACTION_PICK,
//										android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//
//								//
//								// //album.addCategory(Intent.CATEGORY_OPENABLE);
//								// album.setType("image/*");
//								// album.putExtra("crop", "false");
//								//
//								// album.putExtra("aspectX", 1000);
//								// album.putExtra("aspectY", 1000);
//								// album.putExtra("outputX", 1000);
//								// album.putExtra("outputY", 1000);
//								// album.putExtra("return-data", true);
//								
//								startActivityForResult(album, VIDEO_CODE);
//							} catch (Exception e) {
//								Log.i(TAG, "2Exception is " + e.toString());
//							}
//						}
//					}
//				}).create();
//		dlg.show();
//
//	}
	// 采集照片
		private void savasp() {
			// TODO Auto-generated method stub

			final CharSequence[] items = { "视频", "摄像" };
			AlertDialog dlg = new AlertDialog.Builder(NCPZLZFEditActivity.this)
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
//									 Intent album = new  Intent(MediaStore.ACTION_VIDEO_CAPTURE);   
//									    startActivityForResult(intent, CASE_VIDEO); 
									Intent album = new Intent(
											Intent.ACTION_GET_CONTENT);
	//
									album.setType("video/*");
									album = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
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
		AlertDialog dlg = new AlertDialog.Builder(NCPZLZFEditActivity.this)
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
								startActivityForResult(album, RECORD_CODE);
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
			Toast.makeText(NCPZLZFEditActivity.this, "删除成功", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(NCPZLZFEditActivity.this, "删除失败", Toast.LENGTH_LONG)
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
		tvUname.setText(mPerson);
		try {
			// if (Nyid == null || Nyid.equals("") && jid != null) {
			// if (jid == null || jid.equals("")) {
			// d = "0";
			// } else {
			// d = jid;
			// }
			// } else if (jid == null || jid.equals("") && Nyid != null) {
			// if (Nyid == null || Nyid.equals("")) {
			// d = "0";
			// } else {
			// d = Nyid;
			// }
			// } else {
			// d = jid;
			// }
			// Cursor c = myDbHelper.select(MyDbInfo.getTableNames()[0],
			// MyDbInfo.getFieldNames()[0], null, null, null, null,
			// "TIME desc, id desc","0,9");
			if (Nyid == null || Nyid.equals("")) {
				Nyid = "0";

			}
			String sql = "select * from " + MyDbInfo.getTableNames()[12]
					+ " where Id=" + Nyid;
			c = myDbHelper.select(sql);
			if (c.getCount() == 0) {
				return;
			} else {
				c.moveToFirst();
				String result = "";
				etddate.setText(c.getString(2));
				qxList.add(c.getString(1));
				// txtscompanyname.setText(c.getString(1));
				txtOther.setText(c.getString(3));
				txtsaddress.setText(c.getString(4));
				txtsscjlproblem.setText(c.getString(6));
				txtswzjlproblem.setText(c.getString(8));
				txtstrpproblem.setText(c.getString(10));
				txtsnysyproblem.setText(c.getString(12));
				txtsjgqproblem.setText(c.getString(14));
				txtsbzbsproblem.setText(c.getString(16));
				txtsjcproblem.setText(c.getString(18));
				txtstjjproblem.setText(c.getString(20));
				txtwanzhengproblem.setText(c.getString(22));
				txtzhengqiproblem.setText(c.getString(24));
				txtshixingproblem.setText(c.getString(26));
				txtdananproblem.setText(c.getString(28));
				txtzijianproblem.setText(c.getString(30));
				txtcundangproblem.setText(c.getString(32));
				txtcunfangproblem.setText(c.getString(34));
				txtshangshiproblem.setText(c.getString(36));
				txtguifanproblem.setText(c.getString(38));
				txtzlproblem.setText(c.getString(39));
				txtsjyear.setText(c.getString(40));
				txtsjmonth.setText(c.getString(41));
				txtsjday.setText(c.getString(42));
				txtxcyear.setText(c.getString(43));
				txtxcmonth.setText(c.getString(44));
				txtxcday.setText(c.getString(45));
				this.filename = c.getString(48);
				audiofilename = c.getString(49);
				sgpsy = c.getString(c.getColumnIndex("ncsgpsy"));
				sgpsx = c.getString(c.getColumnIndex("ncgpsx"));
				videofilename = c.getString(c.getColumnIndex("ncsvideo"));
				firstvideo = videofilename;

				if (c.getString(5) == null || c.getString(5).equals("")
						|| c.getString(5).equals("null")
						|| c.getString(5).equals("是")) {

					sscjlradiounfinish.setChecked(false);
					sscjlradiofinish.setChecked(true);
				} else {
					sscjlradiounfinish.setChecked(true);
					sscjlradiofinish.setChecked(false);

				}

				if (c.getString(7) == null || c.getString(7).equals("")
						|| c.getString(7).equals("null")
						|| c.getString(7).equals("是")) {

					swzjlradiounfinish.setChecked(false);
					swzjlradiofinish.setChecked(true);
				} else {
					swzjlradiounfinish.setChecked(true);
					swzjlradiofinish.setChecked(false);

				}

				if (c.getString(9) == null || c.getString(9).equals("")
						|| c.getString(9).equals("null")
						|| c.getString(9).equals("是")) {

					strpradiounfinish.setChecked(false);
					strpradiofinish.setChecked(true);
				} else {
					strpradiounfinish.setChecked(true);
					strpradiofinish.setChecked(false);

				}

				if (c.getString(11) == null || c.getString(11).equals("")
						|| c.getString(11).equals("null")
						|| c.getString(11).equals("是")) {

					snysyradiounfinish.setChecked(false);
					snysyradiofinish.setChecked(true);
				} else {
					snysyradiounfinish.setChecked(true);
					snysyradiofinish.setChecked(false);

				}

				if (c.getString(13) == null || c.getString(13).equals("")
						|| c.getString(13).equals("null")
						|| c.getString(13).equals("是")) {

					sjgqrradiounfinish.setChecked(false);
					sjgqrradiofinish.setChecked(true);
				} else {
					sjgqrradiounfinish.setChecked(true);
					sjgqrradiofinish.setChecked(false);

				}
				if (c.getString(15) == null || c.getString(15).equals("")
						|| c.getString(15).equals("null")
						|| c.getString(15).equals("是")) {

					sbzbradiounfinish.setChecked(false);
					sbzbradiofinish.setChecked(true);
				} else {
					sbzbradiounfinish.setChecked(true);
					sbzbradiofinish.setChecked(false);

				}
				if (c.getString(17) == null || c.getString(17).equals("")
						|| c.getString(17).equals("null")
						|| c.getString(17).equals("是")) {

					sjcradiounfinish.setChecked(false);
					sjcradiofinish.setChecked(true);
				} else {
					sjcradiounfinish.setChecked(true);
					sjcradiofinish.setChecked(false);

				}
				if (c.getString(19) == null || c.getString(19).equals("")
						|| c.getString(19).equals("null")
						|| c.getString(19).equals("是")) {

					stjjradiounfinish.setChecked(false);
					stjjradiofinish.setChecked(true);
				} else {
					stjjradiounfinish.setChecked(true);
					stjjradiofinish.setChecked(false);

				}
				if (c.getString(21) == null || c.getString(21).equals("")
						|| c.getString(21).equals("null")
						|| c.getString(21).equals("是")) {

					ncwanzhengradiounfinish.setChecked(false);
					ncwanzhengradiofinish.setChecked(true);
				} else {
					ncwanzhengradiounfinish.setChecked(true);
					ncwanzhengradiofinish.setChecked(false);

				}
				if (c.getString(23) == null || c.getString(23).equals("")
						|| c.getString(23).equals("null")
						|| c.getString(23).equals("是")) {

					nczhengqiradiounfinish.setChecked(false);
					nczhengqiradiofinish.setChecked(true);
				} else {
					nczhengqiradiounfinish.setChecked(true);
					nczhengqiradiofinish.setChecked(false);

				}
				if (c.getString(25) == null || c.getString(25).equals("")
						|| c.getString(25).equals("null")
						|| c.getString(25).equals("是")) {

					ncshixingradiounfinish.setChecked(false);
					ncshixingradiofinish.setChecked(true);
				} else {
					ncshixingradiounfinish.setChecked(true);
					ncshixingradiofinish.setChecked(false);

				}
				if (c.getString(27) == null || c.getString(27).equals("")
						|| c.getString(27).equals("null")
						|| c.getString(27).equals("是")) {

					ncdananradiounfinish.setChecked(false);
					ncdananradiofinish.setChecked(true);
				} else {
					ncdananradiounfinish.setChecked(true);
					ncdananradiofinish.setChecked(false);

				}

				if (c.getString(29) == null || c.getString(29).equals("")
						|| c.getString(29).equals("null")
						|| c.getString(29).equals("是")) {

					nczijianradiounfinish.setChecked(false);
					nczijianradiofinish.setChecked(true);
				} else {
					nczijianradiounfinish.setChecked(true);
					nczijianradiofinish.setChecked(false);

				}
				if (c.getString(31) == null || c.getString(31).equals("")
						|| c.getString(31).equals("null")
						|| c.getString(31).equals("是")) {

					nccundangradiounfinish.setChecked(false);
					nccundangradiofinish.setChecked(true);
				} else {
					nccundangradiounfinish.setChecked(true);
					nccundangradiofinish.setChecked(false);

				}
				if (c.getString(33) == null || c.getString(33).equals("")
						|| c.getString(33).equals("null")
						|| c.getString(33).equals("是")) {

					nccunfangradiounfinish.setChecked(false);
					nccunfangradiofinish.setChecked(true);
				} else {
					nccunfangradiounfinish.setChecked(true);
					nccunfangradiofinish.setChecked(false);

				}
				if (c.getString(35) == null || c.getString(35).equals("")
						|| c.getString(35).equals("null")
						|| c.getString(35).equals("是")) {

					ncshangshiradiounfinish.setChecked(false);
					ncshangshiradiofinish.setChecked(true);
				} else {
					ncshangshiradiounfinish.setChecked(true);
					ncshangshiradiofinish.setChecked(false);

				}
				if (c.getString(37) == null || c.getString(37).equals("")
						|| c.getString(37).equals("null")
						|| c.getString(37).equals("是")) {

					ncguifanradiounfinish.setChecked(false);
					ncguifanradiofinish.setChecked(true);
				} else {
					ncguifanradiounfinish.setChecked(true);
					ncguifanradiofinish.setChecked(false);

				}

				// InitLocation();
				// // 绑定位置
				// sgpsy = c.getString(c.getColumnIndex("nysgpsy"));
				// sgpsx = c.getString(c.getColumnIndex("nysgpsx"));
				// LatLng point = new LatLng(23.150725, 113.257469);
				// try {
				//
				// point = new LatLng(Double.parseDouble(sgpsy),
				// Double.parseDouble(sgpsx));
				// } catch (Exception e) {
				//
				// }
				// // 构建Marker图标
				// BitmapDescriptor bitmap = BitmapDescriptorFactory
				// .fromResource(R.drawable.maker);
				// // 构建MarkerOption，用于在地图上添加Marker
				// mBaiduMap.clear();
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
			String fields[] = { "nccompany", "ncdate", "ncmembers",
					"ncaddress", "ncxnresult", "ncxnproblem", "ncdwresult",
					"ncdwproblem", "ncbcresult", "ncbcproblem", "nczyresult",
					"nczyproblem", "ncqjresult", "ncqjproblem", "ncwrresult",
					"ncwrproblem", "ncqqresult", "ncqqproblem", "ncglresult",
					"ncglproblem", "ncwzresult", "ncwzproblem", "nczqresult",
					"nczqproblem", "ncsxresult", "ncsxproblem", "ncdaresult",
					"ncdaproblem", "nczjresult", "nczjproblem", "nccdresult",
					"nccdproblem", "nccfresult", "nccfproblem", "ncssresult",
					"ncssproblem", "ncgfresult", "ncgfproblem", "nczfproblem",
					"ncsjyear", "ncsjmonth", "ncsjday", "ncxcyear",
					"ncxcmonth", "ncxcday", "ncgpsx", "ncsgpsy", "ncsimages",
					"ncsaudio", "ncsvideo" };
			String values[] = { "nccompany", "ncdate", "ncmembers",
					"ncaddress", "ncxnresult", "ncxnproblem", "ncdwresult",
					"ncdwproblem", "ncbcresult", "ncbcproblem", "nczyresult",
					"nczyproblem", "ncqjresult", "ncqjproblem", "ncwrresult",
					"ncwrproblem", "ncqqresult", "ncqqproblem", "ncglresult",
					"ncglproblem", "ncwzresult", "ncwzproblem", "nczqresult",
					"nczqproblem", "ncsxresult", "ncsxproblem", "ncdaresult",
					"ncdaproblem", "nczjresult", "nczjproblem", "nccdresult",
					"nccdproblem", "nccfresult", "nccfproblem", "ncssresult",
					"ncssproblem", "ncgfresult", "ncgfproblem", "nczfproblem",
					"ncsjyear", "ncsjmonth", "ncsjday", "ncxcyear",
					"ncxcmonth", "ncxcday", "ncgpsx", "ncsgpsy", "ncsimages",
					"ncsaudio", "ncsvideo" };
			for (int i = 0; i < values.length; i++) {
				values[i] = "";
			}
			values[0] = codeString;
			values[1] = etddate.getText().toString();
			values[2] = txtOther.getText().toString();
			values[3] = txtsaddress.getText().toString();
			values[4] = ((RadioButton) findViewById(rgsscjlresult
					.getCheckedRadioButtonId())).getText().toString();
			values[5] = txtsscjlproblem.getText().toString();

			values[6] = ((RadioButton) findViewById(rgswzjlresult
					.getCheckedRadioButtonId())).getText().toString();
			values[7] = txtswzjlproblem.getText().toString();
			values[8] = ((RadioButton) findViewById(rgstrpresult
					.getCheckedRadioButtonId())).getText().toString();
			values[9] = txtstrpproblem.getText().toString();
			values[10] = ((RadioButton) findViewById(rgsnysyresult
					.getCheckedRadioButtonId())).getText().toString();
			values[11] = txtsnysyproblem.getText().toString();
			values[12] = ((RadioButton) findViewById(rgsjgqresult
					.getCheckedRadioButtonId())).getText().toString();
			values[13] = txtsjgqproblem.getText().toString();
			values[14] = ((RadioButton) findViewById(rgsbzbsresult
					.getCheckedRadioButtonId())).getText().toString();
			values[15] = txtsbzbsproblem.getText().toString();
			values[16] = ((RadioButton) findViewById(rgsjcresult
					.getCheckedRadioButtonId())).getText().toString();
			values[17] = txtsjcproblem.getText().toString();
			values[18] = ((RadioButton) findViewById(rgstjjresult
					.getCheckedRadioButtonId())).getText().toString();
			values[19] = txtstjjproblem.getText().toString();
			values[20] = ((RadioButton) findViewById(ncwanzhengresult
					.getCheckedRadioButtonId())).getText().toString();
			values[21] = txtwanzhengproblem.getText().toString();
			values[22] = ((RadioButton) findViewById(nczhengqiresult
					.getCheckedRadioButtonId())).getText().toString();
			values[23] = txtzhengqiproblem.getText().toString();
			values[24] = ((RadioButton) findViewById(ncshixingresult
					.getCheckedRadioButtonId())).getText().toString();
			values[25] = txtshixingproblem.getText().toString();
			values[26] = ((RadioButton) findViewById(ncdananresult
					.getCheckedRadioButtonId())).getText().toString();
			values[27] = txtdananproblem.getText().toString();
			values[28] = ((RadioButton) findViewById(nczijianresult
					.getCheckedRadioButtonId())).getText().toString();
			values[29] = txtzijianproblem.getText().toString();
			values[30] = ((RadioButton) findViewById(nccundangresult
					.getCheckedRadioButtonId())).getText().toString();
			values[31] = txtcundangproblem.getText().toString();
			values[32] = ((RadioButton) findViewById(nccunfangresult
					.getCheckedRadioButtonId())).getText().toString();
			values[33] = txtcunfangproblem.getText().toString();

			values[34] = ((RadioButton) findViewById(ncshangshiresult
					.getCheckedRadioButtonId())).getText().toString();
			values[35] = txtshangshiproblem.getText().toString();
			values[36] = ((RadioButton) findViewById(ncguifanresult
					.getCheckedRadioButtonId())).getText().toString();
			values[37] = txtguifanproblem.getText().toString();
			values[38] = txtzlproblem.getText().toString();

			values[39] = txtsjyear.getText().toString();
			values[40] = txtsjmonth.getText().toString();
			values[41] = txtsjday.getText().toString();
			values[42] = txtxcyear.getText().toString();
			values[43] = txtxcmonth.getText().toString();
			values[44] = txtxcday.getText().toString();

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
			values[45] = sgpsx;
			Log.e("aaaaaa", "==========================" + sgpsx);
			values[46] = sgpsy;
			values[47] = filename;
			values[48] = audiofilename;
			values[49] = videofilename;
			String[] wherevalue = null;
			// if (Nyid == null || Nyid.equals("")) {
			// wherevalue = new String[] { jid };
			// } else if (jid == null || jid.equals("")) {
			// wherevalue = new String[] { Nyid };
			// } else {
			// wherevalue = new String[] { jid };
			// }
			wherevalue = new String[] { Nyid };
			myDbHelper.update(MyDbInfo.getTableNames()[12], fields, values,
					"id=?", wherevalue);
			myDbHelper.close();
			Toast.makeText(NCPZLZFEditActivity.this, "更新成功", Toast.LENGTH_LONG)
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
			String fields[] = { "nccompany", "ncdate", "ncmembers",
					"ncaddress", "ncxnresult", "ncxnproblem", "ncdwresult",
					"ncdwproblem", "ncbcresult", "ncbcproblem", "nczyresult",
					"nczyproblem", "ncqjresult", "ncqjproblem", "ncwrresult",
					"ncwrproblem", "ncqqresult", "ncqqproblem", "ncglresult",
					"ncglproblem", "ncwzresult", "ncwzproblem", "nczqresult",
					"nczqproblem", "ncsxresult", "ncsxproblem", "ncdaresult",
					"ncdaproblem", "nczjresult", "nczjproblem", "nccdresult",
					"nccdproblem", "nccfresult", "nccfproblem", "ncssresult",
					"ncssproblem", "ncgfresult", "ncgfproblem", "nczfproblem",
					"ncsjyear", "ncsjmonth", "ncsjday", "ncxcyear",
					"ncxcmonth", "ncxcday", "ncgpsx", "ncsgpsy", "ncsimages",
					"ncsaudio", "ncsvideo", "missionId", "missname",
					"missperson" };
			String values[] = { "nccompany", "ncdate", "ncmembers",
					"ncaddress", "ncxnresult", "ncxnproblem", "ncdwresult",
					"ncdwproblem", "ncbcresult", "ncbcproblem", "nczyresult",
					"nczyproblem", "ncqjresult", "ncqjproblem", "ncwrresult",
					"ncwrproblem", "ncqqresult", "ncqqproblem", "ncglresult",
					"ncglproblem", "ncwzresult", "ncwzproblem", "nczqresult",
					"nczqproblem", "ncsxresult", "ncsxproblem", "ncdaresult",
					"ncdaproblem", "nczjresult", "nczjproblem", "nccdresult",
					"nccdproblem", "nccfresult", "nccfproblem", "ncssresult",
					"ncssproblem", "ncgfresult", "ncgfproblem", "nczfproblem",
					"ncsjyear", "ncsjmonth", "ncsjday", "ncxcyear",
					"ncxcmonth", "ncxcday", "ncgpsx", "ncsgpsy", "ncsimages",
					"ncsaudio", "ncsvideo", "missionId", "missname",
					"missperson" };
			for (int i = 0; i < values.length; i++) {
				values[i] = ""; 
			} 
			values[0] = codeString;
			values[1] = etddate.getText().toString();
			values[2] = txtOther.getText().toString();
			values[3] = txtsaddress.getText().toString();
			values[4] = ((RadioButton) findViewById(rgsscjlresult
					.getCheckedRadioButtonId())).getText().toString();
			values[5] = txtsscjlproblem.getText().toString();

			values[6] = ((RadioButton) findViewById(rgswzjlresult
					.getCheckedRadioButtonId())).getText().toString();
			values[7] = txtswzjlproblem.getText().toString();
			values[8] = ((RadioButton) findViewById(rgstrpresult
					.getCheckedRadioButtonId())).getText().toString();
			values[9] = txtstrpproblem.getText().toString();
			values[10] = ((RadioButton) findViewById(rgsnysyresult
					.getCheckedRadioButtonId())).getText().toString();
			values[11] = txtsnysyproblem.getText().toString();
			values[12] = ((RadioButton) findViewById(rgsjgqresult
					.getCheckedRadioButtonId())).getText().toString();
			values[13] = txtsjgqproblem.getText().toString();
			values[14] = ((RadioButton) findViewById(rgsbzbsresult
					.getCheckedRadioButtonId())).getText().toString();
			values[15] = txtsbzbsproblem.getText().toString();
			values[16] = ((RadioButton) findViewById(rgsjcresult
					.getCheckedRadioButtonId())).getText().toString();
			values[17] = txtsjcproblem.getText().toString();
			values[18] = ((RadioButton) findViewById(rgstjjresult
					.getCheckedRadioButtonId())).getText().toString();
			values[19] = txtstjjproblem.getText().toString();
			values[20] = ((RadioButton) findViewById(ncwanzhengresult
					.getCheckedRadioButtonId())).getText().toString();
			values[21] = txtwanzhengproblem.getText().toString();
			values[22] = ((RadioButton) findViewById(nczhengqiresult
					.getCheckedRadioButtonId())).getText().toString();
			values[23] = txtzhengqiproblem.getText().toString();
			values[24] = ((RadioButton) findViewById(ncshixingresult
					.getCheckedRadioButtonId())).getText().toString();
			values[25] = txtshixingproblem.getText().toString();
			values[26] = ((RadioButton) findViewById(ncdananresult
					.getCheckedRadioButtonId())).getText().toString();
			values[27] = txtdananproblem.getText().toString();
			values[28] = ((RadioButton) findViewById(nczijianresult
					.getCheckedRadioButtonId())).getText().toString();
			values[29] = txtzijianproblem.getText().toString();
			values[30] = ((RadioButton) findViewById(nccundangresult
					.getCheckedRadioButtonId())).getText().toString();
			values[31] = txtcundangproblem.getText().toString();
			values[32] = ((RadioButton) findViewById(nccunfangresult
					.getCheckedRadioButtonId())).getText().toString();
			values[33] = txtcunfangproblem.getText().toString();

			values[34] = ((RadioButton) findViewById(ncshangshiresult
					.getCheckedRadioButtonId())).getText().toString();
			values[35] = txtshangshiproblem.getText().toString();
			values[36] = ((RadioButton) findViewById(ncguifanresult
					.getCheckedRadioButtonId())).getText().toString();
			values[37] = txtguifanproblem.getText().toString();
			values[38] = txtzlproblem.getText().toString();

			values[39] = txtsjyear.getText().toString();
			values[40] = txtsjmonth.getText().toString();
			values[41] = txtsjday.getText().toString();
			values[42] = txtxcyear.getText().toString();
			values[43] = txtxcmonth.getText().toString();
			values[44] = txtxcday.getText().toString();

			String filename = "";
			try {
				filename = saveimage();
			} catch (Exception e1) {

			}
			values[45] = sgpsx;
			Log.e("aaaaaa", "==========================" + sgpsx);
			values[46] = sgpsy;
			values[47] = filename;
			values[48] = audiofilename;
			values[49] = videofilename;
			values[50] = missId;
			values[51] = mName;
			values[52] = mPerson;
			myDbHelper.insert(MyDbInfo.getTableNames()[12], fields, values);
			myDbHelper.close();
			Toast.makeText(NCPZLZFEditActivity.this, "保存成功", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			Toast.makeText(NCPZLZFEditActivity.this, "保存失败", Toast.LENGTH_LONG)
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
					// bitmap = BitmapFactory.decodeFile(Photofilepath,
					// bitmapOptions);
					// 对图片进行压缩
					String f = Photofilepath;
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					// 获取这个图片的宽和高
					bitmap = BitmapFactory.decodeFile(f + "", options);// 此时返回bm为空
					options.inJustDecodeBounds = false;
					// 计算缩放比
					int be = (int) (options.outHeight / (float) 400);// 配置图片分辨率
					if (be <= 0) {
						be = 1;
					}
					options.inSampleSize = be;
					// 重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦
					bitmap = BitmapFactory.decodeFile(f + "", options);
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
			Log.e("aaaa", "==========" + bitmap.getWidth() + "========"
					+ bitmap.getHeight());
			Matrix m = new Matrix();
			m.postRotate(digree);
			DisplayMetrics dm = new DisplayMetrics();

			getWindowManager().getDefaultDisplay().getMetrics(dm);

			System.out.println("heigth : " + dm.heightPixels);

			System.out.println("width : " + dm.widthPixels);
			Log.e("aaaa", "==========" + dm.heightPixels + "========"
					+ dm.widthPixels);

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
		bm.compress(Bitmap.CompressFormat.PNG, 75, baos);
		return baos.toByteArray();
	}

	// 跳转
	public static void startActivity(Context context, String id, String nid,
			String name, String person, String qx) {
		Intent in = new Intent(context, NCPZLZFEditActivity.class);
		missId = id;
		Nyid = nid;
		mName = name;
		mPerson = person;
		county = qx;
		Log.e("aaaa", "==============" + id);
		context.startActivity(in);
	}

	private void Updata(String missId, String uname, String status) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("MissionId", missId);		
		hm.put("Mobilephone", uname);
		hm.put("Status", status);
		WebServiceJC.WorkJC("changeStatus", hm);// 更新任务状态
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
