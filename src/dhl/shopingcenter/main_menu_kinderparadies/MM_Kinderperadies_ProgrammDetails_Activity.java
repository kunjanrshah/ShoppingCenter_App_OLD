package dhl.shopingcenter.main_menu_kinderparadies;

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

public class MM_Kinderperadies_ProgrammDetails_Activity extends Activity implements OnClickListener{
	private Button btn_Home = null, btn_Back = null;
	private ImageView mImge_program_details = null;
	private TextView txt_programm_title = null, txt_programm_date = null, txt_programm_details = null;
	private static final String ACTION = "particular_program&key=";
	private CommonClass mClass = null;
	private String uId = "";
	private Bundle mBundle = null;
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xkinder_programm_details);
		
		getBundleData();
		getBasicData();
		if( mClass.CheckNetwork(MM_Kinderperadies_ProgrammDetails_Activity.this)){
			new get_FullDescription_KIDERPROGRAMM().execute(Constant.GETTING);
		}
	}
	/***************************************************************************************************************/
	private void getBasicData(){
		mClass = new CommonClass();
		
		mImge_program_details = (ImageView)findViewById(R.id.img_programm);
		
		txt_programm_title = (TextView)findViewById(R.id.txt_programm_title);
		txt_programm_date = (TextView)findViewById(R.id.txt_programm_date);
		txt_programm_details = (TextView)findViewById(R.id.txt_programm_details);
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Back = (Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
	}
	/***************************************************************************************************************/
	private void getBundleData(){
		mBundle = getIntent().getExtras();
		if( mBundle != null){
			uId = mBundle.getString(Constant.EVENT_ID);
			mTitle = mBundle.getString(Constant.EVENT_TITILE);
			mDate = mBundle.getString(Constant.KINDER_DETAIL_DATE);
		}
		
	}
	/***************************************************************************************************************/
	public void onClick(View v)
	{
		if ( v == btn_Home ) {
			Intent in = new Intent(MM_Kinderperadies_ProgrammDetails_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}
		if (v == btn_Back) {
			finish();
		}
	}
	
	/***************************************************************************************************************/
//	this is used for set the Imges
	private void setShopImages(){
		final String mShopImageURL = mDownloadLink + mImageName;
		if(mClass.CheckNetwork(this)){
			 new Thread(new Runnable() {
				    public void run() {
				      final Bitmap mBitmap = mClass.DownloadImagePost(mShopImageURL);
				      mImge_program_details.post(new Runnable() {
				        public void run() {
				        	//mImge_program_details.setImageBitmap(mBitmap);
				        	Drawable mDrawable = new BitmapDrawable(mBitmap);
				        	mImge_program_details.setBackgroundDrawable(mDrawable);
				        }
				      });
				    }
				  }).start();
		}
	}
	/**************************************************************************************************************/
	private String mDescription="", mDownloadLink ="",mDate = "",mTitle = "", mImageName ="";
	private void setText_Description(){
		txt_programm_title.setText( mTitle );
		txt_programm_date.setText( mDate );
		
	}
	/***************************************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	
	
	private class get_FullDescription_KIDERPROGRAMM extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_Kinderperadies_ProgrammDetails_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(MM_Kinderperadies_ProgrammDetails_Activity.this)){
				result = mClass.PostConnectionString(Constant.WEBSERVICE_URL+ACTION+ uId, null);
				System.out.println("URL :--  "+Constant.WEBSERVICE_URL+ACTION+uId);
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
								mDescription 	= 	jOb_desc.getString("description").toString();
								mImageName 		= 	jOb_desc.getString("image_url").toString();
							}
							setShopImages();
							setText_Description();
							txt_programm_details.setText( mDescription );
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
