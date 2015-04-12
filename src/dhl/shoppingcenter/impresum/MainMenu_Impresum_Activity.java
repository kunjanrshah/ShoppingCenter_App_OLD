package dhl.shoppingcenter.impresum;

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

public class MainMenu_Impresum_Activity extends Activity implements OnClickListener{

	private TextView tvImpresum = null, tvImpresum_Gestaltung = null, tvImpresum_Umsetzung = null;
	private Button btn_Home = null;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ximpressum);
		
		getBasicData();
	}
	/*****************************************************************************************/
	private void getBasicData(){
		
		btn_Home  = (Button)findViewById(R.id.btn_Home);
		btn_Home.setOnClickListener(this);
		
		tvImpresum = (TextView)findViewById(R.id.txt_impresum);
		tvImpresum_Gestaltung = (TextView)findViewById(R.id.txt_impresum_gestaltung);
		tvImpresum_Umsetzung = (TextView)findViewById(R.id.txt_impresum_umsetzung);
		
		tvImpresum.setOnClickListener(this);
		tvImpresum_Gestaltung.setOnClickListener(this);
		tvImpresum_Umsetzung.setOnClickListener(this);
	}
	/*****************************************************************************************/
	public void onClick(View v) {
		Intent mIntent = null;
		if( v == btn_Home){
			finish();
		}else
		if( v == tvImpresum){
			mIntent = new Intent( MainMenu_Impresum_Activity.this, MM_Impresum_impresum.class);
			mIntent.putExtra(Constant.BUNDLE_IMPRESUM, tvImpresum.getText().toString());
			startActivity(mIntent);
		}else if( v == tvImpresum_Gestaltung){
			mIntent = new Intent( MainMenu_Impresum_Activity.this, MM_Impresum_Gestaltung.class);
			startActivity(mIntent);
		}else if( v == tvImpresum_Umsetzung){
			mIntent = new Intent( MainMenu_Impresum_Activity.this, MM_Impresum_Umsetzung.class);
			startActivity(mIntent);
		}
	}
	
}
