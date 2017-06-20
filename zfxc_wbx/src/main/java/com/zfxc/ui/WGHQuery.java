/**
 * 
 */
package com.zfxc.ui;

import com.example.zfxc_wbx.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

/**
 * @author dell
 *
 */
public class WGHQuery extends Activity {

	private WebView webView;
	private String url="http://www.aqsc.agri.cn/wghncp/cpcx";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_wgh_webview);
		webView=(WebView)findViewById(R.id.wgh_web);
		webView.loadUrl(url);
	}
	// 跳转
		public static void startActivity(Context context) {
			Intent in = new Intent(context, WGHQuery.class);
			context.startActivity(in);
		}
}
