package com.example.project7.sricrop;

import com.example.project7.sricrop.display1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.project7.R;
import android.view.View.OnClickListener;
import android.widget.Button;

public class textselect extends Activity {
	
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
				Intent i=new Intent(textselect.this,display1.class);
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
				Intent i=new Intent(textselect.this,display1.class);
				i.putExtra("language", language);
				startActivity(i);
			}
			
		});
		
	}
}