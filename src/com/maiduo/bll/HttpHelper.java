package com.maiduo.bll;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class HttpHelper {

	public static final    String CTWAP = "ctwap";    
    public static final    String CMWAP = "cmwap";    
    public static final    String WAP_3G = "3gwap";    
    public static final    String UNIWAP = "uniwap";    
    public static final    int TYPE_NET_WORK_DISABLED = 0;// 网络不可用    
    public static final    int TYPE_CM_CU_WAP = 4;// 移动联通wap10.0.0.172    
    public static final    int TYPE_CT_WAP = 5;// 电信wap 10.0.0.200    
    public static final    int TYPE_OTHER_NET = 6;// 电信,移动,联通,wifi 等net网络    
    public static Uri PREFERRED_APN_URI = Uri    
    .parse("content://telephony/carriers/preferapn"); 
    
    
	private static String back = null;

	public static String getJsonData(Context context, String url) {

		try {
			back = null;
			Log.e("TT", url);
			StringBuilder sb = new StringBuilder();
			// 取得取得默认的HttpClient实例
			HttpClient httpClient = new DefaultHttpClient();
			// 请求超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 4000);
			// 读取超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 4000);
			// 创建HttpGet实例
			HttpGet httpGet = new HttpGet(url);
			// 连接服务器
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Log.e("TT", "HttpStatus.SC_OK:"+httpResponse.getStatusLine().getStatusCode());
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Log.e("TT", "HttpStatus.SC_OK");
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					InputStream instream = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(instream, "UTF-8"));
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					Log.e("TT", sb+"++sb");
					back = sb.toString();
					Log.e("TT", "back:"+back);
				}
			} else {
				Toast.makeText(context, "网络不给力哦", 1000).show();
			}
		} catch (Exception e) {
//			MyToast.toast(context, "程序异常", 1000);
			Log.e("A---HttpHelper---A", e+"");
			e.printStackTrace();
		}

		Log.e("A---HttpHelper---A", back);
		return back;
	}
	
	
	public static String getJsonDataUTF_8(Context context, String url) {

		try {
			
			Log.e("TT", url);
			StringBuilder sb = new StringBuilder();
			// 取得取得默认的HttpClient实例
			HttpClient httpClient = new DefaultHttpClient();
			// 请求超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 4000);
			// 读取超时
			httpClient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 4000);
			// 创建HttpGet实例
			HttpGet httpGet = new HttpGet(url);
			// 连接服务器
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Log.e("TT", "HttpStatus.SC_OK:"+httpResponse.getStatusLine().getStatusCode());
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Log.e("TT", "HttpStatus.SC_OK");
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					InputStream instream = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(instream, "UTF-8"));
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					Log.e("TT", sb+"++sb");
					back = sb.toString();
					Log.e("TT", "back:"+back);
				}
			} else {
				Toast.makeText(context, "网络不给力哦", 1000).show();
			}
		} catch (Exception e) {
//			MyToast.toast(context, "程序异常", 1000);
			Log.e("A---HttpHelper---A", e+"");
			e.printStackTrace();
		}

		Log.e("A---HttpHelper---A", back);
		return back;
	}

	public static List<Map<String, Object>> getData(Context context,
			String url, String keys) {
		String[] keydata = keys.split(",");

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(HttpHelper.getJsonData(context, url))
					.getJSONObject("result");
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
				for (int j = 0; j < keydata.length; j++) {
					map.put(keydata[j], jsonObject2.getString(keydata[j]));
				}
				list.add(map);
			}
		} catch (Exception e) {
			Log.e("A---HttpssHelper---A", "出错");
			e.printStackTrace();
		}
		return list;
	}

	public static String getData(String url) {

		try {
			Log.e("A---HttpssHelper---A", url);
			StringBuilder sb = new StringBuilder();
			// 取得取得默认的HttpClient实例
			HttpClient httpClient = new DefaultHttpClient();
			// 创建HttpGet实例
			HttpGet httpGet = new HttpGet(url);
			// 连接服务器
			HttpResponse httpResponse = httpClient.execute(httpGet);
			// 请求成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					InputStream instream = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(instream, "UTF-8"));

					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}

					back = sb.toString();
				}
			}
		} catch (Exception e) {
			Log.e("A---HttpssHelper---A", "出错");
			e.printStackTrace();
		}

		Log.e("A---HttpHelper---A", back);
		return back;
	} 
}
