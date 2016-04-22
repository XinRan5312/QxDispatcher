package com.jm.android.jmlauncher.dispatcher.entry;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by qixinh on 16/3/10.
 *
 * 这个工具类用来直接跳转到某个业务线或模块的Activity，这个Activity要在manifest.xml中配置scheme data，保证给的URl能找到它
 *
 * 优点是简单不用写中间类
 *
 * 缺点是要在manifest.xml中配置scheme data，不适合综合管理
 *
 *
 * 建议：除非一下两种情况用这个工具类，其它情况还是推荐用JMCommonDispatcherManager这个工具类
 *     1：暂时用到的Activity，就是用一段时间就可能就不用了
 *     2：跳转的时候我可能要添加一个返回中间Activity
 */
public  class JMCommonSendScheme {
    public static final String TARGET_SCHEME_URI = "JM_SPIDER_TARGET_SCHEME_URL";
    private static String homeScheme = null;

    public JMCommonSendScheme() {
    }

    public static String getHomeScheme(Context ctx) {
        if(TextUtils.isEmpty(homeScheme)) {
            homeScheme = getMetaData(ctx.getApplicationContext(), "MAIN_SCHEME");
        }

        return homeScheme;
    }

    public static String getMetaData(Context app, String name) {
        Bundle bundle = app.getApplicationInfo().metaData;
        if(bundle != null) {
            return bundle.getString(name);
        } else {
            XmlResourceParser parser = null;
            AssetManager assmgr = null;

            try {
                assmgr = (AssetManager)AssetManager.class.newInstance();
                Method e = AssetManager.class.getDeclaredMethod("addAssetPath", new Class[]{String.class});
                e.setAccessible(true);
                int cookie = ((Integer)e.invoke(assmgr, new Object[]{app.getApplicationInfo().sourceDir})).intValue();
                if(cookie != 0) {
                    String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
                    parser = assmgr.openXmlResourceParser(cookie, "AndroidManifest.xml");
                    boolean findAppMetadata = false;
                    int event = parser.getEventType();
                    while(event != 1) {
                        switch(event) {
                            case 2:
                                String nodeName = parser.getName();
                                String metadataName;
                                if("meta-data".equals(nodeName)) {
                                    findAppMetadata = true;
                                    metadataName = parser.getAttributeValue(ANDROID_RESOURCES, "name");
                                    if(metadataName.equals(name)) {
                                        String var12 = parser.getAttributeValue(ANDROID_RESOURCES, "value");
                                        return var12;
                                    }
                                } else if(findAppMetadata) {
                                    metadataName = null;
                                    return metadataName;
                                }
                            default:
                                event = parser.next();
                        }
                    }
                }
            } catch (Throwable var16) {
                var16.printStackTrace();
            } finally {
                if(parser != null) {
                    parser.close();
                }

                if(assmgr != null) {
                    assmgr.close();
                }

            }

            return null;
        }
    }

    @SuppressLint("NewApi")
    public static void sendSchemeAddMediaStack(Fragment context, String url) {
        sendSchemeAddMediaStack(context, url, getHomeScheme(context.getActivity()));
    }

    public static void sendSchemeAddMediaStack(Context context, String url) {
        sendSchemeAddMediaStack(context, url, getHomeScheme(context));
    }

    @SuppressLint("NewApi")
    public static void sendSchemeAddMediaStack(Fragment context, String url, Bundle bundle) {
        sendSchemeAddMediaStack(context, getHomeScheme(context.getActivity()), url, bundle);
    }

    public static void sendSchemeAddMediaStack(Context context, String url, Bundle bundle) {
        sendSchemeAddMediaStack(context, getHomeScheme(context), url, bundle);
    }

    public static void sendSchemeAddMediaStack(Fragment context, String mediaActivityUrl, String url) {
        sendSchemeAddMediaStack((Fragment) context, mediaActivityUrl, url, (Bundle) null);
    }

    public static void sendSchemeAddMediaStack(Context context, String mediaActivityUrl, String url) {
        sendSchemeAddMediaStack((Context) context, mediaActivityUrl, url, (Bundle) null);
    }


