package com.glogApps.glog.utils;

import java.util.ArrayList;

import com.glogApps.glog.ZoneActivity;
import com.glogApps.glog.emoji.Emoji;
import com.glogApps.glog.models.Comment;
import com.glogApps.glog.models.Nota;
import com.glogApps.glog.sqlite.NotasDataSource;
import com.gordApps.glog.R;
import com.gordApps.glog.R.id;
import com.gordApps.glog.R.layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ArrayAdapterNotes extends ArrayAdapter<Nota>{
	
	private Context contexto;
	private ArrayList<Nota> arrayNotas;
	private NotasDataSource notasDS;
	private Nota currentNota;
	private ListView lv;


		
		/**
		 * Constructor del Adapter
		 * @param contexto: contexto de la activity que hace uso del adapter
		 * @param arrayMascotas: datos que se desean visualizar en el listview
		 */
		public ArrayAdapterNotes(Context contexto,
				ArrayList<Nota> arrayNotas) {
			super(contexto, R.layout.note, arrayNotas);
			this.contexto=contexto;
			this.arrayNotas=arrayNotas;
			this.notasDS = new NotasDataSource(contexto);
			this.currentNota = new Nota();
			

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View item=
			LayoutInflater.from(contexto).inflate(R.layout.note, null);
			//A partir de esta vista, recojo los controles
			//Imagen de la mascota
//			ImageView fotoMascota=(ImageView)item.findViewById(R.id.imgMascota);
//			fotoMascota.setImageDrawable((utilidades.UtilsImagenes.cargarImagenAssets(contexto, arrayMascotas.get(position).urlImagen)));
			
		
		//	ImageView avatarComment = (ImageView)item.findViewById(R.id.avatarComment);
		// Ralentiza mucho la insercion
		//	new DownloadImageTask(avatarComment).execute("http://wpc.556e.edgecastcdn.net/80556E/img.news.tops/NE0QIYl0aqek33_1_zzb.jpg");
		
			// llamar a la restapi para obtener los link a partir del user
			
			//	avatarComment.setImageDrawable(R.drawable.ic_launcher);
			
			//Comment
			TextView text=(TextView)item.findViewById(R.id.textNote);
			text.setText(Emoji.replaceEmoji(arrayNotas.get(position).gettexto()));
			//text.setText(arrayComments.get(position).text);
			
			//Date
			TextView date=(TextView)item.findViewById(R.id.dateNote);
			date.setText(arrayNotas.get(position).getDate());
			
			//User
			TextView user=(TextView)item.findViewById(R.id.userNote);
			user.setText(arrayNotas.get(position).getUser());
					
			
			lv = (ListView)parent.findViewById(R.id.savedCommentsLV);
			
			item.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
				
					//Actualizamos el currentComment
					//currentComment.setUser_id(((TextView)v.findViewById(R.id.userComment)).getText().toString());
					//currentComment.setDate(Utils.(((TextView)v.findViewById(R.id.dateComment)).getText().toString()));
					//currentComment.setText(((TextView)v.findViewById(R.id.txtComment)).getText().toString());
					
					
					//ocultamos todas las rowCommentOptions
					for (int i = 0; i < lv.getChildCount(); i++) {
						lv.getChildAt(i).findViewById(R.id.rowNoteOptions).setVisibility(View.GONE);
					}
					//mostramos sólo la rowCommentOptions que corresponde
					LinearLayout t1 ;
					t1 = (LinearLayout)v.findViewById(R.id.rowNoteOptions);
							
					switch (t1.getVisibility()) {
						case View.VISIBLE:
							t1.setVisibility(View.GONE);
							
							break;
							
						case View.GONE:
							t1.setVisibility(View.VISIBLE);
							for (int i = 0; i < lv.getChildCount(); i++) {
								if(lv.getChildAt(i).findViewById(R.id.rowNoteOptions).getVisibility() == View.VISIBLE)
								{
									currentNota = arrayNotas.get(i);
								}
							}
							
							break;
	
						default:
							break;
					}
					
					
				}
			});
			ImageView imgRecord = (ImageView)item.findViewById(R.id.img_nopt_delete);
			imgRecord.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {					
					
					new AlertDialog.Builder(contexto)
				    .setTitle("Borrar Nota")
				    .setMessage("¿Estas seguro de que quieres borrar esta nota?")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	notasDS.open();
							notasDS.borrarNota(currentNota);
							notasDS.close();
							arrayNotas.remove(currentNota);
							
							ArrayAdapterNotes.this.notifyDataSetChanged();
							
							Toast.makeText(contexto, "Nota borrada", Toast.LENGTH_SHORT).show();
				        }
				     })
				    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        	Toast.makeText(contexto, "Borrado cancelado", Toast.LENGTH_SHORT).show();
				        }
				     })
				    .setIcon(R.drawable.img_nopt_delete)
				     .show();
					
					
				}
			});
		/*	
			ImageView imgShare = (ImageView)item.findViewById(R.id.img_copt_share);
			imgShare.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Toast.makeText(v.getContext(), "Share", Toast.LENGTH_SHORT).show();
					
					
				}
			});
			
			ImageView imgReply = (ImageView)item.findViewById(R.id.img_copt_reply);
			imgReply.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Toast.makeText(v.getContext(), "Reply", Toast.LENGTH_SHORT).show();
					
				}
			});
			
			ImageView imgContact = (ImageView)item.findViewById(R.id.img_copt_contact);
			imgContact.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Toast.makeText(v.getContext(), "Contact", Toast.LENGTH_SHORT).show();
					
					
					
				}
			});
			
			*/
			
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
