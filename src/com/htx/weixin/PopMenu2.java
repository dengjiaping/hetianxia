package com.htx.weixin;

import java.util.ArrayList;
import org.json.JSONObject;
import com.hetianxia.activity.R;
import com.htx.conn.Const;
import com.htx.conn.HttpHelper;
import com.htx.pub.MySharedData;
import com.htx.search.SiteHelper;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
 * 锟睫革拷锟节ｏ拷2013-2-28 17:03:35
 * 	锟斤拷锟斤拷 ListView item 锟斤拷锟斤拷锟接κэ拷埽锟�
 * 
 * @author Yichou
 *
 */
public class PopMenu2 implements OnItemClickListener {
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
	
	public PopMenu2(ChatMainActivity context, String StoreId) {
		this.context = context;
		this.StoreId = StoreId;
		itemList = new ArrayList<String>(5);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.popmenu, null);

		listView = (ListView) view.findViewById(R.id.listView);
		listView.setAdapter(new PopAdapter());
		listView.setOnItemClickListener(this);

		popupWindow = new PopupWindow(view, 
				context.getResources().getDimensionPixelSize(R.dimen.popmenu_width), 
				LayoutParams.WRAP_CONTENT);

		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
		
		popupWindow.showAsDropDown(parent, 10,
				context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));
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

		
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.pomenu_item, null);
				holder = new ViewHolder();
				convertView.setTag(holder);
				holder.groupItem = (TextView) convertView.findViewById(R.id.textView);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.groupItem.setText(itemList.get(position));

			holder.groupItem.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.e("OO", "|--> " + 1);
					getData(itemList.get(position));
					dismiss();
					dismiss();
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
				Log.e("OO", "|--> " + 2);
				context.getData(reStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
