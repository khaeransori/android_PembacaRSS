package com.multimedia.pembacarss;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled") public class Berita extends Activity {
	private WebView webView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.berita);
		
		Bundle bundle = this.getIntent().getExtras();
		webView = (WebView) findViewById(R.id.beritanya);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(bundle.getString("URL_BERITA"));
	}
}
