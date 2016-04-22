package com.jm.android.jmlaunchertest.qxannotation;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qixinh on 16/4/15.
 */
public enum InjectQxParse {
    PARSE_URL;
    private static final Class<?>[] HALT_CLASSES = {Activity.class};
    private Map<String,String> maps=new HashMap<String,String>();

    public void parseUrl(Class<? extends Activity> cls) {
        if (cls == null) return;
        inject(cls);
    }


    private void inject(Class<? extends Activity> clazz) {

        if (clazz.isAnnotationPresent(QxParseActivitysURl.class)) {
            QxParseActivitysURl from = clazz.getAnnotation(QxParseActivitysURl.class);
            String key = from.key();
            String url = from.url();
            if(!key.equals("")&&!url.equals("")){
                maps.put(key,url);
            }

        }
    }
    public Map<String,String> getActivityUrlMap(){
        if(maps.isEmpty()) return Collections.EMPTY_MAP;
        return maps;
    }

    /**
     * 查找注册的所有的Activity 并且让其进行类加载，从何可以在使用前预处理，取出其被注释的值，配合上面的使用
     * @param context
     * @param packageName
     */
    public void readManifestActivitys(Context context,String packageName) {

        try {
            PackageInfo packageInfo=context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            ActivityInfo[] infos = packageInfo.activities;
            for(int i=0;i<infos.length;i++){
//               mList.add(infos[i].name);
                try {
                    Class.forName(infos[i].name);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
//            mTv.setText(mList.get(1));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
