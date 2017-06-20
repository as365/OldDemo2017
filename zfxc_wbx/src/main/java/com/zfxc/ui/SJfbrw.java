/**
 * 
 */
package com.zfxc.ui;

import com.example.zfxc_wbx.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * @author dell
 *
 */
public class SJfbrw extends Activity {

	private WebView webView;
	private String url="http://fxcjd.wghsc.cn";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sjfbrw);
		webView=(WebView)findViewById(R.id.sj_web);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
	}
	// 跳转
		public static void startActivity(Context context) {
			Intent in = new Intent(context, SJfbrw.class);
			context.startActivity(in);
		}
}
