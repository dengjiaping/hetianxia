package com.shopping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.hetianxia.activity.R;
import com.maiduo.ProductList;
import com.maiduo.bll.ApplicationShopping;
import com.maiduo.bll.ApplicationUser;
import com.maiduo.bll.AsyncImageLoader;
import com.maiduo.bll.Config;
import com.maiduo.bll.HttpHelper;
import com.maiduo.bll.SiteHelper;
import com.maiduo.model.Product;
import com.maiduo.model.ShoppingCartProductList;
import com.maiduo.model.ShoppingCartSupplier;
import com.user.OrderList;

public class Cart_After extends Activity implements OnClickListener,OnItemClickListener{
	
	private ListView listView;  
	private ProgressDialog progressDialog;

	private String UserId = "";
	private AsyncImageLoader asyImg = new AsyncImageLoader();
	private TextView btnCart1,tv_name,tv_phone,tv_add ;
	private LinearLayout ll_after;
	private String str_name="",str_phone="",str_add="",sheng="",shi="",xian="",order_id="";
	double dou_price = 0;
	
	private List<ShoppingCartProductList> product_list = new ArrayList<ShoppingCartProductList>();//商家数组
	
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (progressDialog != null)
					progressDialog.dismiss();
		    	addView();//添加商家
		    	if (str_name.length()>0) {
					tv_name.setText(str_name);
					tv_add.setText(sheng+shi+xian+str_add);
					tv_phone.setText(str_phone);
				}
				break;
			case 2:
				if (progressDialog != null)
					progressDialog.dismiss();
				break;
			case 3:
				if (progressDialog != null)
					progressDialog.dismiss();

				new PopupWindows(Cart_After.this, tv_add);
				break;
			case 4:
				if (progressDialog != null)
					progressDialog.dismiss();
				Toast.makeText(Cart_After.this, "提交订单失败", 3000).show();
				break;
			case 5:
				if (progressDialog != null)
					progressDialog.dismiss();

				Toast.makeText(Cart_After.this, "交易成功", 3000).show();
				Intent intent = new Intent(Cart_After.this,OrderList.class);
				startActivity(intent);
				finish();
				break;
			case 6:
				if (progressDialog != null)
					progressDialog.dismiss();
				Toast.makeText(Cart_After.this, "交易失败", 3000).show();

