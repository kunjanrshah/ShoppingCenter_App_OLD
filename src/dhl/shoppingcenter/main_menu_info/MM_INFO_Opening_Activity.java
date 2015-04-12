package dhl.shoppingcenter.main_menu_info;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.ProcessDialog;
import com.dhl.shoppingCenter.R;
import com.dhl.shoppingCenter.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MM_INFO_Opening_Activity extends Activity implements OnClickListener {
	private Button btn_Home = null, btn_Back = null;
	private TextView tvNormal_Opening = null, tvSpecial_Opening = null;
	private String mNormalOpening = "", mSpecialOpening = "";
	private static final String ACTION = "offnungszeiten";
	private CommonClass mClass = null;
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xinfo_openings);
		
		getBasicData();
		
		if( mClass.CheckNetwork(this)){
			new get_FullDescription_Openings().execute(Constant.GETTING);
		}
	}
	/*******************************************************************************************/
	private void getBasicData()
	{
		mClass		= 	new CommonClass();
		btn_Home	=	(Button)findViewById(R.id.btn_Home);
		btn_Back	=	(Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
		
		tvNormal_Opening = (TextView)findViewById(R.id.txt_normal_opening);
		tvSpecial_Opening = (TextView)findViewById(R.id.txt_special_opening);
		
		
	}
	/*******************************************************************************************/
	public void onClick(View v) {
		Intent mIntent = null;
		if (v == btn_Home) {
			mIntent = new Intent(MM_INFO_Opening_Activity.this,MainMenu_Activity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
		} else if (v == btn_Back) {
			finish();
		}
	}
	
	/*******************************************************************************************/
	private void setText_Title_Phone(){
		tvNormal_Opening.setText( Html.fromHtml(mNormalOpening) );
		tvSpecial_Opening.setText( Html.fromHtml(mSpecialOpening) );		
	}
	/*******************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_Openings extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_INFO_Opening_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(MM_INFO_Opening_Activity.this)){
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
								mNormalOpening 	= 	jOb_desc.getString("description").toString();
								mSpecialOpening = jOb_desc.getString("special_description").toString();
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
