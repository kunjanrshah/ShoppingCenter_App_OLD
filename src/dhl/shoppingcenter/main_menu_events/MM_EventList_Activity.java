package dhl.shoppingcenter.main_menu_events;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.ProcessDialog;
import com.dhl.shoppingCenter.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MM_EventList_Activity extends Activity implements OnClickListener, OnItemClickListener{
	private Button btn_Home = null, btn_Back = null;
	private Bundle mBundle = null;
	private CommonClass mClass = null;
	private TextView tvTitleBar = null;
	private ListView mList_Event = null;
	private String mHowtoDisplay;
	
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_list);
		
		getBundleData();
		getBasicData();
		
		if( mClass.CheckNetwork(MM_EventList_Activity.this)){
			new getEveentList_fromWeb().execute(Constant.GETTING);
		}
	}
	/************************************************************************************************************/
	
	private static final String EVENTS = " Events";
	private void getBasicData(){
		mClass = new CommonClass();
		tvTitleBar = (TextView)findViewById(R.id.txt_titleBar_displayIn);
		tvTitleBar.setText( mHowtoDisplay + EVENTS );
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);

	}
	/************************************************************************************************************/
	public void onClick(View v) {
		Intent mIntent = null;
		if( v == btn_Back ){
			finish();
		}else
		if( v == btn_Home ){
			mIntent = new Intent(MM_EventList_Activity.this, MainMenu_Activity.class);
			mIntent.setFlags(mIntent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
		}
	}
	/************************************************************************************************************/
	private void getBundleData(){
		mBundle = getIntent().getExtras();
		if( mBundle != null){
			mHowtoDisplay  = mBundle.getString(Constant.EVENT_BUNDLE);
			if( mHowtoDisplay.equals(Constant.EVENT_CURRENT )){
				ACTION = "aktuelle";
			}else
			if( mHowtoDisplay.equals(Constant.EVENT_FUTURE )){
				ACTION = "zukunftige";
			}
		}
	}
	/************************************************************************************************************/
	private BaseAdapter_EventList mBaseAdapter_EventList = null;
	private void getNumberOfEvents(){
		mList_Event = (ListView)findViewById(R.id.lst_eventList);
		
		mBaseAdapter_EventList = new BaseAdapter_EventList(this,mAryList_Tittle,mAryList_Date,mAryList_ebane);
		mList_Event.setAdapter(mBaseAdapter_EventList);
		
		mList_Event.setOnItemClickListener(this);
		
	}
	/************************************************************************************************************/
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent mIntent = new Intent(MM_EventList_Activity.this, MM_Event_inDetail_Activity.class);
		
		
		String temp = mAryList_Tittle.get(arg2).toString();
		if(temp.contains(" ")){
			String tempTitile = temp.substring(0, temp.indexOf(" "));
			mIntent.putExtra(Constant.EVENT_TITILE, tempTitile);
		}else{
			mIntent.putExtra(Constant.EVENT_TITILE, mAryList_Tittle.get(arg2).toString());
		}
		
		mIntent.putExtra(Constant.EVENT_TITTLE_FULL, mAryList_Tittle.get(arg2).toString());
		mIntent.putExtra(Constant.EVENT_ID, mAryList_Id.get(arg2).toString() );
		startActivity(mIntent);
	}
	
	/************************************************************************************************************/
	
	private ProcessDialog mProcessDialog = null;
	private static String ACTION = "";
	private ArrayList<String> mAryList_Tittle = null, mAryList_Id = null, mAryList_Date = null, mAryList_ebane;
	
	private class getEveentList_fromWeb extends AsyncTask<String, String, String>{
		
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_EventList_Activity.this, Constant.GETTING, Constant.LOADING);
			
			mAryList_Id 	= new ArrayList<String>();
			mAryList_Tittle = new ArrayList<String>();
			mAryList_Date 	= new ArrayList<String>();
			mAryList_ebane	= new ArrayList<String>();
			
			super.onPreExecute();
		}
		protected String doInBackground(String... params) {
			String result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION, null);
			return result;
		}
		
		protected void onPostExecute(String result) {
			if( result != null){
				if(mClass.CheckNetwork(MM_EventList_Activity.this)){
					try {
						JSONObject jOb_Detail = new JSONObject(result);
						System.out.println(jOb_Detail.getString("Data_array"));
						JSONArray jArry_Detail = jOb_Detail.getJSONArray("Data_array");
						
						for( int i = 0 ; i<jArry_Detail.length(); i++){
							JSONObject jOb = jArry_Detail.getJSONObject(i);
							mAryList_Id.add(jOb.getString("uid"));
							mAryList_Tittle.add(jOb.getString("title"));
							mAryList_Date.add(jOb.getString("event_date"));
							mAryList_ebane.add(jOb.getString("ebane"));
						}
//						System.out.println("1----------"+mAryList_Tittle.toString());
//						System.out.println("2----------"+mAryList_Date.toString());
						getNumberOfEvents();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			super.onPostExecute(result);
			mProcessDialog.dismiss();
		}
	}
}
