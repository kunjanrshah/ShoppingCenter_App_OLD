package com.dhl.zoomable;

import com.dhl.shoppingCenter.CommonClass;
import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

public class MultizoomActivity extends Activity {
    private CommonClass mClass = null;
    private TouchImageView touchzoom;
    private LinearLayout mainLinearLayout;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.zoomable);
        
        mClass = new CommonClass();
        mainLinearLayout	=	(LinearLayout)findViewById(R.id.mainLinearLayout);
        touchzoom 			= 	new TouchImageView(this);

     	//touchzoom.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.hello));
     	 //mainLinearLayout.addView(touchzoom);
     	 setShopImages();
    }
    
    //		this is used for set the Images
    private String mURL = null;
	private void setShopImages(){
		
		Bundle mBundle = getIntent().getExtras();
		
		if( mBundle != null)
			mURL = mBundle.getString(Constant.BUNDLE_LAGEPLAN_IMGURL);
		
		System.out.println("mURL :"+mURL);
		if(mClass.CheckNetwork(this)){
			 new Thread(new Runnable() {
				    public void run() {
				      final Bitmap mBitmap = mClass.DownloadImagePost(mURL);
				      touchzoom.post(new Runnable() {
				        public void run() {
				        	touchzoom.setImageBitmap(mBitmap);
				        }
				      });
				    }
				  }).start();
		}
		
		mainLinearLayout.addView(touchzoom);
	}
    
}