package com.htx.search;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.hetianxia.activity.R;

/**
 * 
 * @author lvan
 *
 */
public class DetailImageAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Bitmap> pics;

	public DetailImageAdapter(Context c) {
		context = c;
	}

	public int getCount() {
		int count = 0;
		if (pics != null) {
			count = pics.size();
		}
		return count;
	}

	public void addPic(Bitmap bitmap) {
		if (pics == null) {
			pics = new ArrayList<Bitmap>();
		}
		pics.add(bitmap);
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);// inflater对象可以把xml转换为view
		View template = inflater.inflate(R.layout.detail_pics, null);

		if (pics != null) {
			Bitmap t_bitmap = pics.get(position);
			if (t_bitmap != null) {
				ImageView imageView = (ImageView) template
						.findViewById(R.id.detailimage);
				Matrix matrix = new Matrix();
				matrix.postScale(1.0f, 1.0f); // 长和宽放大缩小的比例
				Bitmap resizeBmp = Bitmap
						.createBitmap(t_bitmap, 0, 0, t_bitmap.getWidth(),
								t_bitmap.getHeight(), matrix, true);
				imageView.setImageBitmap(resizeBmp);
			}
		}
		return template;
	}
}
