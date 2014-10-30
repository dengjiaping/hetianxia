package com.htx.weixin;

import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.pub.PubActivity;
import com.htx.search.SiteHelper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class shop_toSpeak extends PubActivity{
	static EditText editText1;
	private LoadingDialog dialoga;
	private String string = "";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialoga.dismiss();
				MyToast.toast(shop_toSpeak.this, string,1000);
				break;
			case 2:
				dialoga.dismiss();
				MyToast.toast(shop_toSpeak.this, string,1000);
				Intent intent = new Intent(shop_toSpeak.this,
						StoreComment.class);
				intent.putExtra("content", editText1.getText().toString());
				//startActivity(intent);
				//NearbySearchActivity.instance.finish();
				setResult(4322, intent);
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_speak);

		editText1 = (EditText) findViewById(R.id.editText1);
		Button button1 = (Button) findViewById(R.id.button1);
		Button bbt = (Button) findViewById(R.id.bbt);
		dialoga = new LoadingDialog(shop_toSpeak.this);
		bbt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if(editText1.getText().toString().length()<8){
					MyToast.toast(shop_toSpeak.this, "输入文字过少(至少10个文字)", 1000);
					return;
				}
				Intent in = getIntent();
				final String StoreId = in.getStringExtra("StoreId");
				dialoga.show();
				new Thread(new Runnable() {
					public void run() {
						GetpingData(StoreId);
					}
				}).start();
			}
		});

		button1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}

	
	private void GetpingData(String StoreId) {

		String user = MySharedData.sharedata_ReadString(shop_toSpeak.this, "UserId");
		String hash = SiteHelper.MD5(user + Const.UrlHashKey).toLowerCase();
		String url ="http://api.36936.com/ClientApi/Pos/CommentStore.ashx?userId=" + user + "&hash=" + hash
				+ "&DoType=" + 0 +"&Comment=" + editText1.getText() +"&StoreId="+StoreId ;
		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(shop_toSpeak.this, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				message.what = 2;
				string ="评论成功！";
			} else {
				message.what = 1;
				string = reObject.getString("message");
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.what = 1;
			string = "网络异常！";
		}
		mHandler.sendMessage(message);

	}
}
