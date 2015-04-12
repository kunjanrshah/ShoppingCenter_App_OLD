package dhl.shoppingcenter.main_menu_events;

import java.util.ArrayList;
import java.util.Date;

import com.dhl.shoppingCenter.R;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BaseAdapter_EventList extends BaseAdapter{

	private Context mContext = null;
	private LayoutInflater mLayout = null;
	private ArrayList<String> mAryList_Title = null, mAryList_Date = null, mAryList_ebane= null;

	public BaseAdapter_EventList(Context mContext,
			ArrayList<String> mAryList_Title, ArrayList<String> mAryList_Date,
			ArrayList<String> mAryList_ebane){
		this.mContext = mContext;
		this.mLayout = LayoutInflater.from(mContext);
		this.mAryList_Title = mAryList_Title;
		this.mAryList_Date = mAryList_Date;
		this.mAryList_ebane = mAryList_ebane;
	}
	public int getCount() {
	
		return mAryList_Title.size();
	}

	
	public Object getItem(int arg0) {
	
		return null;
	}

	
	public long getItemId(int position) {
	
		return 0;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if( convertView == null ){
			convertView = mLayout.inflate(R.layout.custome_events, null);
		}
		
		TextView tvTitle = (TextView)convertView.findViewById(R.id.txt_event_title);
		TextView tvDate = (TextView)convertView.findViewById(R.id.txt_event_date);
		
		tvTitle.setText( mAryList_Title.get(position).toString() );
		String mTemp = " ( "+ mAryList_ebane.get(position).toString() +" )";
		tvDate.setText( mAryList_Date.get(position).toString() + mTemp );
		
//		Date mDate = new Date(1323973800);
//		DateFormat mDateFormat = new DateFormat();
//		System.out.println("Date in my format : - "+mDateFormat.format("dd.MM.yyyy", mDate).toString());
		
		return convertView;
	}

}
