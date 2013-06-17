package com.multimedia.pembacarss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PembacaRSS extends ListActivity {
	List<String> headlines;
	List<String> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        headlines = new ArrayList<String>();
        links = new ArrayList<String>();
        try
        {
        	URL url = new URL("http://rss.detik.com/");
        	
        	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        	factory.setNamespaceAware(false);
        	XmlPullParser xpp = factory.newPullParser();
        	boolean insideItem = false;
        	
        	xpp.setInput(getInputStream(url), "UTF_8");
        	
        	int evenType = xpp.getEventType();
        	while(evenType != XmlPullParser.END_DOCUMENT) {
        		if(evenType == XmlPullParser.START_TAG) {
        			if(xpp.getName().equalsIgnoreCase("item")) {
        				insideItem = true;
        			}else if(xpp.getName().equalsIgnoreCase("title")) {
        				if(insideItem)
        					headlines.add(xpp.nextText()); //simpan headlines
        			}else if(xpp.getName().equalsIgnoreCase("link")) {
        				if(insideItem)
        					links.add(xpp.nextText()); //simpan url pada list
        			}
        		}else if(evenType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
        			insideItem = false;
        		}
        		
        		evenType = xpp.next(); //move to next element
        	}
        } catch(MalformedURLException e) {
        	e.printStackTrace();
        } catch(XmlPullParserException e) {
        	e.printStackTrace();
        } catch(IOException e) {
        	e.printStackTrace();
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_row, headlines);
        setListAdapter(adapter);
    }
    
    public InputStream getInputStream(URL url) {
    	try {
    		return url.openConnection().getInputStream();
    	} catch (IOException e) {
    		return null;
    	}
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Intent intent = new Intent(getApplicationContext(), Berita.class);
    	intent.putExtra("URL_BERITA", links.get(position).toString());
    	startActivity(intent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    } 
}