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
    }

    public static void e(String text){

    }

    public static void d(String text){

    }

    public static void wtf(String text){

    }

    private static String buildMessage(String text){

        return text;
    }
}
