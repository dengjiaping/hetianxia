package com.htx.conn;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class BitmapDownloaderTask extends AsyncTask<String,Void,Bitmap>{
	
	private String picUrl;
	
	public BitmapDownloaderTask(String _picUrl){
		picUrl = _picUrl;
	}
	
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap_t = ImageUtil.getBitmap(picUrl);
		return bitmap_t;
	}
	
	protected void onPostExecute(Bitmap bitmap) {
		if(isCancelled()){
			bitmap = null;
		}
	}
	

}