				Intent intent2 = new Intent(Cart_After.this,OrderList.class);
				startActivity(intent2);
				finish();
				break;
			}
		}
	};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_after);
        
        //模拟100101登录
        //ApplicationUser.getInstance().Login("1975931", "qq0705");
        // 判断登录
        SharedPreferences sharedata = Cart_After.this.getSharedPreferences("data", 0);
        UserId= sharedata.getString("UserId", "");
        
        tv_add = (TextView)findViewById(R.id.after_tv_add);
        tv_name = (TextView)findViewById(R.id.after_tv_name);
        tv_phone = (TextView)findViewById(R.id.after_tv_phone);
    	main = (LinearLayout)this.findViewById(R.id.linearLayoutMain);
        ll_after = (LinearLayout)findViewById(R.id.after_ll_add);
        ll_after.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
	        	  Intent intent = new Intent();
	        	  intent.setClass(Cart_After.this,com.shopping.Receive.class);
	              startActivityForResult(intent, 101);  
	              
			}
		});
        // 首次加载提示加载中
        progressDialog = new ProgressDialog(this);   
		progressDialog.setMessage(getString(R.string.loadingstr));   
		progressDialog.show(); 
		
		new Thread(new Runnable() {
			public void run() {
				// 加载数据
				getData();
			}
		}).start();
		
	}
	/**
	 * 初始化ListView的适配器
	 *//*
	private void initializeAdapter(){
		List<Data> datalist = new ArrayList<Data>();
		 
		//adapter = new DataAdapter(datalist);
	}*/
	
	private  void getData() {
		String url = Config.UrlShoppingShowCart+"?userId="+ UserId;
		String url_add = "http://api.36936.com/ClientApi/PointsShop/ReceiveAddress.ashx?userId="+ UserId;
		
		//这里需要分析服务器回传的json格式数据，
        try {
        	String reStr_add = HttpHelper.getJsonData(Cart_After.this,url_add);
        	JSONObject reObject_add = new JSONObject(reStr_add);
        	if(reObject_add.getInt("status") == 1){
        		JSONArray jsonArray_add = reObject_add.getJSONArray("list");
        		if (jsonArray_add.length()>0) {
	            	JSONObject jsonObject2_add = (JSONObject)jsonArray_add.opt(0); 
					str_name = jsonObject2_add.getString("username");
					str_add = jsonObject2_add.getString("address");
					str_phone = jsonObject2_add.getString("mobile");
					sheng = jsonObject2_add.getString("province");
					shi = jsonObject2_add.getString("city");
					xian = jsonObject2_add.getString("county");
				}
        	}
        	
        	String reStr = HttpHelper.getJsonData(Cart_After.this,url);
        	JSONObject reObject = new JSONObject(reStr);
        	if(reObject.getInt("status") == 0){
        		if(reObject.getString("msg").equals("没有找到商品")){
        			
        			Button btnGoShop = (Button) findViewById(R.id.btnGoShop);
        			btnGoShop.setVisibility(View.VISIBLE);
        			btnGoShop.setOnClickListener(new Button.OnClickListener()
        	        {
        	          public void onClick(View v)
        	          {
//        	        	  if(ApplicationShopping.getInstance().getCartType() == Config.ShoppingMaiduoCartType){
//        	        		  Intent intent = new Intent();
//            	        	  intent.setClass(Cart.this,com.maiduo.Home.class);
//            	              startActivity(intent);
//            	              Cart.this.finish();
//        	        	  }else if(ApplicationShopping.getInstance().getCartType() == Config.ShoppingJifenCartType){
//        	        		  Intent intent = new Intent();
//            	        	  intent.setClass(Cart.this,com.jifen.product.Home.class);
//            	              startActivity(intent);
//            	              Cart.this.finish();
//        	        	  }
        	        	  
        	        	  
        	             
        	          }
        	        });
        			
        		}
        		return ;
        	}
        	JSONArray jsonArray = reObject.getJSONArray("list");

	            product_list = new ArrayList<ShoppingCartProductList>(); // 商品数组
	            for(int j=0;j<jsonArray.length();j++){ 
	            	JSONObject jsonObject2 = (JSONObject)jsonArray.opt(j); 
	            	ShoppingCartProductList data2 = new ShoppingCartProductList();
	            	data2.setID(jsonObject2.getString("ID"));
	            	data2.setProId(jsonObject2.getString("ProId"));
	            	data2.setProName(jsonObject2.getString("ProName"));
	            	data2.setStyleId(jsonObject2.getString("StyleId"));
	            	data2.setStyleName(jsonObject2.getString("StyleName"));
	            	data2.setProNum(jsonObject2.getString("ProNum"));
	            	data2.setShopPrice(jsonObject2.getString("ShopPrice"));
	            	data2.setPriceSum(jsonObject2.getString("priceSum"));
	            	data2.setUnit(jsonObject2.getString("Unit"));
	            	data2.setProImg(jsonObject2.getString("ProImg"));
	            	data2.setSupplierId(jsonObject2.getString("SupplierId"));
	            	data2.setProductId(jsonObject2.getString("productId"));
	            	
	            	product_list.add(data2);
	            }

				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
		} catch (JSONException e) {
			Message msg = new Message();
			msg.what = 2;
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
//			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
			Message msg = new Message();
			msg.what = 2;
			mHandler.sendMessage(msg);
		} 
		
	}
	
    private LinearLayout main;
    /**
     * 初始化当前界面
     */
    private void init(){
    	

    	getData();//获取数据
    	
    }
    
    View[] view;
    /**
     * 添加商家
     */
    private void addView(){
    	LayoutInflater factory = LayoutInflater.from(this);
        //得到自定义对话框
    	view = new View[product_list.size()];
//    	for(int i = 0;i < view.length;i++){
//	        view[i] = factory.inflate(R.layout.shopping_shop_item, null);
//	        main.addView(view[i]);
//    	}
    	for(int i = 0;i < 1;i++){
	        view[i] = factory.inflate(R.layout.shopping_shop_items, null);
	        main.addView(view[i]);
    	}
    	
    	initShop();
    }
    
