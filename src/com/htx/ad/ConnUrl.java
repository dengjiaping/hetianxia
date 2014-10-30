package com.htx.ad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

//import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.conn.IsNetwork;
import com.htx.pub.AsyncImageLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

/**
 * 
 * @author lvan
 * 
 */
public class ConnUrl {

	/**
	 * 获取广告图片
	 * 
	 * @param url
	 */
	public static void getData(Context context, String url) {

		try {
			String reStr = HttpHelper.getJsonData(context, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 0) {
				return;
			}
			if (reObject.getInt("status") == 1) {
				JSONArray jsonArray = reObject.getJSONArray("list");
				if (!jsonArray.equals(null)) {
					for (int i = 0; i < jsonArray.length(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						JSONObject js = jsonArray.getJSONObject(i);
						map.put("ad1", js.getString("ad1"));
						map.put("ad2", js.getString("ad2"));

						useTheImage(context, js.getString("ad1"));
						useTheImage(context, js.getString("ad2"));

					}

					String readString = readFile();
					String imageUrlsList = null;
					if (readString != null) {
						readString = readString.replaceAll(" ", "");
						imageUrlsList = reStr.toString().replaceAll(" ", "");
					}
					if (readString == null || !readString.equals(imageUrlsList)) {
						saveName(reStr);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 存储数据到文件
	public static void saveName(String string) throws Exception {

		// 创建缓存文件
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + Const.SDFilePath + "/", "adImageUrls.txt");

		// 创建图片缓存文件夹
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (!sdCardExist) {
			try {
				// 如果文件不存在
				if (!file.exists()) {
					file.createNewFile();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(string.getBytes());
		outputStream.close();
		System.out.println("写入广告文件成功！");
	}

	/** 读取文件 */
	public static String readFile() {

		String filecontent = null;
		// SD卡上文件储存地址
		String DOMNLOAD_PATH = Environment.getExternalStorageDirectory()
				.getAbsolutePath();

		String path = DOMNLOAD_PATH + Const.SDFilePath + "/adImageUrls.txt";
		// 创建缓存文件
		File file = new File(path);

		// 创建图片缓存文件夹
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			// 如果文件夹不存在
			if (file != null && file.exists()) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException e1) {
					Log.d("", "Error: Input File not find!");
					return null;
				}

				CharBuffer cb = null;
				try {
					cb = CharBuffer.allocate(fis.available());
				} catch (IOException e1) {
					Log.d("", "Error: CharBuffer initial failed!");
					return null;
				}

				InputStreamReader isr = null;
				try {
					isr = new InputStreamReader(fis, "utf-8");
					try {
						if (cb != null) {
							isr.read(cb);
						}
						filecontent = new String(cb.array());
						isr.close();
						return filecontent;
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	/**
	 * 使用SD卡上的图片
	 * 
	 * iV : 要显示图片的ImageView
	 */
	public static Bitmap useTheImage(Context context, String imageUrl) {
	
		AsyncImageLoader asyImg;

		if (IsNetwork.isNetworkEnabled(context) == 2) {
			return null;
		}
		System.out.println("IIIIIIIIIII" + imageUrl);
		Bitmap bmpDefaultPic = null;

		if (imageUrl != null) {
			// 判断strPath是否为网络地址
			if (!URLUtil.isNetworkUrl(imageUrl)) {
				return null;
			}
			String imaNa = imageUrl.substring(
					imageUrl.lastIndexOf(".com/") + 1, imageUrl.length())
					.toLowerCase();
			imaNa = imaNa.replaceAll("/", "_");

			// 获得文件路径
			String imageSDCardPath = Environment.getExternalStorageDirectory()
					+ Const.SDImagePath + "/" + imaNa;
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;
			bmpDefaultPic = BitmapFactory.decodeFile(imageSDCardPath,options);
			
			//bmpDefaultPic = BitmapFactory.decodeFile(imageSDCardPath, null);
			if (bmpDefaultPic == null) {
				asyImg = new AsyncImageLoader();
				Drawable draw = asyImg.loadImageFromUrl(imageUrl);
				asyImg.saveFile(draw, imageUrl);
				BitmapDrawable bd = (BitmapDrawable) draw;
				if (bd == null) {
					return null;
				}
				bmpDefaultPic = bd.getBitmap();
				
//			走流————————		
//					ContentResolver cr = context.getContentResolver();
//					try {
//						bmpDefaultPic = BitmapFactory.decodeStream(cr.openInputStream(Uri.parse(imageUrl)), null, options);
//					} catch (FileNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
			}
		}
		return bmpDefaultPic;

	}
}