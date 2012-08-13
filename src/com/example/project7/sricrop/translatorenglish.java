package com.example.project7.sricrop;

import java.io.FileOutputStream;
import java.io.IOException;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project7.R;

public class translatorenglish extends Activity implements OnInitListener {
	
	private static String DB_PATH=Environment.getExternalStorageDirectory().toString()+"/SriProject/tessdata/";
	
	private static String DB_NAME="tamil.db";
	private SQLiteDatabase mydb;
	String i2;
	
	private Context mycontext;
	String[] sentence;
	EditText et;
	EditText tt;
	String sfile;
	Toast toast;
	String id;
	String language;
	Button button;
	private static final int MY_DATA_CHECK_CODE = 0;
	 private TextToSpeech myTTS;
	ArrayList <Character> word1=new ArrayList <Character>();

	public void onCreate(Bundle is) {
		super.onCreate(is);
		setContentView(R.layout.main3);
		tt=(EditText)findViewById(R.id.edittext1);
		//setContentView(et);
		et=(EditText)findViewById(R.id.widget36);
		button=(Button)findViewById(R.id.widget3);
		Intent i=getIntent();
		String cha=i.getStringExtra("unicode");
		i2=i.getStringExtra("text");
		sfile=i.getStringExtra("sfile");
		id=i.getStringExtra("id");
		language=i.getStringExtra("language");
		et.setText(i2);
		
		openDatabase(cha);
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String words = null;
				words=et.getText().toString();
	    	
	    	speakWords(words);
			}
			
		});
		/*Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);*/
		
	}
	private void speakWords(String speech) {

		//speak straight away
    	myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
}

	/*public translatortamil(Context context) {
		//super(context, DB_NAME, null,1);
		this.mycontext=context;
		// TODO Auto-generated constructor stub
	}*/
	public void createDatabase() throws IOException {
		boolean dbExist=checkDatabase();
		if(dbExist) {
			System.out.println("Database created");
		}
		else {
			//this.getReadableDatabase();
			copyDataBase();
		}
	}
	
	/*protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				//the user has the necessary data - create the TTS
			myTTS = new TextToSpeech(this, this);
			}
			/*else {
					//no data - install it now
				Intent installTTSIntent = new Intent();
				//installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			}
		}
	}*/

	

	private void copyDataBase() throws IOException {
		// TODO Auto-generated method stub
		InputStream myinput=mycontext.getAssets().open("tamil.db");
		String outFilename=DB_PATH+DB_NAME;
			OutputStream myoutput=new FileOutputStream(outFilename);
		byte[] buffer=new byte[1024];
		int length;
		while((length=myinput.read(buffer))>0) {
			myoutput.write(buffer,0,length);
		}
		myoutput.flush();
		myoutput.close();
		myinput.close();
		System.out.println("datafile copied");
	}
	private boolean checkDatabase() throws SQLiteException {
		// TODO Auto-generated method stub
		SQLiteDatabase checkdb=null;
		try {
			String mypath=DB_PATH+DB_NAME;
			checkdb=SQLiteDatabase.openDatabase(mypath, null, 0);
		}
		catch (SQLiteException e) {
			System.out.println(e.toString());
		}
		if(checkdb!=null) {
			checkdb.close();
		}
		return checkdb!=null ? true : false;
	}
	
	public void openDatabase(String cha) {
		try {
		String mypath=DB_PATH+DB_NAME;
		ArrayList <String> word=new ArrayList <String>();
		mydb=SQLiteDatabase.openDatabase(mypath, null, 0);
		System.out.println("Database opened");
		String[] selection=null;
		for(int i=0;i<cha.length();i++) {
			char n=cha.charAt(i);
			Cursor c2=mydb.rawQuery("select unicode from tamil where letter='"+n+"'", selection);
			c2.moveToFirst();
			String n1=c2.getString(c2.getColumnIndex("unicode")).toString();
			System.out.println(n1);
			word.add(n1);
			
		}
		
		

		
	
		sentence=(String[])word.toArray(new String[0]);
		//String arraytext=sentence.replace("[", "").replace("]", "").replace(" ", "").replace(",", "");
		//String text=sentence.toString().substring(0);
		//System.out.println(sentence.substring(0));
		//String b=sentence.replaceAll("[,] ", "").replace("[", "").replace("]", "");
		//System.out.println(arraytext);
		ArrayList <Character> char1=new ArrayList<Character>();
		for(int i=0;i<sentence.length;i++) {
			int uni=Integer.parseInt(sentence[i]);
			char s=(char)uni;
			System.out.println(s);
		}
		
		}
		catch(SQLiteException e) {
			String message=e.toString();
			toast = Toast.makeText(translatorenglish.this, message, 6000);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		catch (RuntimeException io) {
			String message=io.toString();
			toast = Toast.makeText(translatorenglish.this, message, 30000);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		
}	
	
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	if(id.equalsIgnoreCase("img")) {
	       Intent i=new Intent(this,display.class);
	       i.putExtra("retext", i2);
	       i.putExtra("filename", "sdcard/SriProject/crop.jpg");
	       i.putExtra("sfile", sfile);
	       i.putExtra("language", language);
	       startActivity(i);
	    	}
	    	else if(id.equalsIgnoreCase("text")) {
	 	       Intent i=new Intent(this,display1.class);
	 	       i.putExtra("language", language);
	 	       startActivity(i);
	 	    	}
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onInit(int initStatus) {
		// TODO Auto-generated method stub
		if (initStatus == TextToSpeech.SUCCESS) {
			if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
				myTTS.setLanguage(Locale.US);
		}
		else if (initStatus == TextToSpeech.ERROR) {
			Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
		}
	}
}
