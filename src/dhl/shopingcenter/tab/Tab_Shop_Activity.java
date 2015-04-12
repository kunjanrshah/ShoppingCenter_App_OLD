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
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Tab_Shop_Activity extends Activity implements OnClickListener{
	private Button btn_Home = null, btn_Back = null;
	private Bundle mBundle = null;
	private CommonClass mClass = null;
	private static final String ACTION = "particular_shop&key=";
	private static String mDownloadLink = "";
	private String uID	=	"", mTitle = "", mDesc = "",mImageName="";
	private TextView tvTitle_TitleBar = null,tvTitle = null, tvDescription = null;
	private ImageView img_coupon_aswell_shop = null;
	private Typeface calibri = null,calibri_bold;
	//Typeface calibri = Typeface.createFromAsset(getAssets(), "fonts/Calibri.ttf");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_shop);
		
		calibri = Typeface.createFromAsset(getAssets(), "fonts/Calibri.ttf");
		calibri_bold = Typeface.createFromAsset(getAssets(), "fonts/Calibri_bold.ttf");
		
		getBasicData();
		
		if( mClass.CheckNetwork(this) ){
			new get_FullDescription_shop().execute(Constant.GETTING);
		}
	}
	/**************************************************************************************************************/
	private void getBasicData(){
		
		img_coupon_aswell_shop = (ImageView)findViewById(R.id.img_coupon_aswell_shop);
		
		tvTitle_TitleBar = (TextView)findViewById(R.id.txt_titleBar_displayIn);
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
			tvTitle_TitleBar.setText(mBundle.getString(Constant.BUNDEL_SHOP_NAME).toString());
			tvTitle_TitleBar.setTypeface( calibri_bold );
		}
	}
	
	 /**************************************************************************************************************/
	public void onClick(View v) 
	{
		if ( v == btn_Home ) {
			Intent in = new Intent(Tab_Shop_Activity.this, MainMenu_Activity.class);
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
	private void setTitle_Description(){
		tvTitle.setText( mTitle );
		tvDescription.setText( Html.fromHtml(mDesc) );
		tvDescription.setTypeface(calibri);
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
	private class get_FullDescription_shop extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(Tab_Shop_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(Tab_Shop_Activity.this)){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION+uID, null);
			}
			if( result!= null)
				return result;
			else
				return "Error";
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
								mDesc 	=	jOb_desc.getString("shop_desc").toString();
								mImageName = jOb_desc.getString("shop_imageurl").toString();
//								System.out.println(" Shop Name :"+ jOb_desc.getString("shop_title").toString() );
//								System.out.println(" Shop_Desc :"+ jOb_desc.getString("shop_desc").toString() );
							}
							setTitle_Description();
							setShopImages();
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
