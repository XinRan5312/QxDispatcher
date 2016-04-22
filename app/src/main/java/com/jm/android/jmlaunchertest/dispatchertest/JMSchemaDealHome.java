package com.jm.android.jmlaunchertest.dispatchertest;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import com.jm.android.jmlauncher.dispatcher.constants.JMCommonAppConstants;
import com.jm.android.jmlauncher.dispatcher.modle.JMCommonIBaseDispatcher;

import java.util.Map;


/**
 * Created by qixinh on 16/3/9.
 */
public class JMSchemaDealHome extends JMCommonIBaseDispatcher {

    private Class<? extends Activity> cls;
    private Bundle mBundle;

    public JMSchemaDealHome(Context context) {
        super(context);
    }


    @Override
    public void dealStartActivityForResult(String lastPathSegment, String path, Map<String, String> map,
                                           Bundle bundle, Activity context, int requestCode) {
        creatActivityClassAndBundle(lastPathSegment, path, map, bundle);
        Intent to = new Intent();
        if (mBundle != null) {
            to.putExtras(mBundle);

        }
        to.setClass(context, cls);
        context.startActivityForResult(to, requestCode);


    }

    @Override
    public void deal(String lastPathSegment, String path, Map<String, String> map, Bundle bundle) {
        creatActivityClassAndBundle(lastPathSegment, path, map, bundle);
        Intent to = new Intent();
        if (mBundle != null) {
            to.putExtras(mBundle);

        }
        to.setClass(mAppContext, cls);
        mAppContext.startActivity(to);
    }

    private void creatActivityClassAndBundle(String lastPathSegment, String path, Map<String, String> map,
                                             Bundle bundle) {
        if (cls == null) {
            String module = map.get("module");
            JMCommonAppConstants.IntentTo tab = JMCommonAppConstants.IntentTo.HOME;

            if (lastPathSegment.equalsIgnoreCase("w")) {
                cls = HomeActivity.class;
            }
            if (lastPathSegment.equalsIgnoreCase("r")) {
                cls = ThreeActivity.class;
            }
            if (path.equalsIgnoreCase("/home/name")) {
                //cls=
            }
            if ("main".equalsIgnoreCase(module)) {
                tab = JMCommonAppConstants.IntentTo.HOME;
            } else if ("order".equalsIgnoreCase(module)) {
                tab = JMCommonAppConstants.IntentTo.ORDER;
            } else if ("user_center".equalsIgnoreCase(module)) {
                tab = JMCommonAppConstants.IntentTo.USER_CENTER;
            } else if ("markering".equalsIgnoreCase(module)) {
                tab = JMCommonAppConstants.IntentTo.USER_CENTER;
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSerializable(JMCommonAppConstants.INTENT_TO_ACTIVITY, tab);
            mBundle = bundle;
        }

    }

}
