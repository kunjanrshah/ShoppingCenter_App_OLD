package com.dhl.shoppingCenter;

import dhl.shopingcenter.main_menu_kinderparadies.MainMenu_Kinderperadies_Activity;
import dhl.shoppingcenter.impresum.MainMenu_Impresum_Activity;
import dhl.shoppingcenter.main_menu_action.MainMenu_Action_Activity;
import dhl.shoppingcenter.main_menu_events.MainMenu_Events_Activity;
import dhl.shoppingcenter.main_menu_info.MainMenu_INFO_Activity;
import dhl.shoppingcenter.main_menu_specials.MainMenu_Specials_Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu_Activity extends Activity implements OnClickListener{

	private Button btn_shop = null, btn_actions = null, btn_info = null, btn_events = null, btn_chidern = null, btn_specials = null;
	private ImageView img_info_impresum=null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xmain_menu);
		
		getBasicInfo();
	}
	
	private void getBasicInfo()
	{
		btn_shop		=	(Button)findViewById(R.id.btn_shop);
		btn_actions		=	(Button)findViewById(R.id.btn_action);
		btn_info		=	(Button)findViewById(R.id.btn_Info);
		btn_events		=	(Button)findViewById(R.id.btn_event);
		btn_chidern		=	(Button)findViewById(R.id.btn_children);
		btn_specials	=	(Button)findViewById(R.id.btn_special);
		
		img_info_impresum =(ImageView)findViewById(R.id.img_info_impresum);
		
		btn_shop.setOnClickListener(this);
		btn_actions.setOnClickListener(this);
		btn_info.setOnClickListener(this);
		btn_events.setOnClickListener(this);
		btn_chidern.setOnClickListener(this);
		btn_specials.setOnClickListener(this);
		
		img_info_impresum.setOnClickListener(this);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater mInflater = this.getMenuInflater();
		mInflater.inflate(R.menu.menu_pushnotification_settings, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		
		MenuItem menuSettins = menu.findItem(R.id.menu_Settings);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 super.onOptionsItemSelected(item);
		 switch( item.getItemId() ){
		 	case R.id.menu_Settings:
		 	
		 		Intent in = new Intent(MainMenu_Activity.this, MainMenu_Settings_Activity.class);
		 		startActivity(in);
		 		break;
		 }
		 return true;
	}

	@Override
	public void onClick(View v) 
	{
		Intent in = null;
		if ( v == btn_shop){
			in = new Intent(MainMenu_Activity.this, Shops_Activity.class);
			startActivity(in);
		}else if( v == btn_actions ){
			in = new Intent(MainMenu_Activity.this, MainMenu_Action_Activity.class);
			startActivity(in);
		}else if( v == btn_info ){
			in = new Intent(MainMenu_Activity.this, MainMenu_INFO_Activity.class);
			startActivity(in);
		}else if( v == btn_events ){
			in = new Intent(MainMenu_Activity.this, MainMenu_Events_Activity.class);
			startActivity(in);
		}else if( v == btn_chidern ){
			in = new Intent(MainMenu_Activity.this, MainMenu_Kinderperadies_Activity.class);
			startActivity(in);
		}else if( v == btn_specials ){
			in = new Intent(MainMenu_Activity.this, MainMenu_Specials_Activity.class);
			startActivity(in);
		}else if( v == img_info_impresum ){
			in = new Intent(MainMenu_Activity.this, MainMenu_Impresum_Activity.class);
			startActivity(in);
		}
		
	}
}
