package com.htx.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import com.htx.conn.Const;
import com.htx.pub.MySharedData;
import com.htx.search.SiteHelper;
import android.content.Context;
import android.util.Log;

public class GetPOIInfo {

	public static String getinfo(Context context, String query,
			String location, String radius, String pagenum) {

		String str = "";
		String init = "";
		String adUserId = MySharedData.sharedata_ReadString(context, "UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);
		// String strcon =
		// "http://api.36936.com/ClientApi/Guide/ClientSearch.ashx?"
		// + "k="
		// + query
		// + "&xy="
		// + location
		// + "&r="
		// + radius
		// + "&p="
		// + (Integer.parseInt(pagenum) + 1) + "&hash=" + hash;

		String strcon = "http://api.36936.com/ClientApi/Guide/ClientSearchHigh.ashx?k="
				+ query
				+ "&xy="
				+ location
				+ "&r="
				+ radius
				+ "&p="
				+ (Integer.parseInt(pagenum) + 1) + "&hash=" + hash;

		Log.e("TT", strcon);
		try {
			StringBuilder sb = new StringBuilder();
			// 取得取得默认的HttpClient实例
			HttpClient httpClient = new DefaultHttpClient();
			// 创建HttpGet实例
			HttpGet httpGet = new HttpGet(strcon);
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
					str = sb.toString();
				}
			}

			JSONObject reObject = new JSONObject(str);
			// 处理数据
			if (reObject.getInt("status") == 1) {
				JSONObject result = reObject.getJSONObject("result");
				init += "{\"status\":" + 1 + ",\"message\": \"ok\",\"total\":"
						+ result.getString("total") + ",\"results\":[";
				JSONArray list = result.getJSONArray("list");
				for (int i = 0; i < list.length(); i++) {
					JSONObject listitem = (JSONObject) list.opt(i);

					init += "{\"uid\":\"" + listitem.getString("uid")
							+ "\",\"address\":\""
							+ listitem.getString("address")
							+ "\",\"location\":{\"lat\":"
							+ listitem.getString("XPoint") + ",\"lng\":"
							+ listitem.getString("YPoint") + "},\"name\": \""
							+ listitem.getString("title")
							+ "\",\"telephone\": \""
							+ listitem.getString("Tel")
							+ "\",\"detail_info\":{\"distance\":"
							+ listitem.getString("Distance")
							+ ",\"price\":\"直返"
							+ listitem.getString("ReturnRatio")
							+ "%\",\"tag\":\"" + listitem.getString("brief")
							+ "\",\"image_num\":\""
							+ listitem.getString("logo")
							+ "\",\"type\":\""
							+ listitem.getString("isBaidu")
							+ "\",\"detail_url\":\""
							+ listitem.getString("haveDetail")
							+ "\",\"overall_rating\":\""
							+ listitem.getString("Star") + "\"}},";
				}

				init += "]}";
				str = init.replace("}},]}", "}}]}");

				Log.e("TT", "1------> " + str);
			} else {
				// 处理数据
				init += "{\"status\":\"" + 0
						+ ",\"message\": \"NO\",\"results\":[]}";
				str = init;
				Log.e("TT", "2------> " + str);
			}

			// JSONObject reObject = new JSONObject(str);
			// // 处理数据1
			// JSONObject object0 = reObject.getJSONObject("hotInfo");
			// if (object0.getInt("status") == 1) {
			// JSONObject object1 = object0.getJSONObject("result");
			// JSONArray object2 = object1.getJSONArray("list");
			// for (int i = 0; i < object2.length(); i++) {
			//
			// JSONObject jsonObject2 = (JSONObject) object2.opt(i);
			// init += "{\"uid\":\"" + jsonObject2.getString("id")
			// + "\",\"address\":\""
			// + jsonObject2.getString("address")
			// + "\",\"location\":{\"lng\":"
			// + jsonObject2.getString("XPoint") + ",\"lat\":"
			// + jsonObject2.getString("YPoint")
			// + "},\"name\": \"" + jsonObject2.getString("title")
			// + "\",\"telephone\": \""
			// + jsonObject2.getString("Tel")
			// + "\",\"detail_info\":{\"distance\":"
			// + jsonObject2.getString("Distance")
			// + ",\"price\":\"直返"
			// + jsonObject2.getString("ReturnRatio")
			// + "%\",\"tag\":\"" + jsonObject2.getString("brief")
			// + "\",\"image_num\":\"" + jsonObject2.getString("logo")
			// +
			// "\",\"overall_rating\":\""+jsonObject2.getString("Star")+"\"}},";
			// }
			// 处理数据2
			// JSONObject reObje0 = reObject.getJSONObject("normalInfo");
			// if (reObje0.getInt("status") == 0) {
			// String results = reObje0.getJSONArray("results").toString();
			// results =results.replace("[","");
			// results ="["+init+results;
			// str
			// ="{\"status\":0,\"message\":\"ok\",\"total\":"+reObje0.getInt("total")+",\"results\":"
			// +results+"}";
			// }
			// }else{
			// // 只处理数据2
			// str = reObject.getJSONObject("normalInfo").toString();
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}