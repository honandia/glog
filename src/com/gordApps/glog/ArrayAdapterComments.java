package com.gordApps.glog;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterComments extends ArrayAdapter<Comment>{
	
	private Context contexto;
	private ArrayList<Comment> arrayComments;
		
		/**
		 * Constructor del Adapter
		 * @param contexto: contexto de la activity que hace uso del adapter
		 * @param arrayMascotas: datos que se desean visualizar en el listview
		 */
		public ArrayAdapterComments(Context contexto,
				ArrayList<Comment> arrayComments) {
			super(contexto, R.layout.comment, arrayComments);
			this.contexto=contexto;
			this.arrayComments=arrayComments;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View item=
			LayoutInflater.from(contexto).inflate(R.layout.comment, null);
			//A partir de esta vista, recojo los controles
			//Imagen de la mascota
//			ImageView fotoMascota=(ImageView)item.findViewById(R.id.imgMascota);
//			fotoMascota.setImageDrawable((utilidades.UtilsImagenes.cargarImagenAssets(contexto, arrayMascotas.get(position).urlImagen)));
			//Nombre
			TextView text=(TextView)item.findViewById(R.id.textComment);
			text.setText(arrayComments.get(position).text);
			//Descripción
//			TextView descrip=(TextView)item.findViewById(R.id.txtDescrip);
//			descrip.setText(arrayMascotas.get(position).descrip);
			//Edad
//			TextView edad=(TextView)item.findViewById(R.id.txtEdad);
//			edad.setText(Integer.toString(arrayMascotas.get(position).edad));
			
			return item;
			
		}
		
		
		

}
