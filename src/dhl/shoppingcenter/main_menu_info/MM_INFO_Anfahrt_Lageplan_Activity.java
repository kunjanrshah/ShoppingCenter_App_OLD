package dhl.shoppingcenter.main_menu_info;

import org.json.JSONArray;
import org.json.JSONObject;
import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.ProcessDialog;
import com.dhl.shoppingCenter.R;
import dhl.shopingcenter.tab.Tab_Lageplan_Activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MM_INFO_Anfahrt_Lageplan_Activity extends Activity implements OnClickListener{
	private ImageView imgLageplan = null;
	private static String mDownloadLink = "";
	private static final String ACTION = "pkw";
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private String mImageName="";
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(R.layout.tab_lageplan);
		setContentView(R.layout.tab_lageplan);
		
		getBasicData();
		if( mClass.CheckNetwork(MM_INFO_Anfahrt_Lageplan_Activity.this )){
			new get_FullDescription_shop().execute(Constant.GETTING);
		}
	}
	/**************************************************************************************************************/
	private void getBasicData(){
		
		imgLageplan = (ImageView)findViewById(R.id.imgLageplan);
		
//		tvTitle_TitleBar = (TextView)findViewById(R.id.txt_titleBar_displayIn);
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
		
		mClass = new CommonClass();
		//mBundle = getIntent().getExtras();
//		if( mBundle !=null ){
//			System.out.println( "Shop Id :-   "+mBundle.get(Constant.BUNDLE_SHOP).toString());
//			uID = mBundle.get(Constant.BUNDLE_SHOP).toString();
//			tvTitle_TitleBar.setText(mBundle.getString(Constant.BUNDEL_SHOP_NAME).toString());
//		}
	}
	/**************************************************************************************************************/
	public void onClick(View v) 
	{
		if ( v == btn_Home ) {
			Intent in = new Intent(MM_INFO_Anfahrt_Lageplan_Activity.this, MainMenu_Activity.class);
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
	//		this is used for set the Imges
	private void setShopImages(){
		final String mShopImageURL = mDownloadLink + mImageName;
		if(mClass.CheckNetwork(this)){
			 new Thread(new Runnable() {
				    public void run() {
				      final Bitmap mBitmap = mClass.DownloadImagePost(mShopImageURL);
				      imgLageplan.post(new Runnable() {
				        public void run() {
				        	//imgLageplan.setImageBitmap(mBitmap);
				        	Drawable mDraw = new BitmapDrawable(mBitmap);
				        	imgLageplan.setBackgroundDrawable(mDraw);
				        }
				      });
				    }
				  }).start();
		}
	}
	/**************************************************************************************************************/
	
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_shop extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_INFO_Anfahrt_Lageplan_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(MM_INFO_Anfahrt_Lageplan_Activity.this)){
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
						mDownloadLink = jOb_Detail.getString("download_link");
						System.out.println("Link :"+mDownloadLink);
						
						JSONArray jArray_Details = jOb_Detail.getJSONArray("Data_array");
						if( jArray_Details!=null)
						{
							for(int i=0;i<jArray_Details.length(); i++){
								JSONObject jOb_desc = jArray_Details.getJSONObject(i);
								mImageName = jOb_desc.getString("legaplanimageurl").toString();
							}
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
