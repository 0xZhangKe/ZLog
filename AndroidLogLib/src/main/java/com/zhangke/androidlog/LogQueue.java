package com.zhangke.androidlog;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * Singleton class.</p>
 * Created by ZhangKe on 2018/1/11.
 */

public class LogQueue {

    /**
     * 存储日志的队列
     */
    private LinkedBlockingDeque<LogBean> mLogQueue = new LinkedBlockingDeque<>();
    private LogDispatcher mLogDispatcher;

    public LogQueue(String logDir){
        mLogDispatcher = new LogDispatcher(mLogQueue, logDir);
    }

    public void start(){
        mLogDispatcher.start();
    }

    public void add(LogBean logBean){

    }
}
