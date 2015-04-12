package dhl.shoppingcenter.main_menu_action;

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

public class MainMenu_Action_Activity extends Activity implements OnClickListener{
	private TextView tvFirst = null, tvSecond = null;
	private Button btn_Home = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainmenu_actionbutton);
	
		getBasicData();
	}
	
	private void getBasicData()
	{
		tvFirst = (TextView)findViewById(R.id.txt_business_First);
		tvSecond = (TextView)findViewById(R.id.txt_business_Second);
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Home.setOnClickListener(this);
		
		tvFirst.setOnClickListener(this);
		tvSecond.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent mIntent = null;
		if( v == btn_Home ){
			mIntent = new Intent(MainMenu_Action_Activity.this,MainMenu_Activity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
		}else if( v == tvFirst ){
			mIntent = new Intent( MainMenu_Action_Activity.this, Action_Coupon_Activity.class );
			mIntent.putExtra(Constant.BUNDLE_WHICH_CATAGORY, tvFirst.getText().toString());
			startActivity( mIntent );
		}else if ( v == tvSecond ){
			mIntent = new Intent( MainMenu_Action_Activity.this, Action_Abgebot_Activity.class);
			mIntent.putExtra(Constant.IS_ABGEBOT, Constant.ABGEBOT);
			startActivity( mIntent );
		}
		
		
	}
}
