package com.jm.android.jmlauncher.dispatcher.constants;

/**
 * Created by qixinh on 16/3/8.
 */
public class JMCommonAppConstants {
    public final static String JM_SCHEME="jm";
    public final static String INTENT_TO_ACTIVITY="go_to_activity";
    public final static String HOME_MODULE_NAME="home";
    public final static String HOME_MODULE_NAME_CLASS_PATH="com.jm.android.jmlaunchertest.dispatchertest.JMSchemaDealHome";
    public final static String ONLINE_MODULE_NAME="online";
    public static enum IntentTo{
        HOME,
        MARKERING,
        USER_CENTER,
        ORDER;
    }
}
