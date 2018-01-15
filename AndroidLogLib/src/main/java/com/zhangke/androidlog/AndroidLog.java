package com.zhangke.androidlog;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Logging helper class.
 * Created by ZhangKe on 2018/1/11.
 */

public class AndroidLog {

    private LogQueue mLogQueue;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.CHINA);
    private static Boolean saveToFile = true;

    public synchronized static void openSaveToFile(){
        saveToFile = true;
    }

    public synchronized static void closeSaveToFile(){
        saveToFile = false;
    }

    public AndroidLog(String logDir) {
        mLogQueue = new LogQueue(logDir);
        mLogQueue.start();
    }

    public void e(String TAG, String text){
        Log.e(TAG, text);
        if(saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.ERROR));
        }
    }

    public void e(String TAG, String text, Throwable e){
        Log.e(TAG, "e: ", e);
        if(saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, String.format("%s--->%s", text, e.toString())), LogType.ERROR));
        }
    }

    public void d(String TAG, String text){
        Log.e(TAG, text);
        if(saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.DEBUG));
        }
    }

    public void info(String TAG, String text){
        Log.e(TAG, text);
        if(saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.INFO));
        }
    }

    public void wtf(String TAG, String text){
        Log.e(TAG, text);
        if(saveToFile) {
            mLogQueue.add(new LogBean(buildMessage(TAG, text), LogType.WTF));
        }
    }

    private String buildMessage(String TAG, String text){
        try {
            StringBuilder sbLog = new StringBuilder();
            sbLog.append(simpleDateFormat.format(new Date()));
            sbLog.append("/");
            sbLog.append(TAG);
            sbLog.append("--->");
            sbLog.append(text);
            sbLog.append("\n\n");
            return sbLog.toString();
        }catch(Exception e){
            Log.e(TAG, "buildMessage: ", e);
            return "";
        }
    }
}
