package com.jm.android.jmlauncher.dispatcher.entry;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.jm.android.jmlauncher.dispatcher.constants.JMCommonAppConstants;
import com.jm.android.jmlauncher.dispatcher.modle.JMCommonIBaseDispatcher;
import com.jm.android.jmlauncher.dispatcher.modle.JMCommonSchemeItem;
import com.jm.android.jmlauncher.dispatcher.modle.JMCommonUrlItem;
import com.jm.android.jmlauncher.dispatcher.net.JMCommonAsynTaskForConfigData;
import com.jm.android.jmlauncher.dispatcher.utils.JMCommonIntentUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by qixinh on 16/3/5
 * <p/>
 * 这个工具类适用于每个业务线写一个单独的JMCommonIBaseDispatcher子类，作为综合管理本业务线或者模块的url跳转的业务，
 * 达到跟其它业务线完全解耦，只要给我指定的Url即可，至于我跳转到哪里是我业务线的自己的事儿
 * 这个的优点是不用Activity在manifest.xml中配置scheme data了
 */
public class JMCommonDispatcherManager {

    private static Object obj = new Object();
    public static final String TAG = JMCommonDispatcherManager.class.getSimpleName();
    private Context mContext;

    private static JMCommonDispatcherManager mIntance = null;

    private JMCommonDispatcherManager(Context context) {
        this.mContext = context;
    }

    private static JMCommonDispatcherManager init(Context context) {
        if (mIntance == null) {
            synchronized (obj) {
                mIntance = new JMCommonDispatcherManager(context);
            }
        }
        return mIntance;
    }

    public static JMCommonDispatcherManager newInstance(Context context) {
        if (mIntance == null) {
            mIntance = init(context);
        }
        return mIntance;
    }


    //private WeakHashMap<String, Class<? extends JMCommonIBaseDispatcher>> mSubmoduleDispacher = new WeakHashMap<String, Class<? extends JMCommonIBaseDispatcher>>();

    private HashMap<String, JMCommonIBaseDispatcher> mSubmoduleDispacher = new HashMap<String, JMCommonIBaseDispatcher>();
    private WeakHashMap<String, String> mUrlsMap = new WeakHashMap<String, String>();

    public void registerSubmoduleDispatcher(String submoduleName, JMCommonIBaseDispatcher cls) {
        if (submoduleName == null || submoduleName.isEmpty() || cls == null) return;
        if (!mSubmoduleDispacher.containsKey(submoduleName))
            mSubmoduleDispacher.put(submoduleName, cls);

    }

    public void unRegisterSubmoduleDispatcher(String submoduleName) {
        if (submoduleName == null || submoduleName.isEmpty()) return;
        if (mSubmoduleDispacher.containsKey(submoduleName))
            mSubmoduleDispacher.remove(submoduleName);
    }

    /**
     * 暂时禁用等公司网络架构确定再考虑是否使用此方法
     */

    public void getConfigOfSubmoduleDispacher(String url) {

        new JMCommonAsynTaskForConfigData(new JMCommonAsynTaskForConfigData.ConfigDataCallBack<JMCommonSchemeItem>() {
            @Override
            public void requestOk(List<JMCommonSchemeItem> list) {
                initSubmoduleDispachersFromNet(list);
            }
        }).execute(url);
    }

    public void initUrlsMapFromNet(List<JMCommonUrlItem> list) {
        if (list == null || list.isEmpty()) return;
        for (JMCommonUrlItem item : list) {
            mUrlsMap.put(item.key, item.uri);
        }
    }

