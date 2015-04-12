package dhl.shoppingcenter.main_menu_info;

import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MM_INFO_Anfahrt_Activity extends Activity implements OnClickListener{
	
	private Button btn_Home = null, btn_back = null;
	private TextView tvPKW = null, tvOV = null, tvLageplan = null;
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xinfo_anfahrt);
		
		getBasicInfo();
	}
	
	private void getBasicInfo(){
		
		btn_back = (Button)findViewById(R.id.btn_back);
		btn_Home = (Button)findViewById(R.id.btn_Home);
		
		btn_back.setOnClickListener(this);
		btn_Home.setOnClickListener(this);
		
		tvPKW = (TextView)findViewById(R.id.txt_PKW);
		tvOV = (TextView)findViewById(R.id.txt_OV);
		tvLageplan = (TextView)findViewById(R.id.txt_lageplan);
		
		tvPKW.setOnClickListener(this);
		tvOV.setOnClickListener(this);
		tvLageplan.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) 
	{
		Intent in = null;
		if( v == btn_Home ){
			in = new Intent(MM_INFO_Anfahrt_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else if( v == btn_back){
			finish();
		}else if( v == tvPKW){
			
			in = new Intent( MM_INFO_Anfahrt_Activity.this, MM_INFO_Anfahrt_PKW_Activity.class);
			startActivity(in);
		}else if( v == tvOV){
			
			in = new Intent(Intent.ACTION_VIEW,Uri.parse(Constant.ANDROID_MARKET_LINK));
			startActivity(in);
//			Dialog mDialog = new Dialog(this);
//			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			mDialog.setContentView(R.layout.webandroidlink);
//			WebView wvAndroidLink = (WebView)mDialog.findViewById(R.id.wbAndroidMarketLink);
//			wvAndroidLink.loadUrl(Constant.ANDROID_MARKET_LINK);
			
//			mDialog.show();
		}else if( v == tvLageplan){
//			Toast.makeText(this, ""+tvLageplan.getText().toString(), Toast.LENGTH_SHORT).show();
			in = new Intent(MM_INFO_Anfahrt_Activity.this, MM_INFO_Anfahrt_Lageplan_Activity.class);
			startActivity(in);
		}
	}
}
