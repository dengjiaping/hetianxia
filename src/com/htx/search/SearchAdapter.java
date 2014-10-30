package com.htx.search;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.model.ProductListBean;

/**
 * 
 * @author lvan
 *
 */
public class SearchAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<ProductListBean> searchList;

	private HashMap<String, Bitmap> bitmap_Map = new HashMap<String, Bitmap>();

	public SearchAdapter(Context c, ArrayList<ProductListBean> _searchList) {
		context = c;
		searchList = _searchList;
	}

	public void putBitmap(String id, Bitmap bitmap) {
		bitmap_Map.put(id, bitmap);
	}

	public int getCount() {
		int size = 0;
		if (searchList != null) {
			size = searchList.size();
		}
		return size;
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
		View template = inflater.inflate(R.layout.search_view, null);

		if (searchList != null) {

			ImageView image  =(ImageView)template
					.findViewById(R.id.image);
			TextView productName = (TextView) template
					.findViewById(R.id.productName);
			TextView productMoney = (TextView) template
					.findViewById(R.id.productMoney);
			TextView sellCount = (TextView) template
					.findViewById(R.id.sellCount);
			TextView tkCount = (TextView) template.findViewById(R.id.tkCount);
			TextView yunfei = (TextView) template.findViewById(R.id.yunfei);
			LinearLayout yunfei_ll = (LinearLayout) template
					.findViewById(R.id.yunfei_ll);

			productName.setText(searchList.get(position).getTitle());
			//productMoney.setText(searchList.get(position).getPrice() + "元");
			productMoney.setText(searchList.get(position).getPrice() + "元");

			if (searchList.get(position).getBbCount() == "###") {

				if (searchList.get(position).getYunfei()!=null) {
					if (!searchList.get(position).getYunfei().toString()
							.equals("")) {
						yunfei_ll.setVisibility(View.VISIBLE);
						yunfei.setText(searchList.get(position).getYunfei());
					}
				}
				sellCount.setText("售出"
						+ searchList.get(position).getSellerCount() + "笔");
			} else if(!searchList.get(position).getSellerCount().equals("")&&searchList.get(position).getSellerCount()!=null){
//				sellCount.setText(searchList.get(position).getSellerCount()
//						+ "家有售");
				sellCount.setText("多家有售");
			}else{
				sellCount.setVisibility(View.GONE);
				image.setVisibility(View.VISIBLE);
			}
			
			if(searchList.get(position).getPropListStr()!=null&&!searchList.get(position).getPropListStr().equals("")){
			tkCount.setText(searchList.get(position).getPropListStr());
			}else{
				tkCount.setVisibility(View.GONE);
			}

			Bitmap t_bitmap = bitmap_Map.get(searchList.get(position).getPid());

			if (t_bitmap != null) {
				ImageView imageView = (ImageView) template
						.findViewById(R.id.photoSrc);
				imageView.setImageBitmap(t_bitmap);
			}

		}

		return template;
	}
}
