package com.jm.android.jmlaunchertest;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jm.android.jmlauncher.dispatcher.constants.JMCommonAppConstants;
import com.jm.android.jmlauncher.dispatcher.entry.JMCommonDispatcherManager;
import com.jm.android.jmlauncher.dispatcher.modle.JMCommonIBaseDispatcher;
import com.jm.android.jmlauncher.dispatcher.modle.JMCommonSchemeItem;
import com.jm.android.jmlauncher.dispatcher.modle.JMCommonUrlItem;
import com.jm.android.jmlaunchertest.dispatchertest.JMCommonApplication;
import com.jm.android.jmlaunchertest.dispatchertest.JMSchemaDealHome;
import com.jm.android.jmlaunchertest.dispatchertest.TestConstants;
import com.jm.android.jmlaunchertest.qxannotation.InjectQxParse;
import com.jm.android.jmlaunchertest.qxannotation.QxParseActivitysURl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qixinh on 16/3/11.
 */
@QxParseActivitysURl(key="main",url="qx://ww/rr")
public class MainActivity extends JMCommonBaseActivity {
    static {
        InjectQxParse.PARSE_URL.parseUrl(MainActivityOne.class);
    }
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         btn = (Button) findViewById(R.id.btn_dispatcher);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                b.putString(TestConstants.TEST_BUNDLE,"test Bundle Ok");
                JMCommonApplication.dispatcherManager.dispacherTo("modulename","urlKey");
//                JMCommonApplication.dispatcherManager.dispacherToForResult(JMCommonAppConstants.HOME_MODULE_NAME,
//                        "jack", b,MainActivity.this,85);

            }
        });

        Button btn2 = (Button) findViewById(R.id.btn_dispatcher2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                JMCommonDispatcherManager dispatcher = JMCommonDispatcherManager.newInstance(getApplicationContext());
//                JMCommonIBaseDispatcher baseDispatcher = new JMSchemaDealHome(MainActivity.this);
//
//                dispatcher.registerSubmoduleDispatcher("home", baseDispatcher);
//                String ul = "jm://home/w?module=main";
//                dispatcher.dispatcherRequest(Uri.parse(ul));

                //测试直接跳转本地配置scheme data
//                JMCommonSendScheme.sendSchemeAddMediaStack(MainActivity.this, "jm://home/post","jm://three/w");
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btn.setText(""+requestCode);
    }
}
