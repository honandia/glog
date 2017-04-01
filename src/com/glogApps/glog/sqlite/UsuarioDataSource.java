package com.glogApps.glog.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.glogApps.glog.models.Nota;
import com.glogApps.glog.models.User;
import com.glogApps.glog.models.UserDB;
import com.glogApps.glog.sqlite.MySQLiteOpenHelper.TablaNotas;
import com.glogApps.glog.sqlite.MySQLiteOpenHelper.TablaUsuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDataSource {
	private SQLiteDatabase db;
	private MySQLiteOpenHelper dbHelper;
	private String[] columnas = { TablaUsuario.COLUMNA_ID,
			TablaUsuario.COLUMNA_USUARIO};

	public UsuarioDataSource(Context context) {
		dbHelper = MySQLiteOpenHelper.getInstance(context);
	}

	public void open() {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void crearUsuario(UserDB usuario) {
		ContentValues values = new ContentValues();
		values.put(TablaUsuario.COLUMNA_USUARIO, usuario.getUsuario());

		db.insert(TablaUsuario.TABLA_USUARIO, null, values);
	}

	public ArrayList<UserDB> getUsuario() {
		ArrayList<UserDB> listaUsuarios = new ArrayList<UserDB>();

		Cursor cursor = db.query(TablaUsuario.TABLA_USUARIO, columnas, null, null,
				null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			UserDB nuevoUsuario = cursorToUser(cursor);
			listaUsuarios.add(nuevoUsuario);
			cursor.moveToNext();
		}

		cursor.close();
		return listaUsuarios;
	}


	private UserDB cursorToUser(Cursor cursor) {
		UserDB usuario = new UserDB();
		
		usuario.setUsuario(cursor.getString(1));
		
		return usuario;
	}
}
