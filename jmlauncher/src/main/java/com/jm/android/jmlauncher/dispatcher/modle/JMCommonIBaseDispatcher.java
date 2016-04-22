package com.jm.android.jmlauncher.dispatcher.modle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.util.Map;

/**
 * Created by qixinh on 16/3/5.
 */
public abstract class JMCommonIBaseDispatcher {
    protected Context mAppContext;
    public JMCommonIBaseDispatcher(Context context){
        this.mAppContext=context;
    }
    public static final String __ORIGIN_URI = "__origin_uri";
    public abstract void dealStartActivityForResult(String lastPathSegment, String path, Map<String, String> map,
                              Bundle bundle,Activity context,int requestCode);
    public abstract void deal(String lastPathSegment, String path, Map<String, String> map,
                              Bundle bundle);
}
