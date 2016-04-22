package com.jm.android.jmlauncher.schema_parser.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxiao on 16/3/4.
 */
public class JMSchemaBulk {
    //是否是首页
    public boolean mIsDefault = false;

    public List<JMSchemaItem> mList = new ArrayList<JMSchemaItem>();
}
