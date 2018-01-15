package com.zhangke.androidlog;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Singleton class.</p>
 * Created by ZhangKe on 2018/1/11.
 */

class LogQueue {

    private static final String TAG = "LogQueue";

    /**
     * 存储日志的队列
     */
    private LinkedBlockingQueue<LogBean> mLogQueue = new LinkedBlockingQueue<>();
    private LogDispatcher mLogDispatcher;

    LogQueue(String logDir){
        mLogDispatcher = new LogDispatcher(mLogQueue, logDir);
    }

    void start(){
        mLogDispatcher.start();
    }

    void add(final LogBean logBean){
        try {
            boolean b = mLogQueue.offer(logBean);
            if (!b) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mLogQueue.put(logBean);
                        } catch (InterruptedException e) {
                            Log.e(TAG, "run: ", e);
                        }
                    }
                }).start();
            }
        }catch(Exception e){
            Log.e(TAG, "add: ", e);
        }
    }
}
