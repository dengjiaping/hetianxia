package com.htx.action;

import org.json.JSONObject;
import com.htx.conn.HttpHelper;
import com.htx.model.UpdataInfo;
import com.htx.pub.MySharedData;

import android.content.Context;

/**
 * 
 * @author lvan
 *
 */
public class GetWelcomeSet {

	private Context context;

	public GetWelcomeSet(Context context) {
		this.context = context;
	}

	/**
	 * 升级
	 * 
	 * @param url
	 *            接口地址
	 */
	public void getImageUrlData(String url) {
		try {
			String reStr = HttpHelper.getJsonData(context, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				JSONObject object = reObject.getJSONObject("result");
				UpdataInfo.setVersionCode(object.getInt("versionCode"));
				UpdataInfo.setApkUrl(object.getString("url"));
//				UpdataInfo.setApkUrl(object.getString("url_new"));
				UpdataInfo.setDescription(object.getString("description"));
				MySharedData.sharedata_WriteString(context, "share__", object.getString("share"));
				if(object.has("tag")){
				MySharedData.sharedata_WriteString(context, "tag", object.getString("tag"));
				MySharedData.sharedata_WriteString(context, "busName", object.getString("busName"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}