package com.jm.android.jmlauncher.initializer.task;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chaoranf@jumei.com on 16/3/16.
 */
public class PrioritizedTask implements Runnable {

    private final int priority;

    private Method method;
    private Object methodInvokeObj;
    private boolean isOnUiThread = false;

    /**
     * 创建主线程任务
     *
     * @param priority
     * @param method
     * @param methodInvokeObj
     * @param isOnUiThread
     */
    public PrioritizedTask(int priority, Method method, Object methodInvokeObj, boolean isOnUiThread) {
        this.priority = priority + 3;
        //3的目的，是保证，三个优先级级别的ui线程任务，永远优先级比子线程高
        this.method = method;
        this.methodInvokeObj = methodInvokeObj;
        this.isOnUiThread = isOnUiThread;
    }

    /**
     * 创建子线程任务
     *
     * @param method
     * @param methodInvokeObj
     */
    public PrioritizedTask(Method method, Object methodInvokeObj) {
        this.priority = 0;
        this.method = method;
        this.methodInvokeObj = methodInvokeObj;
        this.isOnUiThread = false;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public void run() {
        //执行任务代码..
        try {
            if (isOnUiThread) {
                ((Activity) methodInvokeObj).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            method.invoke(methodInvokeObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                method.invoke(methodInvokeObj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
