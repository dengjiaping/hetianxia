package com.shopping;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.hetianxia.activity.R;
import com.maiduo.bll.ApplicationShopping;
import com.maiduo.bll.AsyncImageLoader;
import com.maiduo.bll.Config;
import com.maiduo.bll.HttpHelper;
import com.maiduo.bll.SiteHelper;
import com.maiduo.model.ShoppingCartProductList;

public class Cart extends Activity implements OnClickListener,OnItemClickListener{
	
	private ListView listView;  
	private ProgressDialog progressDialog;

	private String UserId = "";
	private AsyncImageLoader asyImg = new AsyncImageLoader();
	private TextView btnCart1 ;
	
	private List<ShoppingCartProductList> product_list = new ArrayList<ShoppingCartProductList>();//�̼�����
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (progressDialog != null)
					progressDialog.dismiss();
		    	addView();//����̼�
				break;
			case 2:
				if (progressDialog != null)
					progressDialog.dismiss();
				break;
			}
		}
	};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);
        
        SharedPreferences sharedata = Cart.this.getSharedPreferences("data", 0);
        UserId= sharedata.getString("UserId", "");
		
    	main = (LinearLayout)this.findViewById(R.id.linearLayoutMain);
        // �״μ�����ʾ������
        progressDialog = new ProgressDialog(this);   
		progressDialog.setMessage(getString(R.string.loadingstr));   
		progressDialog.show(); 
		
		new Thread(new Runnable() {
			public void run() {
				// �������
				getData();
			}
		}).start();
		
	}
	/**
	 * ��ʼ��ListView��������
	 *//*
	private void initializeAdapter(){
		List<Data> datalist = new ArrayList<Data>();
		 
		//adapter = new DataAdapter(datalist);
	}*/
	
	private  void getData() {
		String url = Config.UrlShoppingShowCart+"?userId="+ UserId;
		
		//������Ҫ���������ش���json��ʽ��ݣ�
        try {
        	String reStr = HttpHelper.getJsonData(Cart.this,url);
        	JSONObject reObject = new JSONObject(reStr);
        	if(reObject.getInt("status") == 0){
        		if(reObject.getString("msg").equals("û���ҵ���Ʒ")){
        			
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
//        	        	  }
        	          }
        	        });

        		}
    			Message msg = new Message();
    			msg.what = 2;
    			mHandler.sendMessage(msg);
        		return ;
        	}
        	JSONArray jsonArray = reObject.getJSONArray("list");

	            product_list = new ArrayList<ShoppingCartProductList>(); // ��Ʒ����
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
			// TODO Auto-generated catch block
			//e.printStackTrace();
//			Toast.makeText(this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
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
     * ��ʼ����ǰ����
     */
    private void init(){
    	

    	getData();//��ȡ���
    	
    }
    
    View[] view;
    /**
     * ����̼�
     */
    private void addView(){
    	LayoutInflater factory = LayoutInflater.from(this);
        //�õ��Զ���Ի���
    	view = new View[product_list.size()];
//    	for(int i = 0;i < view.length;i++){
//	        view[i] = factory.inflate(R.layout.shopping_shop_item, null);
//	        main.addView(view[i]);
//    	}
    	for(int i = 0;i < 1;i++){
	        view[i] = factory.inflate(R.layout.shopping_shop_item, null);
	        main.addView(view[i]);
    	}
    	
    	initShop();
    }
    
