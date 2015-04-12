package dhl.shoppingcenter.main_menu_action;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import com.dhl.shoppingCenter.BaseAdapter_ShopList;
import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.ProcessDialog;
import com.dhl.shoppingCenter.R;
import com.dhl.shoppingCenter.Shop_Category_Activity;
import com.dhl.shoppingCenter.Shop_details_Activity;

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

public class Action_Abgebot_Activity extends Activity implements OnClickListener, OnItemClickListener {
	private ListView lst_abgebot = null;
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private BaseAdapter_ShopList mBaseAdapter_ShopList = null;
	private ArrayList<String> mAryListAngeboteId = null,mAryListAngeboteTitle = null;
	private static final String ACTION = "angebote";
	private TextView tvTitlebarTitle = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xshop_list);
		
		getBasicData();
		
		if( mClass.CheckNetwork(Action_Abgebot_Activity.this)){
			new get_Angebote_info().execute(Constant.GETTING);
		}
	}
	/**********************************************************************************************************/
	private void getBasicData(){
		
		tvTitlebarTitle = (TextView)findViewById(R.id.txt_titleBar_displayIn);
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Back.setOnClickListener(this);
		btn_Home.setOnClickListener(this);
	
		lst_abgebot = (ListView)findViewById(R.id.lst_shopList);
		lst_abgebot.setOnItemClickListener(this);
		
		mAryListAngeboteId = new ArrayList<String>();
		mAryListAngeboteTitle = new ArrayList<String>();
		
		mClass = new CommonClass();
		
		tvTitlebarTitle.setText( Constant.ANGEBOTE );
	}
	/**********************************************************************************************************/
	public void onClick(View v) {
		if( v == btn_Back ){
			finish();
		}else
			if( v == btn_Home ){
				Intent mIntent = new Intent( Action_Abgebot_Activity.this, MainMenu_Activity.class );
				mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(mIntent);
			}
	}
	/**********************************************************************************************************/
	private void getBaseAdapter_data(){
		mBaseAdapter_ShopList = new BaseAdapter_ShopList(Action_Abgebot_Activity.this, mAryListAngeboteTitle);
		lst_abgebot.setAdapter(mBaseAdapter_ShopList);
	}
	/**********************************************************************************************************/
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent in = new Intent(Action_Abgebot_Activity.this, Action_AngebotDetails_Activity.class);
		in.putExtra(Constant.BUNDLE_SHOP, mAryListAngeboteId.get(arg2).toString() );
		in.putExtra(Constant.ABGEBOT, mAryListAngeboteTitle.get(arg2).toString() );
		startActivity(in);
	}
	/**********************************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_Angebote_info extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			
			mProcessDialog = new ProcessDialog(Action_Abgebot_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(Action_Abgebot_Activity.this)){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION, null);
				System.out.println("URL :--  "+Constant.WEBSERVICE_URL+ACTION);
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result)
		{
		
			super.onPostExecute(result);
			
			if( result != null)
			{
				try {
					JSONObject jOb_Detail = new JSONObject(result);
					if( jOb_Detail!=null)
					{					
						JSONArray jArray_Details = jOb_Detail.getJSONArray("Data_array");
						if( jArray_Details!=null)
						{
							for(int i=0;i<jArray_Details.length(); i++){
								JSONObject jOb_desc = jArray_Details.getJSONObject(i);
								mAryListAngeboteId.add( jOb_desc.getString("uid").toString() );
								mAryListAngeboteTitle.add( jOb_desc.getString("title").toString() );
							}
							//System.out.println("Ag    "+mAryListAngeboteTitle.toString());
						}
						getBaseAdapter_data();
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			mProcessDialog.dismiss();
		}
	}
	/**************************************************************************************************************/
}
