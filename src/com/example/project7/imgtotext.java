package com.example.project7;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class imgtotext extends Activity {
	
	Button gallerybutton;
	Button camerabutton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		gallerybutton=(Button)findViewById(R.id.gallery);
		camerabutton=(Button)findViewById(R.id.camera);
		gallerybutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final Intent i=new Intent(imgtotext.this,folder.class);
				final ProgressDialog dialog = ProgressDialog.show(imgtotext.this,"Collecting...","Please Wait!!!");
			      new Thread(){
			      public void run(){
			      try{
			          
			      }
			      catch(Exception ex){}
					startActivity(i);
					dialog.dismiss();
			      }
			      }.start(); 
			}
		});
		camerabutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				final Intent i=new Intent(imgtotext.this,camera.class);
				final ProgressDialog dialog = ProgressDialog.show(imgtotext.this,"Opening Camera...","Please Wait!!!");
			      new Thread(){
			      public void run(){
			      try{
			          Thread.sleep(1000);
			      }
			      catch(Exception ex){}
					startActivity(i);
					dialog.dismiss();
			      }
			      }.start(); 
							
			}
		});
	}
	 @Override
	 	public boolean onKeyDown(int keyCode, KeyEvent event) {
	 	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	 	        Intent ii=new Intent(imgtotext.this,options.class);
	 	        startActivity(ii);
	 	        return true;
	 	    }
	 	    return super.onKeyDown(keyCode, event);
	 	}
}
