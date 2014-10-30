package com.htx.weixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hetianxia.activity.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	private static Map<Integer, View> m = new HashMap<Integer, View>();

	private List<HashMap<String, String>> items;
	private LayoutInflater inflater;

	public ListViewAdapter(List<HashMap<String, String>> items, Context context) {
		super();
		this.items = items;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return items.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View contentView, ViewGroup arg2) {

		contentView = m.get(position);
//		if (contentView == null) {
			contentView = inflater.inflate(R.layout.list_item, null);
			TextView text1 = (TextView) contentView
					.findViewById(R.id.tv_1);
			TextView text2 = (TextView) contentView
					.findViewById(R.id.tv_2);
			TextView text3 = (TextView) contentView
					.findViewById(R.id.tv_3);
			text1.setText(items.get(position).get("AddTime"));
			if (items.get(position).get("UserName").length()>0) {
				text2.setText(items.get(position).get("UserName"));
			}
			else {
				text2.setText("匿名");
			}
			text3.setText(items.get(position).get("Comment"));
//		}
		m.put(position, contentView);
		return contentView;
	}

}
