package com.glogApps.glog.utils;

import java.util.ArrayList;

import com.glogApps.glog.ZoneActivity;
import com.glogApps.glog.emoji.Emoji;
import com.glogApps.glog.models.Comment;
import com.gordApps.glog.R;
import com.gordApps.glog.R.id;
import com.gordApps.glog.R.layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
			
			ImageView avatarComment = (ImageView)item.findViewById(R.id.avatarComment);
		//	avatarComment.setImageDrawable(R.drawable.ic_launcher);
			
			//Comment
			TextView text=(TextView)item.findViewById(R.id.textComment);
			text.setText(Emoji.replaceEmoji(arrayComments.get(position).getText()));
			//text.setText(arrayComments.get(position).text);
			
			//Date
			TextView date=(TextView)item.findViewById(R.id.dateComment);
			date.setText(Utils.TimeToTextAgo(arrayComments.get(position).getDate()));
			
			//User
			TextView user=(TextView)item.findViewById(R.id.userComment);
			user.setText(arrayComments.get(position).getUser_id());
			
	
			item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					//rowCommentOptions
					LinearLayout t1 ;
					t1 = (LinearLayout)v.findViewById(R.id.rowCommentOptions);
								
					switch (t1.getVisibility()) {
					case View.VISIBLE:
						t1.setVisibility(View.GONE);
						break;
						
					case View.GONE:
						t1.setVisibility(View.VISIBLE);
						break;

					default:
						break;
					}
					
					
				}
			});
			/*		OnLongClickListener commentsLongClick = new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					
					final CharSequence[] items = { "Contestar", "Contactar", "Compartir","Recordar" };
					AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
					builder.setTitle("Opciones de comentario");
					//builder.setMessage("Cosas que puedes hacer con un comentario");
					builder.setItems(items, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int item) {
							// TODO Auto-generated method stub
							//Toast.makeText(, items[item], Toast.LENGTH_SHORT).show();
						}});
				*/
					/*.setPositiveButton("Aceptar",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
						//			Nota nota = (Nota) adapterView
						//					.getItemAtPosition(position);
						//			dataSource.borrarNota(nota);
									
							//		Toast.makeText(, "comentario guardado", Toast.LENGTH_SHORT);
									
									// Refrescamos la lista
						//			refrescarLista();
								}
							})

					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									return;
								}
							});
							*/
		/*			AlertDialog alert = builder.create();
			        alert.show();
					//builder.show();
					return false;
				}
			};
			item.setOnLongClickListener(commentsLongClick);
			*/
			return item;
			
		}
		
		
		

}
