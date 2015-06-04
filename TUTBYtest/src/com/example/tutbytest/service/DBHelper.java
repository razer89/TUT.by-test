package com.example.tutbytest.service;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper instance = null;
	
	private static final String DB_NAME = "tutbyDB";
	private static final int DB_VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper(context);
		}
		return instance;
	}
	
	public static DBHelper getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
					"Instance is not created yet. Call getInstance(Context).");
		}
		return instance;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table tutbyDB ("
				+ "id integer primary key autoincrement," 
				+ "date int"
				+ ");");
	}
	
	public void insert(long time) {
		ContentValues cv = new ContentValues();
		cv.put("date", time);
		getWritableDatabase().insert(DB_NAME, null, cv);
	}
	
	public ArrayList<Long> getAllDates() {
		ArrayList<Long> dateList = new ArrayList<Long>();
		String[] columns = {"date"};
		SQLiteDatabase db = DBHelper.getInstance().getReadableDatabase();
		Cursor c = db.query(DB_NAME, columns, null, null, null, null, null);
		while (c.moveToNext()) {
			dateList.add(Long.valueOf(c.getLong(0)));
		}
		c.close();
		return dateList;
	}
	
	public void eraseDB() {
		getWritableDatabase().delete(DB_NAME, null, null);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}