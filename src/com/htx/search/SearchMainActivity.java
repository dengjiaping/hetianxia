package com.htx.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.hetianxia.activity.R;
import com.htx.action.ProductAction;
import com.htx.bean.AuctionListBean;
import com.htx.bean.ImageBean;
import com.htx.bean.SearchBean;
import com.htx.conn.BitmapDownloaderTask;
import com.htx.conn.Const;
import com.htx.core.AsyncWorkHandler;
import com.htx.main.MainHomeActivity;
import com.htx.model.ProductListBean;
import com.htx.pub.Integral;
import com.htx.pub.LoadingDialog;
import com.htx.pub.MySharedData;
import com.htx.pub.PubActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author lvan
 *
 */
public class SearchMainActivity extends PubActivity {

	private ListView listView;
	private TextView tv;
	private Button goback;
	private SearchAdapter listItemAdapter;
	private ArrayList<ProductListBean> productList;
	private Context _context;
	private Intent _intent;
	private LayoutInflater inflater;
	private View footer;
	private int curpage = 1;// 当前页
	private int pages = 1;// 共多少页
	private int curCount = 0;
	private int totalCount = 0;
	private String keyworld;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_res);

		_context = this;
		_intent = this.getIntent();
		inflater = LayoutInflater.from(_context);
		footer = inflater.inflate(R.layout.search_footer, null);
		listView = (ListView) findViewById(R.id.search_listview);
		listView.addFooterView(footer);
		goback = (Button) findViewById(R.id.search_goback);
		tv = (TextView) findViewById(R.id.search_count_tv);
		keyworld = _intent.getStringExtra("sname");

		if(!net()){
			Toast("无网络！", 2000);
			return;
		}
		
		String adUserId = MySharedData.sharedata_ReadString(SearchMainActivity.this, "UserId");
		Integral.getIntegralData(SearchMainActivity.this, 5, true,adUserId);
		
		AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {
			public Object excute(Map<String, String> params) {
				return ProductAction.getSearchBean(params);
			}

			public void handleMessage(Message msg) {
				if (msg.obj != null) {
					SearchBean bean = (SearchBean) msg.obj;
			//		if ("true".equals(bean.getSuccess())) {
					if ("success".equals(bean.getResultMessage())) {
						tv.setText(bean.getTotalCount());
						pages = (Integer.parseInt(bean.getTotalCount()) % Const.PAGE_SIZE_INT == 0) ? Integer
								.parseInt(bean.getTotalCount()) / Const.PAGE_SIZE_INT : Integer.parseInt(bean
								.getTotalCount()) / Const.PAGE_SIZE_INT + 1;
						totalCount = Integer.parseInt(bean.getTotalCount());

						productList = bean.getProductList();
						if (productList == null)
							productList = new ArrayList<ProductListBean>();

						ArrayList<AuctionListBean> tmpList = bean.getAuctionList();
						if (tmpList != null && tmpList.size() > 0) {
							String str;
							for (AuctionListBean spbean : tmpList) {

								ProductListBean tmpProduct = new ProductListBean();

								tmpProduct.setBbCount("###");
								tmpProduct.setCatId("");
								tmpProduct.setCatIdPath("");
								tmpProduct.setCmtCount("");
								tmpProduct.setCmtScore("");
								tmpProduct.setLwQuantity("");

								str = spbean.getPic();
								
								if (spbean.getPic() != null)
									tmpProduct.setPictUrl(spbean.getPic());
								else
									tmpProduct.setPictUrl("http://127.0.0.1");

								str = spbean.getEpid();
								if (spbean.getEpid() != null)
									tmpProduct.setPid(spbean.getEpid());
								else
									tmpProduct.setPid("1234");

								str = spbean.getPrice();
								if (spbean.getPrice() != null)
									tmpProduct.setPrice(spbean.getPrice());
								else
									tmpProduct.setPrice("0");

								tmpProduct.setPriceRank("");

								str = spbean.getLink();
								if (spbean.getLink() != null)
									tmpProduct.setProperty(spbean.getLink());
								else
									tmpProduct.setProperty("");

								str = spbean.getUserNickName();
								str = spbean.getRealPostFee();
								if (spbean.getUserNickName() != null && spbean.getRealPostFee() != null) {
									tmpProduct.setPropListStr(spbean.getUserNickName() + "  运费："
											+ spbean.getRealPostFee());
									tmpProduct.setYunfei(spbean.getRealPostFee().toString());
								} else {
									if (spbean.getUserNickName() != null && spbean.getRealPostFee() == null) {
										tmpProduct.setPropListStr(spbean.getUserNickName());
									} else {
										if (spbean.getUserNickName() == null && spbean.getRealPostFee() != null) {

											tmpProduct.setYunfei(spbean.getRealPostFee().toString());

											tmpProduct.setPropListStr("  运费：" + spbean.getRealPostFee());
										} else {
											tmpProduct.setPropListStr("");
										}
									}
								}

								str = spbean.getSales();
								if (spbean.getSales() != null)
									tmpProduct.setSellerCount(spbean.getSales());
								else
									tmpProduct.setSellerCount("0");

								tmpProduct.setSpuId("");
								tmpProduct.setStaticScore("");

								str = spbean.getTitle();
								if (spbean.getTitle() != null)
									tmpProduct.setTitle(spbean.getTitle());
								else
									tmpProduct.setTitle("");

								try {
									productList.add(tmpProduct);
								} catch (NullPointerException e) {

								}

							}
						}

						curCount = productList.size();

						listItemAdapter = new SearchAdapter(_context, productList);

						if (listItemAdapter != null) {

							listView.setAdapter(listItemAdapter);

							// 开启多个线程去拉取图片------begin
							if (productList != null) {

								int len = productList.size();
								if (len == 1) {
									Intent intent = new Intent();
									intent.putExtra("product_id", productList.get(0).getPid());
									//////////////////////////////////////////////////
									intent.setClass(_context, MainHomeActivity.class);
									startActivity(intent);
									Toast("搜索结果中只有一个产品，正在跳转到终端页", 1000);
									return;
								}
								for (int i = 0; i < len; i++) {
									final String id = productList.get(i).getPid();

									String image_url = productList.get(i).getPictUrl() + "_80x80.jpg";
									BitmapDownloaderTask task = new BitmapDownloaderTask(image_url) {

										protected void onPostExecute(Bitmap bm) {
											if (isCancelled()) {
												bm = null;
											}
											if (bm != null) {
												ImageBean bean = new ImageBean();
												bean.id = id;
												bean.bitmap = bm;
												listItemAdapter.putBitmap(bean.id, bean.bitmap);
												listItemAdapter.notifyDataSetChanged();
											}
										}
									};
									task.execute("");
								}

							}
							// 开启多个线程去拉取图片------end
						}
					}
				}

				removeDialog(Const.PROGRESSBAR_WAIT);

			}

		};
		// 异步获取信息,实现两个方法excute跟onCompleteWork
		Map<String, String> params = new HashMap<String, String>();
		showDialog(Const.PROGRESSBAR_WAIT);
		params.put("n", keyworld);
		params.put("from", _intent.getStringExtra("from"));
		params.put("p", "0");
		
		String imsi = "204046330839890";
		String imei = "a000003377e7b4";
		
		ProductAction.imei = imei;
		ProductAction.imsi = imsi;

		asyncQueryHandler.doWork(params);

		footer.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (curCount < totalCount) {

					int cur = curCount;

					AsyncWorkHandler asyncQueryHandler = new AsyncWorkHandler() {

						public Object excute(Map<String, String> params) {
							return ProductAction.getSearchBean(params);
						}

						public void handleMessage(Message msg) {

							if (msg.obj != null) {

								SearchBean bean = (SearchBean) msg.obj;

					//			if ("true".equals(bean.getSuccess())) {
								if ("success".equals(bean.getResultMessage())) {

									ArrayList<ProductListBean> tmpList = bean.getProductList();

									ArrayList<AuctionListBean> tmpList2 = bean.getAuctionList();
									if (tmpList2 != null && tmpList2.size() > 0) {
										if (tmpList == null)
											tmpList = new ArrayList<ProductListBean>();

										String str = null;
										for (AuctionListBean spbean : tmpList2) {

											ProductListBean tmpProduct = new ProductListBean();

											tmpProduct.setBbCount("###");
											tmpProduct.setCatId("");
											tmpProduct.setCatIdPath("");
											tmpProduct.setCmtCount("");
											tmpProduct.setCmtScore("");
											tmpProduct.setLwQuantity("");

											str = spbean.getPic();
											
											if (spbean.getPic() != null)
												tmpProduct.setPictUrl(spbean.getPic());
											else
												tmpProduct.setPictUrl("http://127.0.0.1");

											str = spbean.getEpid();
											if (spbean.getEpid() != null)
												tmpProduct.setPid(spbean.getEpid());
											else
												tmpProduct.setPid("1234");

											str = spbean.getPrice();
											if (spbean.getPrice() != null)
												tmpProduct.setPrice(spbean.getPrice());
											else
												tmpProduct.setPrice("0");

											tmpProduct.setPriceRank("");

											str = spbean.getLink();
											if (spbean.getLink() != null)
												tmpProduct.setProperty(spbean.getLink());
											else
												tmpProduct.setProperty("");

											str = spbean.getUserNickName();
											str = spbean.getRealPostFee();
											if (spbean.getUserNickName() != null && spbean.getRealPostFee() != null) {
												tmpProduct.setPropListStr(spbean.getUserNickName() + "  运费："
														+ spbean.getRealPostFee());
												tmpProduct.setYunfei(spbean.getRealPostFee().toString());
											} else {
												if (spbean.getUserNickName() != null && spbean.getRealPostFee() == null) {
													tmpProduct.setPropListStr(spbean.getUserNickName());
												} else {
													if (spbean.getUserNickName() == null && spbean.getRealPostFee() != null) {

														tmpProduct.setYunfei(spbean.getRealPostFee().toString());

														tmpProduct.setPropListStr("  运费：" + spbean.getRealPostFee());
													} else {
														tmpProduct.setPropListStr("");
													}
												}
											}

											str = spbean.getSales();
											if (spbean.getSales() != null)
												tmpProduct.setSellerCount(spbean.getSales());
											else
												tmpProduct.setSellerCount("0");

											tmpProduct.setSpuId("");
											tmpProduct.setStaticScore("");

											str = spbean.getTitle();
											if (spbean.getTitle() != null)
												tmpProduct.setTitle(spbean.getTitle());
											else
												tmpProduct.setTitle("");

											try {
												tmpList.add(tmpProduct);
											} catch (NullPointerException e) {

											}
										}
									}

									if (tmpList != null && tmpList.size() > 0) {

										// curpage++;
										
										for (ProductListBean spbean : tmpList) {
											productList.add(spbean);
											curCount ++;
										}

										listItemAdapter.notifyDataSetChanged();

										// 开启多个后台线程去拉取图片
										if (tmpList != null) {
											int len = tmpList.size();

											for (int i = 0; i < len; i++) {

												final String id = tmpList.get(i).getPid();

												String image_url = tmpList.get(i).getPictUrl() + "_80x80.jpg";
												BitmapDownloaderTask task = new BitmapDownloaderTask(image_url) {

													protected void onPostExecute(Bitmap bm) {
														if (isCancelled()) {
															bm = null;
														}
														if (bm != null) {
															ImageBean bean = new ImageBean();
															bean.id = id;
															bean.bitmap = bm;
															listItemAdapter.putBitmap(bean.id, bean.bitmap);
															listItemAdapter.notifyDataSetChanged();
														}
													}
												};
												task.execute("");
											}

										}

									}

								}

							}

							removeDialog(Const.PROGRESSBAR_WAIT);

						}

					};
					// 异步获取信息,实现两个方法excute跟onCompleteWork
					Map<String, String> params = new HashMap<String, String>();
					showDialog(Const.PROGRESSBAR_WAIT);
					params.put("n", keyworld);
					params.put("from", _intent.getStringExtra("from"));
					params.put("p", "" + cur);

					asyncQueryHandler.doWork(params);
				} else {
					Toast( "已经到最后一页",1000);
				}

			}

		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				Intent intent = new Intent();

				if (productList.get(position).getBbCount() != "###") {
					intent.putExtra("product_id", productList.get(position).getPid());
					intent.putExtra("keyworld", keyworld);
					intent.setClass(_context, DetailActivityGroup.class);
					startActivity(intent);
				} else {
					intent.putExtra("title", productList.get(position).getTitle());
					String picurl = productList.get(position).getPictUrl();
					intent.putExtra("picurl", picurl);
					intent.putExtra("price", productList.get(position).getPrice());
					intent.putExtra("propliststr", productList.get(position).getPropListStr());
					intent.putExtra("sellercount", productList.get(position).getSellerCount() + "笔");
					String shopurl = productList.get(position).getProperty();
					intent.putExtra("shopurl", shopurl);
					intent.setClass(_context, DetailAuctionActivity.class);
					startActivity(intent);
				}
			}
		});

		goback.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				SearchMainActivity.this.finish();
			}
		});

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Const.PROGRESSBAR_WAIT:
			LoadingDialog wait_pd = new LoadingDialog(this);
			// ProgressDialog wait_pd = new ProgressDialog(this);
			// wait_pd.setMessage(Const.SEARCHING);
			return wait_pd;
		}
		return null;
	}
	
	/**
	 * 判断网络状态
	 * 
	 * @return netSataus
	 */
	public boolean net() {
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		cwjManager.getActiveNetworkInfo();
		boolean netSataus = false;
		if (cwjManager.getActiveNetworkInfo() != null) {
			netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		Log.e("网络是否可用：", netSataus + "");
		return netSataus;
	}

}