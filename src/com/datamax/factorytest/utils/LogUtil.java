package com.datamax.factorytest.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogUtil {

    private static final String TAG = "FactoryTest";

    private static final boolean DEBUG = true;

    private static String TUTONG_LOG_FILE = getSDPath() + "/tutong_log.log";

    public static void d(String TAG, String method, String msg) {
        Log.d(TAG, "[" + method + "]" + msg);
    }

    public static void d(String TAG, String msg) {
        if (DEBUG) {
            Log.d(TAG, getFileLineMethod() + msg);
        }
    }

//    public static void d(String msg) {
//        if (DEBUG) {
//            Log.d(_FILE_(), "[" + getLineMethod() + "]" + msg);
//        }
//    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, getLineMethod() + msg);
        }
    }


    public static void w(String TAG, String msg) {
        if (DEBUG) {
            Log.w(TAG, "[" + getFileLineMethod() + "]" + msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(_FILE_(), "[" + getLineMethod() + "]" + msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (DEBUG) {
            Log.i(TAG, "[" + getFileLineMethod() + "]" + msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, getLineMethod() + msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, getLineMethod() + msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(TAG, getLineMethod() + msg);
        }
    }

    public static void f(String TAG, String msg) {
        if (DEBUG) {
            try {
                FileWriter fw = new FileWriter(TUTONG_LOG_FILE, true);
                fw.write(msg + "\n");
                fw.close();
                i(TAG, msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer("[")
                .append(traceElement.getFileName()).append("|")
                .append(traceElement.getLineNumber()).append("|")
                .append(traceElement.getMethodName()).append("]  ");
        return toStringBuffer.toString();
    }

    public static String getLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer("[")
                .append(traceElement.getLineNumber()).append("|")
                .append(traceElement.getClassName()).append("]");
        return toStringBuffer.toString();
    }

    public static String _FILE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return traceElement.getFileName();
    }

    public static String _FUNC_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName();
    }

    public static int _LINE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }

    public static String _TIME_() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(now);
    }

    private static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

    private static long _time_start = -1;
    private static long _time_end = -1;

    public static void timeStart() {
        _time_start = System.currentTimeMillis();
    }

    public static void timeEnd(String msg, boolean isSeconds) {
        _time_end = System.currentTimeMillis();
        i("time", msg + " cost time ========>" + (isSeconds ? ((_time_end - _time_start) / 1000) : (_time_end - _time_start)));
        _time_start = -1;
        _time_end = -1;
    }

    public static void printStackTraces() {
        if (DEBUG) {
            Throwable ex = new Throwable();
            StackTraceElement[] stackElements = ex.getStackTrace();
            if (stackElements != null) {
                for (int i = 0; i < stackElements.length; i++) {
                    i(stackElements[i].getClassName() + "\t" + stackElements[i].getLineNumber() + "\t" + stackElements[i].getMethodName());
                }
            }
        }
    }
}
