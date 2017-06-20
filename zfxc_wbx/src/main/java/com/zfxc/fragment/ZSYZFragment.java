package com.zfxc.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.camera2.CaptureResult;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.zfxc_wbx.R;
import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.client.android.CaptureActivity;
import com.zfxc.maputil.LocationApplication;
import com.zfxc.util.MyDbHelper;

public class ZSYZFragment extends Fragment {

	private View v;
	private Button btScanner;
	private Button btUpData;
	private static final int SCAN_CODE = 1;
	private WebView webView;
	
	private RadioButton okName;
	private RadioButton noName;
	private RadioButton okType;
	private RadioButton noType;
	private RadioButton okTime;
	private RadioButton noTime;
	private RadioButton okUnit;
	private RadioButton noUnit;
	private RadioButton okCertification;
	private RadioButton noCertification;
	private RadioButton okTrace;
	private RadioButton noTrace;
	
	
	private EditText etName;
	private EditText etType;
	private EditText etTime;
	private EditText etUnit;
	private EditText etCertification;
	private EditText etTrace;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.zsyz_fragment, null);
		webView = (WebView) v.findViewById(R.id.zsyz_webview);
		btScanner = (Button) v.findViewById(R.id.zsyz_scanner);
		btUpData=(Button) v.findViewById(R.id.zsyz_updata);
		init();
		btScanner.setOnClickListener(Lisclick);
		btUpData.setOnClickListener(Lisclick);
		okName.setOnClickListener(Lisclick);
		noName.setOnClickListener(Lisclick);
		okType.setOnClickListener(Lisclick);
		noType.setOnClickListener(Lisclick);
		okTime.setOnClickListener(Lisclick);
		noTime.setOnClickListener(Lisclick);
		okUnit.setOnClickListener(Lisclick);
		noUnit.setOnClickListener(Lisclick);
		okCertification.setOnClickListener(Lisclick);
		noCertification.setOnClickListener(Lisclick);
		okTrace.setOnClickListener(Lisclick);
		noTrace.setOnClickListener(Lisclick);
