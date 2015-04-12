package dhl.shoppingcenter.impresum;

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
import android.widget.Button;
import android.widget.TextView;

public class MM_Impresum_Umsetzung extends Activity implements OnClickListener{
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private static final String ACTION = "umsetzung";
	private String mTitle ="",mAddress="",mPhoneNumber="",mEmailId="",mWeb_url="";
	private TextView tvTitle = null, tvAddress = null, tvPhone = null,tvEmail = null, tvWeb = null;
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ximpressum_umsetzung);
		
		getBasicData();
		if( mClass.CheckNetwork(this)){
			new get_FullDescription_Umsetzung().execute(Constant.GETTING);
		}
	}
	
	/*******************************************************************************************************/
	private void getBasicData(){
		mClass = new CommonClass();
		
		btn_Back = (Button)findViewById(R.id.btn_Back);
		btn_Home = (Button)findViewById(R.id.btn_Home);
		
		btn_Back.setOnClickListener(this);
		btn_Home.setOnClickListener(this);
		
		tvTitle = (TextView)findViewById(R.id.txt_title);
		tvAddress = (TextView)findViewById(R.id.txt_address);
		tvPhone = (TextView)findViewById(R.id.txt_phone_number);
		tvEmail = (TextView)findViewById(R.id.txt_emailID);
		tvWeb = (TextView)findViewById(R.id.txt_webURL);
		
		tvPhone.setOnClickListener(this);
		tvEmail.setOnClickListener(this);
		tvWeb.setOnClickListener(this);
	}
	/*******************************************************************************************************/
	public void onClick(View v) {
		if( v == btn_Home ){
			Intent in = new Intent(MM_Impresum_Umsetzung.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else if( v == btn_Back){
			finish();
		}else if( v == tvPhone ){
			Intent mIntentDial = new Intent(Intent.ACTION_DIAL);
			mIntentDial.setData( Uri.parse("tel:"+tvPhone.getText().toString()) );
			startActivity(mIntentDial);
		}else if( v == tvEmail ){
			sendMail();
		}else if( v == tvWeb ){
			getWebSite();
		}
	}
	
	/*******************************************************************************************************/
	private void sendMail(){
		if(mClass.CheckNetwork(MM_Impresum_Umsetzung.this)){
			try{
			        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);	    	
			        emailIntent.setType("plain/text");
			        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ mEmailId });
			        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,Constant.EXTRA_SUBJECT);
			        startActivity(Intent.createChooser(emailIntent, "Send Mail..."));        
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		 }
	}
	
	private void getWebSite(){
		Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+mWeb_url.toString()));
		startActivity(in);
	}
	/*******************************************************************************************************/
	private void setText_Title_(){
		tvTitle.setText( mTitle );
		tvAddress.setText( Html.fromHtml(mAddress) );
		tvPhone.setText( mPhoneNumber );
		tvEmail.setText( mEmailId );
		tvWeb.setText( mWeb_url );
		
	}
	/*******************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_Umsetzung extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_Impresum_Umsetzung.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(MM_Impresum_Umsetzung.this)){
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
								mTitle 		= 	jOb_desc.getString("title").toString();
								mAddress 	= jOb_desc.getString("address").toString();
								mPhoneNumber = jOb_desc.getString("phone_no").toString();
								mEmailId 	= jOb_desc.getString("email_id").toString();
								mWeb_url	= jOb_desc.getString("web_url").toString();
							}
							setText_Title_();
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
