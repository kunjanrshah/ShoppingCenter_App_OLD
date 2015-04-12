package dhl.shopingcenter.main_menu_kinderparadies;

import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu_Kinderperadies_Activity extends Activity implements OnClickListener{
	private Button btn_Home = null;
	private TextView tvTitle = null, tvKINDER_INFO = null, tvKINDER_PROGRAM = null;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xshop);
		
		getBasicInfo();
	}
	/***********************************************************************************************************/
	private void getBasicInfo(){
		
		tvTitle = (TextView)findViewById(R.id.txt_titlebar);
		tvKINDER_INFO = (TextView)findViewById(R.id.txt_business_abc);
		tvKINDER_PROGRAM = (TextView)findViewById(R.id.txt_business_branche);
		
		tvTitle.setText( Constant.KINDER_TITILE );
		tvKINDER_INFO.setText( Constant.KINDER_INFO );
		tvKINDER_PROGRAM.setText( Constant.KINDER_PROGRAMM_SUB );
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Home.setOnClickListener(this);
		
		tvKINDER_INFO.setOnClickListener(this);
		tvKINDER_PROGRAM.setOnClickListener(this);
	}
	/***********************************************************************************************************/
	public void onClick(View v) {
		Intent mIntent = null;
		if( v == btn_Home){
			finish();
		}else
		if( v == tvKINDER_INFO ){
			mIntent = new Intent( MainMenu_Kinderperadies_Activity.this, MM_Kinderparadies_Info_Activity.class);
			startActivity(mIntent);
		}else
		if( v == tvKINDER_PROGRAM){
			mIntent = new Intent(MainMenu_Kinderperadies_Activity.this, MM_Kinderperadies_ProgramList_Activity.class);
			mIntent.putExtra(Constant.BUNDLE_WHICH_CATAGORY, tvKINDER_PROGRAM.getText().toString());
			startActivity(mIntent);
		}
	}
	/***********************************************************************************************************/
}
