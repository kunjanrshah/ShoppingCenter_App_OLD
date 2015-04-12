package dhl.shoppingcenter.main_menu_specials;

import java.util.ArrayList;
import com.dhl.shoppingCenter.MainMenu_Activity;
import com.dhl.shoppingCenter.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MM_Special_AddShoppingLIst_Activity extends Activity implements OnClickListener{
	
	private Button btn_Home = null,btn_Back = null,btn_Add = null,btnadd;
	private ArrayList<Integer> AlstList1ID=new ArrayList<Integer>();
	private ArrayList<String> AlstList1=new ArrayList<String>();
	
	private ArrayList<Integer> AlstList2ID=new ArrayList<Integer>();
	private ArrayList<String> AlstList2=new ArrayList<String>();
	
	private EditText edtadd;
	private ListView list1,list2;
	private List1Adp mList1Adp;
	private List2Adp mList2Adp;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xspecial_add_dynamic);
		
		getBasicData();
	}
	/********************************************************************/
	private void getBasicData(){
		btn_Home = (Button)findViewById(R.id.btn_Home);
		btn_Home.setOnClickListener(this);
		
		btn_Back=(Button)findViewById(R.id.btn_Back);
		btn_Back.setOnClickListener(this);
		
		 	btnadd=(Button)findViewById(R.id.btnadd);
	        edtadd=(EditText)findViewById(R.id.edtadd);
	        list1=(ListView)findViewById(R.id.list1);
	        list2=(ListView)findViewById(R.id.list2);
	        mList1Adp=new List1Adp(this);
	        mList2Adp=new List2Adp(this);
	        
	        GetList1();
	        GetList2();
	        
	        btnadd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!edtadd.getText().toString().equals("")){
						 OpenDatabase();
						 	mDbHelper.InsertShopingList(mSQLiteDatabase, edtadd.getText().toString().trim());
						 CloseDataBase();
						 GetList1();
						 edtadd.setText("");
					}
					
				}
			});
	}
	/********************************************************************/
	public void onClick(View v) {
		if( v == btn_Back){
			finish();
		}else
		if( v == btn_Home){
			Intent in = new Intent(MM_Special_AddShoppingLIst_Activity.this, MainMenu_Activity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(in);
			finish();
		}else
		if( v == btn_Add ){
			
		}
	}
	/***********************************************************/
	 private void GetList1(){
   	  OpenDatabase();
	 		  mCursor = mDbHelper.GetShopingList(mSQLiteDatabase);		
		      AlstList1ID.clear();
	 	      AlstList1.clear();
	 		  if(mCursor!=null){
	     	  if(mCursor.getCount() > 0){
	 	    	mCursor.moveToFirst();
	     		while(mCursor.isAfterLast()==false)
	     		{
	     			    AlstList1ID.add(mCursor.getInt(0));
	     			    AlstList1.add(mCursor.getString(1).toString());
	 					mCursor.moveToNext();	          	 						
	     		}
	     	 }
	 	   }
	 	    mCursor.close();
	    CloseDataBase();
	    mCursor=null;
	    list1.setAdapter(mList1Adp);
     }
	 /***********************************************************/
	 
	 private void GetList2(){
	     OpenDatabase();
	 		 mCursor = mDbHelper.GetShopingDone(mSQLiteDatabase);	
		     AlstList2ID.clear();
	 	     AlstList2.clear();
	 		 if(mCursor!=null){
	 		  if(mCursor.getCount() > 0){
	 	    	mCursor.moveToFirst();
	 			while(mCursor.isAfterLast()==false)
	 			{
	 	
	 				    AlstList2ID.add(mCursor.getInt(0));
	 				    AlstList2.add(mCursor.getString(1).toString());
	 					mCursor.moveToNext();	          	 						
	 			}
		 		}
	 	   }
	 	   mCursor.close();
	    CloseDataBase();
	 	mCursor=null;
		list2.setAdapter(mList2Adp);
      }
    
    
    
      class List1Adp extends BaseAdapter{
    	LayoutInflater mLayoutInflater=null;
    	List1Adp(Context context){
    		mLayoutInflater=LayoutInflater.from(context);
    	}
        
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return AlstList1.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=mLayoutInflater.inflate(R.layout.xcustomtext,null);
			}
			
			final LinearLayout linearlayout1=(LinearLayout)convertView.findViewById(R.id.linearlayout1);
			linearlayout1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {				
					OpenDatabase();
				 	mDbHelper.InsertShopingDone(mSQLiteDatabase, AlstList1.get(position));
				 	mDbHelper.DeleteShopingList(mSQLiteDatabase, AlstList1ID.get(position));
				 	CloseDataBase();
				 	GetList1();
				 	GetList2();
				}
			});
			
			final CheckBox checkBox1=(CheckBox)convertView.findViewById(R.id.checkBox1);
			checkBox1.setChecked(false);
			checkBox1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					checkBox1.setChecked(false);
					OpenDatabase();
				 	mDbHelper.InsertShopingDone(mSQLiteDatabase, AlstList1.get(position));
				 	mDbHelper.DeleteShopingList(mSQLiteDatabase, AlstList1ID.get(position));
				 	CloseDataBase();
				 	GetList1();
				 	GetList2();
				}
			});
			
			TextView txt1=(TextView)convertView.findViewById(R.id.txtvalue);
			txt1.setText(AlstList1.get(position));
			txt1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 OpenDatabase();
					 	mDbHelper.InsertShopingDone(mSQLiteDatabase, AlstList1.get(position));
					 	mDbHelper.DeleteShopingList(mSQLiteDatabase, AlstList1ID.get(position));
					 CloseDataBase();
				     GetList1();
				     GetList2();
				}
			});
			txt1.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					DeleteList1(position);
					return true;
				}
			});
			return convertView;
		}
    	
    }
      
    /*********************************************************************************************/
      class List2Adp extends BaseAdapter{
      	LayoutInflater mLayoutInflater=null;
      	List2Adp(Context context){
      		mLayoutInflater=LayoutInflater.from(context);
      	}
          
  		@Override
  		public int getCount() {
  			// TODO Auto-generated method stub
  			return AlstList2.size();
  		}
  		@Override
  		public Object getItem(int position) {
  			// TODO Auto-generated method stub
  			return null;
  		}

  		@Override
  		public long getItemId(int position) {
  			// TODO Auto-generated method stub
  			return 0;
  		}

  		@Override
  		public View getView(final int position, View convertView, ViewGroup parent) {
  			if(convertView==null){
  				convertView=mLayoutInflater.inflate(R.layout.xcustomtext,null);
  			}
  			
  			final LinearLayout linearlayout1=(LinearLayout)convertView.findViewById(R.id.linearlayout1);
  			linearlayout1.setOnClickListener(new OnClickListener() {
  				
  				@Override
  				public void onClick(View v) {				
  					OpenDatabase();
  					 	mDbHelper.InsertShopingList(mSQLiteDatabase, AlstList2.get(position));
  					 	mDbHelper.DeleteShopingDone(mSQLiteDatabase, AlstList2ID.get(position));
  					 CloseDataBase();
  				     GetList1();
  				     GetList2();
  				}
  			});
  			
  			final CheckBox checkBox1=(CheckBox)convertView.findViewById(R.id.checkBox1);
  			checkBox1.setChecked(true);
  			checkBox1.setOnClickListener(new OnClickListener() {
  				
  				@Override
  				public void onClick(View v) {
  					checkBox1.setChecked(true);
  					 OpenDatabase();
  					 	mDbHelper.InsertShopingList(mSQLiteDatabase, AlstList2.get(position));
  					 	mDbHelper.DeleteShopingDone(mSQLiteDatabase, AlstList2ID.get(position));
  					 CloseDataBase();
  				     GetList1();
  				     GetList2();
  				}
  			});
  			
  			TextView txt2=(TextView)convertView.findViewById(R.id.txtvalue);
  			txt2.setText(AlstList2.get(position));
  			txt2.setOnClickListener(new OnClickListener() {
  				
  				@Override
  				public void onClick(View v) {
  					 OpenDatabase();
  					 	mDbHelper.InsertShopingList(mSQLiteDatabase, AlstList2.get(position));
  					 	mDbHelper.DeleteShopingDone(mSQLiteDatabase, AlstList2ID.get(position));
  					 CloseDataBase();
  				     GetList1();
  				     GetList2();				  
  				}
  			});
  			txt2.setOnLongClickListener(new OnLongClickListener() {
  				
  				@Override
  				public boolean onLongClick(View v) {
  					DeleteList2(position);
  					return true;
  				}
  			});
  			return convertView;
  		}
      	
      }
      /****************************************************************************************/
   // This Is For Delete List1
  	private void DeleteList1(final int position) {
  		AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
  		mAlertDialog.setTitle("Delete");
  		mAlertDialog.setIcon(R.drawable.ic_launcher);
  		mAlertDialog.setMessage("Do you want to delete record?");		//"Do you want to delete favorite record?");
  		mAlertDialog.setCancelable(true);
  		mAlertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
  			@Override
  			public void onClick(DialogInterface dialog, int which) {
  				 OpenDatabase();
  				 	mDbHelper.DeleteShopingList(mSQLiteDatabase, AlstList1ID.get(position));
  				 CloseDataBase();
  				 GetList1();
  			}
  		});
  		mAlertDialog.setButton2("No", new DialogInterface.OnClickListener() {

  			@Override
  			public void onClick(DialogInterface dialog, int which) {
  				dialog.cancel();
  			}
  		});

  		// Create and show the dialog
  		mAlertDialog.show();
  	}
  	
  // This Is For Delete List2
  	private void DeleteList2(final int position) {
  		AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
  		mAlertDialog.setTitle("Delete");
  		mAlertDialog.setIcon(R.drawable.ic_launcher);
  		mAlertDialog.setMessage("Do you want to delete record?");		//"Do you want to delete favorite record?");
  		mAlertDialog.setCancelable(true);
  		mAlertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
  			@Override
  			public void onClick(DialogInterface dialog, int which) {
  				 OpenDatabase();
  				 	mDbHelper.DeleteShopingDone(mSQLiteDatabase, AlstList2ID.get(position));
  				 CloseDataBase();
  				 GetList2();
  			}
  		});
  		mAlertDialog.setButton2("No", new DialogInterface.OnClickListener() {

  			@Override
  			public void onClick(DialogInterface dialog, int which) {
  				dialog.cancel();
  			}
  		});

  		// Create and show the dialog
  		mAlertDialog.show();
  	}
      
  	private Cursor mCursor=null;
  	private SQLiteDatabaseHelper mDbHelper=null;
  	private SQLiteDatabase mSQLiteDatabase=null;
  	private void OpenDatabase()
  	  {
  	  	    mDbHelper = new SQLiteDatabaseHelper(this);
  	  	    mSQLiteDatabase = mDbHelper.getWritableDatabase();
  	  	    mSQLiteDatabase.setLockingEnabled(true);
  	  }
  	private void CloseDataBase()
  	  {
  		if(mDbHelper!=null || mSQLiteDatabase!=null){
  	  		mSQLiteDatabase.close();
  	  		mDbHelper.close();
  		}
  	  }
}
