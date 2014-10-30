package com.htx.pub;

import com.hetianxia.activity.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 加载……
 * 
 * 使用方法： 
 * 
 *  LoadingDialog dialog = new LoadingDialog(MainActivity.this);
 *  dialog.setCanceledOnTouchOutside(false); //显示
	dialog.show();
		
 * dialog.dismiss();//取消
 * 
 */
public class LoadingDialog extends Dialog {

	private TextView tv;
	
	public LoadingDialog(Context context) {
		super(context, R.style.ImageloadingDialogStyle);
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		tv =(TextView)findViewById(R.id.progressBar_tv);
		
	}

	public void tv(String text) {
		tv.setText(text);
	}
}
