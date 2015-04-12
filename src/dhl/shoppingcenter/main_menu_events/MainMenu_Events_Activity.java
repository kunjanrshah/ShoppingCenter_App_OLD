package dhl.shoppingcenter.main_menu_events;

import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu_Events_Activity extends Activity implements OnClickListener{
	
	private TextView tvTitle = null, tvCurrentEvents = null, tvFutureEvents = null;
	private Button btn_Home = null;
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xshop);
		
		getBasicInfo();
	}
	/*********************************************************************************************************/
	private void getBasicInfo(){
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Home.setOnClickListener(this);
		
		tvTitle = (TextView)findViewById(R.id.txt_titlebar);
		tvCurrentEvents = (TextView)findViewById(R.id.txt_business_abc);
		tvFutureEvents = (TextView)findViewById(R.id.txt_business_branche);
		
		tvTitle.setText( Constant.EVENT_TITILE );
		tvCurrentEvents.setText( Constant.EVENT_CURRENT );
		tvFutureEvents.setText( Constant.EVENT_FUTURE );
		
		tvCurrentEvents.setOnClickListener(this);
		tvFutureEvents.setOnClickListener(this);
	}

	/*********************************************************************************************************/
	public void onClick(View v) 
	{
		Intent mIntent = null;
		if( v == btn_Home){
			mIntent = new Intent(MainMenu_Events_Activity.this, MainMenu_Activity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
		}else
		if( v == tvCurrentEvents){
			mIntent = new Intent(MainMenu_Events_Activity.this, MM_EventList_Activity.class);
			mIntent.putExtra(Constant.EVENT_BUNDLE, Constant.EVENT_CURRENT);
			startActivity(mIntent);
		}else
		if( v == tvFutureEvents){
			mIntent = new Intent(MainMenu_Events_Activity.this, MM_EventList_Activity.class);
			mIntent.putExtra(Constant.EVENT_BUNDLE, Constant.EVENT_FUTURE);
			startActivity(mIntent);
		}
	}
	/*********************************************************************************************************/
}
