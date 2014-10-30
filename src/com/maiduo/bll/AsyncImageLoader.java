package com.maiduo.bll;

/**
 * �첽����ͼƬ
 * ʹ�÷�����
 * private AsyncImageLoader asyImg = new AsyncImageLoader();
 * asyImg.LoadImage(productItems.get(position).getPic(), (ImageView)view.findViewById(R.id.pic));
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hetianxia.activity.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AsyncImageLoader {
	// Ϊ�˼ӿ��ٶȣ����ڴ��п���棨��ҪӦ�����ظ�ͼƬ�϶�ʱ������ͬһ��ͼƬҪ��α����ʣ�������ListViewʱ4�ع��
	public Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	private ExecutorService executorService = Executors.newFixedThreadPool(5); // �̶�����߳�4ִ������
	private final Handler handler = new Handler();
	// SD����ͼƬ�����ַ
	private final String path = Environment.getExternalStorageDirectory()
			.getPath() + Config.UrlSDFolderName1;

	/**
	 * 
	 * @param imageUrl
	 *            ͼ��url��ַ
	 * @param callback
	 *            �ص�ӿ�
	 * @return �����ڴ��л����ͼ�񣬵�һ�μ��ط���null
	 */
	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback callback) {
		// �����ʹӻ�����ȡ�����
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		} else if (useTheImage(imageUrl) != null) {
			return useTheImage(imageUrl);
		}
		// ������û��ͼ�����������ȡ����ݣ�����ȡ�����ݻ��浽�ڴ���
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Drawable drawable = Drawable.createFromStream(
							new URL(imageUrl).openStream(), "image.png");
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
					handler.post(new Runnable() {
						public void run() {
							callback.imageLoaded(drawable);
						}
					});
					saveFile(drawable, imageUrl, "/image/");
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		return null;
	}

	// ��������ȡ��ݷ���
	public Drawable loadImageFromUrl(String imageUrl, String dsd) {
		try {
			Drawable drawable = Drawable.createFromStream(
					new URL(imageUrl).openStream(), "image.png");
			saveFile(drawable, imageUrl, dsd);
			return drawable;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ����翪�ŵĻص�ӿ�
	public interface ImageCallback {
		// ע�� �˷�������4����Ŀ������ͼ����Դ
		public void imageLoaded(Drawable imageDrawable);
	}

	// �����̳߳أ��������ڴ滺�湦��,�����ⲿ���÷�װ�˽ӿڣ��򻯵��ù��
	public void LoadImage(final String url, final ImageView iv) {
		if (iv.getImageMatrix() == null) {
			iv.setImageResource(R.drawable.loading);
		}
		// �����ͻ�ӻ�����ȡ��ͼ��ImageCallback�ӿ��з���Ҳ���ᱻִ��
		Drawable cacheImage = loadDrawable(url,
				new AsyncImageLoader.ImageCallback() {
					// ��μ�ʵ�֣�����һ�μ���urlʱ���淽����ִ��
					public void imageLoaded(Drawable imageDrawable) {
						iv.setImageDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			iv.setImageDrawable(cacheImage);
		}
	}

	// �����̳߳أ��������ڴ滺�湦��,�����ⲿ���÷�װ�˽ӿڣ��򻯵��ù��
	public void LoadWelcomeImage(final String url, final ImageView iv) {
		if (iv.getImageMatrix() == null) {
			iv.setImageResource(R.drawable.hetianxia);
		}
		// �����ͻ�ӻ�����ȡ��ͼ��ImageCallback�ӿ��з���Ҳ���ᱻִ��
		Drawable cacheImage = loadDrawable(url,
				new AsyncImageLoader.ImageCallback() {
					// ��μ�ʵ�֣�����һ�μ���urlʱ���淽����ִ��
					public void imageLoaded(Drawable imageDrawable) {
						iv.setImageDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			iv.setImageDrawable(cacheImage);
		}
	}

	// �����̳߳أ��������ڴ滺�湦��,�����ⲿ���÷�װ�˽ӿڣ��򻯵��ù��
	public void LoadSliderImage(final String url, final ImageView iv) {
		iv.setImageResource(R.drawable.loading);
		// �����ͻ�ӻ�����ȡ��ͼ��ImageCallback�ӿ��з���Ҳ���ᱻִ��
		Drawable cacheImage = loadDrawable(url,
				new AsyncImageLoader.ImageCallback() {
					// ��μ�ʵ�֣�����һ�μ���urlʱ���淽����ִ��
					public void imageLoaded(Drawable imageDrawable) {
						iv.setImageDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			iv.setImageDrawable(cacheImage);
		}
	}

	public void LoadWelcomeBgImage(final String url, final LinearLayout iv) {
		// iv.setImageResource(R.drawable.loading);
		// �����ͻ�ӻ�����ȡ��ͼ��ImageCallback�ӿ��з���Ҳ���ᱻִ��
		Drawable cacheImage = loadDrawable(url,
				new AsyncImageLoader.ImageCallback() {
					// ��μ�ʵ�֣�����һ�μ���urlʱ���淽����ִ��
					public void imageLoaded(Drawable imageDrawable) {
						iv.setBackgroundDrawable(imageDrawable);

					}
				});
		if (cacheImage != null) {
			iv.setBackgroundDrawable(cacheImage);
		}
	}

	/**
	 * ����ͼƬ��SD����
	 * 
	 * @param dsd
	 * 
	 * @param bm
	 * @param fileName
	 * 
	 */
	public void saveFile(Drawable dw, String url, String dsd) {
		try {
			BitmapDrawable bd = (BitmapDrawable) dw;
			Bitmap bm = bd.getBitmap();

			// ����ļ�����
			String fileNa = url.substring(url.lastIndexOf(".com/") + 1,
					url.length()).toLowerCase();
			fileNa = fileNa.replaceAll("/", "_");
			File file = new File(path + dsd + fileNa);
			// ����ͼƬ�����ļ���
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
			if (sdCardExist) {
				File maiduo = new File(path);
				File image = new File(path + dsd);
				// ����ļ��в�����
				if (!maiduo.exists()) {
					// ����ָ����·�������ļ���
					maiduo.mkdir();
					// ����ļ��в�����
				} else if (!image.exists()) {
					// ����ָ����·�������ļ���
					image.mkdir();
				}
				// ���ͼƬ�Ƿ����
				if (!file.exists()) {
					file.createNewFile();
				}
			}

			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * ʹ��SD���ϵ�ͼƬ
	 * 
	 */
	public Drawable useTheImage(String imageUrl) {

		Bitmap bmpDefaultPic = null;

		String fileNa = imageUrl.substring(imageUrl.lastIndexOf(".com/") + 1,
				imageUrl.length()).toLowerCase();
		// ����ļ�·��
		String imageSDCardPath = path + "/image/" + fileNa.replaceAll("/", "_");
		File file = new File(imageSDCardPath);
		// ���ͼƬ�Ƿ����
		if (!file.exists()) {
			return null;
		}
		bmpDefaultPic = BitmapFactory.decodeFile(imageSDCardPath, null);
		if (bmpDefaultPic != null ) {
			Drawable drawable = new BitmapDrawable(bmpDefaultPic);
			return drawable;
		} else
			return null;
	}

}
