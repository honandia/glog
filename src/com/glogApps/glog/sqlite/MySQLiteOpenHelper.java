package com.glogApps.glog.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static MySQLiteOpenHelper mOpenHelper = null;
	
	private static final String DATABASE_NAME = "Notas";
	private static final int DATABASE_VERSION = 1;
	
	public static class TablaNotas{
		public static String TABLA_NOTAS = "notas";
		public static String COLUMNA_ID = "_id";
		public static String COLUMNA_TEXTO = "texto";
	}
	
	private static final String DATABASE_CREATE = "create table "
			+ TablaNotas.TABLA_NOTAS + "(" + TablaNotas.COLUMNA_ID
			+ " integer primary key autoincrement, " + TablaNotas.COLUMNA_TEXTO
			+ " text not null);";
	
	public static MySQLiteOpenHelper getInstance(Context context){
		if (mOpenHelper == null){
			mOpenHelper = new MySQLiteOpenHelper(context.getApplicationContext());
		}
		
		return mOpenHelper;
	}
	
	private MySQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("delete table if exists " + TablaNotas.TABLA_NOTAS);
		onCreate(db);
	}

}
