package com.htx.pub;

import com.baidu.mobstat.StatActivity;
import com.hetianxia.activity.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PubActivity extends StatActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getAppManager().addActivity(this);
	}

	public  void Toast(String text, int time) {

		LayoutInflater inflater = this.getLayoutInflater();
		View view = inflater.inflate(R.layout.toast, null);
		TextView txt = (TextView) view.findViewById(R.id.toast_tv);
		txt.setText(text);
		Toast toast=new Toast(this);
//		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 150);
		toast.setDuration(time);
		toast.setView(view);
		toast.show();
	}
	
}
