package dhl.shoppingcenter.main_menu_specials;

import com.dhl.shoppingCenter.Constant;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MM_Special_ShopingList_Activity extends Activity implements OnClickListener{
	private TextView tvTitleBar = null,tvSecond = null,txt_arrow = null,tvShopingList = null;
	private View lineView = null;
	private Button btn_Home = null,btn_Back = null;
	
	 
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainmenu_actionbutton);
		
		getBasicInfo();
	}
	/**************** getBasicInfo() ********************************/
	private void getBasicInfo(){
		
		tvTitleBar = (TextView)findViewById(R.id.txt_titlebar);
		tvShopingList = (TextView)findViewById(R.id.txt_business_First);
		tvSecond = (TextView)findViewById(R.id.txt_business_Second);
		txt_arrow = (TextView)findViewById(R.id.txt_arrow);
		lineView = (View)findViewById(R.id.view_line);
		
		tvSecond.setVisibility(tvSecond.GONE);
		txt_arrow.setVisibility(txt_arrow.GONE);
		lineView.setVisibility(lineView.GONE);
		
		tvTitleBar.setText(Constant.SHOP_LIST_TITLE);
		tvShopingList.setText(Constant.SHOP_LIST_FARK);
		
		tvShopingList.setOnClickListener(this);
		
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Home.setOnClickListener(this);
		
		btn_Back=(Button)findViewById(R.id.btn_Back);
		btn_Back.setOnClickListener(this);
		btn_Back.setVisibility(btn_Back.VISIBLE);
		
	}
	
	/**************** onClick(View v) ********************************/
	public void onClick(View v) {
		if( v ==  tvShopingList){
			Intent in = new Intent(MM_Special_ShopingList_Activity.this,MM_Special_AddShoppingLIst_Activity.class);
			startActivity(in);
		}else
		if( v == btn_Home){
			Intent in = new Intent(MM_Special_ShopingList_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else
		if ( v == btn_Back ) {
			finish();
		}
	}
	
}
