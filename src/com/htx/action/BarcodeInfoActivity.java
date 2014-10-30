package com.htx.action;

import java.util.HashMap;
import java.util.Map;
import com.hetianxia.activity.R;
import com.htx.bean.BarcodeInfoBean;
import com.htx.conn.Const;
import com.htx.core.AsyncWorkHandler;
import com.htx.pub.LoadingDialog;
import com.htx.pub.PubActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

/**
 * 
 * @author lvan
 * 
 */
public class BarcodeInfoActivity extends PubActivity {

	private Context _context;
	private TextView barcodestatus;
	private TextView barcodestr;
	private TextView productname;
	private TextView producer;
	private TextView location;
	private TextView referenceprice;
	private Button pricesearchbtn;
	private Intent _intent;
	private Bundle bundle;
	private String barcode;
	private String pro_name;
	private String refprice;
	private Button goback;
	private Bitmap barcodeimg;
	private ImageView barcode_img;

	private LinearLayout barcodestrll;
	private LinearLayout productnamell;
	private LinearLayout producerll;
	private LinearLayout locationll;
	private LinearLayout referencepricell;

	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.barcode_information);

		super.onCreate(savedInstanceState);

		_context = this;

		_intent = this.getIntent();
		bundle = this.getIntent().getExtras();

		barcode = _intent.getStringExtra("barcode");
		barcodeimg = (Bitmap) bundle.get("img");

		barcodestatus = (TextView) findViewById(R.id.barcodestatus);
		barcodestr = (TextView) findViewById(R.id.barcodestr);
		productname = (TextView) findViewById(R.id.productname);
		producer = (TextView) findViewById(R.id.producer);
		location = (TextView) findViewById(R.id.location);
		referenceprice = (TextView) findViewById(R.id.referenceprice);

		barcodestrll = (LinearLayout) findViewById(R.id.barcodestrll);
		productnamell = (LinearLayout) findViewById(R.id.productnamell);
		producerll = (LinearLayout) findViewById(R.id.producerll);
		locationll = (LinearLayout) findViewById(R.id.locationll);
		referencepricell = (LinearLayout) findViewById(R.id.referencepricell);

		goback = (Button) findViewById(R.id.barcode_information_goback);

		pricesearchbtn = (Button) findViewById(R.id.price_search_btn);
//		selectcitybtn = (Button) findViewById(R.id.select_city_btn);

		barcode_img = (ImageView) findViewById(R.id.barcodeimg);

		// 第一次拉取数据(异步)
		showDialog(Const.PROGRESSBAR_WAIT);
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword", barcode);

		AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {

			public Object excute(Map<String, String> map) {
				return ProductAction.getBarcodeInformation(map);
			}

			public void handleMessage(Message msg) {
				if (msg.obj != null) {
					BarcodeInfoBean bean = (BarcodeInfoBean) msg.obj;

					String temp = bean.getname();
					if (temp != null && !"".equals(temp)) {
						barcodestatus.setVisibility(View.GONE);
						barcodestrll.setVisibility(View.GONE);
						if (bean.getname().equals("")) {
							productnamell.setVisibility(View.GONE);
						} else {
							productname.setText(bean.getname());
						}
						pro_name = bean.getname();
						if (bean.getsupplier().equals("")) {
							producerll.setVisibility(View.GONE);
						} else {
							producer.setText(bean.getsupplier());
						}
						if (bean.getplace().equals("")) {
							locationll.setVisibility(View.GONE);
						} else {
							location.setText(bean.getplace());
						}
						if (bean.getprice().equals("")) {
							referencepricell.setVisibility(View.GONE);
						} else {
							referenceprice.setText(bean.getprice() + "元");
						}
						refprice = bean.getprice();

						if (barcodeimg != null)
							barcode_img.setImageBitmap(barcodeimg);

						pricesearchbtn.setEnabled(true);
					} else {
						barcodestatus.setVisibility(View.GONE);
						barcodestr.setText("\n\n" + barcode + "\n");
						barcodestr.setTextColor(Color.RED);
						productnamell.setVisibility(View.GONE);
						producerll.setVisibility(View.GONE);
						locationll.setVisibility(View.GONE);
						referencepricell.setVisibility(View.GONE);

						if (barcodeimg != null)
							barcode_img.setImageBitmap(barcodeimg);

						pricesearchbtn.setVisibility(View.GONE);
					}

					_intent.putExtra("status", true);
				} else {
					barcodestatus.setText("扫描成功，结果如下");
					barcodestr.setText(barcode);
					productnamell.setVisibility(View.GONE);
					producerll.setVisibility(View.GONE);
					locationll.setVisibility(View.GONE);
					referencepricell.setVisibility(View.GONE);

					if (barcodeimg != null)
						barcode_img.setImageBitmap(barcodeimg);

					pricesearchbtn.setEnabled(false);
				}
				removeDialog(Const.PROGRESSBAR_WAIT);
			}
		};
		asyncQueryHandler.doWork(param);

		goback.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				BarcodeInfoActivity.this.finish();
			}
		});

		pricesearchbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (pro_name != null && !"".equals(pro_name)) {
//					Intent it = new Intent(_context,
//							SearchBarcodeActivity.class);
//					it.putExtra("sname", pro_name);
//					it.putExtra("barcode", barcode);
//					it.putExtra("refprice", refprice);
//					it.putExtra("location", selectcitybtn.getText());
//					startActivity(it);
				}
			}
		});
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