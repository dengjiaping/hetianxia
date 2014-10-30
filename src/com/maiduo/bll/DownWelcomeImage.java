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

	// ����ļ����ַ���
	private String fileNa = "";

	private File myCaptureFile;
	// SD����ͼƬ�����ַ
	private final static String DOMNLOAD_PATH = Environment
			.getExternalStorageDirectory() + Config.UrlSDFolderName;

	private Bitmap bitmap;

	/**
	 * 
	 * ��ȡ�ļ����Ʋ�ִ������
	 * 
	 * @param strPath
	 */
	public void downloadTheFile(final String strPath) {

		try {

			// ����ͼƬ�����ļ���
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
			if (sdCardExist) {
				String path = Environment.getExternalStorageDirectory()
						.getPath() + "/";

				File diror = new File(path + "maiduo");
				// ����ļ��в�����
				if (!diror.exists()) {
					try {
						// ����ָ����·�������ļ���
						diror.mkdir();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}

			// ����ļ��ļ����ַ���
			fileNa = (strPath.substring(strPath.lastIndexOf(".com/") + 1,
					strPath.length()).toLowerCase()).replaceAll("/", "_");
			// ��������ͼƬ
			myCaptureFile = new File(DOMNLOAD_PATH + fileNa);
			if (!myCaptureFile.exists()) {
				myCaptureFile.createNewFile();
			}

			// ִ������
			new Thread(new Runnable() {
				public void run() {

					try {
						// ִ������
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
	 * ִ���°汾�������أ�������ͼƬ��SD����
	 * 
	 * @param filePath
	 * 
	 * @throws Exception
	 */
	private void doDownloadTheFile(String filePath) throws Exception {

		// �ж�strPath�Ƿ�Ϊ�����ַ
		if (!URLUtil.isNetworkUrl(filePath)) {
			Log.i("aa", "��������ַ����");
		} else {

			try {
				// ȡ�õ���byte����, ��byte��������bitmap
				byte[] data = getImage(filePath);

				if (data != null) {
					bitmap = BitmapFactory
							.decodeByteArray(data, 0, data.length);

				} else {
					Log.e("+++", "��");
				}

				if (bitmap != null) {
					// �����ļ�
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
	 * �����ļ�
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 */
	public void saveFile(Bitmap bm) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
		System.out.println("д����ɣ�����");
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
