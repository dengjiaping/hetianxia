package com.htx.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.hetianxia.activity.R;
import com.htx.pub.ShowWebView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class SildeShowWidget extends ViewFlipper {

	private Context mContext;
	private boolean directionFlag = true;// �����־�� trueΪ��������flaseΪ��������
	private int imageCount = 0;
	private int moveFlag;// moveDown��λ��X���
	private boolean startFlag = true;// ��ʼ��ֹͣ ���

	private ArrayList<Bitmap> newsForBit = null;
	private ArrayList<Bitmap> newsForRes = null;
	private List<String> urlstr = new ArrayList<String>();
	
	private long showtime=5000;

	private Timer timer;
	private TimerTask ttask;
	public SildeShowWidget(Context context ,ArrayList<Bitmap> newsForRes,List<String> urlstr) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		this.newsForRes = newsForRes;
		imageCount = newsForRes.size();
		this.urlstr = urlstr;
		addViewToFlipper();
	}
	


	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				showNext();
				break;
			case 101:
				showPrevious();
				break;
			}
			super.handleMessage(msg);
		}
	};

	Thread mSildeShowThread = new Thread(new Runnable() {
		public void run() {
			while (startFlag) {
				try {
					Thread.sleep(showtime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				if (directionFlag) {
					msg.what = 101;
				} else {
					msg.what = 100;
				}
				mHandler.sendMessage(msg);
			}
		}
	});
	

	
	public void addViewToFlipper() {
		for (int i = 0; i < imageCount; i++) {
			addView(addMyView(i,urlstr.get(i)), i);
		}
		// mViewFlipper.set
		setInAnimation(mContext, R.anim.push_left_in);
		setOutAnimation(mContext, R.anim.push_left_out);
		setPersistentDrawingCache(ViewGroup.PERSISTENT_ALL_CACHES);
		setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int x = (int) event.getRawX();
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					moveFlag = x;
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					int m = x - moveFlag;
					if (m > 10) {
						// ��
						directionFlag = true;
						setInAnimation(mContext, R.anim.push_right_in);
						setOutAnimation(mContext, R.anim.push_right_out);
						showPrevious();
					} else if (m < -10) {
						// �һ�
						directionFlag = false;
						setInAnimation(mContext, R.anim.push_left_in);
						setOutAnimation(mContext, R.anim.push_left_out);
						showNext();
					}

					break;
				}
				return true;
			}
		});
	}

	public View addMyView(int index,final String urlstr) {

		RelativeLayout r = (RelativeLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.silde_new_layout, null);
		ImageView imageView = (ImageView) r.findViewById(R.id.imageView1);
		if (newsForBit==null && newsForRes==null) {

		} else {
			if (newsForBit!=null) {
				imageView.setImageBitmap(newsForBit.get(index));
			} else {
				imageView.setImageBitmap(newsForRes.get(index));
				imageView.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						         Intent intent = new Intent(); intent.putExtra("url", urlstr);
						         intent.setClass(mContext, ShowWebView.class);
						         mContext.startActivity(intent);
					}
				});
//				Drawable draw;
//				draw=getResources().getDrawable(newsForRes.get(index));
//				imageView.setBackgroundDrawable(draw);
			}
		}

		return r;
	}

	/**
	 * ����ͼƬ��,��bitmap����
	 * 
	 * @param newsPhoto
	 */
	public void setNewsPhotoForBitmap(ArrayList<Bitmap> newsForBit) {
		this.newsForBit = newsForBit;
		imageCount = newsForBit.size();
	}


	/**
	 * ��������ͼƬ������res��Դ��
	 * 
	 * @param resource
	 *            ͼƬres id
	 */
	public void setNewPhotoForResourceId(ArrayList<Bitmap> newsForRes) {
		this.newsForRes = newsForRes;
		imageCount = newsForRes.size();
	}

	/**
	 * ���ö���Ч��
	 * 
	 * @param inAnimation
	 *            ���붯��
	 * @param outAnimation
	 *            ��ȥ����
	 */
	public void setAnimation(Animation inAnimation, Animation outAnimation) {
		setInAnimation(inAnimation);
		setOutAnimation(outAnimation);
	}

	/**
	 * ���ò��ż��ʱ��
	 */
	public void setShowTime(long time){
		this.showtime=time;
	}
	
	/**
	 * ��ʼ����
	 */
	public void startSildeShow() {
//		mSildeShowThread.start();
		timer=new Timer();
		 ttask=new TimerTask() {
				
				
				public void run() {
					
					Message msg = new Message();
					if (directionFlag) {
						msg.what = 101;
					} else {
						msg.what = 100;
					}
					mHandler.sendMessage(msg);
				}
			};
		timer.schedule(ttask, 1000, 3000);
		
	}

	/**
	 * ֹͣ����
	 */
	public void stopSildeShow() {
		//startFlag = false;
		timer.cancel();
		ttask.cancel();
		timer=null;
		ttask=null;
	}

	/**
	 * �����
	 */
	public void restartSildeShow() {
		startFlag = true;
	}
	
	

	class NewsEntityForBit {
		Bitmap photo;
		String title;
	}

	class NewsEntityForRes {
		int resid;
		String title;
	}

}
