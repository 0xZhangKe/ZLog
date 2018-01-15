package com.zhangke.androidlog;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZhangKe on 2018/1/11.
 */

class LogDispatcher extends Thread {

    private static final String TAG = "LogDispatcher";

    private final int MAX_LOG_SIZE = 1000;

    /**
     * 存储日志的队列
     */
    private LinkedBlockingQueue<LogBean> mLogQueue;
    private String mLogDir;


    LogDispatcher(LinkedBlockingQueue<LogBean> logQueue, String logDir) {
        this.mLogQueue = logQueue;
        this.mLogDir = logDir;
    }

    @Override
    public void run() {
        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            while (true) {
                try {
                    LogBean logBean;
                    logBean = mLogQueue.take();
                    switch(logBean.getLogType()){
                        case ERROR: {
                            saveTextToFile(mLogDir+ "/errorLog.txt", logBean.getLogText());
                            break;
                        }
                        case INFO:
                        case WTF:
                        case DEBUG: {
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, "run: ", e);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "run: ", e);
        }
    }

    /**
     * 普通日志文件名： log1.txt；
     * 错误日志文件名：errorLog1.txt;
     * 每个日志文件最大为 1Mb，超过 1Mb 文件名累加 1.
     */
    private String getLogFile(LogType logType){
        String fileName = "";
        try {
            switch (logType) {
                case ERROR: {
                    fileName = String.format("%s/errorLog1.txt", mLogDir);
                    File file = new File(mLogDir);
                    if (file.exists()) {
                        String[] fileArray = file.list();
                        if(fileArray != null && fileArray.length > 0){
                            List<String> errorLogList = new ArrayList<>();
                            for(String s : fileArray){
                                if(s.contains("errorLog")){
                                    errorLogList.add(s);
                                }
                            }
                            if(!errorLogList.isEmpty()){
                                errorLogList
                            }
                        }
                    }
                    break;
                }
                case INFO:
                case WTF:
                case DEBUG: {
                    fileName = String.format("%s/log1.txt", mLogDir);
                    break;
                }
            }
        }catch(Exception e){
            Log.e(TAG, "getLogFile: ", e);
        }
        return fileName;
    }

    private void saveTextToFile(String fileName, String text){
        try {
            File file = new File(fileName);
            if (!new File(file.getParent()).exists()) {
                File parentFile1 = new File(file.getParent());
                if (!parentFile1.exists()) {
                    File parentFile2 = new File(parentFile1.getParent());
                    if (!parentFile2.exists()) {
                        parentFile2.mkdir();
                    }
                    parentFile1.mkdir();
                }
                new File(file.getParent()).mkdir();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, true);
            writer.write(text);
            writer.close();
        }catch(Exception e){
            Log.e(TAG, "saveTextToFile: ", e);
        }
    }
}
