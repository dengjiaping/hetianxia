package com.htx.weixin;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.hetianxia.activity.R;
import com.htx.pub.MyToast;
import com.htx.pub.PubActivity;

public class Uplun extends PubActivity {

	static EditText editText1;
	static EditText editText2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.u_plun);

		editText1 = (EditText) findViewById(R.id.editText1);
//		editText2 = (EditText) findViewById(R.id.editText2);

		Button button1 = (Button) findViewById(R.id.button1);
		Button bbt = (Button) findViewById(R.id.bbt);

		bbt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				MyToast.toast(Uplun.this, "提交成功,请等待审核！", 1000);
				finish();
			}
		});

		button1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
	}

}
