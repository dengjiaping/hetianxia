package com.htx.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.hetianxia.activity.R;
import com.htx.pub.PubActivity;
import com.htx.service.UpdateServicez;

public class DownloadActivity extends PubActivity {

	private Button back_btn,download_btn;
	public void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.download);
		super.onCreate(savedInstanceState);
		init();
		onclick();
	}

	private void init()
	{
		back_btn = (Button)findViewById(R.id.download_back_btn);
		download_btn = (Button)findViewById(R.id.download_btn);
	}
	
	private void onclick()
	{
		back_btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		download_btn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						UpdateServicez.START);
				intent.putExtra("downPath",
						"http://d.36936.com/dh.apk");
				intent.putExtra("apkName", "合天下导航");
				startService(intent); // 如果先调用startService
			}
		});
		
	}
}