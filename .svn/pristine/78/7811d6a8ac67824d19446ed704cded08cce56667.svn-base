package com.htx.pub;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;

import com.htx.conn.HttpHelper;

public class Locationm {

	/**
	 * 坐标转换为地址
	 * 
	 * @param context
	 */
	public static String getDataRegeocoding(Context context, String string) {

		String url = "http://gc.ditu.aliyun.com/regeocoding?l="
				+ string;//
		try {
			String reStr = HttpHelper.getJsonDataUTF_8(context, url);
			JSONObject reObject = new JSONObject(reStr);
			if (!reObject.has("status")) {
				JSONArray reArray = reObject.getJSONArray("addrList");
				JSONObject jsonObject = (JSONObject) reArray.opt(1);
				return jsonObject.getString("admName")+"###"+jsonObject.getString("name");
			} else {
				return "河南省,郑州市,金水区||金水路文化路9号";
			}
		} catch (Exception e) {
			return "河南省,郑州市,金水区||金水路文化路9号";
		}
	}

	/**
	 * 地址转换为坐标
	 * 
	 * @param context
	 */
	public static String getDataGeocoding(Context context, String string) {

		String url = "http://gc.ditu.aliyun.com/geocoding?a=" + string;
		try {
			String reStr = HttpHelper.getJsonData(context, url);
			JSONObject reObject = new JSONObject(reStr);

			if (reObject.getInt("level") != -1) {
				return reObject.getDouble("lat")+","+reObject.getDouble("lon");
			} else {
				return "34.76803,113.66706";
			}

		} catch (Exception e) {
			return "34.76803,113.66706";
		}
	}
}
