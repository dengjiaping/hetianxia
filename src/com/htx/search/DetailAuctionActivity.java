package com.htx.search;

import com.hetianxia.activity.R;
import com.htx.conn.BitmapDownloaderTask;
import com.htx.conn.Const;
import com.htx.pub.LoadingDialog;
import com.htx.pub.PubActivity;
import com.htx.pub.ShowWebView;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Ivan
 */
public class DetailAuctionActivity extends PubActivity {
	
	private Button goback;
    private Intent _intent;
    private Context context;
    private TextView detail_title;
    private ImageView detail_pic;
    private TextView detail_price;
    private TextView detail_propliststr;
    private TextView detail_sellercount;
    private Button detail_shopurl;
    
    private String title;
    private String picurl;
    private String price;
    private String propliststr;
    private String sellercount;
    private String shopurl;
	
    /** Called when the activity is first created. */
    
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.detail_auction);
        
        super.onCreate(savedInstanceState);
       
        context = this; 
        
        _intent = this.getIntent();
        
        title = _intent.getStringExtra("title");
        picurl = _intent.getStringExtra("picurl");
        price = _intent.getStringExtra("price");
        propliststr = _intent.getStringExtra("propliststr");
        sellercount = _intent.getStringExtra("sellercount");
        shopurl = _intent.getStringExtra("shopurl");
                
        goback = (Button)findViewById(R.id.detail_auction_goback);        
        detail_title = (TextView)findViewById(R.id.detail_auction_title);        
        detail_pic = (ImageView)findViewById(R.id.photoSrc);
        detail_price = (TextView)findViewById(R.id.detail_auction_price);
        detail_propliststr = (TextView)findViewById(R.id.detail_auction_propliststr);
        detail_sellercount = (TextView)findViewById(R.id.detail_auction_sellercount);
        detail_shopurl = (Button)findViewById(R.id.detail_auction_url);
        
        detail_title.setText(title);
        detail_price.setText(price);
        detail_propliststr.setText(propliststr);
        detail_sellercount.setText(sellercount);
        
        detail_shopurl.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("url", shopurl);
				intent.setClass(DetailAuctionActivity.this, ShowWebView.class);
				startActivity(intent);
			}
		});
		
        goback.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				DetailAuctionActivity.this.finish();
			}
        });

        picurl = picurl + "_80x80.jpg";
        BitmapDownloaderTask task = new BitmapDownloaderTask(picurl){
    		
    		protected void onPostExecute(
    				Bitmap bm) {
    			if(isCancelled()){
    				bm = null;
    			}
    			if(bm!=null){    				
    				detail_pic.setImageBitmap(bm);
            	}
    		}
    	};
    	task.execute("");
 
    }
   
    protected Dialog onCreateDialog(int id) {
    	 switch (id) {
	         case Const.PROGRESSBAR_WAIT:
	        	 LoadingDialog wait_pd = new LoadingDialog(this);
	        	 return wait_pd; 
    	 }

     	return null;
    }
    

    
}
