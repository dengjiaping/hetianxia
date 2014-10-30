package com.htx.pub;

import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.search.SiteHelper;

public class Integral extends PubActivity {

	/**
	 * 
	 * @param url
	 */
	public static void getIntegralData(Context context, int no, Boolean bo,
			String adUserId) {
		// String adUserId = MySharedData.sharedata_ReadString(context,
		// "UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);

		String url = "http://api.36936.com/ClientApi/ClientSendPoints.ashx?userId="
				+ adUserId + "&hash=" + hash + "&typeid=" + no;
		Log.e("AA", "##########"+url + "##########");
		try {
			String reStr = HttpHelper.getJsonData(context, url);
			JSONObject reObject = new JSONObject(reStr);

			if (reObject.getInt("status") == 1) {
				if (bo) {
					toast(context, "+" + reObject.getString("num"), 2000);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 加积分
	private static void toast(Context context, String text, int time) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.jf_add, null);
		TextView txt = (TextView) view.findViewById(R.id.toast_tv);
		txt.setText(text);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
		toast.setDuration(time);
		toast.setView(view);

		Animation shakeAnim = AnimationUtils.loadAnimation(context,
				R.anim.shake_y);
		txt.startAnimation(shakeAnim);
		toast.show();
		StartMu(context, "coins.wav");
	}

	/**
	 * 开启声音
	 */
	private static void StartMu(Context context, String mp3) {

		try {
			AssetManager assetManager = context.getAssets();
			AssetFileDescriptor fileDescriptor;
			fileDescriptor = assetManager.openFd(mp3);
			MediaPlayer mediaPlayer = new MediaPlayer();
			mediaPlayer
					.setDataSource(fileDescriptor.getFileDescriptor(),
							fileDescriptor.getStartOffset(),
							fileDescriptor.getLength());
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
