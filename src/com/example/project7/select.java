package com.example.project7;

import com.example.project7.sricrop.display;
import com.example.project7.sricrop.display1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class select extends Activity {
	
	Button engtotam,tamtoeng;
	String language;
	public void onCreate(Bundle bun) {
		super.onCreate(bun);
		setContentView(R.layout.select);
		
		engtotam=(Button)findViewById(R.id.widget33);
		tamtoeng=(Button)findViewById(R.id.widget34);
		
		engtotam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(select.this,display1.class);
				language="eng";
				i.putExtra("language", language);
				startActivity(i);
			}
			
		});
		tamtoeng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				language="tam";
				Intent i=new Intent(select.this,display1.class);
				i.putExtra("language", language);
				startActivity(i);
			}
			
		});
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	       Intent i=new Intent(this,options.class);
	      
	       startActivity(i);
	    	
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}