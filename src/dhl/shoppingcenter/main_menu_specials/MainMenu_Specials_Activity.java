package dhl.shoppingcenter.main_menu_specials;

import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class MainMenu_Specials_Activity extends Activity implements OnClickListener{
	private TextView tvTitle = null, tvCurrentEvents = null, tvFutureEvents = null;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xshop);
		
		getBasicInfo();
	}
	private void getBasicInfo(){
		
		tvTitle = (TextView)findViewById(R.id.txt_titlebar);
		tvCurrentEvents = (TextView)findViewById(R.id.txt_business_abc);
		tvFutureEvents = (TextView)findViewById(R.id.txt_business_branche);
		
		tvTitle.setText( Constant.SPECIALS_TITLE );
		tvCurrentEvents.setText( Constant.SPECIALS_ELINK );
		tvFutureEvents.setText( Constant.SPECIALS_FINDCAR );
		
		tvCurrentEvents.setOnClickListener(this);
		tvFutureEvents.setOnClickListener(this);
	}
	
	public void onClick(View v) 
	{	
		Intent mIntent = null;
		if( v == tvCurrentEvents ){
			mIntent = new Intent(MainMenu_Specials_Activity.this, MM_Special_AddShoppingLIst_Activity.class);
			startActivity(mIntent);
		}else if( v == tvFutureEvents ){
			mIntent = new Intent(MainMenu_Specials_Activity.this, MM_Specials_FindmyCar_Activity.class);
			startActivity(mIntent);
		}
	}
}
