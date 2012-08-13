package com.example.project7.sricrop;

import com.example.project7.R;
import com.example.project7.select;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class display1 extends Activity {
	//String sfile;
	//String filename1;
	Button tbutton,mbutton;
	String recongnizedText;
	String language;
	EditText text;
	String text1;
	String id="text";
	public void onCreate(Bundle ei) {
		super.onCreate(ei);
		setContentView(R.layout.main2);
		//EditText et=(EditText)findViewById(R.id.field);
		Intent i=getIntent();
		//String recognizedText=i.getStringExtra("retext");
		//filename1=i.getStringExtra("filename");
		//sfile=i.getStringExtra("sfile");
		language=i.getStringExtra("language");
		//ImageView iv=(ImageView)findViewById(R.id.image);
		//Bitmap bm=BitmapFactory.decodeFile(filename1);
		//int w=bm.getWidth();
		//int h=bm.getHeight();
		//Bitmap bitmap=Bitmap.createBitmap(bm, 0, 0, w, h);
    	//iv.setImageBitmap(bitmap);
    	//iv.setVisibility(View.VISIBLE);
		//if(recognizedText.length() !=0) {
    		text=(EditText)findViewById(R.id.field);
    		//text.setText(text.getText().toString().length() == 0 ? recognizedText : text.getText() + " " + recognizedText);
			//text.setSelection(text.getText().toString().length());
    		text1=text.getText().toString();
    		
    	//}
		translator(text1);
		
		
		
    }
public void translator(final String text2) {
  		tbutton=(Button)findViewById(R.id.button1);
  		tbutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent ii=new Intent(display1.this,translate1.class);
					ii.putExtra("text", text.getText().toString());
					//ii.putExtra("sfile", sfile);
					ii.putExtra("language", language);
					ii.putExtra("id", id);
					startActivity(ii);
				}
  			
  		});
  		mbutton=(Button)findViewById(R.id.button2);
  		mbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent ii=new Intent(display1.this,meaning.class);
				ii.putExtra("text", text.getText().toString());
				//ii.putExtra("sfile", sfile);
				ii.putExtra("language", language);
				startActivity(ii);
			}
			
		});

  		
  	}

@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        Intent i=new Intent(display1.this,select.class);
	        //Bundle b=new Bundle();
	        i.putExtra("language", language);
	        //i.putExtras(b);
	        startActivity(i);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
  }

 
   /*   @Override
  	public boolean onKeyDown(int keyCode, KeyEvent event) {
  	    if (keyCode == KeyEvent.KEYCODE_BACK) {
  	        Intent i=new Intent(this,ViewImage.class);
  	        //Bundle b=new Bundle();
  	        i.putExtra("filename",filename1 );
  	        i.putExtra("sfile", sfile);
  	        i.putExtra("language", language);
  	        System.out.println(sfile);
  	        //i.putExtras(b);
  	        startActivity(i);
  	        return true;
  	    }
  	    return super.onKeyDown(keyCode, event);
      }*/
}