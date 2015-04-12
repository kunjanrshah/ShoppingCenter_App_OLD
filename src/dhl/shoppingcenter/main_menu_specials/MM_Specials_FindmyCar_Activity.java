package dhl.shoppingcenter.main_menu_specials;

import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MM_Specials_FindmyCar_Activity extends Activity implements OnClickListener{
	private CheckBox chck0 = null, chck1 = null,chck2 = null, chck3 = null, chck4 = null, chck5=null, chck6=null,chck7=null;
	private SharedPreferences mSharedPreferences = null;
	private SharedPreferences.Editor mEditor = null;
	private Button btn_Home = null,btn_Back = null;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xfindmycar);
		
		getBasicData();
		
		mSharedPreferences = getSharedPreferences(Constant.SHARE_PREFERENCE, MODE_WORLD_READABLE);
		if( mSharedPreferences != null ){
			String which = mSharedPreferences.getString(Constant.KEY_INDEX, "nothings");
			if(which.equals("chck0"))
				chck0.setChecked(true);
			else if(which.equals("chck1"))
				chck1.setChecked(true);
			else if(which.equals("chck2"))
				chck2.setChecked(true);
			else if(which.equals("chck3"))
				chck3.setChecked(true);
			else if(which.equals("chck4"))
				chck4.setChecked(true);
			else if(which.equals("chck5"))
				chck5.setChecked(true);
			else if(which.equals("chck6"))
				chck6.setChecked(true);
			else if(which.equals("chck7"))
				chck7.setChecked(true);
		}
	}
	
	private void getBasicData(){
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Home.setOnClickListener(this);
		
		btn_Back=(Button)findViewById(R.id.btn_Back);
		btn_Back.setOnClickListener(this);
		
		chck0 = (CheckBox)findViewById(R.id.chk_P0);
		chck1 = (CheckBox)findViewById(R.id.chk_P1);
		chck2 = (CheckBox)findViewById(R.id.chk_P2);
		chck3 = (CheckBox)findViewById(R.id.chk_P3);
		chck4 = (CheckBox)findViewById(R.id.chk_P4);
		chck5 = (CheckBox)findViewById(R.id.chk_P5);
		chck6 = (CheckBox)findViewById(R.id.chk_P6);
		chck7 = (CheckBox)findViewById(R.id.chk_P7);	
		
		chck0.setOnClickListener(this);
		chck1.setOnClickListener(this);
		chck2.setOnClickListener(this);
		chck3.setOnClickListener(this);
		chck4.setOnClickListener(this);
		chck5.setOnClickListener(this);
		chck6.setOnClickListener(this);
		chck7.setOnClickListener(this);
		
	}
	
	private void getUncheckedAll(){
		chck0.setChecked(false);
		chck1.setChecked(false);
		chck2.setChecked(false);
		chck3.setChecked(false);
		chck4.setChecked(false);
		chck5.setChecked(false);
		chck6.setChecked(false);
		chck7.setChecked(false);
	}

	
	
	private void setIndex_SharedPreference(String mIndex){
		mSharedPreferences = getSharedPreferences(Constant.SHARE_PREFERENCE, MODE_WORLD_WRITEABLE);
		mEditor = mSharedPreferences.edit();
		mEditor.putString(Constant.KEY_INDEX, mIndex);
		mEditor.commit();
	}
	
	Boolean globalClick = false;
	@Override
	public void onClick(View v) {
		
		if (v == chck0) {
			globalClick=chck0.isChecked();
			getUncheckedAll();
			if(globalClick){
				chck0.setChecked(true);
				setIndex_SharedPreference("chck0");				
			}
			else{
				setIndex_SharedPreference("nothings");
				chck0.setChecked(false);		
			}			
		} else if (v == chck1) {
			globalClick=chck1.isChecked();
			getUncheckedAll();
			if(globalClick){
				chck1.setChecked(true);
				setIndex_SharedPreference("chck1");				
			}
			else{
				setIndex_SharedPreference("nothings");
				chck1.setChecked(false);		
			}
		} else if (v == chck2) {
			globalClick=chck2.isChecked();
			getUncheckedAll();
			if(globalClick){
				chck2.setChecked(true);
				setIndex_SharedPreference("chck2");				
			}
			else{
				setIndex_SharedPreference("nothings");
				chck2.setChecked(false);		
			}
		} else if (v == chck3) {
			globalClick=chck3.isChecked();
			getUncheckedAll();
			if(globalClick){
				chck3.setChecked(true);
				setIndex_SharedPreference("chck3");				
			}
			else{
				setIndex_SharedPreference("nothings");
				chck3.setChecked(false);		
			}
		} else if (v == chck4) {
			globalClick=chck4.isChecked();
			getUncheckedAll();
			if(globalClick){
				chck4.setChecked(true);
				setIndex_SharedPreference("chck4");				
			}
			else{
				setIndex_SharedPreference("nothings");
				chck4.setChecked(false);		
			}
		} else if (v == chck5) {
			globalClick=chck5.isChecked();
			getUncheckedAll();
			if(globalClick){
				chck5.setChecked(true);
				setIndex_SharedPreference("chck5");				
			}
			else{
				setIndex_SharedPreference("nothings");
				chck5.setChecked(false);		
			}
		} else if (v == chck6) {
			globalClick=chck6.isChecked();
			getUncheckedAll();
			if(globalClick){
				chck6.setChecked(true);
				setIndex_SharedPreference("chck6");				
			}
			else{
				setIndex_SharedPreference("nothings");
				chck6.setChecked(false);		
			}
		} else if (v == chck7) {
			globalClick=chck7.isChecked();
			getUncheckedAll();
			if(globalClick){
				chck7.setChecked(true);
				setIndex_SharedPreference("chck7");				
			}
			else{
				setIndex_SharedPreference("nothings");
				chck7.setChecked(false);		
			}
			
		}else
			if( v == btn_Home){
				Intent in = new Intent(MM_Specials_FindmyCar_Activity.this, MainMenu_Activity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
				finish();
			}else
			if ( v == btn_Back ) {
				finish();
			} 
	}
}
