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
			TablaNotas.COLUMNA_TEXTO };

	public NotasDataSource(Context context) {
		dbHelper = MySQLiteOpenHelper.getInstance(context);
	}

	public void open() {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void crearNota(String nota) {
		ContentValues values = new ContentValues();
		values.put(TablaNotas.COLUMNA_TEXTO, nota);
		db.insert(TablaNotas.TABLA_NOTAS, null, values);
	}

	public List<Nota> getAllNotas() {
		List<Nota> listaNotas = new ArrayList<Nota>();

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
		return nota;
	}
}

