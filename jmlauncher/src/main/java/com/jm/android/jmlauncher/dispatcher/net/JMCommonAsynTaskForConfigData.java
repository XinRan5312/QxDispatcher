package com.jm.android.jmlauncher.dispatcher.net;

import com.jm.android.jmlauncher.dispatcher.modle.JMCommonSchemeItem;
import com.jm.android.jmlauncher.dispatcher.utils.Molk;

import java.util.List;

/**
 * Created by qixinh on 16/3/9.
 */
public class JMCommonAsynTaskForConfigData extends JMCommonBaseAsynTask<JMCommonSchemeItem> {


    public JMCommonAsynTaskForConfigData(ConfigDataCallBack configDataCallBack) {
        super(configDataCallBack);
    }


    @Override
    public List<JMCommonSchemeItem> doInBackThread(String... params) {
        return Molk.malk();
    }

}
