package dhl.shopingcenter.main_menu_kinderparadies;

import java.util.ArrayList;

import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BaseAdapter_KinderProgrammList extends BaseAdapter{

	private Context mContext = null;
	private LayoutInflater mLayout = null;
	private ArrayList<String> mAryList_ProgrammTitle = null, mAryList_fromDate = null, mAryList_toDate = null;
	
	public BaseAdapter_KinderProgrammList(Context mContext,ArrayList<String> id, ArrayList<String> title, ArrayList<String> fDate, ArrayList<String> tDate){
		this.mContext = mContext;
		this.mLayout = LayoutInflater.from(mContext);
		this.mAryList_ProgrammTitle = title;
		this.mAryList_fromDate = fDate;
		this.mAryList_toDate = tDate;
	}
	
	public int getCount() {

		return mAryList_ProgrammTitle.size();
	}


	public Object getItem(int position) {

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

		tvTitle.setText( mAryList_ProgrammTitle.get(position).toString() );
		
		String mFormat = Constant.FROM + " " + mAryList_toDate.get(position).toString() + " " + Constant.TO
											+ " "+mAryList_fromDate.get(position).toString();
		
		tvDate.setText(mFormat);
		return convertView;
	}
}
