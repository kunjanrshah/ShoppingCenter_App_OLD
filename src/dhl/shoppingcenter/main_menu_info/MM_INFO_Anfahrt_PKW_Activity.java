package dhl.shoppingcenter.main_menu_info;

import org.json.JSONArray;
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
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MM_INFO_Anfahrt_PKW_Activity extends Activity implements OnClickListener{

	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private static final String ACTION = "pkw";
	private TextView tvDescription = null, tvRoller = null, tvGoogleMap = null,tvAddress = null;
	private String mDescription ="",mRoller ="",mLatitude="",mLongitude="",mAddress ="";
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xinfo_anfahrt_pkwundzweirad);
		
		getBasicInfo();
		if( mClass.CheckNetwork(this)){
			new get_FullDescription_Roller().execute(Constant.GETTING);
		}
	}
	/**********************************************************************************************/
	private void getBasicInfo(){
		
		mClass = new CommonClass();
		
		
		btn_Back = (Button)findViewById(R.id.btn_Back);
		btn_Home = (Button)findViewById(R.id.btn_Home);
		
		btn_Back.setOnClickListener(this);
		btn_Home.setOnClickListener(this);
		
		tvDescription = (TextView)findViewById(R.id.txt_description);
		tvRoller = (TextView)findViewById(R.id.txt_roller);
		tvAddress =(TextView)findViewById(R.id.txt_address);
		tvGoogleMap = (TextView)findViewById(R.id.txt_googlemap);
		tvGoogleMap.setOnClickListener(this);
	
	}
	/**********************************************************************************************/
	public void onClick(View v) {
		if( v == btn_Home ){
			Intent in = new Intent(MM_INFO_Anfahrt_PKW_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else if( v == btn_Back){
			finish();
		}else if( v == tvGoogleMap ){
			String full = mLatitude.toString() + "," + mLongitude.toString();
		     try {
		         Intent geoIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("geo:0,0?q="+ full));
		         startActivity(geoIntent);
		     }catch(Exception e){
		    	 e.printStackTrace();
		     }
		}
	}
	/*******************************************************************************************/
	private void setText_Title_Roller(){
		tvRoller.setText( mRoller );
		tvAddress.setText( mAddress );
		tvDescription.setText( Html.fromHtml(mDescription) );
		System.out.println("Desciption :- "+mDescription);
	}
	/*******************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_Roller extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_INFO_Anfahrt_PKW_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(MM_INFO_Anfahrt_PKW_Activity.this)){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION, null);
				System.out.println("URL :--  "+Constant.WEBSERVICE_URL+ACTION );
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
								mRoller 	= 	jOb_desc.getString("roller").toString();
								mDescription = jOb_desc.getString("autobahnausfahrt").toString();
								mLatitude = jOb_desc.getString("latitude");
								mLongitude = jOb_desc.getString("longitude");
								mAddress = jOb_desc.getString("addresse");
							}
							setText_Title_Roller();
						}
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
			mProcessDialog.dismiss();
		}
		
	}
	/*****************************************************************************************************/
}
