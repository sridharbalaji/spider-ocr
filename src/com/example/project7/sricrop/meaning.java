package com.example.project7.sricrop;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import com.example.project7.R;

public class meaning extends Activity {
    /** Called when the activity is first created. */
WebView Browser;
String text;
String lannguage;
String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        Intent i=getIntent();
        lannguage=i.getStringExtra("language");
        text=i.getStringExtra("text");
        System.out.println(text);
        Browser = (WebView) findViewById(R.id.WebEngine);
        if(lannguage.equalsIgnoreCase("eng")) {
        	url="http://translate.google.com/?hl=ta&q="+text;
        }
        else if(lannguage.equalsIgnoreCase("tam")) {
        url="http://translate.google.com/?hl=en&q="+text;
        }
        Browser.loadUrl(url);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the BACK key and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && Browser.canGoBack()) {
            Browser.goBack();
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }

}