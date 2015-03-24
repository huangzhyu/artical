package com.hzy.artical.persistence;

import com.hzy.artical.model.Artical;
import com.hzy.artical.model.Category;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	static final String KEY_ROWID = "_id";
	static final String KEY_FILE_PATH = "filePath";
	static final String KEY_CATEGORY_ID = "categoryId";
	static final String KEY_SAVE_TIME = "saveTime";
	static final String KEY_PARENT_ID = "parentId";
	static final String KEY_CATEGORY_NAME = "name";
	static final String TAG = "DBAdapter";
	static final String DATABASE_NAME = "MyDB";
	static final String DATABASE_TABLE_ARTICALS = "articals";
	static final String DATABASE_TABLE_CATEGORIES = "categories";
	static final int DATABASE_VERSION = 1;

	
	private final Context context;
	
	private DatabaseHelper DBHelper;
	
	private SQLiteDatabase db;
	
	private static final String DATABASE_CREATE = "create table articals (_id integer primary key autoincrement, filePath text not null, categoryId integer not null,  saveTime text not null); create table categories(_id integer primary key autoincrement, parentId integer,name text not null)";

	
	public DBAdapter(Context ctx){
		this.context=ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	public DBAdapter open(){
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		DBHelper.close();
	}
	
	public long insertArtical(Artical artical){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FILE_PATH, artical.getFilePath());
		initialValues.put(KEY_CATEGORY_ID, artical.getCategoryId());
		initialValues.put(KEY_SAVE_TIME, artical.getSaveTime());
		return db.insert(DATABASE_TABLE_ARTICALS, null, initialValues);
	}
	
	public long insertCategory(Category category){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_CATEGORY_NAME, category.getName());
		return db.insert(DATABASE_TABLE_CATEGORIES, null, initialValues);
	}
	
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS articals");
			db.execSQL("DROP TABLE IF EXISTS categories");
			onCreate(db);
		}
	}
}
