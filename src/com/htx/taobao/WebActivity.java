package com.htx.taobao;

import com.hetianxia.activity.R;
import com.htx.pub.LoadingDialog;
import com.htx.pub.PubActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends PubActivity {

	private WebView showWV;
	private String url = "";
	private LoadingDialog dialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_main);
		showWV = (WebView) findViewById(R.id.showWV);

		dialog = new LoadingDialog(WebActivity.this);
		dialog.show();

		Intent inget = this.getIntent();
		url = inget.getStringExtra("url");
		showWV.loadUrl(url);

		showWV.getSettings().setJavaScriptEnabled(true);
		showWV.requestFocus();
		showWV.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		
		showWV.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) { // 这里是设置activity的标题，
						dialog.dismiss();
				}
			}
		});
		
		showWV.setOnKeyListener(new View.OnKeyListener() {      
	        public boolean onKey(View v, int keyCode, KeyEvent event) {    
	            if (event.getAction() == KeyEvent.ACTION_DOWN) {    
	                if (keyCode == KeyEvent.KEYCODE_BACK && showWV.canGoBack()) {  //表示按返回键  时的操作  
	                	showWV.goBack();   //后退    
	                    //webview.goForward();//前进  
	                    return true;    //已处理    
	                }
	            }    
	            return false;    
	        }    
	    });
	}  
}