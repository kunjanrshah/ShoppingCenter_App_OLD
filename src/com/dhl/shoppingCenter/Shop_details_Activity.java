package com.dhl.shoppingCenter;

import dhl.shopingcenter.tab.Tab_Info_Activity;
import dhl.shopingcenter.tab.Tab_Lageplan_Activity;
import dhl.shopingcenter.tab.Tab_Shop_Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class Shop_details_Activity extends TabActivity implements  OnTabChangeListener {
	private TabHost tabHost=null;
	private TabSpec Home_TabSpec,Info_TabSpec,LegePlan_TabSpec,FavoriteTabSpec,MoreTabSpec;
	private Resources res=null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xshop_details);
        res = getResources(); 
        /* TabHost will have Tabs */
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        
        /* TabSpec used to create a new tab. 
         * By using TabSpec only we can able to setContent to the tab.
         * By using TabSpec setIndicator() we can set name to tab. */
        
        /* Search is SearchTabSpec Id. Its used to access outside. */
        Home_TabSpec = tabHost.newTabSpec( Constant.TAB_SHOP);
        Home_TabSpec.setIndicator( Constant.TAB_SHOP,res.getDrawable(R.drawable.tab_home));
        Bundle mBundle = getIntent().getExtras();
        
        Intent mIntentSearch=new Intent(this,Tab_Shop_Activity.class);
        mIntentSearch.putExtra(Constant.BUNDLE_SHOP, mBundle.getString(Constant.BUNDLE_SHOP));
        mIntentSearch.putExtra(Constant.BUNDEL_SHOP_NAME, mBundle.getString(Constant.BUNDEL_SHOP_NAME));
        mIntentSearch.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Home_TabSpec.setContent(mIntentSearch);
        tabHost.addTab(Home_TabSpec);
        
        Info_TabSpec = tabHost.newTabSpec( Constant.TAB_INFO );
        Info_TabSpec.setIndicator(Constant.TAB_INFO,res.getDrawable(R.drawable.tab_info));
        Intent mIntentEvents=new Intent(this,Tab_Info_Activity.class);
        mIntentEvents.putExtra(Constant.BUNDLE_SHOP, mBundle.getString(Constant.BUNDLE_SHOP));
        mIntentEvents.putExtra(Constant.BUNDEL_SHOP_NAME, mBundle.getString(Constant.BUNDEL_SHOP_NAME));
        mIntentEvents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Info_TabSpec.setContent(mIntentEvents);
        tabHost.addTab(Info_TabSpec);
        
        
        LegePlan_TabSpec = tabHost.newTabSpec( Constant.TAB_LAGEPLAN );
        LegePlan_TabSpec.setIndicator( Constant.TAB_LAGEPLAN ,res.getDrawable(R.drawable.tab_lageplan));
        Intent mIntentLogo=new Intent(this,Tab_Lageplan_Activity.class);
        mIntentLogo.putExtra(Constant.BUNDLE_SHOP, mBundle.getString(Constant.BUNDLE_SHOP));
        mIntentLogo.putExtra(Constant.BUNDEL_SHOP_NAME, mBundle.getString(Constant.BUNDEL_SHOP_NAME));
        mIntentLogo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        LegePlan_TabSpec.setContent(mIntentLogo);
        tabHost.addTab(LegePlan_TabSpec);
        
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
          tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor(Constant.TransperantColor));
        }
        
        tabHost.setCurrentTab(0);
        ImageView ivSearch = (ImageView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.icon);
        ivSearch.setImageDrawable(getResources().getDrawable(R.drawable.tab_home_hover));

       ((TextView)((RelativeLayout)tabHost.getTabWidget().getChildAt(0)).getChildAt(1)).setTextColor(Color.parseColor(Constant.WhiteColor));

        tabHost.setOnTabChangedListener(this);
        
        
    }
	
	//this code for detect click on tab
	@Override
	public void onTabChanged(String tabId) {
		
		if(tabId.equals(Constant.TAB_SHOP)){
			
			ImageView ivSearch = (ImageView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.icon);
	        ivSearch.setImageDrawable(getResources().getDrawable(R.drawable.tab_home_hover));

	       ((TextView)((RelativeLayout)tabHost.getTabWidget().getChildAt(0)).getChildAt(1)).setTextColor(Color.parseColor(Constant.WhiteColor));
		}
		else{
			ImageView ivSearch = (ImageView)tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.icon);
	        ivSearch.setImageDrawable(getResources().getDrawable(R.drawable.tab_home));
	        
	        ((TextView)((RelativeLayout)tabHost.getTabWidget().getChildAt(0)).getChildAt(1)).setTextColor(Color.parseColor(Constant.WhiteColor));
		}
		
		if(tabId.equals(Constant.TAB_INFO)){
			 ImageView ivEvents = (ImageView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.icon);
	         ivEvents.setImageDrawable(getResources().getDrawable(R.drawable.tab_info_hover));
	         
	         ((TextView)((RelativeLayout)tabHost.getTabWidget().getChildAt(1)).getChildAt(1)).setTextColor(Color.parseColor(Constant.WhiteColor));
		}
		else{
			ImageView ivEvents = (ImageView)tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.icon);
	        ivEvents.setImageDrawable(getResources().getDrawable(R.drawable.tab_info));
	        
	        ((TextView)((RelativeLayout)tabHost.getTabWidget().getChildAt(1)).getChildAt(1)).setTextColor(Color.parseColor(Constant.WhiteColor));
		}
		
		if(tabId.equals(Constant.TAB_LAGEPLAN)){
			 ImageView ivLogo = (ImageView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.icon);
	         ivLogo.setImageDrawable(getResources().getDrawable(R.drawable.tab_lageplan_hover));
		}
		else{
			ImageView ivLogo = (ImageView)tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.icon);
	        ivLogo.setImageDrawable(getResources().getDrawable(R.drawable.tab_lageplan));
		}
		
	}
}
