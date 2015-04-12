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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MM_INFO_Fundburo extends Activity implements OnClickListener{
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private TextView tvTitle = null, tvDescription = null,tvPhone = null;
	private WebView mWVDescription = null;
	private String mTitle ="", mPhone="",mDescription="",mPhoneNumber="";
	private static final String ACTION = "info_fundburo";
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xinfo_fundburo);
		
		getBasicInfo();
		
		if( mClass.CheckNetwork(MM_INFO_Fundburo.this)){
			new get_FullDescription_Facts().execute(Constant.GETTING);
		}
	}
	/*******************************************************************************************/
	private void getBasicInfo(){
		
		mClass = new CommonClass();
		
		
		btn_Back = (Button)findViewById(R.id.btn_Back);
		btn_Home = (Button)findViewById(R.id.btn_Home);
		
		btn_Back.setOnClickListener(this);
		btn_Home.setOnClickListener(this);
		
		tvTitle = (TextView)findViewById(R.id.txt_title);
		tvDescription = (TextView)findViewById(R.id.txt_description);
		tvPhone = (TextView)findViewById(R.id.txt_phone_number);
		
		//mWVDescription = (WebView)findViewById(R.id.txt_description);
				
		tvPhone.setOnClickListener(this);
	}
	/*******************************************************************************************/
	public void onClick(View v) {
		if( v == btn_Home ){
			Intent in = new Intent(MM_INFO_Fundburo.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else if( v == btn_Back){
			finish();
		}else if( v == tvPhone ){
			getCall();
		}
	}
	
	/*******************************************************************************************/
	private void getCall(){
		Intent mIntentCall = new Intent( Intent.ACTION_DIAL);
		mIntentCall.setData( Uri.parse("tel:"+tvPhone.getText().toString()) );
		startActivity( mIntentCall );
	}
	/*******************************************************************************************/
	private void setText_Title_Phone(){
		tvTitle.setText( mTitle );
		tvDescription.setText( Html.fromHtml(mDescription) );
		//mWVDescription.loadData( mDescription, "text/html", "UTF-8");
		System.out.println("Desciption :- "+mDescription);
		tvPhone.setText( mPhoneNumber );
		
	}
	/*******************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_Facts extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_INFO_Fundburo.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(MM_INFO_Fundburo.this)){
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
								mTitle 	= 	jOb_desc.getString("title").toString();
								mDescription = jOb_desc.getString("description").toString();
								mPhoneNumber = jOb_desc.getString("phone_no").toString();
							}
							setText_Title_Phone();
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
