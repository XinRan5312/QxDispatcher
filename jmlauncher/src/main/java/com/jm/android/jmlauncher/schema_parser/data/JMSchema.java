package com.jm.android.jmlauncher.schema_parser.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Schema类，包含当前所有app需要的所有数据
 * <p/>
 * Created by sunxiao on 16/3/3.
 */
public class JMSchema {
    public String mVersion = "";
    public int mVerID = 0;
    public List<JMSchemaBulk> mList = new ArrayList<JMSchemaBulk>();

    //当前调用链，用来返回结果，以及回退等等
    public List<JMSchemaItem> mTaskList = new ArrayList<JMSchemaItem>();

    /**
     * 在当前mTaskList中添加一个新的关系链
     *
     * @param item
     */
    public void pushItem(JMSchemaItem item) {
    }

}
