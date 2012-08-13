package com.example.project7;

import com.example.project7.sricrop.display;
import com.example.project7.sricrop.display1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class options extends Activity {
	
	Button imgtotext,texttotext;
	public void onCreate(Bundle bun) {
		super.onCreate(bun);
		setContentView(R.layout.options);
		
		imgtotext=(Button)findViewById(R.id.widget31);
		texttotext=(Button)findViewById(R.id.widget32);
		
		imgtotext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(options.this,imgtotext.class);
				startActivity(i);
			}
			
		});
		texttotext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(options.this,select.class);
				startActivity(i);
			}
			
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	       Intent i=new Intent(this,starter.class);
	      
	       startActivity(i);
	    	
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}