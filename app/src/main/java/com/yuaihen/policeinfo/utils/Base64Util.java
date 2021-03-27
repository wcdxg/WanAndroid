package com.yuaihen.policeinfo.utils;

import android.util.Base64;

public class Base64Util {
    /**
     * 编码
     *
     * @param bstr
     * @return String
     */
    public static String encode(byte[] bstr) {
        return Base64.encodeToString(bstr, Base64.DEFAULT);
    }

    /**
     * 解码
     *
     * @param str
     * @return string
     */
    public static byte[] decode(String str) {
        byte[] bt = null;
        try {
            bt = Base64.decode(str, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bt;
    }
}
