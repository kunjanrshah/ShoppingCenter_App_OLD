package dhl.shoppingcenter.impresum;

import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.R;

import dhl.shoppingcenter.main_menu_info.MM_INFO_Kontakt_Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MM_Impresum_Gestaltung extends Activity implements OnClickListener{
	private Button btn_Home = null, btn_Back = null;
	private CommonClass mClass = null;
	private TextView tvEmailID = null, tvWebsite = null;
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ximpresssum_konzeption_origin);
		getBasicData();
	}
	/*******************************************************************************************************/
	private void getBasicData(){
		mClass = new CommonClass();
		
		btn_Back = (Button)findViewById(R.id.btn_Back);
		btn_Home = (Button)findViewById(R.id.btn_Home);
		
		tvEmailID = (TextView)findViewById(R.id.txt_emailID);
		tvWebsite = (TextView)findViewById(R.id.txt_webURL);
		
		btn_Back.setOnClickListener(this);
		btn_Home.setOnClickListener(this);
		
		tvEmailID.setOnClickListener(this);
		tvWebsite.setOnClickListener(this);
	}
	/*******************************************************************************************************/
	public void onClick(View v) {
		if( v == btn_Home ){
			Intent in = new Intent(MM_Impresum_Gestaltung.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else if( v == btn_Back){
			finish();
		}else if( v == tvEmailID){
			sendMail();
		}else if( v == tvWebsite){
			Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse( "http://"+tvWebsite.getText().toString() ) );
			startActivity(in);
		}
	}
	/*******************************************************************************************/
	private void sendMail(){
		if(mClass.CheckNetwork(MM_Impresum_Gestaltung.this)){
			try{
			        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);	    	
			        emailIntent.setType("plain/text");
			        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ tvEmailID.getText().toString()});
			        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,Constant.EXTRA_SUBJECT);
			        startActivity(Intent.createChooser(emailIntent, "Send Mail..."));        
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		 }
	}
}
