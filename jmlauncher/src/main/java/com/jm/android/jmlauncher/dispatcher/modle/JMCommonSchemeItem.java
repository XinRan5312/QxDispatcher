package com.jm.android.jmlauncher.dispatcher.modle;

import java.io.Serializable;


/**
 * Created by qixinh on 16/3/8.
 */
public class JMCommonSchemeItem implements Serializable{
    private static final long serialVersionUID = 1L;
    public String key;
    public String classFullName;
    public JMCommonSchemeItem(){}
    public JMCommonSchemeItem(String key, String classFullName) {
        this.key = key;
        this.classFullName = classFullName;
    }
}
