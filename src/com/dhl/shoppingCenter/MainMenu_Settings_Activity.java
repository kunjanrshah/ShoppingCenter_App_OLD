package com.dhl.shoppingCenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainMenu_Settings_Activity extends Activity implements OnCheckedChangeListener{

	private SharedPreferences mSharedPreferences = null;
	private SharedPreferences.Editor mShareEdit = null;
	private ToggleButton tBtnPushNotification = null;
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xsettings);
		
		getBasicData();
		
		getCheck_Notification();
	}
	
	private void getBasicData(){
		
		mSharedPreferences	= getSharedPreferences(Constant.SHARE_PREFERENCE, MODE_WORLD_READABLE);
		mShareEdit	= mSharedPreferences.edit();
		
		tBtnPushNotification = (ToggleButton)findViewById(R.id.tBtn_pushNotification);
		tBtnPushNotification.setOnCheckedChangeListener(this);
		
		tBtnPushNotification.setChecked(mSharedPreferences.getBoolean(Constant.KEY_PUSHNOTIFICATION, true));
		
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		mSharedPreferences	= getSharedPreferences(Constant.SHARE_PREFERENCE, MODE_WORLD_WRITEABLE);
		mShareEdit.putBoolean(Constant.KEY_PUSHNOTIFICATION, isChecked);
		mShareEdit.commit();
		getCheck_Notification();
	}
	
	private void getCheck_Notification(){
		mSharedPreferences = getSharedPreferences(Constant.SHARE_PREFERENCE, MODE_WORLD_READABLE);
		Boolean mNotification = mSharedPreferences.getBoolean(Constant.KEY_PUSHNOTIFICATION, true);
		System.out.println("Getting Shared for ON/OFF Notifi :-- "+mNotification);
		if( mNotification ){
			startService( new Intent(MainMenu_Settings_Activity.this,BackgroundService.class) );
			System.out.println("Notificatio is start");
		}else {
			stopService( new Intent(MainMenu_Settings_Activity.this, BackgroundService.class) );
			System.out.println("Notificatio is stop");
		}
	}
}
