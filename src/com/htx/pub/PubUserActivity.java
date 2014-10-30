package com.htx.pub;

import com.htx.user.U_Login;

import android.content.Intent;
import android.os.Bundle;

public class PubUserActivity extends PubActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getAppManager().addActivity(this);
		if (!ApplicationUser.CheckLogin(PubUserActivity.this)) {
			Intent intent1 = new Intent(PubUserActivity.this, U_Login.class);
			startActivity(intent1);
		}
	}
}