    @SuppressLint("NewApi")
    public static void sendSchemeAddMediaStack(Fragment context, String mediaActivityUrl, String url, Bundle bundle) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(mediaActivityUrl));
        intent.addFlags(67108864);
        context.startActivity(intent);
        intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);
    }

    public static void sendSchemeAddMediaStack(Context context, String mediaActivityUrl, String url, Bundle bundle) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(mediaActivityUrl));
        intent.addFlags(67108864);
        context.startActivity(intent);
        intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);
    }

    @SuppressLint("NewApi")
    public static void sendSchemeForResultAddMediaStack(Fragment activity, String url, int requestCode) {
        sendSchemeForResultAddMediaStack(activity, url, getHomeScheme(activity.getActivity()), requestCode);
    }

    public static void sendSchemeForResultAddMediaStack(Activity activity, String url, int requestCode) {
        sendSchemeForResultAddMediaStack(activity, url, getHomeScheme(activity), requestCode);
    }

    public static void sendSchemeForResultAddMediaStack(Fragment activity, String mediaActivityUrl, String url, int requestCode) {
        sendSchemeForResultAddMediaStack((Fragment) activity, mediaActivityUrl, url, requestCode, (Bundle) null);
    }

    public static void sendSchemeForResultAddMediaStack(Activity activity, String mediaActivityUrl, String url, int requestCode) {
        sendSchemeForResultAddMediaStack((Activity) activity, mediaActivityUrl, url, requestCode, (Bundle) null);
    }


    @SuppressLint("NewApi")
    public static void sendSchemeForResultAddMediaStack(Fragment activity, String mediaActivityUrl, String url, int requestCode, Bundle bundle) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(mediaActivityUrl));
        intent.putExtra(TARGET_SCHEME_URI, url);
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        activity.startActivityForResult(intent, requestCode);
    }

    public static void sendSchemeForResultAddMediaStack(Activity context, String mediaActivityUrl, String url, int requestCode, Bundle bundle) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(mediaActivityUrl));
        intent.addFlags(67108864);
        context.startActivity(intent);
        intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivityForResult(intent, requestCode);
    }

    public static void sendScheme(Fragment context, String url) {
        sendScheme((Fragment)context, url, (Bundle)null, false, 0);
    }

    public static void sendScheme(Context context, String url) {
        sendScheme((Context)context, url, (Bundle)null, false, 0);
    }

    public static void sendScheme(Fragment context, String url, Bundle bundle) {
        sendScheme((Fragment)context, url, bundle, false, 0);
    }

    public static void sendScheme(Context context, String url, Bundle bundle) {
        sendScheme((Context)context, url, bundle, false, 0);
    }

    public static void sendScheme(Fragment context, String url, boolean clearTop) {
        sendScheme((Fragment)context, url, (Bundle)null, clearTop, 0);
    }

    public static void sendScheme(Context context, String url, boolean clearTop) {
        sendScheme((Context)context, url, (Bundle)null, clearTop, 0);
    }

    public static void sendScheme(Fragment context, String url, int flag) {
        sendScheme((Fragment)context, url, (Bundle)null, false, flag);
    }

    public static void sendScheme(Context context, String url, int flag) {
        sendScheme((Context)context, url, (Bundle)null, false, flag);
    }

    @SuppressLint("NewApi")
    public static void sendScheme(Fragment context, String url, Bundle bundle, boolean clearTop, int flag) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        if(clearTop) {
            intent.addFlags(67108864);
        }

        if(flag != 0) {
            intent.setFlags(flag);
        }

        context.startActivity(intent);
    }

    public static void sendScheme(Context context, String url, Bundle bundle, boolean clearTop, int flag) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        if(clearTop) {
            intent.addFlags(67108864);
        }

        if(flag != 0) {
            intent.setFlags(flag);
        }

        context.startActivity(intent);
    }

    public static void sendSchemeForResult(Fragment activity, String url, int requestCode) {
        sendSchemeForResult((Fragment)activity, url, requestCode, (Bundle)null);
    }

    public static void sendSchemeForResult(Activity activity, String url, int requestCode) {
        sendSchemeForResult((Activity)activity, url, requestCode, (Bundle)null);
    }

    public static void sendSchemeForResult(Fragment activity, String url, int requestCode, int flag) {
        sendSchemeForResult((Fragment)activity, url, requestCode, (Bundle)null, flag);
    }

    public static void sendSchemeForResult(Activity activity, String url, int requestCode, int flag) {
        sendSchemeForResult((Activity)activity, url, requestCode, (Bundle)null, flag);
    }

    public static void sendSchemeForResult(Fragment activity, String url, int requestCode, Bundle bundle) {
        sendSchemeForResult((Fragment)activity, url, requestCode, bundle, 0);
    }

    public static void sendSchemeForResult(Activity activity, String url, int requestCode, Bundle bundle) {
        sendSchemeForResult((Activity)activity, url, requestCode, bundle, 0);
    }

    @SuppressLint("NewApi")
    public static void sendSchemeForResult(Fragment activity, String url, int requestCode, Bundle bundle, int flag) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        if(flag != 0) {
            intent.setFlags(flag);
        }

        activity.startActivityForResult(intent, requestCode);
    }

    public static void sendSchemeForResult(Activity activity, String url, int requestCode, Bundle bundle, int flag) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        if(flag != 0) {
            intent.setFlags(flag);
        }

        activity.startActivityForResult(intent, requestCode);
    }
}
