package com.zhangke.androidlog;

/**
 * Created by ZhangKe on 2018/1/11.
 */

public class LogBean {

    private String logText;
    private LogType logType;

    public LogBean(String logText, LogType logType) {
        this.logText = logText;
        this.logType = logType;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }
}
