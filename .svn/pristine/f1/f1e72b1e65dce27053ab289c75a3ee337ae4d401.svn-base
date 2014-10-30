package com.htx.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.model.stact;
import com.htx.pub.Integral;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;

public class shake_show extends PubActivity {
	private TextView textView, textView1, tt;
	private Intent intent;
	private int aw = 12;
	private String str = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.abc);

		int[] image = { R.drawable.logo1, R.drawable.logo2, R.drawable.logo3 };
		String[] st = { "福晶园蛋糕", "好想你枣业", "萧记烩面" };

		Button nu = (Button) findViewById(R.id.nu);
		ImageView imagee = (ImageView) findViewById(R.id.imagee);
		Button butt = (Button) findViewById(R.id.butt);
		LinearLayout ba = (LinearLayout) findViewById(R.id.ba);

		intent = getIntent();
		textView = (TextView) findViewById(R.id.textView);
		textView1 = (TextView) findViewById(R.id.textView1);
		tt = (TextView) findViewById(R.id.tt);

		tt.setText(intent.getExtras().getString("tt"));
		str = "     " + intent.getExtras().getString("abc");
		textView.setText(str);
		// Log.e("::::::::::::::::::::::::",
		// intent.getExtras().getString("abc"));
		// MySharedData.sharedata_WriteInt(shake_show.this, "shake_show_nw",
		// aw);
		textView.setText("     " + stact.getAbc());
		int e = MySharedData.sharedata_ReadInt(shake_show.this, "image_e");
		imagee.setImageResource(image[e % 3]);
		textView1.setText("提供商：" + st[e % 3]);
		MySharedData.sharedata_WriteInt(shake_show.this, "image_e", e + 1);

		butt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent data = new Intent(shake_show.this, A_shakeActivity.class);
				data.putExtra("tt", tt.getText().toString());
				data.putExtra("back", "back");
				startActivity(data);
				finish();
			}
		});

		nu.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String spreadUrl = MySharedData.sharedata_ReadString(
						shake_show.this, "spreadUrl");
				if (spreadUrl.equals("")) {
					String usid = MySharedData.sharedata_ReadString(
							shake_show.this, "UserId").toString();

					spreadUrl = "http://www.36936.com/UserRegisterManage/MiNiUserReg.aspx?uid="
							+ usid;
				}
				String str = intent.getExtras().getString("abc")
						+ "  好笑笑话尽在《合天下客户端》下载地址:" + spreadUrl;
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
				intent.putExtra(Intent.EXTRA_TEXT, str);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				startActivityForResult(
						Intent.createChooser(intent, getTitle()), 1001);
			}
		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME) {
			Intent data = new Intent(shake_show.this, A_shakeActivity.class);
			data.putExtra("back", "back");
			data.putExtra("tt", tt.getText().toString());
			startActivity(data);
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1001:
			// MySharedData.sharedata_WriteInt(shake_show.this, "shake_show_n",
			// aw+1);
			aw = aw + 1;
			if (aw == 13) {
				String adUserId = MySharedData.sharedata_ReadString(
						shake_show.this, "UserId");
				Integral.getIntegralData(shake_show.this, 7, true, adUserId);
			}
			break;
		}
	}
}
