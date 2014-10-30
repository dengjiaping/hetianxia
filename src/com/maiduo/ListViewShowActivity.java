package com.maiduo;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hetianxia.activity.R;
import com.icq.demo.adapter.ListAdapter;
import com.icq.demo.beans.Item;
import com.icq.demo.db.BaseDBDao;
import com.shopping.Receive;

public class ListViewShowActivity extends Activity{
	private Context mContext;
	
	private ListView mLv_province,mLv_city,mLv_district;
	private ListAdapter mProvinceAdapter,mCityAdapter,mDistrictAdapter;
	private List<Item> provinces,cities,districts;
	
	private String pId,cId,dId;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		mContext=this;
		initView();
		initData();
		initOnItemClick();
		
	}

	private void initOnItemClick() {
		mLv_province.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				mProvinceAdapter.setSelectedPosition(position);
				mProvinceAdapter.notifyDataSetInvalidated(); 
				
				Item proItem=(Item) mProvinceAdapter.getItem(position);
				pId=proItem.getId();
				
				//二级�?
				cities=BaseDBDao.getCitys(pId, mContext);
				mCityAdapter=new ListAdapter(mContext, cities,0);
				mCityAdapter.notifyDataSetChanged();
				mLv_city.setAdapter(mCityAdapter);
				
				//三级�?
				districts=BaseDBDao.getCountrys(cities.get(0).getId(), mContext);
				mDistrictAdapter=new ListAdapter(mContext, districts,0);
				mDistrictAdapter.notifyDataSetChanged();
				mLv_district.setAdapter(mDistrictAdapter);
				
			}
		});
		mLv_city.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				
				mCityAdapter.setSelectedPosition(position);
				mCityAdapter.notifyDataSetInvalidated(); 
				
				Item proItem=(Item) mCityAdapter.getItem(position);
				cId=proItem.getId();
				
				districts=BaseDBDao.getCountrys(cId, mContext);
				mDistrictAdapter=new ListAdapter(mContext, districts,0);
				mDistrictAdapter.notifyDataSetChanged();
				mLv_district.setAdapter(mDistrictAdapter);
				
				
			}
		});
		
		
		mLv_district.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				mDistrictAdapter.setSelectedPosition(position);
				mDistrictAdapter.notifyDataSetInvalidated(); 
				
				Item proItem=(Item) mDistrictAdapter.getItem(position);
				dId=proItem.getId();
				
				//�жϿգ��ҾͲ��ж��ˡ�������  
	            Intent data=new Intent();  
	            data.putExtra("sheng", ((Item)mProvinceAdapter.getItem(position)).getName());  
	            data.putExtra("shi", ((Item)mCityAdapter.getItem(position)).getName());  
	            data.putExtra("xian",((Item)mDistrictAdapter.getItem(position)).getName());  
	            //�����������Լ����ã��������ó�20  
	            setResult(100, data);  
	            //�رյ����Activity  
	            finish(); 
	            
			}
		});
		
		
		
	}



	private void initData() {
		provinces=BaseDBDao.getProvinces(mContext);
		mProvinceAdapter=new ListAdapter(mContext, provinces,0);
		mLv_province.setAdapter(mProvinceAdapter);
		
		cities=BaseDBDao.getCitys(provinces.get(0).getId(), mContext);
		mCityAdapter=new ListAdapter(mContext, cities,0);
		mLv_city.setAdapter(mCityAdapter);
		
		districts=BaseDBDao.getCitys(cities.get(0).getId(), mContext);
		mDistrictAdapter=new ListAdapter(mContext, districts,0);
		mLv_district.setAdapter(mDistrictAdapter);
		
	}



	private void initView() {
		mLv_province=(ListView) findViewById(R.id.list_province);
		mLv_city=(ListView) findViewById(R.id.list_city);
		mLv_district=(ListView) findViewById(R.id.list_district);
	}


	


}
