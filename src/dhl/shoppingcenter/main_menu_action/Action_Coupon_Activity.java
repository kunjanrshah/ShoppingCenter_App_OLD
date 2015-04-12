package dhl.shoppingcenter.main_menu_action;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.ProcessDialog;
import com.dhl.shoppingCenter.R;
import com.dhl.shoppingCenter.R.color;

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
import android.widget.LinearLayout;
import android.widget.TextView;

public class Action_Coupon_Activity extends Activity implements OnClickListener{
	private static final String ACTION = "couponzeitung";
	private static String mDownloadLink = "";
	private LinearLayout mLinear_Details = null;
	private ImageView imgCouponShop = null;
	private TextView tvTitle = null,tvDescription = null;
	private Button btn_Back = null, btn_Home = null;
	private String mTitle="",mImageName="",mDescription="";
	private Bundle mBundle = null;
	private CommonClass mClass = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.action_coupon_details);
		
		getBasicData();
		
		if( mClass.CheckNetwork(Action_Coupon_Activity.this)){
			new get_FullDescription_Coupon().execute(Constant.GETTING);
		}
	}
	/**************************************************************************************************************/
	private void getBasicData(){
		
		mBundle = getIntent().getExtras();
		if ( mBundle != null ){
			mTitle = mBundle.getString(Constant.BUNDLE_WHICH_CATAGORY);
			System.out.println("mTitle :"+mTitle);
		}
		
		mClass = new CommonClass();
		
		mLinear_Details = (LinearLayout)findViewById(R.id.linear_Details);
				
		imgCouponShop = (ImageView)findViewById(R.id.img_coupon_aswell_shop);
		
		tvTitle 	=	(TextView)findViewById(R.id.txt_titleBar_displayIn);
		tvDescription = (TextView)findViewById(R.id.txt_shop_description);
		tvTitle.setText(mTitle);
		
		btn_Back	=	(Button)findViewById(R.id.btn_Back);
		btn_Home	=	(Button)findViewById(R.id.btn_Home);
		
		btn_Back.setOnClickListener(this);
		btn_Home.setOnClickListener(this);
		
		imgCouponShop = (ImageView)findViewById(R.id.img_coupon_aswell_shop);
		
	}
	/**************************************************************************************************************/
	@Override
	public void onClick(View v) 
	{
		Intent mIntent = null;
		if( v == btn_Back){
			finish();
		}else 
			if( v == btn_Home){
				mIntent = new Intent( Action_Coupon_Activity.this, MainMenu_Activity.class);
				mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(mIntent);
				finish();
			}
		
	}
	/**************************************************************************************************************/
	//		this is used for set the Imges
	private void setCouponImages() {
		final String mShopImageURL = mDownloadLink + mImageName;
		if (mClass.CheckNetwork(this)) {
			new Thread(new Runnable() {
				public void run() {
					final Bitmap mBitmap = mClass
							.DownloadImagePost(mShopImageURL);
					imgCouponShop.post(new Runnable() {
						public void run() {
							//imgCouponShop.setImageBitmap(mBitmap);
							Drawable mDraw = new BitmapDrawable(mBitmap);
							imgCouponShop.setBackgroundDrawable(mDraw);
						}
					});
				}
			}).start();
		}
	}
	
	/**************************************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_Coupon extends AsyncTask<String, String, String>{
		
		protected void onPreExecute() {
			System.out.println("this is onpre execute method");
			mProcessDialog = new ProcessDialog(Action_Coupon_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(Action_Coupon_Activity.this)){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION, null);
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result)
		{
		
			super.onPostExecute(result);
		
			if( result != null )
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
								mDescription = jOb_desc.getString("description").toString();
							}
							System.out.println("Description :- "+mDescription);
							tvDescription.setText(mDescription);
							setCouponImages();
							
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
