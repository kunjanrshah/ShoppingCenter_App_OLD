package dhl.shoppingcenter.main_menu_events;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MM_Event_inDetail_Activity extends Activity implements OnClickListener{
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private ImageView img_event_details = null;
	private String mTitleBar = "", mImageName="", uID="",mPhoneNumber="";
	private static final String ACTION = "particular_aktuelle&key=";
	private Bundle mBundle = null;
	private TextView tvTitle = null, tvTitlebar_Title = null, tvDescription = null;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.action_coupon_details);
		
		getBasicData();
		if( mClass.CheckNetwork(MM_Event_inDetail_Activity.this)){
			new get_FullDescription_Events().execute(Constant.GETTING);
		}
	}
	
	/**************************************************************************************************************/
	private void getBasicData(){
		img_event_details = (ImageView)findViewById(R.id.img_coupon_aswell_shop);
		
		tvTitle = (TextView)findViewById(R.id.txt_shop_title);
		tvTitlebar_Title = (TextView)findViewById(R.id.txt_titleBar_displayIn);
		tvDescription = (TextView)findViewById(R.id.txt_shop_description);
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
		
		mClass = new CommonClass();
		mBundle = getIntent().getExtras();
		String full_titlle = "";
		if( mBundle !=null ){
			uID = mBundle.getString(Constant.EVENT_ID).toString();
			mTitleBar = mBundle.getString(Constant.EVENT_TITILE).toString();
			full_titlle = mBundle.getString(Constant.EVENT_TITTLE_FULL).toString();
			
		}
		tvTitlebar_Title.setText( mTitleBar );
		tvTitle.setText( full_titlle );
	}
	/**************************************************************************************************************/

	@Override
	public void onClick(View v) {
		if ( v == btn_Home ) {
			Intent in = new Intent(MM_Event_inDetail_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
		}
		if (v == btn_Back) {
			finish();
		}
	}
	/**************************************************************************************************************/
	//		this is used for set the Imges
	private static String mDownloadLink = "";
	private void setShopImages(){
		final String mShopImageURL = mDownloadLink + mImageName;
		if(mClass.CheckNetwork(this)){
			 new Thread(new Runnable() {
				    public void run() {
				      final Bitmap mBitmap = mClass.DownloadImagePost(mShopImageURL);
				      img_event_details.post(new Runnable() {
				        public void run() {
				        	//img_event_details.setImageBitmap(mBitmap);
				        	Drawable mDrawable = new BitmapDrawable(mBitmap);
				        	img_event_details.setBackgroundDrawable(mDrawable);
				        }
				      });
				    }
				  }).start();
		}
	}
	/**************************************************************************************************************/
	private String mDescriptionEvents="";
	private void setText_Description(){
		tvDescription.setText( mDescriptionEvents );
	}
	/**************************************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_Events extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_Event_inDetail_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(MM_Event_inDetail_Activity.this)){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION+uID, null);
				System.out.println("EVENT URL :--  "+Constant.WEBSERVICE_URL+ACTION+uID);
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
								
								mImageName = jOb_desc.getString("image_url").toString();
								mDescriptionEvents = jOb_desc.getString("description");
								
							}
							setShopImages();
							setText_Description();
						}
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
