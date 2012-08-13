package com.example.project7;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class help extends Activity {
    /** Called when the activity is first created. */
WebView Browser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        Browser = (WebView) findViewById(R.id.WebEngine);
        Browser.loadUrl("file:///android_asset/help/index.html");
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