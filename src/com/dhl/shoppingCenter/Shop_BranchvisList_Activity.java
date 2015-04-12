package com.dhl.shoppingCenter;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
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

public class Shop_BranchvisList_Activity extends Activity implements OnClickListener, OnItemClickListener{
	private Bundle mBundle = null;
	private String mHowtoDisplay;
	private TextView tvTitleBar = null;
	private ListView mList_Branchshop = null;
	private BaseAdapter_ShopList mBaseAdapter_ShopList = null;
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private static final String ACTION = "particular_branche&key=";
	private static String mID="";
	private ArrayList<String> mAryList_ShopID	= null;
	private ArrayList<String> mAryList_ShopTitle = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xshop_list);
		
		getBasicData();
		getBundleData();
		
		if( mClass.CheckNetwork(Shop_BranchvisList_Activity.this) ){
			new getBranchvisShopList().execute(Constant.GETTING);
		}
		System.out.println("Branch vis shoplist ");
	}
	
	/****************************************************************************************************************/
	private void getBasicData(){
		
		mClass = new CommonClass();
		
		tvTitleBar = (TextView)findViewById(R.id.txt_titleBar_displayIn);
		
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
		
	}
	/****************************************************************************************************************/
	private void getBundleData(){
		mBundle = getIntent().getExtras();
		if( mBundle != null){
			mID = mBundle.getString(Constant.BUNDLE_SHOP);
			mHowtoDisplay = mBundle.getString(Constant.BUNDLE_BRANCHSHOP);
		}
		tvTitleBar.setText( mHowtoDisplay );
	}
	/****************************************************************************************************************/
	public void onClick(View v) {
		Intent in = null;
		if( v == btn_Home ){
			in = new Intent(Shop_BranchvisList_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else if( v == btn_Back ){
			finish();
		}
	}
	/****************************************************************************************************************/
	
	private ProcessDialog mProgress = null;
	private class getBranchvisShopList extends AsyncTask<String, String, String>{

		protected void onPreExecute() {
			super.onPreExecute();
			mAryList_ShopID = new ArrayList<String>();
			mAryList_ShopTitle = new ArrayList<String>();
			
			mProgress = new ProcessDialog(Shop_BranchvisList_Activity.this, Constant.GETTING, Constant.LOADING);
		}
		protected String doInBackground(String... params) {
			String result="";
			if( mClass.CheckNetwork( Shop_BranchvisList_Activity.this )){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION+mID, null);
				System.out.println("Branch shop list URL :-   "+Constant.WEBSERVICE_URL+ACTION+mID);
				System.out.println("Fatch Data :- "+result);
			}
			return result;
		}
		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if( result != null){
				try {
					JSONObject jOb_Data = new JSONObject(result);
					if( jOb_Data != null){
						System.out.println("Json Obj :-  "+jOb_Data.get("Data_array").toString());
						JSONArray mJarray_shopList = jOb_Data.getJSONArray("Data_array");
						for(int i=0; i<mJarray_shopList.length(); i++){
							JSONObject jOb	= mJarray_shopList.getJSONObject(i);
							mAryList_ShopID.add( jOb.getString("uid").toString() );					// add data into arrayList for ShopID
							mAryList_ShopTitle.add( jOb.getString("shop_title").toString() ); 
						}
					getList_shop_from_webservice();		
					}
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
			mProgress.dismiss();
		}
		
	}
	
	/****************************************************************************************************************/
	
	private void getList_shop_from_webservice(){
		
		mList_Branchshop = (ListView) findViewById(R.id.lst_shopList);
				
		mBaseAdapter_ShopList = new BaseAdapter_ShopList(this, mAryList_ShopTitle);
		mList_Branchshop.setAdapter(mBaseAdapter_ShopList);
		mList_Branchshop.setOnItemClickListener(this);
	}
	/****************************************************************************************************************/
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent in = new Intent(Shop_BranchvisList_Activity.this, Shop_details_Activity.class);
		in.putExtra(Constant.BUNDLE_SHOP, mAryList_ShopID.get(arg2).toString() );
		in.putExtra(Constant.BUNDEL_SHOP_NAME, mAryList_ShopTitle.get(arg2).toString() );
		startActivity(in);	
	}
	/****************************************************************************************************************/
}
