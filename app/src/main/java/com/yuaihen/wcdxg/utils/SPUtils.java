package com.yuaihen.wcdxg.utils;

import com.blankj.utilcode.util.LogUtils;
import com.tencent.mmkv.MMKV;

/**
 * 数据缓存工具类
 */
public class SPUtils {

    private static final String TAG = "SPUtils";
    private static MMKV mmkv,cookieMMKV;

    /**
     * MMKV支持的数据类型
     * 支持以下 Java 语言基础类型：
     * boolean、int、long、float、double、byte[]
     * 支持以下 Java 类和容器：
     * String、Set<String>
     * 任何实现了Parcelable的类型
     */
    public static MMKV getPreferences() {
        if (mmkv == null) {
            mmkv = MMKV.defaultMMKV();
        }

        return mmkv;
    }

    public static MMKV getCookiePreferences() {
        if (cookieMMKV == null) {
            cookieMMKV = MMKV.mmkvWithID("cookie") ;
        }

        return cookieMMKV;
    }

    public static void clear() {
        getPreferences().clearAll();
    }

    public static void setBean(String key, Object obj) {
        String json = GsonUtil.INSTANCE.getInstance().toJson(obj);
        getPreferences().encode(key, json);
    }

    public static <T> T getBean(String key, Class<T> clazz) {
        String json = getPreferences().decodeString(key);
        try {
            return GsonUtil.INSTANCE.getInstance().fromJson(json, clazz);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }

        return null;
    }

    public static void setBoolean(String key, boolean value) {
        getPreferences().encode(key, value);
    }

    public static boolean getBoolean(String key) {
        return getPreferences().decodeBool(key);
    }

    public static void setString(String key, String value) {
        getPreferences().encode(key, value);
    }

    public static String getString(String key) {
        return getPreferences().decodeString(key, "");
    }

    public static void setInt(String key, int value) {
        getPreferences().encode(key, value);
    }

    public static int getInt(String key) {
        return getPreferences().decodeInt(key);
    }

    public static int getInt(String key, int defValue) {
        return getPreferences().decodeInt(key, defValue);
    }

    public static void setDouble(String key, double value) {
        getPreferences().encode(key, value);
    }

    public static double getDouble(String key) {
        return getPreferences().decodeDouble(key);
    }

    public static void setLong(String key, long value) {
        getPreferences().encode(key, value);
    }

    public static long getLong(String key, Long defValue) {
        return getPreferences().decodeLong(key, defValue);
    }

    public static void setFloat(String key, float value) {
        getPreferences().encode(key, value);
    }

    public static float getFloat(String key, float defValue) {
        return getPreferences().decodeFloat(key, defValue);
    }

}
