package com.jm.android.jmlaunchertest.dispatchertest;

import android.app.Application;

import com.jm.android.jmlauncher.dispatcher.constants.JMCommonAppConstants;
import com.jm.android.jmlauncher.dispatcher.entry.JMCommonDispatcherManager;
import com.jm.android.jmlauncher.dispatcher.modle.JMCommonSchemeItem;
import com.jm.android.jmlauncher.dispatcher.modle.JMCommonUrlItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by qixinh on 16/3/28.
 */
public class JMCommonApplication extends Application {
    public static JMCommonDispatcherManager dispatcherManager;

    @Override
    public void onCreate() {
        super.onCreate();
        dispatcherManager = JMCommonDispatcherManager.newInstance(this);


        initDispatcherModule();
        initMapUrls();

    }


    private void initDispatcherModule() {

        List<JMCommonSchemeItem> list = getDispacherList();
        dispatcherManager.initSubmoduleDispachersFromNet(list);
    }

    private List<JMCommonSchemeItem> getDispacherList() {
        List<JMCommonSchemeItem> list = new ArrayList<>();
        JMCommonSchemeItem item = new JMCommonSchemeItem(JMCommonAppConstants.HOME_MODULE_NAME, JMCommonAppConstants.HOME_MODULE_NAME_CLASS_PATH);
        list.add(item);
        return list;
    }

    private void initMapUrls() {

        JMCommonUrlItem item = new JMCommonUrlItem();
        item.key = "jack";
        item.uri = "jm://three/w";
        List<JMCommonUrlItem> list = new ArrayList<JMCommonUrlItem>();
        JMCommonUrlItem item1 = new JMCommonUrlItem();
        item1.key = "mack";
        item1.uri = "jm://three/r";

        list.add(item);
        list.add(item1);
        dispatcherManager.initUrlsMapFromNet(list);

    }


}
