package com.jm.android.jmlauncher.dispatcher.modle;

import android.app.Activity;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by qixinh on 16/3/30.
 */
public class JMCommonDisptcherItem implements Serializable {

    private static final long serialVersionUID = 1L;
    public String moduleName;
    public String keyOfUrl;
    public Bundle bundle;
    public Activity context;
    public int requestCode;

    public JMCommonDisptcherItem(String moduleName, String keyOfUrl, Bundle bundle, Activity context, int requestCode) {
        this.moduleName = moduleName;
        this.keyOfUrl = keyOfUrl;
        this.bundle = bundle;
        this.context = context;
        this.requestCode = requestCode;
    }
}