    public void initSubmoduleDispachersFromNet(List<JMCommonSchemeItem> list) {
        if (list.isEmpty()) return;
        if (!mSubmoduleDispacher.isEmpty()) mSubmoduleDispacher.clear();
        JMCommonSchemeItem item = null;
        JMCommonIBaseDispatcher obj = null;
        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            try {
                Class<?> cl = Class.forName(item.classFullName);

                try {
                    obj = (JMCommonIBaseDispatcher) cl.getDeclaredConstructor(Context.class).newInstance(mContext);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            mSubmoduleDispacher.put(item.key, obj);
        }
    }


    public void dispacherTo(String moduleName, String keyOfUrl) {
        dispacherTo(moduleName, keyOfUrl, null);
    }

    public void dispacherTo(String moduleName, String keyOfUrl, Bundle bundle) {
        if (keyOfUrl == null || keyOfUrl.isEmpty() || mUrlsMap.isEmpty() || moduleName == null || moduleName.isEmpty())
            throw new NullPointerException();
        String url = mUrlsMap.get(keyOfUrl);
        if (bundle == null) {
            dispatcherRequest(moduleName, url, null);
        } else {
            dispatcherRequest(moduleName, url, bundle);
        }
    }

    public void dispacherToForResult(String moduleName, String keyOfUrl, Activity context, int requestCode) {
        dispacherToForResult(moduleName, keyOfUrl, null, context, requestCode);
    }

    public void dispacherToForResult(String moduleName, String keyOfUrl, Bundle bundle, Activity context, int requestCode) {
        if (keyOfUrl == null || keyOfUrl.isEmpty() || mUrlsMap.isEmpty() || moduleName == null || moduleName.isEmpty())
            throw new NullPointerException();
        String url = mUrlsMap.get(keyOfUrl);
        if (context == null)
            throw new NullPointerException(TAG + "dispacherToForResult() Activity param is null");
        if (bundle == null) {
            dispatcherRequestForResult(moduleName, Uri.parse(url), null, context, requestCode);
        } else {
            dispatcherRequestForResult(moduleName, Uri.parse(url), bundle, context, requestCode);
        }
    }

    private void dispatcherRequest(String moduleName, String url, Bundle bundle) {
        if (url == null || url.isEmpty()) return;
        if (bundle == null) {
            dispatcherRequest(moduleName, Uri.parse(url), null);
        } else {
            dispatcherRequest(moduleName, Uri.parse(url), bundle);
        }

    }

    private void dispatcherRequest(String moduleName, Uri url, Bundle bundle) {
        if (url == null) return;
        String scheme = url.getScheme();
        if (!JMCommonAppConstants.JM_SCHEME.equals(scheme)) {
            return;
        }
        JMCommonIBaseDispatcher b = mSubmoduleDispacher.get(moduleName);
        HashMap<String, String> map = JMCommonIntentUtils.splitParams1(url);
        // add origin uri string to params
        map.put(JMCommonIBaseDispatcher.__ORIGIN_URI, url.toString());
        String lastPathSegment = url.getLastPathSegment();
        String path = url.getEncodedPath();

        if (bundle == null) {
            mSubmoduleDispacher.get(moduleName).deal(lastPathSegment, path, map, null);
        } else {
            mSubmoduleDispacher.get(moduleName).deal(lastPathSegment, path, map, bundle);
        }


    }

    private void dispatcherRequestForResult(String moduleName, Uri url, Bundle bundle, Activity context, int requestCode) {
        if (url == null) return;
        String scheme = url.getScheme();
        if (!JMCommonAppConstants.JM_SCHEME.equals(scheme)) {
            return;
        }
        JMCommonIBaseDispatcher b = mSubmoduleDispacher.get(moduleName);
        HashMap<String, String> map = JMCommonIntentUtils.splitParams1(url);
        // add origin uri string to params
        map.put(JMCommonIBaseDispatcher.__ORIGIN_URI, url.toString());
        String lastPathSegment = url.getLastPathSegment();
        String path = url.getEncodedPath();

        if (bundle == null) {
            mSubmoduleDispacher.get(moduleName).dealStartActivityForResult(lastPathSegment, path, map, null, context, requestCode);
        } else {
            mSubmoduleDispacher.get(moduleName).dealStartActivityForResult(lastPathSegment, path, map, bundle, context, requestCode);
        }


    }

}


