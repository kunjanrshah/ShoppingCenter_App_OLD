package dhl.shoppingcenter.main_menu_info;

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
import android.widget.Toast;

public class MainMenu_INFO_Activity extends Activity implements OnClickListener{
	private Button btn_Home = null, btn_Back = null;
	private TextView tvKontakt = null, tvOpening = null, tvAnfart = null, tvFacts = null, tvFundburo = null, tvGift = null;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xmainmenu_info);
		
		getBasicData();
	}
	
	private void getBasicData()
	{
		btn_Home	=	(Button)findViewById(R.id.btn_Home);
		btn_Back	=	(Button)findViewById(R.id.btn_Back);
		
		btn_Home.setOnClickListener(this);
		btn_Back.setOnClickListener(this);
		
		tvKontakt = (TextView)findViewById(R.id.txt_Kontakt);
		tvOpening = (TextView)findViewById(R.id.txt_Opening);
		tvAnfart = (TextView)findViewById(R.id.txt_anfart);
		tvFacts = (TextView)findViewById(R.id.txt_fact);
		tvFundburo = (TextView)findViewById(R.id.txt_Fundburo);
		tvGift = (TextView)findViewById(R.id.txt_Geschenk_giftCertificates);
		
		tvKontakt.setOnClickListener(this);
		tvOpening.setOnClickListener(this);
		tvAnfart.setOnClickListener(this);
		tvFacts.setOnClickListener(this);
		tvFundburo.setOnClickListener(this);
		tvGift.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent mIntent = null;
		if (v == btn_Home) {
			mIntent = new Intent(MainMenu_INFO_Activity.this,
					MainMenu_Activity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
		} else if (v == btn_Back) {
			finish();
		} else if (v == tvKontakt) {
			mIntent = new Intent(MainMenu_INFO_Activity.this, MM_INFO_Kontakt_Activity.class);
			startActivity(mIntent);
		} else if (v == tvOpening) {
			mIntent = new Intent(MainMenu_INFO_Activity.this, MM_INFO_Opening_Activity.class);
			startActivity(mIntent);
		} else if (v == tvAnfart) {
			mIntent = new Intent(MainMenu_INFO_Activity.this, MM_INFO_Anfahrt_Activity.class);
			startActivity(mIntent);
		} else if (v == tvFacts) {
			mIntent = new Intent(MainMenu_INFO_Activity.this, MM_INFO_Facts.class);
			startActivity(mIntent);
		} else if (v == tvFundburo) {
			mIntent = new Intent(MainMenu_INFO_Activity.this, MM_INFO_Fundburo.class);
			startActivity(mIntent);
		} else if (v == tvGift) {
			mIntent = new Intent(MainMenu_INFO_Activity.this, MM_INFO_Geschenkgutscheine.class);
			startActivity(mIntent);
		}
	}
}
