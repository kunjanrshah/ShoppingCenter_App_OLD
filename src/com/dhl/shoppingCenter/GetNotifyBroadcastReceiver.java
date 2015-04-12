package com.dhl.shoppingCenter;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;


public class GetNotifyBroadcastReceiver extends BroadcastReceiver {
     private Context mContext=null;
	 private NotificationManager notificationMgr=null; 
	 private String business=null,message=null;
	 private CommonClass mClass;
	 private static final String ACTION = "zukunftige_update";
	 
	@Override
	public void onReceive(Context context, Intent intent) {
		 this.mContext=context;
		 
		 mClass = new CommonClass();
		 
		 if(intent.getAction().toString().equals("STARTNOTIFY")){
			    System.out.println("Call Notification BroadcastReceiver");
				startWakeLock();
				notificationMgr =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
				if (mClass.CheckNetworkNoMessage(mContext)) {
		             new getServerNotificationData().execute("Get Server Data");	
			    }
		 }
		 
		 else if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) { 
			    System.out.println("On Boot Call Notification BroadcastReceiver");
				//this code for push notifiacation service start
				SharedPreferences mSharedPreferences=mContext.getSharedPreferences(Constant.SHARE_PREFERENCE,Context.MODE_WORLD_READABLE);
				boolean PushStatus=mSharedPreferences.getBoolean(Constant.KEY_PUSHNOTIFICATION,true);
				if( PushStatus ){
					mContext.startService(new Intent(mContext,BackgroundService.class));
				}
		 }
	}

	private void showNotification(String result,String tickerText,Integer nid)
	 {	    	
			Notification n = new Notification();
					
			n.flags |= Notification.FLAG_SHOW_LIGHTS;
	      	n.flags |= Notification.FLAG_AUTO_CANCEL;
	      	n.flags |= Notification.FLAG_ONGOING_EVENT;
	      //	n.flags |= Notification.FLAG_NO_CLEAR;
	        n.tickerText=tickerText;
	        n.defaults = Notification.DEFAULT_ALL;
	      	
			n.icon = R.drawable.ic_launcher;
			n.when = System.currentTimeMillis();
			
			Intent mIntent=new Intent(mContext,PushNotification_Activity.class);
//			mIntent.putExtra(Constant.PUSHBUSINESS, business);
//			mIntent.putExtra(Constant.PUSHMESSAGE, message);

			// Simply open the parent activity
			PendingIntent pi = PendingIntent.getActivity(mContext, 0,mIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
			// Change the name of the notification here
			n.setLatestEventInfo(mContext,Constant.PUSHTITLE + result, message, pi);

			notificationMgr.notify(nid, n);
	}
		 
	private class getServerNotificationData extends AsyncTask<String, String, String>
     {       
          protected void onPreExecute() {
                  super.onPreExecute();
          }
          
		protected String doInBackground(String... params) {
			String mResult = "";
			if (mClass.CheckNetworkNoMessage(mContext)) {
				mResult = mClass.PostConnectionString(Constant.WEBSERVICE_URL
						+ ACTION, null);

				try {
					JSONObject jOb_Data = new JSONObject(mResult);
					if (jOb_Data != null) {
						System.out.println("Json Obj :-  "
								+ jOb_Data.get("Data_array").toString());
						JSONArray mJarray_shopList = jOb_Data
								.getJSONArray("Data_array");
						for (int i = 0; i < mJarray_shopList.length(); i++) {
							JSONObject jOb = mJarray_shopList.getJSONObject(i);

						}

					}

				} catch (Exception e) {

					e.printStackTrace();
				}
				return mResult;
			}
			else
				return mResult;

		}

		protected void onPostExecute(String result) {
			//Random RndNumber = new Random();
			if (result != null) {
				System.out.println(""+result);
				showNotification("New Notification", Constant.PUSHTITLE, 1000);
			} else {
				System.out.println("Nothing......");
			}
			stopWakeLock();
			super.onPostExecute(result);
		}
     }	
	 
	 private WakeLock wakeLock=null;
	  private static final String WAKE_LOCK_TAG = "WAKE_LOCK_GETNOTIFYBROADCASTRECEIVER";
	  private void startWakeLock() {
	    if (wakeLock == null) {
	      Log.d("Deelioz", "wakeLock is null, getting a new WakeLock");
	      PowerManager pm = (PowerManager)mContext.getSystemService(Context.POWER_SERVICE);
	      Log.d("Deelioz", "PowerManager acquired");
	      wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG);
	      Log.d("Deelioz", "WakeLock set");
	    }
	    wakeLock.acquire();
	    Log.d("Deelioz", "WakeLock acquired");
	  }

	  private void stopWakeLock() {
	    if (wakeLock != null) {
	      wakeLock.release();
	      Log.d("Deelioz", "WakeLock released");
	    }
	  }
}
