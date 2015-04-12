package dhl.shoppingcenter.main_menu_info;

import org.json.JSONArray;
import org.json.JSONObject;
import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.ProcessDialog;
import com.dhl.shoppingCenter.R;
import dhl.shopingcenter.tab.Tab_Info_Activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MM_INFO_Kontakt_Activity extends Activity implements OnClickListener{
	private ImageView img_kontakt	= null;
	private CommonClass mClass = null;
	private Button btn_Home = null, btn_Back = null;
	private TextView tvName = null, tvAddress = null, tvPhone = null, tvEmail = null;
	private String mName = "", mAddress = "",mImageName="", uID="",mPhoneNumber="",mEmailID ="";
	private static String mDownloadLink = "";
	private static final String ACTION = "kontakt";
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xinfo_kontakt);
		
		getBasicInfo();
		if( mClass.CheckNetwork(MM_INFO_Kontakt_Activity.this)){
			new get_FullDescription_Kontakt().execute(Constant.GETTING);
		}
	}
	/*******************************************************************************************/
	private void getBasicInfo(){
		
		mClass = new CommonClass();
		img_kontakt = (ImageView)findViewById(R.id.img_kontakt);
		
		btn_Back = (Button)findViewById(R.id.btn_Back);
		btn_Home = (Button)findViewById(R.id.btn_Home);
		
		btn_Back.setOnClickListener(this);
		btn_Home.setOnClickListener(this);
		
		tvName = (TextView)findViewById(R.id.txt_name);
		tvAddress = (TextView)findViewById(R.id.txt_address);
		tvPhone = (TextView)findViewById(R.id.txt_phone_number);
		tvEmail = (TextView)findViewById(R.id.txt_emailID);
		
		tvEmail.setOnClickListener(this);
		tvPhone.setOnClickListener(this);
	}
	/*******************************************************************************************/
	public void onClick(View v) {
		if( v == btn_Home ){
			Intent in = new Intent(MM_INFO_Kontakt_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else if( v == btn_Back){
			finish();
		}else if( v == tvPhone ){
			getCall();
		}else if( v == tvEmail ){
			sendMail();
		}
	}
	/*******************************************************************************************/
	private void getCall(){
		Intent mIntentCall = new Intent( Intent.ACTION_DIAL);
		mIntentCall.setData( Uri.parse("tel:"+tvPhone.getText().toString()) );
		startActivity( mIntentCall );
	}
	/*******************************************************************************************/
	private void sendMail(){
		if(mClass.CheckNetwork(MM_INFO_Kontakt_Activity.this)){
			try{
			        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);	    	
			        emailIntent.setType("plain/text");
			        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ mEmailID});
			        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,Constant.EXTRA_SUBJECT);
			        startActivity(Intent.createChooser(emailIntent, "Send Mail..."));        
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		 }
	}
	/*******************************************************************************************/
	private void setText_Title_Phone(){
		tvName.setText( mName );
		tvAddress.setText( mAddress );
		tvPhone.setText( mPhoneNumber );
		tvEmail.setText( mEmailID );
	}
	/*******************************************************************************************/
	private void setShopImages(){
		final String mShopImageURL = mDownloadLink + mImageName;
		if(mClass.CheckNetwork(this)){
			 new Thread(new Runnable() {
				    public void run() {
				      final Bitmap mBitmap = mClass.DownloadImagePost(mShopImageURL);
				      img_kontakt.post(new Runnable() {
				        public void run() {
//				        	img_coupon_aswell_shop.setImageBitmap(mBitmap);
				        	Drawable mDraw = new BitmapDrawable(mBitmap);
				        	img_kontakt.setBackgroundDrawable(mDraw);
				        }
				      });
				    }
				  }).start();
		}
	}
	/*******************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_Kontakt extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_INFO_Kontakt_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(MM_INFO_Kontakt_Activity.this)){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION, null);
				System.out.println("URL :--  "+Constant.WEBSERVICE_URL+ACTION+uID);
				//http://180.211.110.195/typo3-projects/mobilecms/nusoap/shop/getmedia_abc.php?action=kontakt
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
						mDownloadLink = jOb_Detail.getString("download_link");
						System.out.println("Link :"+mDownloadLink);
						
						JSONArray jArray_Details = jOb_Detail.getJSONArray("Data_array");
						if( jArray_Details!=null)
						{
							for(int i=0;i<jArray_Details.length(); i++){
								JSONObject jOb_desc = jArray_Details.getJSONObject(i);
								mName 	= 	jOb_desc.getString("name").toString();
								mImageName = jOb_desc.getString("image_url").toString();
								mAddress = jOb_desc.getString("address").toString();
								mPhoneNumber = jOb_desc.getString("phoneno").toString();
								mEmailID	= jOb_desc.getString("email_id").toString();
							}
							setShopImages();
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