//		webView.getSettings().setJavaScriptEnabled(true);
		webView.setVisibility(webView.GONE);
		return v;
	}
	private void init(){
		okName=(RadioButton) v.findViewById(R.id.zsyz_name_ok);
		noName=(RadioButton) v.findViewById(R.id.zsyz_name_no);
		okType=(RadioButton) v.findViewById(R.id.zsyz_type_ok);
		noType=(RadioButton) v.findViewById(R.id.zsyz_type_no);
		okTime=(RadioButton) v.findViewById(R.id.zsyz_time_ok);
		noTime=(RadioButton) v.findViewById(R.id.zsyz_time_no);
		okUnit=(RadioButton) v.findViewById(R.id.zsyz_unit_ok);
		noUnit=(RadioButton) v.findViewById(R.id.zsyz_unit_no);
		okCertification=(RadioButton) v.findViewById(R.id.zsyz_certification_ok);
		noCertification=(RadioButton) v.findViewById(R.id.zsyz_certification_no);
		okTrace=(RadioButton) v.findViewById(R.id.zsyz_trace_ok);
		noTrace=(RadioButton) v.findViewById(R.id.zsyz_trace_no);
		etName=(EditText) v.findViewById(R.id.zsyz_name_et);
		etType=(EditText) v.findViewById(R.id.zsyz_type_et);
		etTime=(EditText) v.findViewById(R.id.zsyz_time_et);
		etUnit=(EditText) v.findViewById(R.id.zsyz_unit_et);
		etCertification=(EditText) v.findViewById(R.id.zsyz_certification_et);
		etTrace=(EditText) v.findViewById(R.id.zsyz_trace_et);
	}
	private OnClickListener Lisclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.zsyz_scanner:
				Intent intent = new Intent(getActivity(), CaptureActivity.class);
				startActivityForResult(intent, SCAN_CODE);
				webView.setVisibility(webView.VISIBLE);
				
				break;
			case R.id.zsyz_updata:
				
				if(TextUtils.isEmpty(etName.getText())&&noName.isChecked()==true){
					Toast.makeText(getActivity(),"产品名称不能为空",Toast.LENGTH_SHORT).show();
				}else if(TextUtils.isEmpty(etType.getText())&&noType.isChecked()==true){
					Toast.makeText(getActivity(),"产品种类不能为空",Toast.LENGTH_SHORT).show();
				}else if(TextUtils.isEmpty(etTime.getText())&&noTime.isChecked()==true){
					Toast.makeText(getActivity(),"包装时间不能为空",Toast.LENGTH_SHORT).show();
				}else if(TextUtils.isEmpty(etUnit.getText())&&noUnit.isChecked()==true){
					Toast.makeText(getActivity(),"生产单位不能为空",Toast.LENGTH_SHORT).show();
				}else if(TextUtils.isEmpty(etCertification.getText())&&noCertification.isChecked()==true){
					Toast.makeText(getActivity(),"认证类型不能为空",Toast.LENGTH_SHORT).show();
				}else if(TextUtils.isEmpty(etTrace.getText())&&noTrace.isChecked()==true){
					Toast.makeText(getActivity(),"追溯号码不能为空",Toast.LENGTH_SHORT).show();
				}else{
					Log.e("aaaaa","==================="+etName.getText().toString());
					webView.setVisibility(webView.GONE);
					webView.clearView();
					etName.setVisibility(etName.INVISIBLE);
					etType.setVisibility(etType.INVISIBLE);
					etTime.setVisibility(etTime.INVISIBLE);
					etUnit.setVisibility(etUnit.INVISIBLE);
					etCertification.setVisibility(etCertification.INVISIBLE);
					etTrace.setVisibility(etTrace.INVISIBLE);
					okName.setChecked(true);
					okType.setChecked(true);
					okTime.setChecked(true);
					okUnit.setChecked(true);
					okCertification.setChecked(true);
					okTrace.setChecked(true);
					etName.setText("");
					etType.setText("");
					etTime.setText("");
					etUnit.setText("");
					etCertification.setText("");
					etTrace.setText("");
				}
				break;
			case R.id.zsyz_name_ok:
				etName.setVisibility(etName.INVISIBLE);
			break;
			case R.id.zsyz_name_no:
				etName.setVisibility(etName.VISIBLE);
			break;
			case R.id.zsyz_type_ok:
				etType.setVisibility(etType.INVISIBLE);
				break;
			case R.id.zsyz_type_no:
				etType.setVisibility(etType.VISIBLE);
				break;
			case R.id.zsyz_time_ok:
				etTime.setVisibility(etTime.INVISIBLE);
				break;
			case R.id.zsyz_time_no:
				etTime.setVisibility(etTime.VISIBLE);
				break;
			case R.id.zsyz_unit_ok:
				etUnit.setVisibility(etUnit.INVISIBLE);
				break;
			case R.id.zsyz_unit_no:
				etUnit.setVisibility(etUnit.VISIBLE);
				break;
			case R.id.zsyz_certification_ok:
				etCertification.setVisibility(etCertification.INVISIBLE);
				break;
			case R.id.zsyz_certification_no:
				etCertification.setVisibility(etCertification.VISIBLE);
				break;
			case R.id.zsyz_trace_ok:
				etTrace.setVisibility(etTrace.INVISIBLE);
				break;
			case R.id.zsyz_trace_no:
				etTrace.setVisibility(etTrace.VISIBLE);
				break;
			default:
				break;
			}
		}
	};
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case SCAN_CODE:
			if (resultCode == -1) {
				String result = data.getStringExtra("scan_result");
				Log.e("aaaa", "===================" + result);
				webView.loadUrl(result);
				// Intent intent =new Intent();
				// intent.setAction("android.intent.action.VIEW");
				// Uri content_url =Uri.parse(result);
				// intent.setData(content_url);
				// startActivity(intent);
			} else if (resultCode == 0) {
				webView.setVisibility(webView.GONE);
				webView.clearView();
				Toast.makeText(getActivity(), "没有扫描出结果", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		default:
			break;
		}
	};
	
}
