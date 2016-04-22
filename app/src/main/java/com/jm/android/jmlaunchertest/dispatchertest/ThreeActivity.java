package com.jm.android.jmlaunchertest.dispatchertest;


import android.os.Bundle;

import com.jm.android.jmlaunchertest.JMCommonBaseActivity;
import com.jm.android.jmlaunchertest.R;

/**
 * Created by qixinh on 16/3/11.
 */
public class ThreeActivity extends JMCommonBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_main);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    public void finish() {
        super.finish();
        setResult(20);
    }
}
