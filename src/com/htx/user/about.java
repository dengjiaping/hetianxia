package com.htx.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.boot.BootActivity;
import com.htx.contact.ContactsBackupsActivity;
import com.htx.main.TabTest;
import com.htx.pub.ApplicationUser;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;

public class about extends PubActivity implements View.OnClickListener {

	private RelativeLayout rl1, rl2, rl3, rl4, rl5, aa6, rl6, rl7, rl8, rl9,
			rl10,b1;
	private Button bbt;
	private TextView btn_qmsg1, btn_qmsg2;
	private int a;
	private int b;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_settings);

		btn_qmsg1 = (TextView) findViewById(R.id.btn_qmsg1);
		btn_qmsg2 = (TextView) findViewById(R.id.btn_qmsg2);
		b1 = (RelativeLayout) findViewById(R.id.b1);
		rl1 = (RelativeLayout) findViewById(R.id.rl1);
		rl2 = (RelativeLayout) findViewById(R.id.rl2);
		rl3 = (RelativeLayout) findViewById(R.id.rl3);
		rl4 = (RelativeLayout) findViewById(R.id.rl4);
		rl5 = (RelativeLayout) findViewById(R.id.rl5);
		rl6 = (RelativeLayout) findViewById(R.id.rl6);
		aa6 = (RelativeLayout) findViewById(R.id.aa6);
		rl7 = (RelativeLayout) findViewById(R.id.rl7);
		rl8 = (RelativeLayout) findViewById(R.id.rl8);
		rl9 = (RelativeLayout) findViewById(R.id.rl9);
		rl10 = (RelativeLayout) findViewById(R.id.rl10);
		bbt = (Button) findViewById(R.id.bbt);

		int aa = MySharedData.sharedata_ReadInt(about.this, "isMSN");
		int bb = MySharedData.sharedata_ReadInt(about.this, "isAd");
		if (aa == 0) {
			btn_qmsg1.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_qmsg_close));
			a=0;
		} else {
			btn_qmsg1.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_qmsg_open));
			a=1;
		}
		if (bb == 0) {
			b=0;
			btn_qmsg2.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_qmsg_close));
		} else {
			btn_qmsg2.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.btn_qmsg_open));
			b=1;
		}

		rl1.setOnClickListener(this);
		rl1.setTag(1);
		rl2.setOnClickListener(this);
		rl2.setTag(2);
		rl3.setOnClickListener(this);
		rl3.setTag(3);
		rl4.setOnClickListener(this);
		rl4.setTag(4);
		rl5.setOnClickListener(this);
		rl5.setTag(5);
		rl6.setOnClickListener(this);
		rl6.setTag(6);
		aa6.setOnClickListener(this);
		aa6.setTag(12);
		rl7.setOnClickListener(this);
		rl7.setTag(7);
		rl8.setOnClickListener(this);
		rl8.setTag(8);
		rl9.setOnClickListener(this);
		rl9.setTag(9);
		rl10.setOnClickListener(this);
		rl10.setTag(10);
		bbt.setOnClickListener(this);
		bbt.setTag(11);
		b1.setOnClickListener(this);
		b1.setTag(13);
		
	}

	@SuppressWarnings("deprecation")
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case 1:
			startActivity(new Intent(about.this, U_info.class));
			break;
		case 2:
			startActivity(new Intent(about.this, A_shakeActivity.class));
			break;
		case 3:
			// startActivity(new Intent(about.this, CaptureActivity.class));
			break;
		case 4:
			if (a == 0) {
				btn_qmsg1.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_qmsg_open));
				MySharedData.sharedata_WriteInt(about.this, "isMSN", 1);
				a=1;
			} else if (a == 1) {
				btn_qmsg1.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_qmsg_close));
				MySharedData.sharedata_WriteInt(about.this, "isMSN", 0);
				a=0;
			}
			break;
		case 5:
			if (b == 0) {
				btn_qmsg2.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_qmsg_open));
				MySharedData.sharedata_WriteInt(about.this, "isAd", 1);
				b=1;
			} else if (b == 1) {
				btn_qmsg2.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.btn_qmsg_close));
				MySharedData.sharedata_WriteInt(about.this, "isAd", 0);
				b=0;
			}
			break;
		case 6:
			Intent intent = new Intent(new Intent(about.this,
					BootActivity.class));
			intent.putExtra("110", "110");
			startActivity(intent);
			finish();
			break;
		case 7:
			Intent in = new Intent();
			in.setAction("android.intent.action.VIEW");
			Uri content_url = Uri
					.parse("http://api.36936.com/UserRegisterManage/MiNiHelps.aspx");
			in.setData(content_url);
			startActivity(in);
			break;
		case 8:
			startActivity(new Intent(about.this, U_toSpeak.class));
			break;
		case 9:
			startActivity(new Intent(about.this, Aboat_.class));
			break;
		case 10:
			Toast("已清除缓存信息", 1000);
			break;
		case 11:
			ApplicationUser.SetUserId(null);
			ApplicationUser.SetUserName("");
			MySharedData.sharedata_WriteString(about.this, "userType", "");
			MySharedData.sharedata_WriteString(about.this, "loginName", "");
			MySharedData.sharedata_WriteString(about.this, "passWord", "");
			MySharedData.sharedata_WriteString(about.this, "userName", "");
			MySharedData.sharedata_WriteString(about.this, "UserId", "");
			startActivity(new Intent(about.this, TabTest.class));
			finish();
			break;
		case 12:
		case 13:
			startActivity(new Intent(about.this, ContactsBackupsActivity.class));
			break;
		}
	}
}
