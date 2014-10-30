package com.htx.weixin;

import java.util.ArrayList;
import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.ApplicationUser;
import com.htx.pub.MySharedData;
import com.htx.search.SiteHelper;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 锟睫革拷锟节ｏ拷2013-2-28 17:03:35 锟斤拷锟斤拷 ListView item 锟斤拷锟斤拷锟接κэ拷埽锟�
 * 
 * @author Yichou
 * 
 */
public class PopMenu3 implements OnItemClickListener {
	public interface OnItemClickListener {
		public void onItemClick(int index);
	}

	private ArrayList<String> itemList;
	private ChatMainActivity context;
	private PopupWindow popupWindow;
	private ListView listView;
	private OnItemClickListener listener;
	private LayoutInflater inflater;
	private String StoreId;

	public PopMenu3(ChatMainActivity context, String StoreId) {
		this.context = context;
		this.StoreId = StoreId;
		itemList = new ArrayList<String>(5);

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.popmenu, null);

		listView = (ListView) view.findViewById(R.id.listView);
		listView.setAdapter(new PopAdapter());
		listView.setOnItemClickListener(this);

		popupWindow = new PopupWindow(view, context.getResources()
				.getDimensionPixelSize(R.dimen.popmenu_width),
				LayoutParams.WRAP_CONTENT);

		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (listener != null) {
			listener.onItemClick(position);
		}
		dismiss();
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	public void addItems(String[] items) {
		for (String s : items)
			itemList.add(s);
	}

	public void addItem(String item) {
		itemList.add(item);
	}

	public void showAsDropDown(View parent) {

		popupWindow.showAsDropDown(parent, 10, context.getResources()
				.getDimensionPixelSize(R.dimen.popmenu_yoff));
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}

	public void dismiss() {
		popupWindow.dismiss();
	}

	private final class PopAdapter extends BaseAdapter {

		public int getCount() {
			return itemList.size();
		}

		public Object getItem(int position) {
			return itemList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.pomenu_item, null);
				holder = new ViewHolder();
				convertView.setTag(holder);
				holder.groupItem = (TextView) convertView
						.findViewById(R.id.textView);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.groupItem.setText(itemList.get(position));
			holder.groupItem.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (itemList.get(position).equals("分享有礼")) {
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("text/plain");
						intent.putExtra(Intent.EXTRA_SUBJECT, "分享");

						String spreadUrl = MySharedData.sharedata_ReadString(
								context, "spreadUrl");
						if (spreadUrl.equals("")) {
							spreadUrl = "http://www.36936.com/UserRegisterManage/MiNiUserReg.aspx?uid="
									+ ApplicationUser.GetUserId();
						}
						String str = "亲！我刚才在"+MySharedData.sharedata_ReadString(context, "titeeeetr")+"商家消费，太意外了，省了好多钱，我还听说，好多商家都可享受呢，已有上百万人在使用，好评率98%以上，赶快免费注册安装吧!"
								+ spreadUrl;

						intent.putExtra(Intent.EXTRA_TEXT, str);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(Intent.createChooser(intent,
								context.getTitle()));
					} else if (itemList.get(position).equals("商家评论")) {
						Intent intent = new Intent(context,
								StoreComment.class);
						intent.putExtra("titleStr", "商家评论");
						intent.putExtra("StoreId", StoreId);
						context.startActivity(intent);
					} else {
						dismiss();
						getData(itemList.get(position));
					}
				}
			});
			return convertView;
		}

		private final class ViewHolder {
			TextView groupItem;
		}
	}

	/**
	 * 获取消息数据
	 * 
	 */
	public void getData(String name) {
		String user = MySharedData.sharedata_ReadString(context, "UserId");
		String hash = SiteHelper.MD5(user + Const.UrlHashKey).toLowerCase();
		String url = Const.getStoreInfo + "?userId=" + user + "&hash=" + hash
				+ "&StoreId=" + StoreId + "&Type=" + name;

		// 这里需要分析服务器回传的json格式数据，
		try {
			String reStr = HttpHelper.getJsonData(context, url);
			JSONObject reObject = new JSONObject(reStr);
			if (reObject.getInt("status") == 1) {
				context.getData(reStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
