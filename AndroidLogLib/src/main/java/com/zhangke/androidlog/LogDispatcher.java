package com.zhangke.androidlog;

import android.os.Process;
import android.util.Log;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by ZhangKe on 2018/1/11.
 */

public class LogDispatcher extends Thread {

    private static final String TAG = "LogDispatcher";

    /**
     * 存储日志的队列
     */
    private LinkedBlockingDeque<String> mLogQueue = new LinkedBlockingDeque<>();
    private String mLogDir;

    public LogDispatcher(LinkedBlockingDeque<String> logQueue, String logDir){
        this.mLogQueue = logQueue;
        this.mLogDir = logDir;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while(true){
            String logText;
            try {
                logText = mLogQueue.take();
            }catch(InterruptedException e){
                Log.e(TAG, "run: ", e);
            }

        }
    }
}
