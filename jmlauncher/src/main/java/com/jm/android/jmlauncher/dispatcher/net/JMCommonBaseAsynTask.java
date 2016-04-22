package com.jm.android.jmlauncher.dispatcher.net;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by qixinh on 16/3/15.
 */
public abstract class JMCommonBaseAsynTask<T> extends AsyncTask<String,Integer,List<T>> {
    private ConfigDataCallBack mConfigDataCallBack;
    public JMCommonBaseAsynTask(ConfigDataCallBack configDataCallBack){
        this.mConfigDataCallBack=configDataCallBack;
    }

    @Override
    protected List<T> doInBackground(String... params) {
        return doInBackThread(params);
    }

    @Override
    protected void onPostExecute(List<T> ts) {
        super.onPostExecute(ts);
    }

    public interface ConfigDataCallBack<E>{
        void requestOk(List<E> list);
    }
    public abstract List<T> doInBackThread(String... params);
}
