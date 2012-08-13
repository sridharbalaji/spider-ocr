package com.example.project7.sricrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.Button;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

public class Scannerimage extends Activity {
	
	public static final String PACKAGE_NAME = "com.example.project7";
	public static final String DATA_PATH = Environment
			.getExternalStorageDirectory().toString() + "/SriProject";
	
	
	// You should have the trained data file in assets folder
	// You can get them at:
	// http://code.google.com/p/tesseract-ocr/downloads/list
	

	ProgressDialog dialog;
	Button tbutton;
	String sfile;
	private static final String LOG_TAG = "Scannerimage.java";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
            AssetManager assetManager = getAssets();
            String[] files = null;
            try {
                files = assetManager.list("");
            } catch (IOException e) {
                Log.v(LOG_TAG, e.toString());
            }
            for(String filename : files) {
                InputStream in = null;
                OutputStream out = null;
                try {
                  in = assetManager.open(filename);
                  File tesserfile=new File(DATA_PATH);
                  if(!tesserfile.exists()) {
                	  tesserfile.mkdirs();
                  }
                  String tesspath=DATA_PATH+"/tessdata/";
                  File tessfile=new File(tesspath);
                  if(!tessfile.exists()) {
                  tessfile.mkdirs();
                  Log.v("tag", "data path created");
                  }
                  out = new FileOutputStream(tesspath + filename.replace(".mp3",""));
                  byte[] buffer = new byte[2048];
                  int read;
                  while((read = in.read(buffer)) != -1){
                    out.write(buffer,0,read);
                  }
                  in.close();
                  in = null;
                  out.flush();
                  out.close();
                  out = null;
                } catch(Exception ie) {
                   Log.v(LOG_TAG, ie.toString());
                }       
            }

    	super.onCreate(savedInstanceState);
    	 getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    	//setContentView(R.layout.main2);
    	ocr1();
    	
   }
    
        public void ocr1() {
    	Bundle b=getIntent().getExtras();
    	//Bitmap filename1=b.getParcelable("parcelb");
    	String language=b.getString("lan");
    	String filename1=b.getString("filename");
    	sfile=b.getString("sfile");
    	//final String IMAGE_PATH=filename1;
    	final String LANG=language;
    	BitmapFactory.Options options=new BitmapFactory.Options();
    	//options.inSampleSize=2;
    	options.inPreferredConfig=Bitmap.Config.ARGB_8888;
    	//InputStream is;
		Bitmap bitmap=BitmapFactory.decodeFile(filename1,options);
    	try {
    		ExifInterface exif=new ExifInterface(filename1.toString());
    		int exifOrientation=exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
    		Log.v(LOG_TAG,"Orient:"+exifOrientation);
    		
    		int rotate=0;
    		
    		switch(exifOrientation) {
    		case ExifInterface.ORIENTATION_ROTATE_90:
    			rotate=90;
    			break;
    		case ExifInterface.ORIENTATION_ROTATE_180:
    			rotate=180;
    			break;
    		case ExifInterface.ORIENTATION_ROTATE_270:
    			rotate=270;
    			break;
    		}
    		Log.v(LOG_TAG,"Rotation:"+rotate);
    		if(rotate!=0) {
    			int w=bitmap.getWidth();
    			int h=bitmap.getHeight();
    			
    			Matrix mtx=new Matrix();
    			mtx.preRotate(rotate);
    			
    			bitmap=Bitmap.createBitmap(bitmap, 0, 0,w,h,mtx,false);
    			bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
    		}
    	}
    	catch(IOException e) {
    		Log.e(LOG_TAG,"Rotate or coversion failed:"+e.toString());
    	}
    	
    	
    	    	
    	Log.v(LOG_TAG,"Befor baseApi");
    	
    	TessBaseAPI baseApi=new TessBaseAPI();
    	baseApi.setDebug(true);
    	baseApi.init(DATA_PATH,LANG);
    	baseApi.setImage(bitmap);
    	final String recognizedText=baseApi.getUTF8Text();
    	baseApi.end();
    	Log.v(LOG_TAG,"OCR Result:" + recognizedText);
    	
    	Intent i=new Intent(this,display.class);
    	i.putExtra("retext", recognizedText);
    	i.putExtra("filename", filename1);
    	i.putExtra("sfile", sfile);
    	i.putExtra("language", language);
    	System.out.println(language);
    	startActivity(i);
    	
    	//if(LANG.equalsIgnoreCase(LANG)) {
    		//recognizedText=recognizedText.replaceAll("[^a-zA-Z0-9]+", " ");
    	//}
    	/*if(recognizedText.length() !=0) {
    		EditText text=(EditText)findViewById(R.id.field);
    		text.setText(text.getText().toString().length() == 0 ? recognizedText : text.getText() + " " + recognizedText);
			text.setSelection(text.getText().toString().length());
    	}*/

        }
}