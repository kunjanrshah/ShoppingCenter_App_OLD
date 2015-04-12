package com.dhl.shoppingCenter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BaseAdapter_ShopList extends BaseAdapter
{
	private Context mContext = null;
	private LayoutInflater mLayout = null;
	private ArrayList<String> mAryList_ShopTitle;
	public BaseAdapter_ShopList(Context mContext, ArrayList<String> mAryList_ShopTitle ){
		this.mContext = mContext;
		this.mLayout = LayoutInflater.from(mContext);
		this.mAryList_ShopTitle	=	mAryList_ShopTitle;
		System.out.println("In Base Adp :- "+ mAryList_ShopTitle.toString() );
	}
	public int getCount() {
	
		return mAryList_ShopTitle.size();
	}

	
	public Object getItem(int position) {
	
		return null;
	}

	
	public long getItemId(int position) {
	
		return 0;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		if( convertView == null){
			convertView = mLayout.inflate(R.layout.custome_list, null);
		}
		
		TextView tvShopTitle = (TextView)convertView.findViewById(R.id.txt_name_list);
		tvShopTitle.setText( mAryList_ShopTitle.get(position).toString() );
		return convertView;
	}

}
