package com.glogApps.glog;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.glogApps.glog.emoji.Emoji;
import com.glogApps.glog.emoji.EmojiView;
import com.glogApps.glog.emoji.SizeNotifierRelativeLayout;
import com.glogApps.glog.models.Comment;
import com.glogApps.glog.utils.ArrayAdapterComments;
import com.glogApps.glog.utils.Utils;
import com.gordApps.glog.R;

/*import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;*/



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ZoneActivity extends ActionBarActivity implements SizeNotifierRelativeLayout.SizeNotifierRelativeLayoutDelegate{

	private ListView lstComments;
	//private String[] comments;
	
	
	
	private ArrayList<Comment> comments;
	
	private String idZone;
	private String nameZone;
	
	//******************
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	public static final int DIALOG_INSERT_COMENT = 1;
	
	private ProgressDialog mProgressDialog;
	
	//@+id/nameZoneLog
	//@+id/txtComment
	//@+id/btnGlog
	
	private TextView nameZoneLog;
	private EditText txtComment;
	
	private int keyboardHeight = 0;
    private int keyboardHeightLand = 0;
	private EmojiView emojiView;
	private ImageView emojiButton;
	private PopupWindow emojiPopup;
	public ActionBarActivity parentActivity;
	private View contentView;
	private boolean keyboardVisible;
	private SizeNotifierRelativeLayout sizeNotifierRelativeLayout;
	
	
	private Comment comment;

	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_DOWNLOAD_PROGRESS:
	        mProgressDialog = new ProgressDialog(this);
	        mProgressDialog.setMessage("Cargando comentarios ...");
	        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        mProgressDialog.setCancelable(false);
	        mProgressDialog.show();
	        return mProgressDialog;
	    
	    case DIALOG_INSERT_COMENT:
	        mProgressDialog = new ProgressDialog(this);
	        mProgressDialog.setMessage("Insertando comentario ...");
	        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        mProgressDialog.setCancelable(false);
	        mProgressDialog.show();
	        return mProgressDialog;
	        
	    default:
	    return null;
	    }
	}
	//******************
	
	public void insertarComentario(View view){
		
		TareaRAInsertarComentario tarea = new TareaRAInsertarComentario();
		tarea.execute();
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zone);
		
		//obtenemos el id de la zona
		
		Bundle bundle = getIntent().getExtras();
		idZone= bundle.getString("ZONE_ID");
		nameZone = bundle.getString("ZONE_NAME");
				
		
		nameZoneLog = (TextView) findViewById(R.id.nameZoneLog);
		

		nameZoneLog.setText(nameZone);
		
		//nameZoneLog.setText((CharSequence) nameZoneLog);
		
		emojiButton = (ImageView)findViewById(R.id.chat_smile_button);
		
		txtComment = (EditText) findViewById(R.id.txtComment);
		
		lstComments = (ListView)findViewById(R.id.commentsZoneLog);
		
		parentActivity = (ActionBarActivity)this;
		
		
		emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emojiPopup == null) {
                    showEmojiPopup(true);
                } else {
                    showEmojiPopup(!emojiPopup.isShowing());
                }
            }
        });
		
		txtComment.setOnKeyListener(new View.OnKeyListener() {
             @Override
             public boolean onKey(View view, int i, KeyEvent keyEvent) {
                 if (i == 4 && !keyboardVisible && emojiPopup != null && emojiPopup.isShowing()) {
                     if (keyEvent.getAction() == 1) {
                         showEmojiPopup(false);
                     }
                     return true;
                 } /*else if (i == KeyEvent.KEYCODE_ENTER && sendByEnter && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                     sendMessage();
                     return true;
                 }*/
                 return false;
             }
         });
		
		sizeNotifierRelativeLayout = (SizeNotifierRelativeLayout)findViewById(R.id.zone_layout);
        sizeNotifierRelativeLayout.delegate = this;
        contentView = sizeNotifierRelativeLayout;
		
        //contentView = findViewById(R.layout.activity_zone);
		
		
		
		
		//*************************
		//Obtener un listado de los comentarios

		
		
		
		
		
		
		
		
		/*if (savedInstanceState == null) {
		    Bundle extras = getIntent().getExtras();
		    if(extras == null) {
		    	idZone= null;
		    } else {
		    	idZone= extras.getString("STRING_I_NEED");
		    }
		} else {
			idZone= (String) savedInstanceState.getSerializable("ZONE_ID");
		}*/
		
		
		
		TareaRAObtenerComentarios tarea = new TareaRAObtenerComentarios();
	    tarea.execute();

	}

	private void createEmojiPopup() {
        emojiView = new EmojiView(parentActivity);
        emojiView.setListener(new EmojiView.Listener() {
            public void onBackspace() {
            	txtComment.dispatchKeyEvent(new KeyEvent(0, 67));
            }

            public void onEmojiSelected(String paramAnonymousString) {
                int i = txtComment.getSelectionEnd();
                CharSequence localCharSequence = Emoji.replaceEmoji(paramAnonymousString);
                txtComment.setText(txtComment.getText().insert(i, localCharSequence));
                int j = i + localCharSequence.length();
                txtComment.setSelection(j, j);
            }
        });
        emojiPopup = new PopupWindow(emojiView);
    }

	private void showEmojiPopup(boolean show) {
        if (parentActivity == null) {
            return;
        }
        InputMethodManager localInputMethodManager = (InputMethodManager)parentActivity.getSystemService("input_method");
        if (show) {
            if (emojiPopup == null) {
                createEmojiPopup();
            }
            int currentHeight;
            WindowManager manager = (WindowManager) ApplicationLoader.applicationContext.getSystemService(Activity.WINDOW_SERVICE);
            int rotation = manager.getDefaultDisplay().getRotation();
            if (keyboardHeight <= 0) {
                keyboardHeight = parentActivity.getSharedPreferences("emoji", 0).getInt("kbd_height", Emoji.scale(200.0f));
            }
            if (keyboardHeightLand <= 0) {
                keyboardHeightLand = parentActivity.getSharedPreferences("emoji", 0).getInt("kbd_height_land3", Emoji.scale(200.0f));
            }
            if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_90) {
                currentHeight = keyboardHeightLand;
            } else {
                currentHeight = keyboardHeight;
            }
            emojiPopup.setHeight(View.MeasureSpec.makeMeasureSpec(currentHeight, View.MeasureSpec.EXACTLY));
            emojiPopup.setWidth(View.MeasureSpec.makeMeasureSpec(contentView.getWidth(), View.MeasureSpec.EXACTLY));

            emojiPopup.showAtLocation(parentActivity.getWindow().getDecorView(), 83, 0, 0);
            if (!keyboardVisible) {
                contentView.setPadding(0, 0, 0, currentHeight);
                emojiButton.setImageResource(R.drawable.ic_msg_panel_hide);
                return;
            }
            emojiButton.setImageResource(R.drawable.ic_msg_panel_kb);
            return;
        }
        if (emojiButton != null) {
            emojiButton.setImageResource(R.drawable.ic_msg_panel_smiles);
        }
        if (emojiPopup != null) {
            emojiPopup.dismiss();
        }
        if (contentView != null) {
            contentView.post(new Runnable() {
                public void run() {
                    if (contentView != null) {
                        contentView.setPadding(0, 0, 0, 0);
                    }
                }
            });
        }
    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zone, menu);
		return true;
	}
	
	private class TareaRAInsertarComentario extends AsyncTask<String,Integer,Boolean> {
		 
		 @Override
		    protected void onPreExecute() {
		        super.onPreExecute();
		        showDialog(DIALOG_INSERT_COMENT);
		    }


		    protected void onProgressUpdate(String... progress) {        
		    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		    }
		
		
		
		protected Boolean doInBackground(String... params) {
	 		
			boolean resul = true;
	 
			HttpClient httpClient = new DefaultHttpClient();
			 
			HttpPut put = new HttpPut("http://restapiglog.herokuapp.com/zone/"+idZone+"/comment");
			put.setHeader("content-type", "application/json");
			 
			try
			{
			
				
				//Construimos el objeto cliente en formato JSON
			    JSONObject dato = new JSONObject();
			 
			    comment = new Comment(txtComment.getText().toString(),Utils.getDatePhone(),"Jaimito");
			    
			    dato.put("text", comment.text);
			    dato.put("date", comment.date);		  
			    dato.put("user_id", comment.user_id);
			 
			    StringEntity entity = new StringEntity(dato.toString());
			    put.setEntity(entity);
			 
			        HttpResponse resp = httpClient.execute(put);
			        String respStr = EntityUtils.toString(resp.getEntity());
			 
			   //     if(respStr.equals("true"))
			  //          lblResultado.setText("Actualizado OK.");
			}
			catch(Exception ex)
			{
			        Log.e("ServicioRest","Error!", ex);
			}
	 
	        return resul;
	    }
	 
	    protected void onPostExecute(Boolean result) {
	 
	        if (result)
	        {

	        	comments.add(comment);
	        	
	        	ArrayAdapterComments adaptador = new ArrayAdapterComments(ZoneActivity.this, comments);
	        	
	        	 
                lstComments.setAdapter(adaptador);
              
               dismissDialog(DIALOG_INSERT_COMENT);
               
	        	Toast.makeText(ZoneActivity.this,"Comentario insertado", Toast.LENGTH_SHORT).show();

	        }
	    }
	}
	
	private class TareaRAObtenerComentarios extends AsyncTask<String,Integer,Boolean> {
		 
		 @Override
		    protected void onPreExecute() {
		        super.onPreExecute();
		        showDialog(DIALOG_DOWNLOAD_PROGRESS);
		    }


		    protected void onProgressUpdate(String... progress) {        
		    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		    }
		
		
		
		protected Boolean doInBackground(String... params) {
	 		
			boolean resul = true;
	 
	        HttpClient httpClient = new DefaultHttpClient();
	        
	        HttpGet del = new HttpGet("http://restapiglog.herokuapp.com/commentsinzone/"+idZone);
	         
	        del.setHeader("content-type", "application/json");
	         
	        try
	        {
	                HttpResponse resp = httpClient.execute(del);
	                String respStr = EntityUtils.toString(resp.getEntity());
	         
	                JSONArray respJSON = new JSONArray(respStr);
	         
	    //            comments = new String[respJSON.length()];
	                comments = new ArrayList<Comment>();
	         
	                for(int i=0; i<respJSON.length(); i++)
	                {
	                    JSONObject obj = respJSON.getJSONObject(i);
	         
	                    String text = obj.getString("text");
	                    String date = obj.getString("date");
	                    String user = obj.getString("user_id");
	                    
	                    comments.add(new Comment(text,date,user));
	                             
	                 //   comments[i] = "" + text;
	                    
	                }         
	        }
	        catch(Exception ex)
	        {
	                Log.e("ServicioRest","Error!", ex);
	        }
	 
	        return resul;
	    }
	 
	    protected void onPostExecute(Boolean result) {
	 
	        if (result)
	        {
	        	//Rellenamos la lista con los resultados
	        	
	 //       	lvMascotas=(ListView)findViewById(R.id.listMascotas);
	 //   		ArrayList<Mascota> arrayMascotas=utilidades.BBDDfake.getMascotas();
	 //   		ArrayAdapterMascotas adapter=new ArrayAdapterMascotas(this, arrayMascotas);
	 //   		lvMascotas.setAdapter(adapter);
	    		
	        	ArrayAdapterComments adaptador = new ArrayAdapterComments(ZoneActivity.this, comments);
	        	
	     /*   	ArrayAdapter<String> adaptador =
                        new ArrayAdapter<String>(ZoneActivity.this,
                        android.R.layout.simple_list_item_1, comments);*/
                        		
                        		
                        //		R.layout.comment, comments);
                
                //lstComments = (ListView)findViewById(R.id.commentsZoneLog); 
                
	        	lstComments.setAdapter(adaptador);
               
                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
                
	        	Toast.makeText(ZoneActivity.this,"Listado de comentarios obtenidos", Toast.LENGTH_SHORT).show();
	        	// lblResultado.setText("Insertado OK.");
	        }
	    }
	}

	@Override
	public void onSizeChanged(int height) {
		Rect localRect = new Rect();
        parentActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);

        WindowManager manager = (WindowManager) ApplicationLoader.applicationContext.getSystemService(Activity.WINDOW_SERVICE);
        int rotation = manager.getDefaultDisplay().getRotation();

        if (height > Emoji.scale(50)) {
            if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_90) {
                keyboardHeightLand = height;
                parentActivity.getSharedPreferences("emoji", 0).edit().putInt("kbd_height_land3", keyboardHeightLand).commit();
            } else {
                keyboardHeight = height;
                parentActivity.getSharedPreferences("emoji", 0).edit().putInt("kbd_height", keyboardHeight).commit();
            }
        }

        if (emojiPopup != null && emojiPopup.isShowing()) {
            WindowManager wm = (WindowManager) parentActivity.getSystemService(Context.WINDOW_SERVICE);
            final WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams)emojiPopup.getContentView().getLayoutParams();
            layoutParams.width = contentView.getWidth();
            if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_90) {
                layoutParams.height = keyboardHeightLand;
            } else {
                layoutParams.height = keyboardHeight;
            }
            wm.updateViewLayout(emojiPopup.getContentView(), layoutParams);
            if (!keyboardVisible) {
                contentView.post(new Runnable() {
                    @Override
                    public void run() {
                        contentView.setPadding(0, 0, 0, layoutParams.height);
                        contentView.requestLayout();
                    }
                });
            }
        }

        boolean oldValue = keyboardVisible;
        keyboardVisible = height > 0;
        if (keyboardVisible && contentView.getPaddingBottom() > 0) {
            showEmojiPopup(false);
        } else if (!keyboardVisible && keyboardVisible != oldValue && emojiPopup != null && emojiPopup.isShowing()) {
            showEmojiPopup(false);
        }
		
	}
}
