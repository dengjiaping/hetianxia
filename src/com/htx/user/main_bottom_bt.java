package com.htx.user;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.hetianxia.activity.R;
import com.htx.pub.PubActivity;

public class main_bottom_bt extends PubActivity {

	private LinearLayout but;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_bottom_right_dialog);

		but = (LinearLayout) findViewById(R.id.but);

		but.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		finish();

		return super.onKeyDown(keyCode, event);
	}

}
