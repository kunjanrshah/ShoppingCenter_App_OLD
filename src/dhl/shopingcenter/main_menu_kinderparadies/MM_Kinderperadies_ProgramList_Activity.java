package dhl.shopingcenter.main_menu_kinderparadies;

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

public class MM_Kinderperadies_ProgramList_Activity extends Activity implements OnItemClickListener, OnClickListener {
	private ListView lst_kinderProgramList = null;
	private TextView tvTitleBar_Title = null;
	private BaseAdapter_KinderProgrammList mBaseAdapter_kinderProgramList = null;
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xshop_list);
	
		getBasicData();
		if( mClass.CheckNetwork(MM_Kinderperadies_ProgramList_Activity.this)){
			new get_ProgrammList_Server().execute(Constant.GETTING);
		}
	}
	/*************************************************************************************************************/
	private Bundle mBundle = null;
	private void getBasicData(){
		mClass = new CommonClass();
		
		lst_kinderProgramList = (ListView)findViewById(R.id.lst_shopList);
		
		
		
		lst_kinderProgramList.setOnItemClickListener(this);
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
		
		tvTitleBar_Title = (TextView)findViewById(R.id.txt_titleBar_displayIn);
		
		mBundle = getIntent().getExtras();
		if( mBundle != null ){
			if( mBundle.getString(Constant.BUNDLE_WHICH_CATAGORY).equals(Constant.KINDER_PROGRAMM_SUB))
					tvTitleBar_Title.setText( Constant.KINDER_PROGRAMM_SUB );
		}
	}
	/*************************************************************************************************************/
	private void getBaseAdapter_ProgramList(){
		mBaseAdapter_kinderProgramList = new BaseAdapter_KinderProgrammList(this,mAryList_Uid,mAryList_ProgrammTitle,mAryList_fromDate,mAryList_toDate);
		lst_kinderProgramList.setAdapter(mBaseAdapter_kinderProgramList);
	}
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String mTemp = mAryList_toDate.get(arg2).toString().substring(0, 5);
		String mDate = mTemp + " - " +mAryList_fromDate.get(arg2).toString();
		Intent mIntent = new Intent(MM_Kinderperadies_ProgramList_Activity.this, MM_Kinderperadies_ProgrammDetails_Activity.class);
		mIntent.putExtra(Constant.EVENT_ID, mAryList_Uid.get(arg2).toString() );
		mIntent.putExtra(Constant.EVENT_TITILE, mAryList_ProgrammTitle.get(arg2).toString() );
		mIntent.putExtra(Constant.KINDER_DETAIL_DATE, mDate);
		startActivity(mIntent);
	}
	/************************************************************************************************************/
	@Override
	public void onClick(View v) {
		Intent mIntent = null;
		if( v == btn_Back ){
			finish();
		}else
		if( v == btn_Home ){
			mIntent = new Intent(MM_Kinderperadies_ProgramList_Activity.this, MainMenu_Activity.class);
			mIntent.setFlags(mIntent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
		}		
	}
	/************************************************************************************************************/
	private ProcessDialog mProcessDialog = null;
	private static final String ACTION = "program";
	private ArrayList<String> mAryList_Uid = null, mAryList_ProgrammTitle = null, mAryList_fromDate = null, mAryList_toDate = null;
	
	private class get_ProgrammList_Server extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_Kinderperadies_ProgramList_Activity.this, Constant.GETTING, Constant.LOADING);
			mAryList_Uid = new ArrayList<String>();
			mAryList_ProgrammTitle = new ArrayList<String>();
			mAryList_toDate = new ArrayList<String>();
			mAryList_fromDate = new ArrayList<String>();
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result="";
			if( mClass.CheckNetwork(MM_Kinderperadies_ProgramList_Activity.this )){
				result= mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION, null);
			}
			return result;
		}
		
		
		protected void onPostExecute(String result) {
			if( result != null )
			{
				if( mClass.CheckNetwork(MM_Kinderperadies_ProgramList_Activity.this)){
					try {
						JSONObject jOB_getProgramm = new JSONObject(result);
						
						JSONArray jAryProgramm = jOB_getProgramm.getJSONArray("Data_array");
						for (int i = 0; i < jAryProgramm.length(); i++) {
							JSONObject jOb_Prog = jAryProgramm.getJSONObject(i);
							
							mAryList_Uid.add(jOb_Prog.getString("uid").toString());
							mAryList_ProgrammTitle.add(jOb_Prog.getString("title").toString());
							mAryList_toDate.add(jOb_Prog.getString("from_date").toString());
							mAryList_fromDate.add(jOb_Prog.getString("to_date").toString());
							
//							System.out.println(" "+jOb_Prog.getString("uid"));
//							System.out.println(" "+jOb_Prog.getString("title"));
//							System.out.println(" "+jOb_Prog.getString("from_date"));
//							System.out.println(" "+jOb_Prog.getString("to_date"));
							
						}
						getBaseAdapter_ProgramList();
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
