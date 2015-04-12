package com.dhl.shoppingCenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Shops_Activity extends Activity implements OnClickListener {
	private TextView tvBusiness_ABC =null, tvBusiness_branhe = null;
	private Button btn_Home = null;
	private CommonClass mClass = null;
	private Typeface calibri = null,calibri_bold;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xshop);
		
		getBasicData();
	}
	
	private void getBasicData(){
		
		mClass = new CommonClass();
		
		tvBusiness_ABC = (TextView)findViewById(R.id.txt_business_abc);
		tvBusiness_branhe =	(TextView)findViewById(R.id.txt_business_branche);
		
		btn_Home = 	(Button)findViewById(R.id.btn_Home);
		btn_Home.setOnClickListener(this);
		
		tvBusiness_ABC.setOnClickListener(this);
		tvBusiness_branhe.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		Intent in = null;
		
		if( v == btn_Home){
			finish();
		}else 
		if( v == tvBusiness_ABC )
		{
			in = new Intent(Shops_Activity.this, Shop_Category_Activity.class);
			in.setFlags(in.FLAG_ACTIVITY_CLEAR_TOP);
			in.putExtra(Constant.BUNDLE_WHICH_CATAGORY, Constant.BUNDLE_ABC);
			startActivity(in);
		}else
		if( v == tvBusiness_branhe ){
			in = new Intent(Shops_Activity.this, Shop_Category_Activity.class);
			in.putExtra(Constant.BUNDLE_WHICH_CATAGORY, Constant.BUNDLE_BRANCHE);
			startActivity(in);
		}
	}
}
