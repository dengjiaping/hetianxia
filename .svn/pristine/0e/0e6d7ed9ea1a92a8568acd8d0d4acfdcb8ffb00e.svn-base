package com.htx.bank;

import java.util.ArrayList;
import java.util.Map;
import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.MyToast;
import com.htx.search.SiteHelper;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ListViewAdpter extends BaseAdapter {
	public ArrayList<Map<String, String>> data; // 数据源
	private Context context;
	private float downX; // 点下时候获取的x坐标
	private float upX; // 手指离开时候的x坐标
	private Button button; // 用于执行删除的button
	private Animation animation; // 删除时候的动画
	private View view;
	private int positions;
	private LoadingDialog dialoga;
	private String string = "";
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialoga.dismiss();
				button.setVisibility(View.GONE); // 点击删除按钮后，影藏按钮
				deleteItem(view, positions); // 删除数据，加动画
				break;
			case 2:
				dialoga.dismiss();
				MyToast.toast(context, string, 1000);
				break;
			}
			super.handleMessage(msg);
		}
	};

	public ListViewAdpter(ArrayList<Map<String, String>> data, Context context) {
		this.data = data;
		this.context = context;
		animation = AnimationUtils.loadAnimation(context, R.anim.push_out); // 用xml获取一个动画
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.banklistitem, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView.findViewById(R.id.text);
			holder.textView1 = (TextView) convertView.findViewById(R.id.text1);
			holder.textView2 = (TextView) convertView.findViewById(R.id.text2);
			holder.button = (Button) convertView.findViewById(R.id.bt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnTouchListener(new OnTouchListener() { // 为每个item设置setOnTouchListener事件

					public boolean onTouch(View v, MotionEvent event) {
						final ViewHolder holder = (ViewHolder) v.getTag(); // 获取滑动时候相应的ViewHolder，以便获取button按钮
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN: // 手指按下
							downX = event.getX(); // 获取手指x坐标
							if (button != null) {
								button.setVisibility(View.GONE); // 影藏显示出来的button
							}
							break;
						case MotionEvent.ACTION_UP: // 手指离开
							upX = event.getX(); // 获取x坐标值
							break;
						}

						if (holder.button != null) {
							if (Math.abs(downX - upX) > 5) { // 2次坐标的绝对值如果大于35，就认为是左右滑动
								holder.button.setVisibility(View.VISIBLE); // 显示删除button
								button = holder.button; // 赋值给全局button，一会儿用
								view = v; // 得到itemview，在上面加动画
								return true; // 终止事件
							}
							return false; // 释放事件，使onitemClick可以执行
						}
						return false;
					}

				});

		holder.button.setOnClickListener(new OnClickListener() { // 为button绑定事件
					public void onClick(final View v) {
						if (button != null) {
							dialoga = new LoadingDialog(context);
							dialoga.show();
							new Thread(new Runnable() {
								public void run() {
									initData(v, position, data.get(position)
											.get("bankNo"));
								}
							}).start();
						}
					}
				});
		String bankName =data.get(position).get("bankName");
		if(!bankName.replaceAll(" ", "").equals("")){
			holder.textView1.setText(bankName); // 显示数据
		}else{
			holder.textView1.setText("银行卡号"); // 显示数据
		}
		
		String CardTypeName =data.get(position).get("CardTypeName");
		if(!CardTypeName.replaceAll(" ", "").equals("")){
			holder.textView2.setText("("+CardTypeName+")"); // 显示数据
		}
		holder.textView.setText(appendSeprator(
				data.get(position).get("bankNo"), " ", 4)); // 显示数据
		return convertView;
	}

	public void deleteItem(View view, final int position) {
		view.startAnimation(animation); // 给view设置动画
		animation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) { // 动画执行完毕
				data.remove(position); // 把数据源里面相应数据删除
				notifyDataSetChanged();
			}
		});
	}

	static class ViewHolder {
		TextView textView; // 显示数据的view
		TextView textView1; // 显示数据的view
		TextView textView2; // 显示数据的view
		Button button; // 删除按钮
	}

	public String appendSeprator(String srcStr, String seprator, int count) {
		StringBuffer sb = new StringBuffer(srcStr);
		int index = count;
		while (sb.length() > count && index < sb.length() - 1) {
			sb.insert(index, seprator);
			index += count + 1;
		}
		return sb.toString();
	}

	private void initData(View view, int aposition, String et) {
		et = et.replaceAll(" ", "");
		String adUserId = MySharedData.sharedata_ReadString(context, "UserId");
		String hash = SiteHelper.MD5(adUserId + Const.UrlHashKey);
		String url = "http://api.36936.com/ClientApi/Pos/ClientBindBank.ashx?"
				+ "userid="
				+ adUserId
				+ "&hash="
				+ hash
				+ "&type=unbind&BankNo=" + et;
		Message message = new Message();
		try {
			String reStr = HttpHelper.getJsonData(context, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				positions = aposition;
				message.what = 1;
			} else {
				message.what = 2;
			}
		} catch (Exception e) {
			message.what = 2;
			e.printStackTrace();
		}
		mHandler.sendMessage(message);
	}
}
