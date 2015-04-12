package dhl.shoppingcenter.main_menu_action;

import org.json.JSONArray;
import org.json.JSONObject;
import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.ProcessDialog;
import com.dhl.shoppingCenter.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Action_AngebotDetails_Activity extends Activity implements OnClickListener {
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private ImageView img_coupon_aswell_shop = null;
	private String mTitle = "", mDescription = "",mImageName="", uID="";
	private static final String ACTION = "particular_angebote&key=";
	private static String mDownloadLink = "";
	private TextView tvTitle = null, tvDescription = null,tvTitleBar_title = null;
	private Bundle mBundle = null;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.action_coupon_details);
		
		getBasicData();
		if( mClass.CheckNetwork(Action_AngebotDetails_Activity.this)){
			new get_FullDescription_Angebote().execute(Constant.GETTING);
		}
	}
	/**************************************************************************************************************/
	private void getBasicData(){
		img_coupon_aswell_shop = (ImageView)findViewById(R.id.img_coupon_aswell_shop);
		
		tvTitleBar_title = (TextView)findViewById(R.id.txt_titleBar_displayIn);
		tvTitle = (TextView)findViewById(R.id.txt_shop_title);
		tvDescription = (TextView)findViewById(R.id.txt_shop_description);
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
		
		mClass = new CommonClass();
		mBundle = getIntent().getExtras();
		if( mBundle !=null ){
			System.out.println( "Shop Id :-   "+mBundle.get(Constant.BUNDLE_SHOP).toString());
			uID = mBundle.get(Constant.BUNDLE_SHOP).toString();
			mTitle = mBundle.getString(Constant.ABGEBOT).toString();
		}
		tvTitle.setText( mTitle );
		tvTitleBar_title.setText( mTitle );
	}
	/**************************************************************************************************************/
	public void onClick(View v) 
	{
		if ( v == btn_Home ) {
			Intent in = new Intent(Action_AngebotDetails_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(in);
			finish();
		}
		if (v == btn_Back) {
			finish();
		}
	}
	/**************************************************************************************************************/
//	this is used for set the Imges
	private void setShopImages(){
		final String mShopImageURL = mDownloadLink + mImageName;
		if(mClass.CheckNetwork(this)){
			 new Thread(new Runnable() {
				    public void run() {
				      final Bitmap mBitmap = mClass.DownloadImagePost(mShopImageURL);
				      img_coupon_aswell_shop.post(new Runnable() {
				        public void run() {
				        	//img_coupon_aswell_shop.setImageBitmap(mBitmap);
				        	Drawable mDraw = new BitmapDrawable(mBitmap);
				        	img_coupon_aswell_shop.setBackgroundDrawable(mDraw);
				        }
				      });
				    }
				  }).start();
		}
	}
	/**************************************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_Angebote extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(Action_AngebotDetails_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(Action_AngebotDetails_Activity.this)){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION+uID, null);
				System.out.println("URL :--  "+Constant.WEBSERVICE_URL+ACTION+uID);
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
						System.out.println("Link :***   "+mDownloadLink);
						JSONArray jArray_Details = jOb_Detail.getJSONArray("Data_array");
						if( jArray_Details!=null)
						{
							for(int i=0;i<jArray_Details.length(); i++){
								JSONObject jOb_desc = jArray_Details.getJSONObject(i);
								
								mImageName = jOb_desc.getString("image_url").toString();
								mDescription = jOb_desc.getString("description").toString();
								
							}
							setShopImages();
							tvDescription.setText( Html.fromHtml(mDescription) );
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
