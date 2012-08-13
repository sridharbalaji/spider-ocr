package com.example.project7.sricrop;

import java.net.URI;

import android.app.Activity;

import com.example.project7.imgtotext;
import com.example.project7.R;
import com.example.project7.folder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ViewImage extends Activity {
	Bitmap bm1;
	final Bundle b=new Bundle();
	String sfile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		System.gc();
		Bundle data=getIntent().getExtras();
		Intent i=getIntent();
		final String filename1=data.getString("filename");
		sfile=i.getStringExtra("sfile");
		
		//bm1=data.getParcelable("data");
		BitmapFactory.Options bfo=new BitmapFactory.Options();
		bfo.inSampleSize=2;
		ImageView iv=(ImageView)findViewById(R.id.my_image);
		Bitmap bm=BitmapFactory.decodeFile(filename1,bfo);
		int w=bm.getWidth();
		int h=bm.getHeight();
		Bitmap bitmap=Bitmap.createScaledBitmap(bm, w,h, true);
		iv.setImageBitmap(bitmap);
		
		
		
		final String[] items=new String[] {"Tamil","English"};
		Spinner lanspinner=(Spinner)findViewById(R.id.selectlan);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lanspinner.setAdapter(adapter);
		lanspinner.setPrompt("Select Language");
		
		lanspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				items[0]="Tamil";
				if(position==0) {
					String selectedItem="tam";
					b.putString("lan", selectedItem);
				}
				else if(position==1) {
					String selectedItem="eng";
					b.putString("lan", selectedItem);
				}
				/*else if(position==2) {
					String selectedItem="hin";
					b.putString("lan", selectedItem);
				}
				else if(position==3) {
					String selectedItem="jpn";
					b.putString("lan",selectedItem);
				}*/
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		Button scanbutton=(Button)findViewById(R.id.button1);
		scanbutton.setText("Scan");
		scanbutton.setOnClickListener(new OnClickListener() {
			
			public void createintent() {
				      Intent intent=new Intent(ViewImage.this,Scannerimage.class);
						b.putString("filename", filename1);
						b.putString("sfile", sfile);
						intent.putExtras(b);
						startActivity(intent);				
			}

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final ProgressDialog dialog = ProgressDialog.show(ViewImage.this, "Scanning", "Collecting Text", true, false, null);

				new Thread(){
				      public void run(){
				      try{
				    	 Thread.sleep(5000);
				      }
				      catch(Exception ex){}
				      createintent();
						dialog.dismiss();
							      }
				      }.start();
			}
			
		});
		
	}
	 @Override
 	public boolean onKeyDown(int keyCode, KeyEvent event) {
 	    if (keyCode == KeyEvent.KEYCODE_BACK) {
 	        Intent ii=new Intent(ViewImage.this,sricrop.class);
 	        //Bundle b=new Bundle();
 	        //b.putString("filename", sfile);
 	        ii.putExtra("filename", sfile);
 	        startActivity(ii);
 	        return true;
 	    }
 	    return super.onKeyDown(keyCode, event);
 	}

}
