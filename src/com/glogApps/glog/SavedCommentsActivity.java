package com.glogApps.glog;

import java.util.ArrayList;
import java.util.List;

import com.glogApps.glog.models.Comment;
import com.glogApps.glog.models.Nota;
import com.glogApps.glog.sqlite.NotasDataSource;
import com.glogApps.glog.utils.ArrayAdapterNotes;
import com.gordApps.glog.R;
import com.gordApps.glog.R.layout;
import com.gordApps.glog.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SavedCommentsActivity extends ActionBarActivity {
	
	private ListView lvNotas;	
	private ArrayList<Nota> listaNotas;
	private NotasDataSource dataSource;
	
	private ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_comments);
		
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Comentarios guardados");
		
		// Instanciamos NotasDataSource para
		// poder realizar acciones con la base de datos
		dataSource = new NotasDataSource(this);
		dataSource.open();
		
		// Instanciamos los elementos
		lvNotas = (ListView) findViewById(R.id.savedCommentsLV);

		// Cargamos la lista de notas disponibles
		ArrayList<Nota> listaNotas = dataSource.getAllNotas();
		
		/*ArrayAdapter<Nota> adapter = new ArrayAdapter<Nota>(this,
				android.R.layout.simple_list_item_1, listaNotas);*/
		
		ArrayAdapterNotes adapter = new ArrayAdapterNotes(this, listaNotas);

		// Establecemos el adapter
		lvNotas.setAdapter(adapter);
		
		dataSource.close();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saved_comments, menu);
		return true;
	}

}
