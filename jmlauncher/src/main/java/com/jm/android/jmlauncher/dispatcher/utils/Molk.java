package com.jm.android.jmlauncher.dispatcher.utils;



import com.jm.android.jmlauncher.dispatcher.modle.JMCommonSchemeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qixinh on 16/3/16.
 */
public class Molk {
    public static List<JMCommonSchemeItem> malk(){
        JMCommonSchemeItem item = new JMCommonSchemeItem();
        item.key = "home";
        item.classFullName = "com.example.xinran.jumeidespatcher.dispatcher.JMSchemaDealHome";
        List<JMCommonSchemeItem> list = new ArrayList<JMCommonSchemeItem>();
        list.add(item);
        return list;
    }
}
