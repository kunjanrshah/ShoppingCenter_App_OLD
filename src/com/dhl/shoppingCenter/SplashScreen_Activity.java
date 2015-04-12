package com.dhl.shoppingCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen_Activity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.xsplashscreen);
        
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	Thread th = new Thread(){
    		public void run(){
    			try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent in = new Intent(SplashScreen_Activity.this, MainMenu_Activity.class);
					startActivity(in);
					finish();
				}
    		}
    	};
    	
    	th.start();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
    		return true;
    	}
    	return super.onKeyDown(keyCode, event);
    }
}