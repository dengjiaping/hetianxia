package com.maiduo.bll;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ShoppingCart {
	/**
	 * 加入购物车
	 * 
	 * @param context
	 * @param styleId
	 * @param num
	 * @param cartType
	 * @param buyType
	 * @return 0 失败 1 成功
	 */
	public static int AddCart(Context context, String userid, String styleId,
			int num, String productId) {
		int re = 0;
		/*
		 * SimpleDateFormat sDateFormat = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss:fff"); String date =
		 * sDateFormat.format(new java.util.Date()); date = date.replace(" ",
		 * "-");
		 */

		/*
		 * SimpleDateFormat sDateFormat = new
		 * SimpleDateFormat("yyyy-MM-dd,HH:mm:ss"); String date =
		 * sDateFormat.format(new java.util.Date());
		 */

		// String date = "2012";
		String url = "http://api.36936.com/ClientApi/PointsShop/cart/Add.ashx"
				+ "?userId=" + userid + "&Num=" + num + "&styleId=" + styleId
				+ "&productId=" + productId + "&hash="
				+ MD5(userid + Config.UrlHashKey2);
		// url = SiteHelper.GetSendUrl(url); // 加密网址
		try {
			String content = HttpHelper.getJsonData(context, url);
			JSONObject jsonObject = new JSONObject(content);

			if (jsonObject.getInt("status") == 0) { // 如果没有成功加入购物车
				Toast.makeText(context, jsonObject.getString("error"),
						Toast.LENGTH_SHORT).show();
			} else {
				// 成功
				re = 1;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
		}

		return re;
	}

	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

}
