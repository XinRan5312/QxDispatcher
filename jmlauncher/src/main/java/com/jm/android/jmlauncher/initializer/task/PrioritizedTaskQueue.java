package com.jm.android.jmlauncher.initializer.task;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by chaoranf@jumei.com on 16/3/16.
 */
public class PrioritizedTaskQueue implements Runnable {

    private PriorityBlockingQueue<PrioritizedTask> queue;
    private ExecutorService exec;

    public PrioritizedTaskQueue(PriorityBlockingQueue<PrioritizedTask> q, ExecutorService e) {
        queue = q;
        exec = e;
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()) {
                if(queue!=null){

                }
                PrioritizedTask toExecuted = queue.take();
                Log.i("testff","优先级"+toExecuted.getPriority());
                //通过这里可以知道哪些线程优先执行，但是需要注意，优先开始执行，不代表他一定优先结束
                exec.execute(toExecuted);
            }
        } catch (InterruptedException e) {

        }

    }

}
