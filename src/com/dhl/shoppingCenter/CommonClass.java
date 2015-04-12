package com.dhl.shoppingCenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class CommonClass {
		
	private static final String strTAG="ServerConnection";
	private ConnectivityManager connectivity;
	private NetworkInfo netinfo;
	public static boolean isDebug=false;
	
	/**This method use for check Network Connectivity
	 */
	public boolean CheckNetwork(Context mContext) {
		    this.connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	        this.netinfo= connectivity.getActiveNetworkInfo();
	        if(netinfo!=null && netinfo.isConnected()==true)
	        {
	        	//Toast.makeText(this, "Connection Avilable", Toast.LENGTH_LONG).show();
	        	return true;
	        }
	        else
	        {
	        	//String msg = mContext.getResources().getString(R.string.)
	        	Toast.makeText(mContext, Constant.NETWORKNOT, Toast.LENGTH_LONG).show();
	        	return false;       	
	        }
		 
	}
	
	/**This method use for check Network Connectivity No Message
	 */
	public boolean CheckNetworkNoMessage(Context mContext) {
		    this.connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	        this.netinfo= connectivity.getActiveNetworkInfo();
	        if(netinfo!=null && netinfo.isConnected()==true)
	        {
	        	//Toast.makeText(this, "Connection Avilable", Toast.LENGTH_LONG).show();
	        	return true;
	        }
	        else
	        {
	        	//Toast.makeText(mContext, "Not Network Connectivity Avilable", Toast.LENGTH_LONG).show();
	        	return false;       	
	        }
		
	}
	
	/**This method use for PostConnection to Server
	 */
	public JSONArray PostConnection(String strUrl,ArrayList<NameValuePair> alstNameValuePair) {
		InputStream mInputStream = null;
		try {
			//This is the default apacheconnection.
			HttpClient mHttpClient = new DefaultHttpClient();
			
			//Pathe of serverside 
			HttpPost mHttpPost = new HttpPost(strUrl);
			
			if(alstNameValuePair!=null)
			{ 
				//post the valur you want to pass.
				 mHttpPost.setEntity(new UrlEncodedFormEntity(alstNameValuePair));
			}
			
			//get the valu from the saerverside as response.
			HttpResponse mHttpResponse = mHttpClient.execute(mHttpPost);
			HttpEntity mHttpEntity = mHttpResponse.getEntity();
			mInputStream = mHttpEntity.getContent();
		
		  } 
		  catch (Exception e) {
			 // TODO Auto-generated catch block
			 Log.e(strTAG,"Error in HttpClient,HttpPost,HttpResponse,HttpEntity");
		  }
		
		 String strLine = null;
		 String strResult = null;
		//convert response in to the string.
		try {
			  BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream,"iso-8859-1"), 8);
			  StringBuilder mStringBuilder = new StringBuilder();
		  	  while((strLine = mBufferedReader.readLine()) != null) {
		  		mStringBuilder.append(strLine + "\n");
			  }
		  	  mInputStream.close();
		  	  strResult = mStringBuilder.toString();
			 System.out.println("RS :"+strResult.toString());
			
		   } 
		   catch (Exception e) {
			  // TODO Auto-generated catch block
			  //System.out.println("Error in BufferedReadering");
		      Log.e(strTAG,"Error in BufferedReadering");
		    }
		
		JSONArray mJSONArray=null;
		//parse into the json format
		try {
			   if(strResult!=null){
				   mJSONArray = new JSONArray(strResult);
			   }
			   return mJSONArray;
		    } 
	    catch (JSONException e) {
			    // TODO Auto-generated catch block
	    	    Log.e(strTAG,"Error in JSONArray So Null Value Return");
			    return null;
		    }
	}
	
	/**This method use for PostConnectionObject to Server
	 */
	public JSONObject PostConnectionObject(String strUrl,ArrayList<NameValuePair> alstNameValuePair) {
		InputStream mInputStream = null;
		try {
			//This is the default apacheconnection.
			HttpClient mHttpClient = new DefaultHttpClient();
			
			//Pathe of serverside 
			HttpPost mHttpPost = new HttpPost(strUrl);
			if(alstNameValuePair!=null)
			{ 
				//post the valur you want to pass.
				 mHttpPost.setEntity(new UrlEncodedFormEntity(alstNameValuePair));
			}
			
			//get the valu from the saerverside as response.
			HttpResponse mHttpResponse = mHttpClient.execute(mHttpPost);
			HttpEntity mHttpEntity = mHttpResponse.getEntity();
			mInputStream = mHttpEntity.getContent();
		
		  } 
		  catch (Exception e) {
			 // TODO Auto-generated catch block
			 Log.e(strTAG,"Error in HttpClient,HttpPost,HttpResponse,HttpEntity");
		  }
		
		 String strLine = null;
		 String strResult = null;
		//convert response in to the string.
		try {
			  BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream,"iso-8859-1"), 8);
			  StringBuilder mStringBuilder = new StringBuilder();
		  	  while((strLine = mBufferedReader.readLine()) != null) {
		  		mStringBuilder.append(strLine + "\n");
			  }
		  	  mInputStream.close();
		  	  strResult = mStringBuilder.toString();
		  	  System.out.println("R_Ob: "+strResult);
			
		   } 
		   catch (Exception e) {
			  // TODO Auto-generated catch block
		      Log.e(strTAG,"Error in BufferedReadering");
		    }
		
		 JSONObject mJSONObject=null;
		 
		//parse into the json format
		try {
			   if(strResult!=null){
				   mJSONObject = new JSONObject(strResult);
			   }
			   return mJSONObject;
		    } 
	    catch (JSONException e) {
			    // TODO Auto-generated catch block
	    	    Log.e(strTAG,"Error in JSONArray So Null Value Return");
			    return null;
		    }
	}
	
	/**this method is used to get Server Side data from post method.
	 * */
	public String PostConnectionString(String strUrl,ArrayList<NameValuePair> alstNameValuePair) {
		InputStream mInputStream = null;
		try {
			//This is the default apacheconnection.
			HttpClient mHttpClient = new DefaultHttpClient();
			
			//Pathe of serverside 
			HttpPost mHttpPost = new HttpPost(strUrl);
			if(alstNameValuePair!=null)
			{ 
				//post the valur you want to pass.
				 mHttpPost.setEntity(new UrlEncodedFormEntity(alstNameValuePair));
			}
			
			//get the valu from the saerverside as response.
			HttpResponse mHttpResponse = mHttpClient.execute(mHttpPost);
			HttpEntity mHttpEntity = mHttpResponse.getEntity();
			mInputStream = mHttpEntity.getContent();
		
		  } 
		  catch (Exception e) {
			 // TODO Auto-generated catch block
			 Log.e(strTAG,"Error in HttpClient,HttpPost,HttpResponse,HttpEntity");
		  }
		
		 String strLine = null;
		 String strResult = null;
		//convert response in to the string.
		try {
			  BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream,"iso-8859-1"), 8);
			  StringBuilder mStringBuilder = new StringBuilder();
		  	  while((strLine = mBufferedReader.readLine()) != null) {
		  		mStringBuilder.append(strLine + "\n");
			  }
		  	  mInputStream.close();
		  	  strResult = mStringBuilder.toString();
		  	  System.out.println("R_Ob: "+strResult);
		  	  
		   } 
		   catch (Exception e) {
			  // TODO Auto-generated catch block
		      Log.e(strTAG,"Error in BufferedReadering");
		    }
		return strResult;
	}
	/**This method use for GetConnectionObject to Server
	 */
	//public JSONObject GetConnectionObject(String strUrl) {
	public String GetConnectionObject(String strUrl) {
		InputStream mInputStream = null;
		try {
			//This is the default apacheconnection.
			HttpClient mHttpClient = new DefaultHttpClient();		
			//Pathe of serverside 
			HttpGet mHttpGet = new HttpGet(strUrl);
			
			//get the valu from the saerverside as response.
			HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
			HttpEntity mHttpEntity = mHttpResponse.getEntity();
			mInputStream = mHttpEntity.getContent();
		
		  } 
		  catch (Exception e) {
			 // TODO Auto-generated catch block
			 Log.e(strTAG,"Error in HttpClient,HttpPost,HttpResponse,HttpEntity");
		  }
		
		 String strLine = null;
		 String strResult = null;
		//convert response in to the string.
		try {
			BufferedReader mBufferedReader = new BufferedReader(
					new InputStreamReader(mInputStream, "iso-8859-1"), 8);
			StringBuilder mStringBuilder = new StringBuilder();
			while ((strLine = mBufferedReader.readLine()) != null) {
				mStringBuilder.append(strLine + "\n");
			}
			mInputStream.close();
			strResult = mStringBuilder.toString();
			System.out.println(strResult);
			return strResult;
		} catch (Exception e) {
			Log.e(strTAG, "Error in BufferedReadering");
			return "error";
		}
		
//		 JSONObject mJSONObject=null;
//		//parse into the json format
//		try {
//			   if(strResult!=null){
//				   mJSONObject = new JSONObject(strResult);
//			   }
//			   return mJSONObject;
//		    } 
//	    catch (JSONException e) {
//			    // TODO Auto-generated catch block
//	    	    Log.e(strTAG,"Error in JSONArray So Null Value Return");
//			    return null;
//		    }
	}
	
	/**This method use for image Get from server
	 */
	public Bitmap DownloadImageGet(String strUrl)
    {        
        Bitmap mBitmap = null;
        InputStream mInputStream = null;        
        try {
        	mInputStream = OpenHttpConnectionGet(strUrl);
            if(mInputStream != null)
            {
            	 Log.e(strTAG,"Error File Not Found");
            	mBitmap = BitmapFactory.decodeStream(mInputStream);
            	mInputStream.close();
            }
           
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	  Log.e(strTAG,"Error in DownloadImage InputStream");
        }
        return mBitmap;                
    }
	
	/**This method use for OpenHttpConnectionGet
	 */
	public InputStream OpenHttpConnectionGet(String strUrl)throws IOException
    {
        InputStream mInputStream = null;
        int intResponse = -1;
               
        URL mURL = new URL(strUrl); 
        URLConnection mURLConnection = mURL.openConnection();
                 
        if (!(mURLConnection instanceof HttpURLConnection)) {           
           	Log.e(strTAG,"Error in HTTP connection Not Connect");
            throw new IOException("Not an HTTP connection");
        }
        try{
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mURLConnection;
            mHttpURLConnection.setAllowUserInteraction(false);
            mHttpURLConnection.setInstanceFollowRedirects(true);
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection.connect(); 
            intResponse = mHttpURLConnection.getResponseCode();
            
            if (intResponse == HttpURLConnection.HTTP_OK) {
            	mInputStream = mHttpURLConnection.getInputStream();                                 
            }                     
        }
        catch (Exception e)
        {
        	Log.e(strTAG,"Error in Error connecting");
            throw new IOException("Error connecting");            
        }
        return mInputStream;     
    }
    /**This method use for image Post from server
	 */
	public Bitmap DownloadImagePost(String strUrl)
    {        
        Bitmap mBitmap = null;
        InputStream mInputStream = null;        
        try {
        	mInputStream = OpenHttpConnectionPost(strUrl);
            if(mInputStream != null)
            {
            	 Log.e(strTAG,"Error File Not Found");
            	mBitmap = BitmapFactory.decodeStream(mInputStream);
            	mInputStream.close();
            }
           
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	  Log.e(strTAG,"Error in DownloadImage InputStream");
        }
        return mBitmap;                
    }
	
	/**This method use for OpenHttpConnectionpost
	 */
	public InputStream OpenHttpConnectionPost(String strUrl)throws IOException
    {
        InputStream mInputStream = null;
        int intResponse = -1;
               
        URL mURL = new URL(strUrl); 
        URLConnection mURLConnection = mURL.openConnection();
                 
        if (!(mURLConnection instanceof HttpURLConnection)) {           
           	Log.e(strTAG,"Error in HTTP connection Not Connect");
            throw new IOException("Not an HTTP connection");
        }
        try{
            HttpURLConnection mHttpURLConnection = (HttpURLConnection) mURLConnection;
            mHttpURLConnection.setAllowUserInteraction(false);
            mHttpURLConnection.setInstanceFollowRedirects(true);
            mHttpURLConnection.setRequestMethod("POST");
            mHttpURLConnection.connect(); 
            intResponse = mHttpURLConnection.getResponseCode();
            
            if (intResponse == HttpURLConnection.HTTP_OK) {
            	mInputStream = mHttpURLConnection.getInputStream();                                 
            }                     
        }
        catch (Exception e)
        {
        	Log.e(strTAG,"Error in Error connecting");
            throw new IOException("Error connecting");            
        }
        return mInputStream;     
    }
    
//    /**This method Custom Toast Message Display
//	 */  
//    public void  ToastDisplay(Context mContext,int intBackGroundResourceId,int intIconResourceId,String strDisplaystring,int intColor,int intTime,int intGravity) {
//    	
//   	    LayoutInflater inflater=LayoutInflater.from(mContext);
//        View layout=inflater.inflate(R.layout.toast_layout,null);
//        
//        LinearLayout toastlinearlayout=(LinearLayout)layout.findViewById(R.id.toastlinearlayout);
//        toastlinearlayout.setBackgroundResource(intBackGroundResourceId);
//        
//        ImageView toastimage=(ImageView)layout.findViewById(R.id.toastimage);
//        toastimage.setImageResource(intIconResourceId);
//        
//        TextView toasttext=(TextView)layout.findViewById(R.id.toasttext);
//        toasttext.setTextColor(intColor);
//        toasttext.setText(strDisplaystring);
//        
//        Toast toast = new Toast(mContext);
//        toast.setGravity(intGravity, 0, 0);
//	    toast.setDuration(intTime);
//	    toast.setView(layout);
//	    toast.show();	
//	 }
    
    /**This method use to Check Memory Card
	 */  
    public Boolean MemoryCardCheck() {
		
    	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
	}

}
