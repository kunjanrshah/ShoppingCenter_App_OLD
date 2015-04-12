package dhl.shopingcenter.tab;

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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Tab_Info_Activity extends Activity implements OnClickListener{
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private ImageView img_coupon_aswell_shop = null;
	private String mTitle = "", mContact_person_name = "",mImageName="", uID="",mPhoneNumber="";
	private static final String ACTION = "particular_shop&key=";
	private static String mDownloadLink = "";
	private TextView tvTitle_TitleBar = null,tvTitle = null, tvContactPerson = null, tvPhoneNumber = null;
	private Bundle mBundle = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_info);
		
		getBasicData();
		if( mClass.CheckNetwork(Tab_Info_Activity.this))
			new get_FullDescription_shop().execute(Constant.GETTING);
	}
	/**************************************************************************************************************/
	private void getBasicData(){
		img_coupon_aswell_shop = (ImageView)findViewById(R.id.img_coupon_aswell_shop);
		
		tvTitle_TitleBar = (TextView)findViewById(R.id.txt_titleBar_displayIn);
		tvTitle = (TextView)findViewById(R.id.txt_shop_title);
		tvContactPerson = (TextView)findViewById(R.id.txt_Contact_person);
		tvPhoneNumber = (TextView)findViewById(R.id.txt_phone_number);
		tvPhoneNumber.setOnClickListener(this);
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
		
		mClass = new CommonClass();
		mBundle = getIntent().getExtras();
		if( mBundle !=null ){
			System.out.println( "Shop Id :-   "+mBundle.get(Constant.BUNDLE_SHOP).toString());
			uID = mBundle.get(Constant.BUNDLE_SHOP).toString();
			tvTitle_TitleBar.setText(mBundle.getString(Constant.BUNDEL_SHOP_NAME).toString());
		}
		
	}
	/**************************************************************************************************************/
	@Override
	public void onClick(View v) 
	{
		if ( v == btn_Home ) {
			Intent in = new Intent(Tab_Info_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else
		if (v == btn_Back) {
			finish();
		}else
		if( v == tvPhoneNumber){
			Intent mIntentDial = new Intent(Intent.ACTION_DIAL);
			mIntentDial.setData( Uri.parse("tel:"+tvPhoneNumber.getText().toString()) );
			startActivity(mIntentDial);
		}
	}
	
	/**************************************************************************************************************/
	//		this is used for set the Imges
	private void setShopImages(){
		final String mShopImageURL = mDownloadLink + mImageName;
		if(mClass.CheckNetwork(this)){
			 new Thread(new Runnable() {
				    public void run() {
				      final Bitmap mBitmap = mClass.DownloadImagePost(mShopImageURL);
				      img_coupon_aswell_shop.post(new Runnable() {
				        public void run() {
//				        	img_coupon_aswell_shop.setImageBitmap(mBitmap);
				        	Drawable mDraw = new BitmapDrawable(mBitmap);
				        	img_coupon_aswell_shop.setBackgroundDrawable(mDraw);
				        }
				      });
				    }
				  }).start();
		}
	}
	/**************************************************************************************************************/
	private void setText_Title_Phone(){
		tvTitle.setText( mTitle );
		tvContactPerson.setText( mContact_person_name );
		tvPhoneNumber.setText( mPhoneNumber );
	}
	/**************************************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_shop extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(Tab_Info_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(Tab_Info_Activity.this)){
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
						System.out.println("Link :"+mDownloadLink);
						
						JSONArray jArray_Details = jOb_Detail.getJSONArray("Data_array");
						if( jArray_Details!=null)
						{
							for(int i=0;i<jArray_Details.length(); i++){
								JSONObject jOb_desc = jArray_Details.getJSONObject(i);
								mTitle 	= 	jOb_desc.getString("shop_title").toString();
								mImageName = jOb_desc.getString("shop_imageurl").toString();
								mContact_person_name = jOb_desc.getString("contact_person_name").toString();
								mPhoneNumber = jOb_desc.getString("phone_no");
								System.out.println("ABCD lagepaosn ---   "+jOb_desc.getString("lega_plan_imageurl").toString());
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
	/**************************************************************************************************************/
}