//    /**商家名称单选按钮数组*/
//    private RadioButton radio[];
    /**商家名称数组*/
    private TextView name[];
    /**商品列表数组*/
    private ListView list[];
    /**去结算按钮数组*/
    private Button button[];
    private static int index;
    private static int num;
    /**结算按钮数组*/
    /**
     * 初始化商家信息
     */
    private void initShop(){
//    	num = view.length;
    	num = 1;
//    	radio = new RadioButton[num];
    	name = new TextView[num];
    	list = new ListView[num];
    	button = new Button[num];
    	//初始化界面
    	for(int i = 0;i < num;i++){
//    		//初始化List
    		list[i] = (ListView)view[i].findViewById(R.id.listViewProduct);
    		list[i].setAdapter(new ProductListAdapter(Cart_After.this,product_list));
    		initListViewHeight(list[i]);
//    		//初始化总价
    		TextView totalPrice = (TextView)view[i].findViewById(R.id.textViewTotalPrice);
    		for (int j = 0; j < product_list.size(); j++) {
    			try {

    				dou_price = dou_price+ Double.parseDouble(product_list.get(i).getPriceSum());
				} catch (Exception e) {
				}
			}
    		totalPrice.setText(dou_price+"");
    		//初始化按钮
    		button[i] = (Button)view[i].findViewById(R.id.buttonPay);
    	}
    	//初始化事件处理
    	for(int i = 0;i < num;i++){
    		index = i;
    		//去结算按钮事件
    		button[i].setOnClickListener(Cart_After.this);
    		
    		
    	}
    }
    
    /**
	 * 设置列表框高度，解决滚屏问题 
	 */
	private void initListViewHeight(ListView list){
		ProductListAdapter listAdapter = (ProductListAdapter)list.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
        	ViewGroup.LayoutParams params = list.getLayoutParams();
        	params.height = 0;
        	list.setLayoutParams(params);
            return;  
        }
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, list);  
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }   
        ViewGroup.LayoutParams params = list.getLayoutParams();  
        params.height = totalHeight + (list.getDividerHeight() * (listAdapter.getCount() - 1));  
        params.height += 5;
        list.setLayoutParams(params);  
	}


	public void onClick(View v) {
		for(int i = 0;i < 1;i++){
			if(v == button[i]){
				if (str_name.length()==0) {
					Toast.makeText(Cart_After.this, "请填写收货地址", 3000).show();
					return;
				}

				new Thread(new Runnable() {
					public void run() {
						// 加载数据

						String hash = SiteHelper.MD5(UserId+Config.UrlHashKey2);
						String url = "http://api.36936.com/ClientApi/PointsShop/order/ConfirmOrder.ashx"+"?userId="+ UserId+"&receiveName="+str_name
								+"&province="+sheng+"&city="+shi+"&county="+xian+"&address="+str_add+"&userTel="+str_phone+"&isInvoice=false&invoiceTitle=&hash="+hash+"&liuyan=";
					
						 try {
					        	String reStr = HttpHelper.getJsonData(Cart_After.this,url);
					        	JSONObject reObject = new JSONObject(reStr);
					        	if(reObject.getInt("status") == 1){
//					        		if(reObject.getString("msg").equals("没有找到商品")){
//					        			orderId
//					        		}
					        		order_id = reObject.getString("orderId");
									Message msg = new Message();
									msg.what = 3;
									mHandler.sendMessage(msg);
					        	}
					        	else {

									Message msg = new Message();
									msg.what = 4;
									mHandler.sendMessage(msg);
								}
					        	
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
//								Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
								Message msg = new Message();
								msg.what =4;
								mHandler.sendMessage(msg);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
//								Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
								Message msg = new Message();
								msg.what =4;
								mHandler.sendMessage(msg);
							} 
					}
				}).start();
			}
		}
	}

	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		for(int i = 0;i < list.length;i++){
			if(arg0 == list[i]){
//				//Toast(supplier_list.get(i).getList().get(arg2).getProName());
//				if(CartType == Config.ShoppingJifenCartType){
//					Intent intent = new Intent();
//	                intent.putExtra("id",supplier_list.get(i).getList().get(arg2).getJifenId()+"");
//	                intent.setClass(Cart.this,com.jifen.product.Product.class);
//	                startActivity(intent);	
//				}else{
//					Intent intent = new Intent();
//	                intent.putExtra("id",supplier_list.get(i).getList().get(arg2).getPid()+"");
//	                intent.setClass(Cart.this,com.maiduo.Product.class);
//	                startActivity(intent);
//				}
			}
		}		
	}
	
	
	
	/*// 这里继承实体类
	class Data extends com.maiduo.model.ShoppingCartProductList {
		
	}*/
	
	class ProductListAdapter extends BaseAdapter {
		
		private List<ShoppingCartProductList> list;
		
		private LayoutInflater listContainer;           //视图容器 
		
		public ProductListAdapter(Context context,List<ShoppingCartProductList> list){
			super();
			listContainer =  LayoutInflater.from(context);
			this.list = list;
		}
		

		public int getCount() {
			return list.size();
		}


		public Object getItem(int position) {
			return list.get(position);
		}


		public long getItemId(int position) {
			return position;
		}


		public View getView(int position, View convertView, ViewGroup parent) {
			ProductView view = null;
			if(convertView == null){
				view = new ProductView();
				convertView = listContainer.inflate(R.layout.shopping_list_items, null);
				view.img = (ImageView)convertView.findViewById(R.id.productImg);
				
				view.name = (TextView)convertView.findViewById(R.id.productName);
				view.price = (TextView)convertView.findViewById(R.id.productPrice);
				view.num = (TextView)convertView.findViewById(R.id.productNum);
				view.guige = (TextView)convertView.findViewById(R.id.productGuige);
				view.shoppingDel = (Button)convertView.findViewById(R.id.btnShoppingDel);
				convertView.setTag(view);
			}else{
				view = (ProductView)convertView.getTag();
			}
			//设置界面内容
			final ShoppingCartProductList map = list.get(position);
			
			view.name.setText(map.getProName());
			String priceText = "";
			
			view.price.setText(map.getShopPrice());
			view.num.setText(map.getProNum()+"");
			view.guige.setText(map.getStyleName());
			
			String url = map.getProImg();
			asyImg.LoadImage( "http://hnbsshop.com/upLoadImages/"+url, view.img);
			view.img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	
                }
            });
			
			return convertView;
		}
		
