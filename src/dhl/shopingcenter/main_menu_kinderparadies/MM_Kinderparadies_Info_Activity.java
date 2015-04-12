package dhl.shopingcenter.main_menu_kinderparadies;

import org.json.JSONArray;
import org.json.JSONObject;
import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.ProcessDialog;
import com.dhl.shoppingCenter.R;
import dhl.shoppingcenter.main_menu_info.MM_INFO_Facts;
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
import android.widget.Toast;

public class MM_Kinderparadies_Info_Activity extends Activity implements OnClickListener{

	private Button btn_Home = null, btn_Back = null;
	private TextView tvTitle = null, tvDescription = null,tvPhone = null;
	private CommonClass mClass = null;
	private String mTitle ="", mDescription="";
	private static final String ACTION = "info_kontakt";
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xkinder_infokontakt);
		//Toast.makeText(this, "KinderParadiese Info n Kontal", Toast.LENGTH_SHORT).show();
		getBasicInfo();
		if( mClass.CheckNetwork(this)){
			new get_FullDescription_InfoKontakt().execute(Constant.GETTING);
		}
	}
	
	/*******************************************************************************************/
	private void getBasicInfo(){
		
		mClass = new CommonClass();
		
		
		btn_Back = (Button)findViewById(R.id.btn_Back);
		btn_Home = (Button)findViewById(R.id.btn_Home);
		
		btn_Back.setOnClickListener(this);
		btn_Home.setOnClickListener(this);
		
		tvTitle = (TextView)findViewById(R.id.txt_title);
		tvDescription = (TextView)findViewById(R.id.txt_description);
	}
	/*******************************************************************************************/
	public void onClick(View v) {
		if( v == btn_Home ){
			Intent in = new Intent(MM_Kinderparadies_Info_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else if( v == btn_Back){
			finish();
		}
	}
	/*******************************************************************************************/
	private void setText_Title_Phone(){
//		tvTitle.setText( "Kinderparadies" );
		tvDescription.setText( Html.fromHtml(mDescription) );
		//mWVDescription.loadData( mDescription, "text/html", "UTF-8");
	}
	/*******************************************************************************************/
	// this is used for getting data from server side
	private ProcessDialog mProcessDialog = null;
	private class get_FullDescription_InfoKontakt extends AsyncTask<String, String, String>{
		protected void onPreExecute() {
			mProcessDialog = new ProcessDialog(MM_Kinderparadies_Info_Activity.this, Constant.GETTING, Constant.LOADING);
			super.onPreExecute();
		}
		
		protected String doInBackground(String... params) {
			String result ="";
			if( mClass.CheckNetwork(MM_Kinderparadies_Info_Activity.this)){
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
//								mTitle 	= 	jOb_desc.getString("title").toString();
								mDescription = jOb_desc.getString("description").toString();
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
