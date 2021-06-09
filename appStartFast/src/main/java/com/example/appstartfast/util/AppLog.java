package com.example.appstartfast.util;

import android.util.Log;

/**
 * @description: 自定义封装Log
 * @author: kangsx
 * @date: 2020/4/16
 * @version: 1.1.0
 */
public class AppLog {
    public static Boolean isLogEnable = false;
    public static void E(String message) {
        String[] infos = getAutoJumpLogInfos();
        if (isLogEnable) {
            Log.e(infos[0], message + infos[1] + infos[2]);
        }
    }

    public static void D(String message) {
        String[] infos = getAutoJumpLogInfos();
        if (isLogEnable) {
            Log.d(infos[0], message + infos[1] + infos[2]);
        }
    }

    public static void V(String message) {
        String[] infos = getAutoJumpLogInfos();
        if (isLogEnable) {
            Log.v(infos[0], message + infos[1] + infos[2]);
        }
    }

    public static void I(String message) {
        String[] infos = getAutoJumpLogInfos();
        if (isLogEnable) {
            Log.i(infos[0], message + infos[1] + infos[2]);
        }
    }

    public static void W(String message) {
        String[] infos = getAutoJumpLogInfos();
        if (isLogEnable) {
            Log.w(infos[0], message + infos[1] + infos[2]);
        }
    }

    /**
     * 获取打印信息所在方法名，行号等信息
     *
     * @return
     */
    public static String[] getAutoJumpLogInfos() {
        String[] infos = new String[]{"", "", ""};
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length < 5) {
            return infos;
        } else {
            infos[0] = elements[4].getClassName().substring(
                    elements[4].getClassName().lastIndexOf(".") + 1);
            infos[1] = "  [" + elements[4].getMethodName() + "() ";
            infos[2] = "(" + elements[4].getFileName() + ":"
                    + elements[4].getLineNumber() + ")]";
            return infos;
        }
    }
}
