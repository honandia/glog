package com.glogApps.glog.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.glogApps.glog.models.Nota;
import com.glogApps.glog.sqlite.MySQLiteOpenHelper.TablaNotas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotasDataSource {
	private SQLiteDatabase db;
	private MySQLiteOpenHelper dbHelper;
	private String[] columnas = { TablaNotas.COLUMNA_ID,
			TablaNotas.COLUMNA_TEXTO, 
			TablaNotas.COLUMNA_USER, 
			TablaNotas.COLUMNA_DATE, 
			TablaNotas.COLUMNA_GLOG,
			TablaNotas.COLUMNA_DESCGLOG,
			TablaNotas.COLUMNA_PLACEGLOG};

	public NotasDataSource(Context context) {
		dbHelper = MySQLiteOpenHelper.getInstance(context);
	}

	public void open() {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void crearNota(Nota nota) {
		ContentValues values = new ContentValues();
		values.put(TablaNotas.COLUMNA_TEXTO, nota.gettexto());
		values.put(TablaNotas.COLUMNA_USER, nota.getUser());
		values.put(TablaNotas.COLUMNA_DATE, nota.getDate());
		values.put(TablaNotas.COLUMNA_GLOG, nota.getgLog());
		values.put(TablaNotas.COLUMNA_DESCGLOG, nota.getDescGLog());
		values.put(TablaNotas.COLUMNA_PLACEGLOG, nota.getPlaceGLog());
		db.insert(TablaNotas.TABLA_NOTAS, null, values);
	}

	public ArrayList<Nota> getAllNotas() {
		ArrayList<Nota> listaNotas = new ArrayList<Nota>();

		Cursor cursor = db.query(TablaNotas.TABLA_NOTAS, columnas, null, null,
				null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Nota nuevaNota = cursorToNota(cursor);
			listaNotas.add(nuevaNota);
			cursor.moveToNext();
		}

		cursor.close();
		return listaNotas;
	}

	public void borrarNota(Nota nota) {
		long id = nota.getId();
		db.delete(TablaNotas.TABLA_NOTAS, TablaNotas.COLUMNA_ID + " = " + id,
				null);
	}

	private Nota cursorToNota(Cursor cursor) {
		Nota nota = new Nota();
		nota.setId(cursor.getLong(0));
		nota.setTexto(cursor.getString(1));
		nota.setUser(cursor.getString(2));
		nota.setDate(cursor.getString(3));
		nota.setgLog(cursor.getString(4));
		nota.setDescGLog(cursor.getString(5));
		nota.setPlaceGLog(cursor.getString(6));
		return nota;
	}
}

