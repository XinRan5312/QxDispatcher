package com.jm.android.jmlaunchertest;

import android.app.Activity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import com.jm.android.jmlaunchertest.qxannotation.InjectQxParse;
import com.jm.android.jmlaunchertest.qxannotation.QxParseActivitysURl;


@QxParseActivitysURl(key="mainOne",url="qx://w/r")
public class MainActivityOne extends Activity {
    static {
        InjectQxParse.PARSE_URL.parseUrl(MainActivityOne.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
