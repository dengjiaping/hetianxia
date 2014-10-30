package com.example.testviewpagerandtabhost;

import com.hetianxia.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class ShowWebView extends Activity {
	/** Called when the activity is first created. */
	private WebView wv_content;
	private ProgressBar progressBar;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webview2);
		// super.InitFoot(0);
		String url = "";
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		
		Intent intent = this.getIntent();
		url = intent.getStringExtra("url");
   
		wv_content = (WebView) findViewById(R.id.content);
		wv_content.getSettings().setJavaScriptEnabled(true);
		wv_content.getSettings().setDefaultTextEncodingName("utf-8");
		wv_content.getSettings().setBuiltInZoomControls(true);
		wv_content.getSettings().setUseWideViewPort(true);
		wv_content.getSettings().setSupportZoom(true);
		wv_content.getSettings().setAppCacheEnabled(true);
		wv_content.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

		wv_content.setWebViewClient(new WebViewClient() {
			
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if ((url + "....").substring(0, 4).equals("tel:")) {

					Intent intent = new Intent(Intent.ACTION_DIAL, Uri
							.parse(url));
					startActivity(intent);
				} else {
					view.loadUrl(url);
				}

				return true;
			}
			//
			// public void onReceivedSslError(WebView view,
			// SslErrorHandler handler, android.net.http.SslError error) {
			// handler.proceed();
			// }
		});

		wv_content.setWebChromeClient(new WebChromeClient() {
			
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) { // ����������activity�ı��⣬
					// title.setText("�������")��
					progressBar.setVisibility(view.GONE);
				} else {
					// title.setText("������.......");
					progressBar.setVisibility(view.VISIBLE);
				}
			}
		});


		wv_content.loadUrl(url);

	}

	// ���û���
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_content.canGoBack()) {
			wv_content.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}