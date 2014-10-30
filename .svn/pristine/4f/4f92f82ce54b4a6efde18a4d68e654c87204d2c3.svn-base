package com.htx.user;

import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.AsyncImageLoader;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import com.htx.pub.UploadUtil;
import com.htx.search.SiteHelper;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class User_NewinfoActivity extends PubActivity implements
		OnClickListener {
	private Button bt;
	private EditText Edit_nickname, Edit_QQ, Edit_phone,Edit_name,Edit_IDc,Edit_email;
	private String[] items = new String[] { "图库", "拍照" };
	private ImageView Image_touxiang;
	private AsyncImageLoader asyImg = new AsyncImageLoader();
	private static String requestURL = "http://api.36936.com/ClientApi/ClientUploadImage.ashx";
	private String Url = "http://api.36936.com/ClientApi/ClientEditUserInfo.ashx?";
    private LinearLayout ll_info_name,ll_info_IDc;
	
	private LoadingDialog dialoga;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialoga.dismiss();
				Toast("修改完成！", 1500);
				break;

			case 2:
				dialoga.dismiss();
				Toast("网络不给力哦！", 1500);
				break;
			}
			super.handleMessage(msg);
		}
	};

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_newinfo);

		Edit_nickname = (EditText) findViewById(R.id.Edit_nickname);
		Edit_QQ = (EditText) findViewById(R.id.Edit_QQ);
		Edit_phone = (EditText) findViewById(R.id.Edit_phone);
		bt = (Button) findViewById(R.id.bt);
		Image_touxiang = (ImageView) findViewById(R.id.Image_touxiang);
		ll_info_name = (LinearLayout)findViewById(R.id.ll_info_name);
		ll_info_IDc = (LinearLayout)findViewById(R.id.ll_info_IDc);
		Edit_name = (EditText)findViewById(R.id.Edit_name);
		Edit_IDc = (EditText)findViewById(R.id.Edit_IDc);
		Edit_email = (EditText)findViewById(R.id.Edit_email);
		
		
		dialoga = new LoadingDialog(User_NewinfoActivity.this);

		// 初始化
		init();

		// 完成修改
		bt.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				dialoga.show();

				if (Edit_nickname != null && Edit_QQ != null
						&& Edit_phone != null) {

					dialoga.show();
					new Thread(new Runnable() {
						public void run() {

							upImage();

							String adUserId = MySharedData
									.sharedata_ReadString(
											User_NewinfoActivity.this, "UserId");
							String hash = SiteHelper.MD5(adUserId
									+ Const.UrlHashKey);

							String url_add = "";
							if (MySharedData.sharedata_ReadString(
									User_NewinfoActivity.this, "UU_IDc").length()>0) {
								url_add = url_add+"&idCard=";
							}
							else {
								url_add = url_add+"&idCard="+Edit_IDc.getText().toString();
							}
							if (MySharedData.sharedata_ReadString(
									User_NewinfoActivity.this, "UU_username").length()>0) {
								url_add = url_add+"&userName=";
							}
							else {
								url_add = url_add+"&userName="+Edit_name.getText().toString();
							}
							
							
							Url = Url
									+ "&userid="
									+ adUserId
									+ "&ename="
									+ Edit_nickname.getText().toString()
									+ "&qq="
									+ Edit_QQ.getText().toString()
									+ "&mobile="
									+ Edit_phone.getText().toString()
									+ "&eMail="
									+ Edit_email.getText().toString()
									+url_add
									+ "&userSex="
									+ MySharedData.sharedata_ReadString(
											User_NewinfoActivity.this,
											"UU_userSex") + "&hash=" + hash;
							
							System.out.println("___________url________"+Url);

							try {
								String reStr = HttpHelper.getJsonData(
										User_NewinfoActivity.this, Url);
								JSONObject reObject = new JSONObject(reStr);

								System.out.println("___________restr________"+reStr);
								if (reObject.getInt("status") == 1) {
									Message message = new Message();
									message.what = 1;
									mHandler.sendMessage(message);
									finish();
								} else {
									Message message = new Message();
									message.what = 2;
									mHandler.sendMessage(message);
//									Toast(reObject.getString("msg"), 1000);
								}

							} catch (Exception e) {
								Message message = new Message();
								message.what = 2;
								mHandler.sendMessage(message);
								e.printStackTrace();
							}
						}
					}).start();
				}
			}
		});

		// 根据ID找到RadioGroup
		RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup);
		final RadioButton radio0 = (RadioButton) findViewById(R.id.radio0);
		final RadioButton radio2 = (RadioButton) findViewById(R.id.radio1);
		
		String str_sex = MySharedData.sharedata_ReadString(User_NewinfoActivity.this,"UU_userSex");
		if (str_sex.equals("男")) {
			radio0.setChecked(true);
			radio2.setChecked(false);
		}
		else {
			radio0.setChecked(false);
			radio2.setChecked(true);
		}
		// 绑定一个匿名监听器
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				if (checkedId == radio0.getId()) {
					MySharedData.sharedata_WriteString(
							User_NewinfoActivity.this, "UU_userSex", "男");
				} else {
					MySharedData.sharedata_WriteString(
							User_NewinfoActivity.this, "UU_userSex", "女");
				}

			}
		});

		// 更换头像
		Image_touxiang.setOnClickListener(this);
	}

	private void init() {

		dialoga.show();
		
		Edit_nickname.setText(MySharedData.sharedata_ReadString(
				User_NewinfoActivity.this, "UU_eName"));
		Edit_QQ.setText(MySharedData.sharedata_ReadString(
				User_NewinfoActivity.this, "UU_qq"));
		Edit_phone.setText(MySharedData.sharedata_ReadString(
				User_NewinfoActivity.this, "UU_mobile"));
		Edit_name.setText(MySharedData.sharedata_ReadString(
				User_NewinfoActivity.this, "UU_username"));
		Edit_IDc.setText(MySharedData.sharedata_ReadString(
				User_NewinfoActivity.this, "UU_IDc"));
		
		String str_Edit_name = MySharedData.sharedata_ReadString(
				User_NewinfoActivity.this, "UU_username");
		String str_Edit_IDc = MySharedData.sharedata_ReadString(
				User_NewinfoActivity.this, "UU_IDc");
		if (str_Edit_name.length()>0) {
			ll_info_name.setVisibility(View.GONE);
		}
		else {
			ll_info_name.setVisibility(View.VISIBLE);
		}
		if (str_Edit_IDc.length()>0) {
			ll_info_IDc.setVisibility(View.GONE);
		}
		else {
			ll_info_IDc.setVisibility(View.VISIBLE);
		}
		Edit_email.setText(MySharedData.sharedata_ReadString(
				User_NewinfoActivity.this, "UU_email"));
		
		Image_touxiang.setBackgroundDrawable(asyImg
				.loadImageFromUrl(MySharedData.sharedata_ReadString(
						User_NewinfoActivity.this, "UU_Avatar")));
//		MySharedData.sharedata_WriteString(User_NewinfoActivity.this,
//				"userSex", "男");
		dialoga.dismiss();

	}

	/**
	 * 控件点击事件实现
	 * 
	 * 不同控件的实现， 这个地方用了两个控件，只为了自己记录学习
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Image_touxiang:
			ShowPickDialog();
			break;

		default:
			break;
		}
	}

	/**
	 * 选择提示对话框
	 */
	private void ShowPickDialog() {
		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						switch (which) {

						// 图库
						case 0:
							dialog.dismiss();
							/**
							 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
							 * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
							 */
							Intent intent1 = new Intent(Intent.ACTION_PICK,
									null);

							/**
							 * 下面这句话，与其它方式写是一样的效果，如果：
							 * intent.setData(MediaStore.Images
							 * .Media.EXTERNAL_CONTENT_URI);
							 * intent.setType("image/*");设置数据类型
							 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如
							 * ："image/jpeg 、 image/png等的类型" 这个地方有个疑问：
							 * 就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
							 */
							intent1.setDataAndType(
									MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
									"image/*");
							startActivityForResult(intent1, 1);
							break;

						// 拍照
						case 1:
							dialog.dismiss();
							/**
							 * 同上
							 */
							Intent intent2 = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 下面这句指定调用相机拍照后的照片存储的路径
							intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri
									.fromFile(new File(Environment
											.getExternalStorageDirectory(),
											"xiaoma.jpg")));
							startActivityForResult(intent2, 2);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			switch (requestCode) {
			// 如果是直接从相册获取
			case 1:
				startPhotoZoom(data.getData());
				break;
			// 如果是调用相机拍照时
			case 2:
				File temp = new File(Environment.getExternalStorageDirectory()
						+ "/xiaoma.jpg");
				startPhotoZoom(Uri.fromFile(temp));
				break;
			// 取得裁剪后的图片
			case 3:
				/**
				 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
				 * 当前功能时，会报NullException，只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
				 */
				if (data != null) {
					setPicToView(data);
				}
				break;
			default:
				break;

			}
			super.onActivityResult(requestCode, resultCode, data);
		} catch (Exception e) {
			Log.e("aa", "什么都没选！");
		}
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，不懂C C++
		 * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);

	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);

			Bitmap bmp = toRoundCorner(drawable2Bitmap(drawable), 16);

			Drawable drawable0 = bitmap2Drawable(bmp);
			Image_touxiang.setBackgroundDrawable(drawable0);

			asyImg.saveFile(drawable0,
					"http://www.baidu.com/touxiang_image.png");
			// dialoga.show();
			// new Thread(new Runnable() {
			// public void run() {
			// upImage();
			// Message message = new Message();
			// message.what = 1;
			// mHandler.sendMessage(message);
			// }
			// }).start();
		}
	}

	private void upImage() {
		String imageUrl = "http://www.baidu.com/touxiang_image.png";
		String path = Environment.getExternalStorageDirectory().getPath();
		String fileNa = imageUrl.substring(imageUrl.lastIndexOf(".com/") + 1,
				imageUrl.length()).toLowerCase();
		// 获得文件路径
		String imageSDCardPath = path + Const.SDImagePath + "/"
				+ fileNa.replaceAll("/", "_");
		File file = new File(imageSDCardPath);
		if (file != null && !file.toString().equals("")) {

			try {
				String request = UploadUtil.uploadFile(file, requestURL);
				if (request == null && file.toString().equals("")) {
					Message message = new Message();
					message.what = 1; 
					mHandler.sendMessage(message);
					return;
				}
				JSONObject reObject = new JSONObject(request);
				if (reObject.getInt("status") == 1) {
					Url += "avatar=" + reObject.getString("imgUrl");
				} else {
					Message message = new Message();
					message.what = 1;
					mHandler.sendMessage(message);
					Url += "avatar=";
					return;
				}
			} catch (JSONException e) {
				Message message = new Message();
				message.what = 2;
				mHandler.sendMessage(message);
				Url += "avatar=";
				e.printStackTrace();
				return;
			}
		} else {
			Message message = new Message();
			message.what = 2;
			mHandler.sendMessage(message);
			Url += "&avatar=";
			return;
		}
	}

	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * Bitmap转化为drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}

	/**
	 * Drawable 转 bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof NinePatchDrawable) {
			Bitmap bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		} else {
			return null;
		}
	}

}
