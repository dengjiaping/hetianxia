package com.htx.app;

import java.util.HashMap;
import java.util.List;
import com.hetianxia.activity.R;
import com.htx.pub.AsyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author lvan
 * 
 */
public class MyAdpater extends BaseAdapter {

	private List<HashMap<String, String>> list;
	private AsyncImageLoader asyImg = new AsyncImageLoader();
	private Context context;

	public MyAdpater(Context context, List<HashMap<String, String>> list) {
		this.list = list;
		this.context = context;
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.app_main_item, null);
		}
		final TextView ItemTitle = (TextView) view.findViewById(R.id.textView);
		ItemTitle.setText(list.get(position).get("apkName"));
		final ImageView image = (ImageView) view.findViewById(R.id.imageView);
		asyImg.LoadImage(list.get(position).get("imagePath"),image);
		return view;
	}

}