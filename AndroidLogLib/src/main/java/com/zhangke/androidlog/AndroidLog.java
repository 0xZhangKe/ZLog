package com.zhangke.androidlog;

import android.content.Context;

/**
 * Logging helper class.
 * Created by ZhangKe on 2018/1/11.
 */

public class AndroidLog {

    private LogQueue mLogQueue;
    private String mLogDir;

    public AndroidLog(String logDir) {
        this.mLogDir = logDir;
        mLogQueue = new LogQueue(mLogDir);
        mLogQueue.start();
    }

    public void e(String text){
        mLogQueue.add(new LogBean(buildMessage(text), LogType.ERROR));
    }

    public void d(String text){
        mLogQueue.add(new LogBean(buildMessage(text), LogType.DEBUG));
    }

    public void info(String text){
        mLogQueue.add(new LogBean(buildMessage(text), LogType.INFO));
    }

    public void wtf(String text){
        mLogQueue.add(new LogBean(buildMessage(text), LogType.WTF));
    }

    private String buildMessage(String text){

        return text;
    }
}
