package dhl.shoppingcenter.main_menu_specials;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper{
	private static final String SHOPINGLIST="ShopingList";
	private static final String DONELIST = "ShopingDone";
	public SQLiteDatabaseHelper(Context context){
		super(context, "ShopingListDatabase", null, 1);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String creatTAB1 = "CREATE TABLE "+SHOPINGLIST+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE VARCHAR(25))";
		String creatTAB2 = "CREATE TABLE "+DONELIST+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE VARCHAR(25))";
		db.execSQL(creatTAB1);
		db.execSQL(creatTAB2);
		System.out.println("Table is created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
		
	}
	
	public void InsertShopingList(SQLiteDatabase db,String title){
		ContentValues cv = new ContentValues();
		cv.put("TITLE", title);
		db.insert(SHOPINGLIST, null, cv);
	}
	
	public Cursor GetShopingList(SQLiteDatabase db){
		return  db.query(SHOPINGLIST,null, null, null, null, null, null);
	}
	
	public void DeleteShopingList(SQLiteDatabase db,int id){
		String strwhere ="_id =" + id;
		db.delete(SHOPINGLIST, strwhere, null);
	}

	
	public void InsertShopingDone(SQLiteDatabase db,String title){
		ContentValues cv = new ContentValues();
		cv.put("TITLE", title);
		db.insert(DONELIST, null, cv);
	}
	
	public Cursor GetShopingDone(SQLiteDatabase db){
		return  db.query(DONELIST,null, null, null, null, null, null);
	}
	
	public void DeleteShopingDone(SQLiteDatabase db,int id){
		String strwhere ="_id =" + id;
		db.delete(DONELIST, strwhere, null);
	}

}
