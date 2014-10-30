package com.htx.pub;

/**
 * 异步加载图片
 * 使用方法：
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
import com.htx.conn.Const;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AsyncImageLoader {

	// 为了加快速度，在内存中开启缓存（主要应用于重复图片较多时，或者同一个图片要多次被访问，比如在ListView时来回滚动）
	public Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	private ExecutorService executorService = Executors.newFixedThreadPool(5); // 固定五个线程来执行任务
	private final Handler handler = new Handler();
	// SD卡上图片储存地址
	private final String path = Environment.getExternalStorageDirectory()
			.getPath();

	/**
	 * 
	 * @param imageUrl
	 *            图像url地址
	 * @param callback
	 *            回调接口
	 * @return 返回内存中缓存的图像，第一次加载返回null
	 */
	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback callback) {
		// 如果缓存过就从缓存中取出数据
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		} 
		else if (useImage(imageUrl) != null) {
			return useImage(imageUrl);
		} 
		else {
			// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
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
						saveFile(drawable, imageUrl);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
		return null;
	}

	public Drawable loadDrawable2(final String imageUrl,
			final ImageCallback callback) {
	
			// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
			executorService.submit(new Runnable() {
				public void run() {
					try {
						final Drawable drawable = Drawable.createFromStream(
								new URL(imageUrl).openStream(), "image.png");
					
						handler.post(new Runnable() {
							public void run() {
								callback.imageLoaded(drawable);
							}
						});
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
		
		return null;
	}

	
	// 从网络上取数据方法
	public Drawable loadImageFromUrl(String imageUrl) {

		try {
			Drawable drawable = Drawable.createFromStream(
					new URL(imageUrl).openStream(), "image.png");
			return drawable;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 对外界开放的回调接口
	public interface ImageCallback {
		// 注意 此方法是用来设置目标对象的图像资源
		public void imageLoaded(Drawable imageDrawable);
	}

	/**
	 * 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程
	 */
	public void LoadImage(final String url, final ImageView iv) {
		if (iv.getImageMatrix() == null) {
			iv.setImageResource(R.drawable.hetianxia);
		}
		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = loadDrawable(url,
				new AsyncImageLoader.ImageCallback() {
					// 请参见实现：如果第一次加载url时下面方法会执行
					public void imageLoaded(Drawable imageDrawable) {
						iv.setImageDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			iv.setImageDrawable(cacheImage);
		}
	}
	public void LoadImage2(final String url, final ImageView iv) {
		if (iv.getImageMatrix() == null) {
			iv.setImageResource(R.drawable.hetianxia);
		}
		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = loadDrawable2(url,
				new AsyncImageLoader.ImageCallback() {
					// 请参见实现：如果第一次加载url时下面方法会执行
					public void imageLoaded(Drawable imageDrawable) {
						iv.setImageDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			iv.setImageDrawable(cacheImage);
		}
	}
	/**
	 * 
	 * @param imageUrl
	 *            图像url地址
	 * @param callback
	 *            回调接口
	 * @return 返回内存中缓存的图像，第一次加载返回null
	 */
	public Drawable loadDrawab(final String imageUrl,
			final ImageCallback callback) {
		// 如果缓存过就从缓存中取出数据
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				return softReference.get();
			}
		}

		// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
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
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		return null;
	}

	/**
	 * 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程
	 */
	public void LoadImageff(final String url, final ImageView iv, final int wh) {

		iv.setImageResource(R.drawable.hetianxia);

		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = loadDrawab(url,
				new AsyncImageLoader.ImageCallback() {

					// 请参见实现：如果第一次加载url时下面方法会执行
					public void imageLoaded(Drawable imageDrawable) {
						iv.setImageBitmap(drawabToBitamp(imageDrawable, wh));
						return;
					}
				});
		if (cacheImage != null) {
			iv.setImageBitmap(drawabToBitamp(cacheImage, wh));
		}
	}

	private Bitmap drawabToBitamp(Drawable drawable, int wh) {

		Bitmap bitmap;

		if (drawable == null) {
			return null;
		}

		int ww = drawable.getIntrinsicWidth();
		int hh = drawable.getIntrinsicHeight();
		int w = wh;
		int h = wh;
		if (ww >= hh) {
			h = (hh * w) / ww;
		} else {
			w = (ww * h) / hh;
		}

		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		bitmap = Bitmap.createBitmap(w, h, config);
		// 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);

		return bitmap;

		// Bitmap oldbmp = drawableToBitmap(drawable); // drawable转换成bitmap
		// Matrix matrix = new Matrix(); // 创建操作图片用的Matrix对象
		// float scaleWidth = ((float) w / ww); // 计算缩放比例
		// float scaleHeight = ((float) h / hh);
		// matrix.postScale(scaleWidth, scaleHeight); // 设置缩放比例
		// Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, ww, hh,
		// matrix, true); // 建立新的bitmap，其内容是对原bitmap的缩放后的图
		// return new BitmapDrawable(newbmp); // 把bitmap转换成drawable并返回

	}

	// static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
	// {
	// int width = drawable.getIntrinsicWidth(); // 取drawable的长宽
	// int height = drawable.getIntrinsicHeight();
	// Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?
	// Bitmap.Config.ARGB_8888
	// : Bitmap.Config.RGB_565; // 取drawable的颜色格式
	// Bitmap bitmap = Bitmap.createBitmap(width, height, config); // 建立对应bitmap
	// Canvas canvas = new Canvas(bitmap); // 建立对应bitmap的画布
	// drawable.setBounds(0, 0, width, height);
	// drawable.draw(canvas); // 把drawable内容画到画布中
	// return bitmap;
	// }

	/**
	 * 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程LinearLayout
	 */
	public void LoadBackImage(final String url, final RelativeLayout iv) {

		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = loadDrawable(url,
				new AsyncImageLoader.ImageCallback() {
					// 请参见实现：如果第一次加载url时下面方法会执行
					public void imageLoaded(Drawable imageDrawable) {
						iv.setBackgroundDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			iv.setBackgroundDrawable(cacheImage);
		}
	}

	/**
	 * 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程
	 */
	public void LoadBackImage(final String url, final LinearLayout iv) {

		iv.setBackgroundResource(R.drawable.hetianxia);
		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = loadDrawable(url,
				new AsyncImageLoader.ImageCallback() {
					// 请参见实现：如果第一次加载url时下面方法会执行
					public void imageLoaded(Drawable imageDrawable) {
						iv.setBackgroundDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			iv.setBackgroundDrawable(cacheImage);
		}
	}

	/**
	 * 引入线程池，并引入内存缓存功能,并对外部调用封装了接口，简化调用过程
	 */
	public void LoadBackImage(final String url, final ImageView iv) {

		iv.setBackgroundResource(R.drawable.hetianxia);
		// 如果缓存过就会从缓存中取出图像，ImageCallback接口中方法也不会被执行
		Drawable cacheImage = loadDrawable(url,
				new AsyncImageLoader.ImageCallback() {
					// 请参见实现：如果第一次加载url时下面方法会执行
					public void imageLoaded(Drawable imageDrawable) {
						iv.setBackgroundDrawable(imageDrawable);
					}
				});
		if (cacheImage != null) {
			iv.setBackgroundDrawable(cacheImage);
		}
	}

	/**
	 * 保存图片到SD卡上
	 * 
	 * @param dsd
	 * 
	 * @param bm
	 * @param fileName
	 * 
	 */
	public void saveFile(Drawable dw, String url) {
		try {
			BitmapDrawable bd = (BitmapDrawable) dw;
			Bitmap bm = bd.getBitmap();

			// 获得文件名字
			String fileNa = url.substring(url.lastIndexOf(".com/") + 1,
					url.length()).toLowerCase();
			fileNa = fileNa.replaceAll("/", "_");
			File file = new File(path + Const.SDImagePath + "/" + fileNa);

			// 创建图片缓存文件夹
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在

			if (sdCardExist) {
				// 检查图片是否存在
				if (!file.exists()) {
					file.createNewFile();
				}
			}

			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			if (fileNa.endsWith(".jpg")) {
				bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			} else {
				bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
			}
			bos.flush();
			bos.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 使用SD卡上的图片
	 * 
	 */
	public Drawable useImage(String imageUrl) {

		Bitmap bmpDefaultPic = null;

		String fileNa = imageUrl.substring(imageUrl.lastIndexOf(".com/") + 1,
				imageUrl.length()).toLowerCase();
		// 获得文件路径
		String imageSDCardPath = path + Const.SDImagePath + "/"
				+ fileNa.replaceAll("/", "_");
		File file = new File(imageSDCardPath);
		// 检查图片是否存在
		if (!file.exists()) {
			return null;
		}
		bmpDefaultPic = BitmapFactory.decodeFile(imageSDCardPath, null);
		if (bmpDefaultPic != null) {
			Drawable drawable = new BitmapDrawable(bmpDefaultPic);
			Log.e("AA", "SDCade");
			return drawable;
		} else
			return null;
	}

}
