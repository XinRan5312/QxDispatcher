package com.jm.android.jmlauncher.initializer.utils;

import android.app.Activity;

import com.jm.android.jmlauncher.initializer.annonation.JMAsync;
import com.jm.android.jmlauncher.initializer.annonation.JMPriviority;
import com.jm.android.jmlauncher.initializer.annonation.JMSync;
import com.jm.android.jmlauncher.initializer.annonation.eJMPriviority;
import com.jm.android.jmlauncher.initializer.task.PrioritizedTask;
import com.jm.android.jmlauncher.initializer.task.PrioritizedTaskQueue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 启动器
 * <p/>
 * Created by sunxiao on 16/3/3.
 */
public class JMInitializer {

    private ExecutorService mThreadPool = Executors.newCachedThreadPool();
    private PriorityBlockingQueue<PrioritizedTask> mQueue;
    private int DEFAULT_SIZE = 16;
    Comparator<PrioritizedTask> threadOrder = new Comparator<PrioritizedTask>() {
        @Override
        public int compare(PrioritizedTask o1, PrioritizedTask o2) {
            //优先级排序
            if (o1.getPriority() > o2.getPriority()) {
                return -1;
            } else if (o1.getPriority() < o2.getPriority()) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    private JMInitializer() {

    }

    private static class SingletonHolder {
        private static final JMInitializer INSTANCE = new JMInitializer();
    }

    public static final JMInitializer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 初始化某个activity的方法
     *
     * @param activity 需要获取的某个类
     */
    public void init(Activity activity) {

        mQueue = new PriorityBlockingQueue<PrioritizedTask>(DEFAULT_SIZE, threadOrder);
        Method[] methods = activity.getClass().getMethods();
        //对方法进行分类，主要涉及优先级和主子线程的交叉
        //先按主线程、子线程执行分类
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            //注解暂时有三种类型，
            //1是主线程，还是子线程执行，2优先级，3是否存在依赖
            if (method.isAnnotationPresent(JMSync.class)) {
                //主线程需要做优先级的区分
                if (method.isAnnotationPresent(JMPriviority.class)) {
                    JMPriviority priviority = method.getAnnotation(JMPriviority.class);
                    if (priviority.value() == eJMPriviority.ePriviorityHigh) {
                        mQueue.add(newOnUiThreadTask(eJMPriviority.ePriviorityHigh, method, activity));

                    } else if (priviority.value() == eJMPriviority.ePriviorityNormal) {
                        mQueue.add(newOnUiThreadTask(eJMPriviority.ePriviorityNormal, method, activity));

                    } else if (priviority.value() == eJMPriviority.ePriviorityLow) {
                        mQueue.add(newOnUiThreadTask(eJMPriviority.ePriviorityLow, method, activity));

                    } else {
                        mQueue.add(newOnUiThreadTask(eJMPriviority.ePriviorityLow, method, activity));
                    }
                } else {
                    mQueue.add(newOnUiThreadTask(eJMPriviority.ePriviorityLow, method, activity));
                }

            } else if (method.isAnnotationPresent(JMAsync.class)) {
                //如果不加这个，使用孙晓说的把所有方法都加载到这里，然后去执行，如果有问题，直接抛异常，可以
                //但是这样会不会更消耗资源

                //异步执行，无所谓优先级，直接扔到线程池，自己去执行吧
                //因为和ui的线程在一个队列，所以将UI线程的优先级定位456，子线程的优先级定位123
                mQueue.add(newOnSonThreadTask(method, activity));

            } else {
                //没有添加这两种注解的方法，不进行处理

            }

        }
        PrioritizedTaskQueue taskQueue = new PrioritizedTaskQueue(mQueue, mThreadPool);
        mThreadPool.execute(taskQueue);
    }

    private PrioritizedTask newOnUiThreadTask(eJMPriviority priviority, Method method, Activity activity) {
        int tempPriority = 0;
        if (priviority == eJMPriviority.ePriviorityHigh) {
            tempPriority = 2;
        } else if (priviority == eJMPriviority.ePriviorityNormal) {
            tempPriority = 1;
        } else if (priviority == eJMPriviority.ePriviorityLow) {
            tempPriority = 0;
        }
        PrioritizedTask task = new PrioritizedTask(tempPriority, method, activity, true);
        return task;
    }

    private PrioritizedTask newOnSonThreadTask(Method method, Activity activity) {
        PrioritizedTask task = new PrioritizedTask(method, activity);
        return task;
    }

}
