package com.htx.user;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.hetianxia.activity.R;
import com.htx.pub.AsyncImageLoader;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;

public class Aboat_ extends PubActivity {

	private ImageView imageView2;
	private AsyncImageLoader asyImg = new AsyncImageLoader();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		imageView2 = (ImageView) findViewById(R.id.imageView2);

		Button bton = (Button) findViewById(R.id.button);
		bton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		String txt = MySharedData
				.sharedata_ReadString(Aboat_.this, "spreadUrl");

		if (!txt.equals("")) {

			String url = "http://qr.liantu.com/api.php?&bg=EEEEEE&fg=0072BC&gc=0072BC&pt=0072BC&inpt=0072BC&w=300&m=20&el=m&text="
					+ txt ;
			asyImg.LoadImage(url, imageView2);
		}
	}
}
