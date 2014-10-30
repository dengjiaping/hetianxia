package com.htx.weixin;

import java.util.List;
import com.hetianxia.activity.R;
import com.htx.pub.AsyncImageLoader;
import com.htx.pub.MySharedData;
import com.htx.pub.ShowWebView;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatMsgViewAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
		int IMVT_MSG = 2;
	}

	private List<ChatMsgEntity> coll;

	private Context ctx;

	private LayoutInflater mInflater;
	private MediaPlayer mMediaPlayer = new MediaPlayer();

	public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll) {

		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);
		if (entity.getMsgType() == 1) {
			return IMsgViewType.IMVT_COM_MSG;
		} else if (entity.getMsgType() == 0) {
			return IMsgViewType.IMVT_TO_MSG;
		} else {
			return IMsgViewType.IMVT_MSG;
		}
	}

	public int getViewTypeCount() {
		return 3;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final ChatMsgEntity entity = coll.get(position);
		int isComMsg = entity.getMsgType();
		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (entity.getMsgType() == 1) {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);
			} else if (entity.getMsgType() == 0) {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_right, null);
			} else if (entity.getMsgType() == 11 || entity.getMsgType() == 2) {
				convertView = mInflater.inflate(R.layout.chatting_item_msg,
						null);
			}
			viewHolder = new ViewHolder();
			viewHolder.logo = (ImageView) convertView
					.findViewById(R.id.iv_userhead);
			viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView
					.findViewById(R.id.tv_username);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			viewHolder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_time);
			viewHolder.isComMsg = isComMsg;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvSendTime.setText(entity.getDate());
		AsyncImageLoader asy = new AsyncImageLoader();
		if (entity.getMsgType() == 1) {
			asy.LoadImage(entity.getLogo(), viewHolder.logo);
		} else if (entity.getMsgType() == 0) {
			if (!MySharedData.sharedata_ReadString(ctx, "UU_Avatar").equals("")) {
				asy.LoadImage(
						MySharedData.sharedata_ReadString(ctx, "UU_Avatar"),
						viewHolder.logo);
			}
		} else {
			if (!MySharedData.sharedata_ReadString(ctx, position+"")
					.equals("")) {
//				asy.LoadImage2(
//						entity.getLogo(),
//						viewHolder.logo);
				asy.LoadImage(
						MySharedData.sharedata_ReadString(ctx, position+""),
						viewHolder.logo);
			}
//			viewHolder.logo.setImageDrawable(asy.loadImageFromUrl(entity.getLogo()));
		}
		if (entity.getText().contains(".amr")) {
			viewHolder.tvContent.setText("");
			viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.ic_head, 0);
			// viewHolder.tvTime.setText(entity.getTime());
		} else {
			viewHolder.tvContent.setText(entity.getText());
			viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					0, 0);
			// viewHolder.tvTime.setText("");
		}
		viewHolder.ll.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ctx, ShowWebView.class);
				intent.putExtra("url",
						MySharedData.sharedata_ReadString(ctx, "InfoUrl"));
				ctx.startActivity(intent);
			}
		});
		viewHolder.tvUserName.setText(entity.getName());
		

		return convertView;
	}

	static class ViewHolder {
		public LinearLayout ll;
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public TextView tvTime;
		public ImageView logo;
		public int isComMsg = 0;
	}

}
