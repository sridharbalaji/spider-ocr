package com.example.project7.sricrop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.example.project7.R;

public class displaytrans extends Activity {
	public void onCreate(Bundle is) {
		super.onCreate(is);
		setContentView(R.layout.main3);
		Intent i=getIntent();
		String a=i.getStringExtra("word");
		System.out.println(a);
	}
}
