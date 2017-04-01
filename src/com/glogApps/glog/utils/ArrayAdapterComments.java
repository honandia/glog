package com.glogApps.glog.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.glogApps.glog.UserActivity;
import com.glogApps.glog.emoji.Emoji;
import com.glogApps.glog.models.Comment;
import com.glogApps.glog.models.Nota;
import com.glogApps.glog.sqlite.NotasDataSource;
import com.gordApps.glog.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ArrayAdapterComments extends ArrayAdapter<Comment>{
	
	private Context contexto;
	private ArrayList<Comment> arrayComments;
	private NotasDataSource notasDS;
	private Comment currentComment;
	private ListView lv;
    private LinearLayout layoutACapturar;

		
		/**
		 * Constructor del Adapter
		 * @param contexto: contexto de la activity que hace uso del adapter
		 * @param arrayComments: datos que se desean visualizar en el listview
		 */
		public ArrayAdapterComments(Context contexto,
				ArrayList<Comment> arrayComments) {
			super(contexto, R.layout.comment, arrayComments);
			this.contexto=contexto;
			this.arrayComments=arrayComments;
			this.notasDS = new NotasDataSource(contexto);
			this.currentComment = new Comment();
			

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View item=
			LayoutInflater.from(contexto).inflate(R.layout.comment, null);
			//A partir de esta vista, recojo los controles
			//Imagen de la mascota
//			ImageView fotoMascota=(ImageView)item.findViewById(R.id.imgMascota);
//			fotoMascota.setImageDrawable((utilidades.UtilsImagenes.cargarImagenAssets(contexto, arrayMascotas.get(position).urlImagen)));
			
		
			//ImageView avatarComment = (ImageView)item.findViewById(R.id.avatarComment);
		// Ralentiza mucho la insercion
			//new DownloadImageTask(avatarComment).execute("http://wpc.556e.edgecastcdn.net/80556E/img.news.tops/NE0QIYl0aqek33_1_zzb.jpg");
		
			// llamar a la restapi para obtener los link a partir del user
			
			//	avatarComment.setImageDrawable(R.drawable.ic_launcher);
			
			//Comment
			TextView text=(TextView)item.findViewById(R.id.textNote);
			text.setText(Emoji.replaceEmoji(arrayComments.get(position).getText()));
			//text.setText(arrayComments.get(position).text);
			
			//Date
			TextView date=(TextView)item.findViewById(R.id.dateNote);
			date.setText(Utils.TimeToTextAgo(arrayComments.get(position).getDate()));
			
			//User
			TextView user=(TextView)item.findViewById(R.id.userNote);
			user.setText(arrayComments.get(position).getUser_id());
					
			
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
									currentComment = arrayComments.get(i);
									layoutACapturar = (LinearLayout)lv.getChildAt(i).findViewById(R.id.rowComment);
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
				    .setTitle("Guardar comentario")
				    .setMessage("¿Estas seguro de que quieres guardar este comentario?")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	Nota noteToSave = new Nota();
							noteToSave.setTexto(currentComment.getText());
							noteToSave.setUser(currentComment.getUser_id());
							noteToSave.setDate(currentComment.getDate().format2445());
							
							noteToSave.setgLog("glog");
							noteToSave.setDescGLog("desc glog");
							noteToSave.setPlaceGLog("placeGlog");
							
							notasDS.open();
							notasDS.crearNota(noteToSave);
							notasDS.close();
							Toast.makeText(contexto, "Guardado", Toast.LENGTH_SHORT).show();
				        }
				     })
				    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        	Toast.makeText(contexto, "Comentario no guardado", Toast.LENGTH_SHORT).show();
				        }
				     })
				    .setIcon(R.drawable.ic_copt_record)
				     .show();	
				}
			});
			
			ImageView imgShare = (ImageView)item.findViewById(R.id.img_copt_share);
			imgShare.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
							
					/*TextView nombreZona = new TextView(contexto);
					nombreZona.setText("NOMBRE_DE_LA_ZONA");
					layoutACapturar.addView(nombreZona);*/
					
					compartirView(layoutACapturar);
					
					/*Intent sendIntent = new Intent();
					sendIntent.setAction(Intent.ACTION_SEND);
					sendIntent.putExtra(Intent.EXTRA_TEXT, currentComment.getText());
					sendIntent.setType("text/plain");
					contexto.startActivity(sendIntent);
					Toast.makeText(v.getContext(), "Share", Toast.LENGTH_SHORT).show();
					*/
					
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
					//obtener el user y pasarselo a la activity de users
					
					Intent intent = new Intent(contexto,UserActivity.class);
					intent.putExtra("USER_ID", currentComment.getUser_id());
    	        	contexto.startActivity(intent);
    	            
					Toast.makeText(v.getContext(), "Contact", Toast.LENGTH_SHORT).show();
					
					
					
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
		
		private void compartirView(View viewShare) {
		    // Creamos un bitmap con el tamaño de la vista
		    Bitmap bitmap = Bitmap.createBitmap(viewShare.getWidth(),
		            viewShare.getHeight(), Bitmap.Config.ARGB_8888);
		    // Creamos el canvas para pintar en el bitmap
		    Canvas canvas = new Canvas(bitmap);
		    // Pintamos el contenido de la vista en el canvas y así en el bitmap
		    viewShare.draw(canvas);
		 
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		 
		    Uri uriF = null;
		    try {
		        File f = File.createTempFile("sharedImage", ".jpg",
		                contexto.getExternalCacheDir());
		        f.deleteOnExit();
		        FileOutputStream fo = new FileOutputStream(f);
		        fo.write(stream.toByteArray());
		        fo.close();
		 
		        uriF = Uri.fromFile(f);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		 
		    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		    sharingIntent.setType("image/jpeg");
		 
		    sharingIntent.putExtra(Intent.EXTRA_STREAM, uriF);
		    contexto.startActivity(sharingIntent);
		 
		}
		
		
		

}
