package com.htx.pub;

import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.HttpHelper;
import com.htx.user.U_Login;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
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
public class ShowWebView extends PubActivity {
	/** Called when the activity is first created. */
	private WebView wv_content;
	private Button button1, button2;
	private String url = "";
	private LoadingDialog dialog;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_webview);

		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);

		// 取userId
		String adUserId = MySharedData.sharedata_ReadString(ShowWebView.this,
				"UserId");                                       

		if (adUserId.equals("")) {
			startActivity(new Intent(ShowWebView.this, U_Login.class));
			finish();
		}

		dialog = new LoadingDialog(ShowWebView.this);
		dialog.show();

		Intent inget = this.getIntent();
		 url =inget.getStringExtra("url");
		getData(url, adUserId);

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
					Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
					startActivity(in);
				} else {
					view.loadUrl(url);
				}

				return true;
			}

		});

		wv_content.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) { // 这里是设置activity的标题，
						dialog.dismiss();
				}
			}
		});
		
		wv_content.loadUrl(url);

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
				ShowWebView.this.finish();
			}
		});
		
		wv_content.setOnKeyListener(new View.OnKeyListener() {      
	        public boolean onKey(View v, int keyCode, KeyEvent event) {    
	            if (event.getAction() == KeyEvent.ACTION_DOWN) {    
	                if (keyCode == KeyEvent.KEYCODE_BACK && wv_content.canGoBack()) {  //表示按返回键  时的操作  
	                	wv_content.goBack();   //后退    
	                    //webview.goForward();//前进  
	                    return true;    //已处理    
	                }
	            }    
	            return false;    
	        }    
	    });
	}

	/**
	 * 添加返利链接
	 */
	private void getData(String ourl, String userid) {

		String murl = "http://api.36936.com/ClientApi/ClientGetRebatesUrl.ashx?userId="
				+ userid + "&url=" + ourl;

		// 这里需要分析服务器回传的json格式数据，
		try {
			String reStr = HttpHelper.getJsonData(ShowWebView.this, murl);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				url = reObject.getString("msg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}