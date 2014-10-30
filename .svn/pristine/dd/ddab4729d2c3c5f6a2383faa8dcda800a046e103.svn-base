package com.htx.search;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.bean.AuctionListBean;

/**
 * 
 * @author lvan
 *
 */
public class DetailShopsAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<AuctionListBean> shopsList;

	private int count;

	public DetailShopsAdapter(Context c, ArrayList<AuctionListBean> list) {
		context = c;
		shopsList = list;
	}

	public int getCount() {
		if (shopsList != null) {
			count = shopsList.size();
		}
		return count;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		// inflater对象可以把xml转换为view
		LayoutInflater inflater = LayoutInflater.from(context);
		View template = inflater.inflate(R.layout.shops_view, null);

		if (shopsList != null) {

			TextView shop_name = (TextView) template
					.findViewById(R.id.shop_name);
			TextView shop_price = (TextView) template
					.findViewById(R.id.shop_pric);

			shop_name.setText(shopsList.get(position).getUserNickName());
			shop_price.setText(shopsList.get(position).getPrice() + "元");

		}

		return template;
	}
}