//    /**�̼���Ƶ�ѡ��ť����*/
//    private RadioButton radio[];
    /**�̼��������*/
    private TextView name[];
    /**��Ʒ�б�����*/
    private ListView list[];
    /**ȥ���㰴ť����*/
    private Button button[];
    private static int index;
    private static int num;
    /**���㰴ť����*/
    /**
     * ��ʼ���̼���Ϣ
     */
    private void initShop(){
//    	num = view.length;
    	num = 1;
//    	radio = new RadioButton[num];
    	name = new TextView[num];
    	list = new ListView[num];
    	button = new Button[num];
    	//��ʼ������
    	for(int i = 0;i < num;i++){
    		//��ʼ���̼����
//    		radio[i] = (RadioButton)view[i].findViewById(R.id.radioButtonShopName);
//    		radio[i].setText("�̼����" + i);
//    		name[i] = (TextView)view[i].findViewById(R.id.textViewShopName);
//    		name[i].setText(product_list.get(i).getProName()+"");
//    		//��ʼ��List
    		list[i] = (ListView)view[i].findViewById(R.id.listViewProduct);
    		list[i].setAdapter(new ProductListAdapter(Cart.this,product_list));
    		initListViewHeight(list[i]);
//    		list[i].setOnItemClickListener(Cart.this);
//    		
//    		//��ʼ���ܼ�
    		TextView totalPrice = (TextView)view[i].findViewById(R.id.textViewTotalPrice);
    		double dou_price = 0;
    		for (int j = 0; j < product_list.size(); j++) {
    			try {

    				dou_price = dou_price+ Double.parseDouble(product_list.get(i).getPriceSum());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
    		totalPrice.setText(dou_price+"");
//    		if(product_list.get(i).getPriceSum() != 0.0){
//    			allPriceText += SiteHelper.GetStrFormatPrice(product_list.get(i).getAllPrice());
//    		}
//    		if(product_list.get(i).getAllPrice() != 0.0 && product_list.get(i).getAllJifen() != 0.0){
//    			allPriceText += " + ";
//    		}
//    		if(product_list.get(i).getAllJifen() != 0.0){
//    			allPriceText += SiteHelper.GetStrFormatJifen(product_list.get(i).getAllJifen());
//    		}
//    		totalPrice.setText(allPriceText);
//    		TextView tvRegADRate = (TextView)view[i].findViewById(R.id.tvRegADRate);
//    		tvRegADRate.setText(SiteHelper.GetStrFormatPrice00(product_list.get(i).getAllRegADRate()));
//    		if(CartType == Config.ShoppingJifenCartType){// ����ǻ�ֹ��ﳵ������Ҫ��ʾ
//    			tvRegADRate.setVisibility(View.GONE);
//    			TextView tvRegADRateTitle = (TextView)view[i].findViewById(R.id.tvRegADRateTitle);
//    			tvRegADRateTitle.setVisibility(View.GONE);
//			}
    		
    		//��ʼ����ť
    		button[i] = (Button)view[i].findViewById(R.id.buttonPay);
    	}
    	//��ʼ���¼�����
    	for(int i = 0;i < num;i++){
    		index = i;
    		//radio[i].setOnClickListener(this);
    		//list�¼�
    		
    		//ȥ���㰴ť�¼�
    		button[i].setOnClickListener(Cart.this);
    		
    		
    	}
    }
    
    /**
	 * �����б��߶ȣ����������� 
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
   /* 
    *//**
     * ����б����
     * @param i
     * @return
     *//*
    private void getData(int i){
    	ArrayList<ShoppingCartSupplier> list = new ArrayList<ShoppingCartSupplier>();
    	for(int j = 0;j < i + 1;j++){
    		HashMap<String,Object> map = new HashMap<String,Object>();
    		map.put("img", );
    		map.put("name", "��Ʒ���" + i + j);
    		map.put("price", "" + (100 + j));
    		map.put("num", "2");
    		map.put("comment", "��ɫ");
    		list.add(map);
    	}
    	
    }*/


	public void onClick(View v) {
		for(int i = 0;i < 1;i++){
//			if(v == radio[i]){
//				System.out.println("radio" + i + "�����£�");
//				if(radio[i].isChecked()){
//					for(int j = 0;j < view.length;j++){
//						if(i != j){
//							radio[j].setChecked(false);
//						}
//					}
//				}
//			}
			if(v == button[i]){
				//System.out.println("button" + i + "������!");
				//Toast(supplier_list.get(i).getShopName());
				Intent intent = new Intent();
	            intent.putExtra("supplierId",product_list.get(i).getSupplierId());
	            intent.putExtra("initData",1); // ��ʼ�����
	            //intent.putExtra("cartType",Config.ShoppingMaiduoCartType);
	            //intent.putExtra("allPrice",supplier_list.get(i).getAllPrice());
	            intent.setClass(Cart.this,com.shopping.Cart_After.class);
	            startActivity(intent);
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
	
	
	
	/*// ����̳�ʵ����
	class Data extends com.maiduo.model.ShoppingCartProductList {
		
	}*/
	
	class ProductListAdapter extends BaseAdapter {
		
		private List<ShoppingCartProductList> list;
		
		private LayoutInflater listContainer;           //��ͼ���� 
		
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
				convertView = listContainer.inflate(R.layout.shopping_list_item, null);
				view.img = (ImageView)convertView.findViewById(R.id.productImg);
				
				view.name = (TextView)convertView.findViewById(R.id.productName);
				view.price = (TextView)convertView.findViewById(R.id.productPrice);
				view.num = (TextView)convertView.findViewById(R.id.productNum);
				view.guige = (TextView)convertView.findViewById(R.id.productGuige);
				view.shoppingDel = (Button)convertView.findViewById(R.id.btnShoppingDel);
				view.jia = (Button)convertView.findViewById(R.id.shop_cart_jia);
				view.jian = (Button)convertView.findViewById(R.id.shop_cart_jian);
				convertView.setTag(view);
			}else{
				view = (ProductView)convertView.getTag();
			}
			//���ý�������
			final ShoppingCartProductList map = list.get(position);
			
			view.name.setText(map.getProName());
			String priceText = "";
//			if(map.getPriceSum().length()>0){
//				priceText += SiteHelper.GetStrFormatPrice(map.getPriceSum());
//			}
//			if(map.getPrice() != 0.0 && map.getJifen() != 0.0){
//				priceText += " + ";
//			}
//			if(map.getJifen() != 0.0){
//				priceText += SiteHelper.GetStrFormatJifen(map.getJifen()) ;
//			}
			
			view.price.setText(map.getShopPrice());
//			view.regADRate.setText(SiteHelper.GetStrFormatPrice00(map.getRegADRate()));
//			if(CartType == Config.ShoppingJifenCartType){
//				view.regADRate.setVisibility(View.GONE);
//				view.regADRateTitle.setVisibility(View.GONE);
//			}
			view.num.setText(map.getProNum()+"");
			view.guige.setText(map.getStyleName());
			
			view.jia.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					
//
//					new Thread(new Runnable() {
//						public void run() {
//							// �������
//
//							String hash = SiteHelper.MD5(UserId+Config.UrlHashKey2);
//							String url = "http://api.36936.com/ClientApi/PointsShop/cart/ModifyNum.ashx?userId="+ UserId+"&styleId="+map.getStyleId()+"&num="+(Integer.parseInt(map.getProNum())+1)+"&hash="+hash;
//							String count = "";
//							Handler handler = new Handler() {
//
//								public void handleMessage(Message m) {
//
//									switch (m.what) {
//
//									// ����UI���µȲ���
//									case 1:
//										break;
//									case 2:
//										break;
//									}
//								}
//							};
//							try {
//						        	String reStr = HttpHelper.getJsonData(Cart.this,url);
//						        	JSONObject reObject = new JSONObject(reStr);
//						        	if(reObject.getInt("status") == 1){
//						        		map.setProNum((Integer.parseInt(map.getProNum())+1)+"");
//						        		count = reObject.getString("count");
//										map.setProNum(count);
//										Message msg = new Message();
//										msg.what = 1;
//										handler.sendMessage(msg);
//						        	}
//						        	else {
//
//										Message msg = new Message();
//										msg.what = 2;
//										handler.sendMessage(msg);
//									}
//						        	
//								} catch (JSONException e) {
//									Message msg = new Message();
//									msg.what =2;
//									handler.sendMessage(msg);
//								} catch (Exception e) {
//									Message msg = new Message();
//									msg.what =2;
//									handler.sendMessage(msg);
//								} 
//						}
//					}).start();
					
				}
			});
			view.jian.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
			String url = map.getProImg();
			asyImg.LoadImage( "http://hnbsshop.com/upLoadImages/"+url, view.img);
			
//			

			
			view.img.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

		                	 
		                
                	
                                 
                }
            });
			
			
			return convertView;
		}
		
		private ProgressDialog progressDialog;
		private Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (progressDialog != null)
						progressDialog.dismiss();
					break;
				}
			}
		};

		
		private void btnShoppingDel(int styleId){
			
			final int StyleId = styleId;
			// �״μ�����ʾ������
			progressDialog = new ProgressDialog(Cart.this);   
			progressDialog.setMessage("�����Ƴ��ﳵ... ");   
			progressDialog.show(); 
			mHandler.postDelayed(new Runnable() {   
			    public void run() {   
			    	
			    	
			    		String url = Config.UrlShoppingCartEdit+"?userid="+ UserId + "&act=del&StyleId="+ StyleId;
			    		url = SiteHelper.GetSendUrl(url); // ������ַ
			    		
			    		//������Ҫ���������ش���json��ʽ��ݣ�
			            try {
			            	String reStr = HttpHelper.getJsonData(Cart.this,url);
			            	JSONObject reObject = new JSONObject(reStr);
			            	if(reObject.getInt("status") == 0){
			            		
			            		Toast.makeText(Cart.this,reObject.getString("error"),Toast.LENGTH_SHORT).show();
			            		return ;
			            	}
			            	
			    			
			    		} catch (JSONException e) {
			    			// TODO Auto-generated catch block
			    			//e.printStackTrace();
			    			Toast.makeText(Cart.this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
			    		} catch (Exception e) {
			    			// TODO Auto-generated catch block
			    			//e.printStackTrace();
			    			Toast.makeText(Cart.this,getString(R.string.loading_error),Toast.LENGTH_SHORT).show();
			    		} 
			    		
			    		Intent intent = new Intent();
			        	  intent.putExtra("CartType", ApplicationShopping.getInstance().getCartType() );
			              intent.setClass(Cart.this,com.shopping.Cart.class);
			              startActivity(intent);
			              Cart.this.finish();
			    	
			    	Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
			    }   
			},1000);  
		}
		
		/**
		 * ��Ҫ��ʾ��ݵ���ͼ��
		 * @author 
		 */
		private class ProductView{
			/**��ƷͼƬ*/
			ImageView img;
			/**��Ʒ���*/
			TextView name;
			/**��Ʒ����*/
			TextView price;
			/**��ȯ*/
			TextView regADRate;
			/**��ȯ*/
			TextView regADRateTitle;
			/**��Ʒ��*/
			TextView num;
			/**��Ʒ���*/
			TextView guige;
			/**ɾ����Ʒ*/
			Button shoppingDel;
			Button jia;
			Button jian;
			
			
		}

	}
	
	
	
}
