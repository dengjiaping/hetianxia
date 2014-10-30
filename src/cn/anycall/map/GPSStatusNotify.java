package cn.anycall.map;

import com.hetianxia.activity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
public class GPSStatusNotify {
	AlertDialog dialog;

	private boolean isGpsOpen(Context context) {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		boolean GPS_status = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);// 获得手机是不是设置了GPS开启状态true：gps开启，false：GPS未开启

		return GPS_status;

	}
	
	public void checkGPSStatus(Context context) {
		if (isGpsOpen(context)) {
			//if (dialog != null && dialog.isShowing()) {
			//	dialog.dismiss();
			//}
		} else {
			//showNotify(context);
			if (NearbySearchResListActivity.GPSstate.equals("0")) {
				showDownloadDialog(context);
			}
		}
	}

	/**
	 * 显示下载组件提示框
	 */
	public void showDownloadDialog(final Context context) {
		final Dialog dialog = new Dialog(context, R.style.dialog);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View alertDialogView = inflater.inflate(R.layout.superman_alertdialog,
				null);
		dialog.setContentView(alertDialogView);
		Button okButton = (Button) alertDialogView.findViewById(R.id.ok);
		Button cancelButton = (Button) alertDialogView
				.findViewById(R.id.cancel);
		TextView comeText = (TextView) alertDialogView.findViewById(R.id.title);
		comeText.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);
		comeText.setText("GPS设置");
		TextView contentText = (TextView) alertDialogView.findViewById(R.id.content);
		contentText.setText("打开GPS功能，点击“设置”按钮进入系统设置，打开GPS。");
		okButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				// 进入GPS设置页面
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				try {
					context.startActivity(intent);

				} catch (ActivityNotFoundException ex) {

					intent.setAction(Settings.ACTION_SETTINGS);
					try {
						context.startActivity(intent);

						dialog.dismiss();
					} catch (Exception e) {
					}
				}
				// String assetsApk="SpeechService.apk";
				
			}

		});
		cancelButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NearbySearchResListActivity.GPSstate = "1";
				dialog.dismiss();
			}
		});
		dialog.show();
		WindowManager windowManager = ((Activity) context).getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		dialog.getWindow().setAttributes(lp);

		return;
	}
}
