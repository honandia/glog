package com.glogApps.glog.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static MySQLiteOpenHelper mOpenHelper = null;
	
	private static final String DATABASE_NAME = "Nota";
	private static final int DATABASE_VERSION = 4;
	
	public static class TablaNotas{
		public static String TABLA_NOTAS = "notas";
		public static String COLUMNA_ID = "_id";
		public static String COLUMNA_TEXTO = "texto";
		public static String COLUMNA_USER = "user";
		public static String COLUMNA_DATE = "date";
		public static String COLUMNA_GLOG = "gLog";
		public static String COLUMNA_DESCGLOG = "descGLog";
		public static String COLUMNA_PLACEGLOG = "placeGLog";
	}
	
	public static class TablaUsuario{
		public static String TABLA_USUARIO = "usuario";
		public static String COLUMNA_ID = "_id";
		public static String COLUMNA_USUARIO = "usuario";
	}
	
	private static final String DATABASE_CREATE = "create table "
			+ TablaNotas.TABLA_NOTAS + "(" + TablaNotas.COLUMNA_ID
			+ " integer primary key autoincrement, " + TablaNotas.COLUMNA_TEXTO
			+ " text not null,"
			+TablaNotas.COLUMNA_USER + " text,"
			+TablaNotas.COLUMNA_DATE + " text,"
			+TablaNotas.COLUMNA_GLOG + " text,"
			+TablaNotas.COLUMNA_DESCGLOG + " text,"
			+TablaNotas.COLUMNA_PLACEGLOG + " text);";
	
	private static final String DATABASE_CREATE_TABLA_USUARIO = "create table "
			+ TablaUsuario.TABLA_USUARIO + "(" + TablaUsuario.COLUMNA_ID
			+ " integer primary key autoincrement, " + TablaUsuario.COLUMNA_USUARIO
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
		db.execSQL(DATABASE_CREATE_TABLA_USUARIO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + TablaNotas.TABLA_NOTAS);
		db.execSQL("drop table if exists " + TablaUsuario.TABLA_USUARIO);
		onCreate(db);
	}

}
