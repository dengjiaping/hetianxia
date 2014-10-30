package com.htx.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.hetianxia.activity.R;
import com.htx.pub.PubActivity;

public class main_top_bt extends PubActivity {

	private LinearLayout imageView1, imageView2, imageView3;
	private Button but;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_top_right_dialog);

		imageView1 = (LinearLayout) findViewById(R.id.imageView1);
		imageView2 = (LinearLayout) findViewById(R.id.imageView2);
		imageView3 = (LinearLayout) findViewById(R.id.imageView3);
		but = (Button) findViewById(R.id.but);

		imageView1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent data = new Intent();
				// 请求代码可以自己设置，这里设置成20
				setResult(20, data);
				finish();
			}
		});
		imageView2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent data = new Intent();
				// 请求代码可以自己设置，这里设置成20
				setResult(21, data);
				finish();
			}
		});

		imageView3.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent data = new Intent();
				// 请求代码可以自己设置，这里设置成20
				setResult(22, data);
				finish();
			}
		});

		but.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent data = new Intent();
				// 请求代码可以自己设置，这里设置成20
				setResult(21, data);
				finish();
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			Intent data = new Intent();
			// 请求代码可以自己设置，这里设置成20
			setResult(21, data);
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}

}
