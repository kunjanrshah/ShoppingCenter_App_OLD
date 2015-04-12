package com.dhl.shoppingCenter;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class BackgroundService extends Service
{
	private AlarmManager mAlarmManager=null;
	private PendingIntent pendingIntent=null;
	
    @Override
	public void onCreate() {
		super.onCreate();
		
        startWakeLock();
        System.out.println("Start Notification Service");
		Intent mIntent = new Intent(this, GetNotifyBroadcastReceiver.class);
		mIntent.setAction("STARTNOTIFY");
		
		pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, mIntent, 0);
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0,60000*1, pendingIntent);
	}
    
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onDestroy()
	{
		if(mAlarmManager!=null && pendingIntent!=null){
		    mAlarmManager.cancel(pendingIntent);
		}
		stopWakeLock();
		System.out.println("Stop Notification Service");   
	    super.onDestroy();
	}
	
	private WakeLock wakeLock=null;
	  private static final String WAKE_LOCK_TAG = "WAKE_LOCK_BACKGROUNDSERVICE";
	  private void startWakeLock() {
	    if (wakeLock == null) {
	      Log.d("PMK", "wakeLock is null, getting a new WakeLock");
	      PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	      Log.d("PMK", "PowerManager acquired");
	      wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG);
	      Log.d("PMK", "WakeLock set");
	    }
	    wakeLock.acquire();
	    Log.d("PMK", "WakeLock acquired");
	  }

	  private void stopWakeLock() {
	    if (wakeLock != null) {
	      wakeLock.release();
	      Log.d("PMK", "WakeLock released");
	    }
	  }
}