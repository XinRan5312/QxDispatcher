package com.jm.android.jmlaunchertest.dispatchertest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jm.android.jmlauncher.dispatcher.constants.JMCommonAppConstants;
import com.jm.android.jmlauncher.dispatcher.entry.JMCommonDispatcherManager;
import com.jm.android.jmlaunchertest.JMCommonBaseActivity;
import com.jm.android.jmlaunchertest.R;

/**
 * Created by qixin.Hou on 16/3/11.
 */
public class HomeActivity extends JMCommonBaseActivity {


    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);
        tv = (TextView) findViewById(R.id.tv);
        Intent intent = this.getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            String str = b.getString(TestConstants.TEST_BUNDLE);
            if (str != null) {
                tv.setText(str);
            }
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JMCommonApplication.dispatcherManager.dispacherToForResult(JMCommonAppConstants.HOME_MODULE_NAME, "mack", HomeActivity.this,86);

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          tv.setText(""+requestCode);
    }

    @Override
    public void finish() {
        super.finish();
        setResult(30);
    }
}
