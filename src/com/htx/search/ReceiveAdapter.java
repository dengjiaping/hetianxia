package com.htx.search;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hetianxia.activity.R;
import com.htx.bean.AuthBean;
import com.htx.conn.DataFormalUtil;

/**
 * 
 * @author lvan
 *
 */
public class ReceiveAdapter extends BaseAdapter{
	
    private Context context;
    private ArrayList<AuthBean> receiveList;
    private int count;
    
    public ReceiveAdapter(Context c){
        context = c;
    }
	
    public int getCount() {
		if(receiveList!=null){
			count = receiveList.size();
		}else{
			count = 0;
		}
        return count;
    }
    
    public Object getItem(int position) {
        return position;
    }
    
    public long getItemId(int position) {
        return position;
    }

	public void setReceiveList(ArrayList<AuthBean> receiveList) {
		this.receiveList = receiveList;
	}
	
	public ArrayList<AuthBean> getReceiveList() {
		return receiveList;
	}
	
    /**
     * 分页查询更新list
     * @param _receiveList
     */
    public void containsList(ArrayList<AuthBean> _receiveList){
    	if(receiveList!=null){
    		for(AuthBean bean : _receiveList){
    			receiveList.add(bean);
    		}
    	}else{
    		receiveList = _receiveList;
    	}
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	//inflater对象可以把xml转换为view
    	LayoutInflater inflater = LayoutInflater.from(context);
    	View template = inflater.inflate(R.layout.receive_view, null);
    	
    	if(receiveList!=null){
    		
	    	TextView authName = (TextView)template.findViewById(R.id.authName);
	    	TextView receiveTime = (TextView)template.findViewById(R.id.receiveTime);
	    	TextView from = (TextView)template.findViewById(R.id.from);
	    	TextView content = (TextView)template.findViewById(R.id.content);
	    	
	    	authName.setText(receiveList.get(position).getAuthor());
	    	receiveTime.setText(receiveList.get(position).getCtime());
	    	from.setText(receiveList.get(position).getUrlName());
	    	content.setText(receiveList.get(position).getSummary());
	    	
    	}
    	
        return template;
    }
}
