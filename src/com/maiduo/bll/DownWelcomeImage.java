package com.maiduo.bll;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

public class DownWelcomeImage {

	// 获得文件名字符串
	private String fileNa = "";

	private File myCaptureFile;
	// SD卡上图片储存地址
	private final static String DOMNLOAD_PATH = Environment
			.getExternalStorageDirectory() + Config.UrlSDFolderName;

	private Bitmap bitmap;

	/**
	 * 
	 * 截取文件名称并执行下载
	 * 
	 * @param strPath
	 */
	public void downloadTheFile(final String strPath) {

		try {

			// 创建图片缓存文件夹
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
			if (sdCardExist) {
				String path = Environment.getExternalStorageDirectory()
						.getPath() + "/";

				File diror = new File(path + "maiduo");
				// 如果文件夹不存在
				if (!diror.exists()) {
					try {
						// 按照指定的路径创建文件夹
						diror.mkdir();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}

			// 获得文件文件名字符串
			fileNa = (strPath.substring(strPath.lastIndexOf(".com/") + 1,
					strPath.length()).toLowerCase()).replaceAll("/", "_");
			// 创建缓存图片
			myCaptureFile = new File(DOMNLOAD_PATH + fileNa);
			if (!myCaptureFile.exists()) {
				myCaptureFile.createNewFile();
			}

			// 执行下载
			new Thread(new Runnable() {
				public void run() {

					try {
						// 执行下载
						doDownloadTheFile(strPath);

					} catch (Exception e) {
						Log.e("aa", e.getMessage(), e);
					}
				}
			}).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 执行新版本进行下载，并保存图片到SD卡上
	 * 
	 * @param filePath
	 * 
	 * @throws Exception
	 */
	private void doDownloadTheFile(String filePath) throws Exception {

		// 判断strPath是否为网络地址
		if (!URLUtil.isNetworkUrl(filePath)) {
			Log.i("aa", "服务器地址错误！");
		} else {

			try {
				// 取得的是byte数组, 从byte数组生成bitmap
				byte[] data = getImage(filePath);

				if (data != null) {
					bitmap = BitmapFactory
							.decodeByteArray(data, 0, data.length);

				} else {
					Log.e("+++", "空");
				}

				if (bitmap != null) {
					// 保存文件
					saveFile(bitmap);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Get image from newwork
	 * 
	 * @param path
	 *            The path of image
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] getImage(String path) throws Exception {

		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(0);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return readStream(inStream);
		} else {
			return null;
		}
	}

	/**
	 * Get data from stream
	 * 
	 * @param inStream
	 * @return byte[]
	 * @throws Exception
	 */

	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}

	/**
	 * Get image from newwork
	 * 
	 * @param path
	 *            The path of image
	 * @return InputStream
	 * @throws Exception
	 */
	public InputStream getImageStream(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return conn.getInputStream();
		}
		return null;
	}

	/**
	 * 保存文件
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 */
	public void saveFile(Bitmap bm) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		System.out.println("写入完成！！！");
		bos.flush();
		bos.close();
		onDestroy();
	}

	protected void onDestroy() {
		if (bitmap != null) {
			bitmap.recycle();
			bitmap = null;
		}
	}
}