//		private ProgressDialog progressDialog;
//		private Handler mHandler = new Handler() {
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//				case 1:
//					if (progressDialog != null)
//						progressDialog.dismiss();
//					break;
//				}
//			}
//		};

		private void btnShoppingDel(int styleId){
			
			final int StyleId = styleId;
			// 首次加载提示加载中
			progressDialog = new ProgressDialog(Cart_After.this);   
			progressDialog.setMessage("正在移除购物车... ");   
			progressDialog.show(); 
			mHandler.postDelayed(new Runnable() {   
			    public void run() {   
			    	
			    	
			    		String url = Config.UrlShoppingCartEdit+"?userid="+ UserId + "&act=del&StyleId="+ StyleId;
			    		url = SiteHelper.GetSendUrl(url); // 加密网址
			    		
			    		//这里需要分析服务器回传的json格式数据，
			            try {
			            	String reStr = HttpHelper.getJsonData(Cart_After.this,url);
			            	JSONObject reObject = new JSONObject(reStr);
			            	if(reObject.getInt("status") == 0){
			            		
			            		Toast.makeText(Cart_After.this,reObject.getString("error"),Toast.LENGTH_SHORT).show();
			            		return ;
			            	}
			            	
			    			
			    		} catch (JSONException e) {
			    			// TODO Auto-generated catch block
			    			//e.printStackTrace();
			    			Toast.makeText(Cart_After.this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
			    		} catch (Exception e) {
			    			// TODO Auto-generated catch block
			    			//e.printStackTrace();
			    			Toast.makeText(Cart_After.this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
			    		} 
			    	
			    		Intent intent = new Intent();
			        	  intent.putExtra("CartType", ApplicationShopping.getInstance().getCartType() );
			              intent.setClass(Cart_After.this,com.shopping.Cart_After.class);
			              startActivity(intent);
			              Cart_After.this.finish();
			    	
			    	Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
			    }   
			},1000);  
		}
		
		/**
		 * 需要显示数据的视图类
		 * @author 
		 */
		private class ProductView{
			/**产品图片*/
			ImageView img;
			/**产品名称*/
			TextView name;
			/**产品单价*/
			TextView price;
			/**返券*/
			TextView regADRate;
			/**返券*/
			TextView regADRateTitle;
			/**产品数量*/
			TextView num;
			/**产品规格*/
			TextView guige;
			/**删除商品*/
			Button shoppingDel;
			
			
		}

	}
	

	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
        //可以根据多个请求代码来作相应的操作  
        if(101==resultCode)  
        {  
        	str_name =data.getExtras().getString("name");
        	str_add =data.getExtras().getString("add");
        	str_phone =data.getExtras().getString("phone");
        	sheng =data.getExtras().getString("sheng");
        	shi =data.getExtras().getString("shi");
        	xian =data.getExtras().getString("xian");
        	tv_name.setText(str_name);
        	tv_add.setText(sheng+shi+xian+str_add);
        	tv_phone.setText(str_phone);
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }  

	private class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			
			TextView tv_money = (TextView)view.findViewById(R.id.money);
			tv_money.setText("权益宝："+dou_price+"个");
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
//					String uId = MySharedData.sharedata_ReadString(Sub_Jh.this,
//							"uid");
//					Intent intent_jh = new Intent(Sub_Jh.this,
//							JiaoHuanList.class);
//					intent_jh.putExtra("uid", uId);
//					startActivity(intent_jh);
//					finish();
				}
			});
			
			Button bt_submit = (Button)view.findViewById(R.id.item_popupwindows_submit);
			bt_submit.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub

					new Thread(new Runnable() {
						public void run() {
							// 加载数据

							String hash = SiteHelper.MD5(UserId+Config.UrlHashKey2);
							String url = "http://api.36936.com/ClientApi/PointsShop/order/PayOrer.ashx"+"?userId="+ UserId+"&oid="+order_id+"&hash="+hash;
						
							 try {
						        	String reStr = HttpHelper.getJsonData(Cart_After.this,url);
						        	JSONObject reObject = new JSONObject(reStr);
						        	if(reObject.getInt("status") == 1){
//						        		if(reObject.getString("msg").equals("没有找到商品")){
//						        			
//						        		}
										Message msg = new Message();
										msg.what = 5;
										mHandler.sendMessage(msg);
						        	}
						        	else {

										Message msg = new Message();
										msg.what = 6;
										mHandler.sendMessage(msg);
									}
						        	
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									//e.printStackTrace();
//									Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
									Message msg = new Message();
									msg.what =6;
									mHandler.sendMessage(msg);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									//e.printStackTrace();
//									Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
									Message msg = new Message();
									msg.what =6;
									mHandler.sendMessage(msg);
								} 
						}
					}).start();
				}
			});

		}
	}
}
