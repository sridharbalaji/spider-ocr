package com.example.project7.sricrop;

import java.io.FileOutputStream;
import com.example.project7.R;

import java.io.IOException;
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
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class translatortamil extends Activity implements OnInitListener {
	
	private static String DB_PATH=Environment.getExternalStorageDirectory().toString()+"/SriProject/tessdata/";
	
	private static String DB_NAME="english.db";
	
	private static final int MY_DATA_CHECK_CODE = 0;
	 private TextToSpeech mTts;
	
	private SQLiteDatabase mydb;
	String i2;
	String id;
	
	private TextToSpeech myTTS;
	//status check code
	
	private Context mycontext;
	String sentence;
	String language;
	EditText et;
	EditText tt;
	String sfile;
	Toast toast;
	Button button;
	public void onCreate(Bundle is) {
		super.onCreate(is);
		setContentView(R.layout.main3);
		tt=(EditText)findViewById(R.id.edittext1);
		//setContentView(et);
		et=(EditText)findViewById(R.id.widget36);
		button=(Button)findViewById(R.id.widget3);
		Intent i=getIntent();
		ArrayList<Integer> ii=i.getIntegerArrayListExtra("unicode");
		i2=i.getStringExtra("text");
		id=i.getStringExtra("id");
		language=i.getStringExtra("language");
		sfile=i.getStringExtra("sfile");
		et.setText(i2);
		Integer[] s=(Integer[])ii.toArray(new Integer[0]);
		openDatabase(s);
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
					// TODO Auto-generated method stub
					String words = null;
						words=tt.getText().toString();
			    	
			    	speakWords(words);
				}
	  			
			
		});
		
		Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
	}
	
	private void speakWords(String speech) {

		//speak straight away
    	myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				//the user has the necessary data - create the TTS
			myTTS = new TextToSpeech(this, this);
			}
			/*else {
					//no data - install it now
				Intent installTTSIntent = new Intent();
				installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			}*/
		}
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
	

	private void copyDataBase() throws IOException {
		// TODO Auto-generated method stub
		InputStream myinput=mycontext.getAssets().open("englishs.db");
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
	
	public void openDatabase(Integer[] unicode) {
		try {
		String mypath=DB_PATH+DB_NAME;
		ArrayList <String> word=new ArrayList <String>();
		String out;
		mydb=SQLiteDatabase.openDatabase(mypath, null, 0);
		System.out.println("Database opened");
		boolean remove=false;
		for(int ii=0;ii<unicode.length;ii++) {
		String[] selection=null;
		if(unicode[ii]==3021) {
			if(unicode[ii+1]==2993&&unicode[ii-1]==2993) {
				Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
				c2.moveToFirst();
				String n=c2.getString(c2.getColumnIndex("text")).toString();
				//if(m.contains(n)) {
				word.remove(n);
				System.out.println("removed:"+n);
				word.add("t");
				System.out.println("t");
				c2.moveToLast();
				//}
			}
			/*else if(unicode[ii-1]==2949||unicode[ii-1]==2950||unicode[ii-1]==2951||unicode[ii-1]==2952||unicode[ii-1]==2953||unicode[ii-1]==2954||unicode[ii-1]==2958||unicode[ii-1]==2959||unicode[ii-1]==2960||unicode[ii-1]==2962||unicode[ii-1]==2963) {
				Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
				c2.moveToFirst();
				String n=c2.getString(c2.getColumnIndex("text")).toString();
				//if(m.contains(n)) {
				word.remove(n);
				System.out.println("removed:"+n);
				word.add("t");
				System.out.println("t");
				c2.moveToLast();
				//}
			}*/
			
			/*else {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
			c2.moveToFirst();
			String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			word.remove(n);
			System.out.println("removed:"+n);
			word.add(n.replace("a",""));
			System.out.println(n.replace("a", ""));
			c2.moveToLast();
			}*/
			
		}
		else if(unicode[ii]==10) {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			//Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
			//c2.moveToFirst();
			//String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			//word.remove(n);
			//System.out.println("removed:"+n);
			//String m=n.replace("a", "i");
			word.add(" ");
			//System.out.println(m);
			//}
		}
		else if(unicode[ii]==3007) {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
			c2.moveToFirst();
			String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			word.remove(n);
			System.out.println("removed:"+n);
			String m=n.replace("a", "i");
			word.add(m.toString());
			System.out.println(m);
			c2.moveToLast();
			//}
		}
		else if(unicode[ii]==3008) {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
			c2.moveToFirst();
			String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			word.remove(n);
			System.out.println("removed:"+n);
			String m=n.replace("a", "ie");
			word.add(m.toString());
			System.out.println(m);
			c2.moveToLast();
			//}
		}
		else if(unicode[ii]==3009) {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
			c2.moveToFirst();
			String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			word.remove(n);
			System.out.println("removed:"+n);
			String m=n.replace("a", "u");
			word.add(m.toString());
			System.out.println(m);
			c2.moveToLast();
			//}
		}
		else if(unicode[ii]==3010) {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
			c2.moveToFirst();
			String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			word.remove(n);
			System.out.println("removed:"+n);
			String m=n.replace("a", "uu");
			word.add(m.toString());
			System.out.println(m);
			c2.moveToLast();
			//}
		}
		else if(unicode[ii]==3014) {
			if(unicode[ii+2]==3006) {
				Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii+1], selection);
				c2.moveToFirst();
				String n=c2.getString(c2.getColumnIndex("text")).toString();
				//if(m.contains(n)) {
				//word.remove(n);
				System.out.println("removed:"+n);
				String m=n.replace("a", "o");
				word.add(m.toString());
				System.out.println(m);
				remove=true;
				c2.moveToLast();
			}
			else if(unicode[ii+2]==3031) {
				Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii+1], selection);
				c2.moveToFirst();
				String n=c2.getString(c2.getColumnIndex("text")).toString();
				//if(m.contains(n)) {
				//word.remove(n);
				System.out.println("removed:"+n);
				String m=n.replace("a", "au");
				word.add(m.toString());
				System.out.println(m);
				remove=true;
				c2.moveToLast();
			}
			else {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii+1], selection);
			c2.moveToFirst();
			String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			//word.remove(n);
			System.out.println("removed:"+n);
			String m=n.replace("a", "e");
			word.add(m.toString());
			System.out.println(m);
			remove=true;
			c2.moveToLast();
			}
			}
		else if(unicode[ii]==3024) {
			word.add("om");
		}
		else if(unicode[ii]==3031) {
			if(unicode[ii-1]==2962) {
				Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
				c2.moveToFirst();
				String n=c2.getString(c2.getColumnIndex("text")).toString();
				word.remove(n);
				word.add("au");
				
			}
		}
		
		else if(unicode[ii]==3015) {
			if(unicode[ii+2]==3006) {
				Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii+1], selection);
				c2.moveToFirst();
				String n=c2.getString(c2.getColumnIndex("text")).toString();
				//if(m.contains(n)) {
				//word.remove(n);
				System.out.println("removed:"+n);
				String m=n.replace("a", "oa");
				word.add(m.toString());
				System.out.println(m);
				remove=true;
				c2.moveToLast();
			}
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			else {
			Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii+1], selection);
			c2.moveToFirst();
			String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			//word.remove(n);
			System.out.println("removed:"+n);
			String m=n.replace("a", "ae");
			word.add(m.toString());
			System.out.println(m);
			remove=true;
			c2.moveToLast();
			}
		}
		else if(unicode[ii]==3016) {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii+1], selection);
			c2.moveToFirst();
			String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			word.remove(n);
			System.out.println("removed:"+n);
			String m=n.replace("a", "ai");
			word.add(m.toString());
			System.out.println(m);
			remove=true;
			c2.moveToLast();
			}
		/*else if(unicode[ii]==3018&&unicode[ii+2]==3006) {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii+1], selection);
			c2.moveToFirst();
			String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			word.remove(n);
			System.out.println("removed:"+n);
			String m=n.replace("a", "o");
			word.add(m.toString());
			System.out.println(m);
			remove=true;
			}
			else if(unicode[ii]==3019) {
				//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
				//c1.moveToFirst();
				//String m=c1.getString(c1.getColumnIndex("text"));
				Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
				c2.moveToFirst();
				String n=c2.getString(c2.getColumnIndex("text")).toString();
				//if(m.contains(n)) {
				word.remove(n);
				System.out.println("removed:"+n);
				String m=n.replace("a", "uu");
				word.add(m.toString());
				System.out.println(m);
				}
				else if(unicode[ii]==3020) {
					//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
					//c1.moveToFirst();
					//String m=c1.getString(c1.getColumnIndex("text"));
					Cursor c2=mydb.rawQuery("select text from translate where unicode="+unicode[ii-1], selection);
					c2.moveToFirst();
					String n=c2.getString(c2.getColumnIndex("text")).toString();
					//if(m.contains(n)) {
					word.remove(n);
					System.out.println("removed:"+n);
					String m=n.replace("a", "uu");
					word.add(m.toString());
					System.out.println(m);object
					}*/
		else if(unicode[ii]==32) {
			//Cursor c1=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
			//c1.moveToFirst();
			//String m=c1.getString(c1.getColumnIndex("text"));
			//Cursor c2=mydb.rawQuery("select tobjectext from translate where unicode="+unicode[ii-1], selection);
			//c2.moveToFirst();
			//String n=c2.getString(c2.getColumnIndex("text")).toString();
			//if(m.contains(n)) {
			//word.remove(n);
			//System.out.println("removed:"+n);
			//String m=n.replace("a", "uu");
			word.add(" ");
			//System.out.println(m);
			//}
		}


		else {
		Cursor c=mydb.rawQuery("select text from translate where unicode="+unicode[ii], selection);
		//selection=new String[c.getCount()];
		c.moveToFirst();
			out=c.getString(c.getColumnIndex("text")).toString();
			
			if(unicode[ii+1]==3021) {
				String n1=out.replace("a", "");
				out=n1;
			}
			
			if(remove==false) {
			word.add(out);
			System.out.println("added:"+out);
			}
			if(remove==true) {
				word.remove(out);
				System.out.println("removed"+out);
			}
				remove=false;
				c.moveToLast();
		}
		sentence=(String) word.toString();
		//String text=sentence.toString().substring(0);
		System.out.println(sentence.substring(0));
		String b=sentence.replaceAll("[,] ", "").replace("[", "").replace("]", "");
		System.out.println(b);
		tt.setText(b);
		}
		
		//Intent i=new Intent();
		
		

	}
		catch(SQLiteException e) {
			String message=e.toString();
			toast = Toast.makeText(translatortamil.this, message, 6000);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		catch (RuntimeException io) {
			String message=io.toString();
			toast = Toast.makeText(translatortamil.this, message, 30000);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		
}	
	
	/*private class display2 extends Activity {
		EditText et;
		public void onCreate(Bundle is) {
				super.onCreate(is);
				setContentView(R.layout.main3);
				//et=(EditText)findViewById(R.id.editText1);
			// TODO Auto-generated constructor stub
			et=(EditText)findViewById(R.id.editText1);

			et.setText(sentence);
		}
	}*/
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
			if(myTTS.isLanguageAvailable(Locale.ENGLISH)==TextToSpeech.LANG_AVAILABLE)
				myTTS.setLanguage(Locale.ENGLISH);
		}
		else if (initStatus == TextToSpeech.ERROR) {
			Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
		}
	}
}
