package com.htx.pub;

import com.hetianxia.activity.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author lvan
 *
 * @todo 自定义Toast
 *
 */
public class MyToast {

	public static void toast(Context context,String text, int time) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.toast, null);
		TextView txt = (TextView) view.findViewById(R.id.toast_tv);
		txt.setText(text);
		Toast toast=new Toast(context);
//		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
		toast.setDuration(time);
		toast.setView(view);
		toast.show();
	}
}
