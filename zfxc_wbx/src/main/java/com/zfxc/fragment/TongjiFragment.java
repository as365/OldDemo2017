/**
 * 
 */
package com.zfxc.fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.example.zfxc_wbx.R;
import com.zfxc.util.MyDbInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * @author dell
 *
 */
public class TongjiFragment extends Fragment{

	private View view;
	private WebView tjwebView;
	public String qxs;
	private String mqx;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.tongji_fragment, null);
		tjwebView = (WebView) view.findViewById(R.id.tj_webview);  
	        // 启用javascript  
		tjwebView.getSettings().setJavaScriptEnabled(true);  
	        // 从assets目录下面的加载html  
//		tjwebView.loadUrl("http://123.127.160.82/svmm/admin/zfxc/For_app_01.aspx"); 
		mqx=MyDbInfo.qx;
		
		switch (mqx) {
		case "市无公害中心":
			qxs="shiji";
			break;
		case "蓟州区":
			qxs="jizhou";
			break;
		case "宝坻区":
			qxs="baodi";
			break;
		case "武清区":
			qxs="wuqing";
			break;
		case "宁河区":
			qxs="ninghe";
			break;
		case "静海区":
			qxs="jinghai";
			break;
		case "东丽区":
			qxs="dongli";
			break;
		case "西青区":
			qxs="xiqing";
			break;
		case "津南区":
			qxs="jinnan";
			break;
		case "北辰区":
			qxs="beichen";
			break;
		case "滨海新区":
			qxs="binhaixinqu";
			break;
		case "滨海新区大港":
			qxs="binhaixinqudagang";
			break;
		case "滨海新区塘沽":
			qxs="binhaixinqutanggu";
			break;
		case "滨海新区汉沽":
			qxs="binhaixinquhangu";
			break;
		}
		Log.e("aaaaa","======================================="+qxs);
		tjwebView.loadUrl("http://fxcjd.wghsc.cn/admin/zfxc/For_app_01.aspx?qx="+qxs); 
//		tjwebView.loadUrl("file:///mnt/sdcard//doc-example-pie-media.html");
//		file:///mnt/sdcard/xx.txt 
//		tjwebView.addJavascriptInterface(this, "doc-example-pie-media");
//		tjwebView.loadUrl(" file:///android_asset/pie3d_01.html ");  
//		 String tpl = getFromAssets("doc-example-pie-media.html");  
//		 tjwebView.loadDataWithBaseURL(null, tpl, "text/html", "utf-8", null); 
		  DisplayMetrics dm = new DisplayMetrics();
		   //获取屏幕信息
		  getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		  
		           int screenWidth = dm.widthPixels;
		   
		           int screenHeigh = dm.heightPixels;
		           Log.e("aaaaa","==================="+screenWidth+"=========="+screenHeigh);
		return view;
	}
	/* 
	    * 获取html文件 
	    */  
	   public String getFromAssets(String fileName) {  
	       try {  
	           InputStreamReader inputReader = new InputStreamReader(  
	                   getResources().getAssets().open(fileName));  
	           BufferedReader bufReader = new BufferedReader(inputReader);  
	           String line = "";  
	           String Result = "";  
	           while ((line = bufReader.readLine()) != null)  
	               Result += line;  
	           return Result;  
	       } catch (Exception e) {  
	           e.printStackTrace();  
	       }  
	       return "";  
	   }  
//	 OnClickListener btnClickListener = new Button.OnClickListener() {  
//	        public void onClick(View v) {  
//	            // 无参数调用  
//	            contentWebView.loadUrl("javascript:javacalljs()");  
//	            // 传递参数调用  
//	            contentWebView.loadUrl("javascript:javacalljswithargs(" + "'hello world'" + ")");  
//	        }  
//	    };  
	  
//	    public void startFunction() {  
//	        Toast.makeText(this, "js调用了java函数", Toast.LENGTH_SHORT).show();  
//	        runOnUiThread(new Runnable() {  
//	  
//	            @Override  
//	            public void run() {  
//	                msgView.setText(msgView.getText() + "\njs调用了java函数");  
//	  
//	            }  
//	        });  
//	    }  
//	  
//	    public void startFunction(final String str) {  
//	        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();  
//	        runOnUiThread(new Runnable() {  
//	  
//	            @Override  
//	            public void run() {  
//	                msgView.setText(msgView.getText() + "\njs调用了java函数传递参数：" + str);  
//	  
//	            }  
//	        });  
//	    }  
}
