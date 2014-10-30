package com.icq.demo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hetianxia.activity.R;
import com.icq.demo.beans.Item;

public class ListAdapter extends BaseAdapter {
	private Context context;
	private List<Item> myList;
	private LayoutInflater mInflater;
	private int selectedPosition = -1;// 閫変腑鐨勪綅缃�
	public ListAdapter(Context context, List<Item> myList,int selected) {
		this.context = context;
		this.myList = myList;
		this.mInflater = LayoutInflater.from(this.context);
		selectedPosition=selected;
	}

	public int getCount() {
		return myList.size();
	}

	public Object getItem(int position) {
		return myList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public View getView(final int position,  View convertView, final ViewGroup parent) {
		
		 ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.block_list_item, null);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final View cView=convertView;
		final ViewHolder cHolder=holder;
		holder.iLayout=(LinearLayout) convertView.findViewById(R.id.ll_root);
		holder.name = (TextView) convertView.findViewById(R.id.tv_name);
		holder.img = (ImageView) convertView.findViewById(R.id.iv_right);
		holder.name.setText(myList.get(position).getName());
		if (selectedPosition == position) {
			holder.iLayout.setSelected(true);
			holder.iLayout.setPressed(true);
			holder.iLayout.setBackgroundColor(Color.RED);
		} else {
			holder.iLayout.setSelected(false);
			holder.iLayout.setPressed(false);
			holder.iLayout.setBackgroundColor(Color.TRANSPARENT);   

		}

		return convertView;
	}
	
	class ButtonView {
		int layoutViewId;

		ButtonView(int tId) {
			layoutViewId = tId;
		}
	}
     

	public final class ViewHolder {
		public TextView name;
		public ImageView img;
		public LinearLayout iLayout;
	}

}