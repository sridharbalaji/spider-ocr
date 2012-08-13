package com.example.project7;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class starter extends Activity {
    /** Called when the activity is first created. */
	
	private Button newbutton,helpbutton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        newbutton=(Button)findViewById(R.id.new1);
        helpbutton=(Button)findViewById(R.id.help);
        newbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				final ProgressDialog dialog = ProgressDialog.show(starter.this,"Opening...","Please Wait!!!");
			      new Thread(){
			      public void run(){
			      try{
			          Thread.sleep(1000);
			      }
			      catch(Exception ex){}
			      Intent i=new Intent(starter.this,options.class);
					startActivity(i);
					dialog.dismiss();
			      }
			      }.start(); 
				}
		});
helpbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				final ProgressDialog dialog = ProgressDialog.show(starter.this,"Opening...","Please Wait!!!");
			      new Thread(){
			      public void run(){
			      try{
			          Thread.sleep(1000);
			      }
			      catch(Exception ex){}
			      Intent i=new Intent(starter.this,help.class);
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
 	    	Intent intent = new Intent(Intent.ACTION_MAIN);
 	    	intent.addCategory(Intent.CATEGORY_HOME);
 	    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 	    	startActivity(intent);
 	        return true;
 	    }
 	    return super.onKeyDown(keyCode, event);
 	}
}