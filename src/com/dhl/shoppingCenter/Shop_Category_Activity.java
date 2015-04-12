package com.dhl.shoppingCenter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

public class Shop_Category_Activity extends Activity implements OnClickListener, OnItemClickListener
{
	private Bundle mBundle = null;
	private String mHowtoDisplay;
	private TextView tvTitleBar = null;
	private ListView mList_shop = null;
	private BaseAdapter_ShopList mBaseAdapter_ShopList = null;
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private String whichData = "";
	private ArrayList<String> mAryList_ShopID	= null;
	private ArrayList<String> mAryList_ShopTitle = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xshop_list);
		
		getBundleData();
		getBasicData();
		
		if (mClass.CheckNetwork(Shop_Category_Activity.this)) {
			new getShopList().execute(Constant.GETTING);
		}
	}
	/**********************************************************************************************************/
	private void getBundleData(){
		mBundle = getIntent().getExtras();
		mHowtoDisplay = mBundle.getString(Constant.BUNDLE_WHICH_CATAGORY);
		if( mHowtoDisplay.equals(Constant.BUNDLE_ABC))
			whichData = "abc";
		else
			if ( mHowtoDisplay.equals(Constant.BUNDLE_BRANCHE))
				whichData = "branche";
	}
	/**********************************************************************************************************/
	private void getBasicData(){
		
		mClass = new CommonClass();
		
		
		tvTitleBar = (TextView)findViewById(R.id.txt_titleBar_displayIn);
		tvTitleBar.setText( mHowtoDisplay );
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
		
	}
	/**********************************************************************************************************/
	private void getList_shop_from_webservice(){
				
		mList_shop = (ListView) findViewById(R.id.lst_shopList);
				
		mBaseAdapter_ShopList = new BaseAdapter_ShopList(this, mAryList_ShopTitle);
		mList_shop.setAdapter(mBaseAdapter_ShopList);
		mList_shop.setOnItemClickListener(this);
	}

	/**********************************************************************************************************/
	public void onClick(View v)
	{	
		Intent in = null;
		if( v == btn_Home ){
			in = new Intent(Shop_Category_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else if( v == btn_Back ){
			finish();
		}
	}

	/**********************************************************************************************************/
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		Intent in = new Intent(Shop_Category_Activity.this, Shop_details_Activity.class);
		if( mBundle.getString(Constant.BUNDLE_WHICH_CATAGORY).equals(Constant.BUNDLE_ABC)){
			Intent in = new Intent(Shop_Category_Activity.this, Shop_details_Activity.class);
			in.putExtra(Constant.BUNDLE_SHOP, mAryList_ShopID.get(arg2).toString() );
			in.putExtra(Constant.BUNDEL_SHOP_NAME, mAryList_ShopTitle.get(arg2).toString() );
			startActivity(in);
		}else if( mBundle.getSerializable(Constant.BUNDLE_WHICH_CATAGORY).equals(Constant.BUNDLE_BRANCHE)){
			Intent in = new Intent(Shop_Category_Activity.this, Shop_BranchvisList_Activity.class);
			in.putExtra(Constant.BUNDLE_SHOP, mAryList_ShopID.get(arg2).toString());
			in.putExtra(Constant.BUNDLE_BRANCHSHOP, mAryList_ShopTitle.get(arg2).toString());
			startActivity(in);
		}
		
	}
	/**********************************************************************************************************/
	private ProcessDialog mProgress = null;
	private class getShopList extends AsyncTask<String, String, String>{

		protected void onPreExecute() {
			super.onPreExecute();
			mAryList_ShopID = new ArrayList<String>();
			mAryList_ShopTitle = new ArrayList<String>();
			
			mProgress = new ProcessDialog(Shop_Category_Activity.this, Constant.GETTING, Constant.LOADING);
		}
		protected String doInBackground(String... params) {
			String result="";
			if( mClass.CheckNetwork( Shop_Category_Activity.this )){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+whichData, null);
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
							
							if ( whichData.equals("abc")){
								mAryList_ShopTitle.add( jOb.getString("shop_title").toString() );	}	// add data into arrayList for shoptitle
							else {
								mAryList_ShopTitle.add( jOb.getString("branch_title").toString() ); }
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
}
