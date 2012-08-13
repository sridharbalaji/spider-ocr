package com.example.project7.sricrop;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.project7.R;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class translate1 extends Activity {
	ArrayList <Integer>word=new ArrayList<Integer>();
ArrayList <CharSequence> chatext=new ArrayList<CharSequence>();
	String text;
	String sfile;
	String language;
	String text1;
	String id;
	public void onCreate(Bundle ei) {
		super.onCreate(ei);	
		Intent i1=getIntent();
		text=i1.getStringExtra("text");
		sfile=i1.getStringExtra("sfile");
		id=i1.getStringExtra("id");
		language=i1.getStringExtra("language");
		String cha=text.replaceAll("[-,.!^%$#@~*()_{}/><|+=;:]", "").replace("[", "").replace("]", "");
		if(language.contains("eng")) {
			cha=cha.toLowerCase();
			translatorengtext(cha);
		}
		else if(language.contains("tam")) {
			translator(cha);
		}
	}
	
	private void translatorengtext(String cha) {
		// TODO Auto-generated method stub
			if(language.contains("eng")) {
				Intent i=new Intent(this,translatorenglish.class);
				//String ii=(String)unicodes.toString();
				i.putExtra("unicode", cha);
				i.putExtra("text", text);
				i.putExtra("sfile", sfile);
				i.putExtra("language", language);
				i.putExtra("id", id);
				startActivity(i);
			}
			//opentrans.openDatabase(unicodes);
	}
	public void translator(String text1) {
		/*if(language=="eng") {
			text1=text1.toLowerCase();
			System.out.println(text1);
		}*/
		for(int i=0;i<text1.length();i++) {
			char s=text1.charAt(i);
			//System.out.println("Unicode Text:"+(int)s+",");
			int unicode=(int)s;
			word.add(unicode);
			//edittext1=(EditText)findViewById(R.id.editText1);
			//edittext1.setText("");
		}
		Integer[] unicodes=(Integer[]) word.toArray(new Integer[0]);
		for(int j=0;j<unicodes.length;j++) {
			System.out.println(unicodes[j]);
		}
		translateuni(unicodes);
		

	}
	private void translateuni(Integer[] unicodes) {
		// TODO Auto-generated method stub
		//translatortamil opentrans=new translatortamil();
		/*try {
			opentrans.createDatabase();
		}
		catch (IOException e) {
			throw new Error("Unable to connect database");
		}*/
		try {
			if(language.contains("tam")) {
			Intent i=new Intent(this,translatortamil.class);
			//String ii=(String)unicodes.toString();
			i.putIntegerArrayListExtra("unicode", word);
			i.putExtra("text", text);
			i.putExtra("sfile", sfile);
			i.putExtra("id", id);
			i.putExtra("language", language);
			startActivity(i);
			}
			
		}
		catch(SQLiteException se) {
			throw se;
		}
	}
	/*public translate1(String a) {
		System.out.println(a.substring(0));
		String b=a.replaceAll("[,] ", "").replace("[", "").replace("]", "");
		System.out.println(b);
		
	}*/
	
}
