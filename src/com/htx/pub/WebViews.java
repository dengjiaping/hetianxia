package com.htx.pub;

import com.hetianxia.activity.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * 
 * @author Administrator
 * 
 *         Intent intent = new Intent(); intent.putExtra("url", adurl);
 *         intent.setClass(MyAdListDetail.this, ShowWebView.class);
 *         startActivity(intent);
 * 
 */
public class WebViews extends PubActivity {
	/** Called when the activity is first created. */
	private WebView wv;
	private Button button1, button2;
	private String url = "";
	private LoadingDialog dialog;
//	private ProgressBar pb;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webview);

//		pb = (ProgressBar)findViewById(R.id.pb);
//		pb.setMax(100);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);

		dialog = new LoadingDialog(WebViews.this); 
		dialog.show();

		Intent inget = this.getIntent();
		url = inget.getStringExtra("url");

		wv = (WebView) findViewById(R.id.content);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setDefaultTextEncodingName("utf-8");
		wv.getSettings().setBuiltInZoomControls(true);
		wv.getSettings().setDomStorageEnabled(true);
		wv.getSettings().setUseWideViewPort(true);
		wv.getSettings().setSupportZoom(true);
		wv.getSettings().setAppCacheEnabled(true);
		wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		wv.getSettings().setRenderPriority(RenderPriority.HIGH);
		

		wv.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if ((url + "....").substring(0, 4).equals("tel:")) {
					Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
					startActivity(in);
				} else {
					view.loadUrl(url);
				}

				return true;
			}

		});

		wv.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) { // 这里是设置activity的标题，
						dialog.dismiss();
		                //  WebViews.this.setProgress(newProgress * 100);  
					//pb.setProgress(newProgress);
//					if (newProgress==100) {
//						pb.setVisibility(View.GONE);
//					}
//					super.onProgressChanged(view, newProgress);
				}
			}
		});

		wv.loadUrl(url);
	
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent();
				in.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(url);
				in.setData(content_url);
				startActivity(in);
			}
		});

		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				WebViews.this.finish();
			}
		});

		wv.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) { // 表示按返回键
																				// 时的操作
						wv.goBack(); // 后退
						// webview.goForward();//前进
						return true; // 已处理
					}
				}
				return false;
			}
		});
	}
}