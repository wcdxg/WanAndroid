package com.yuaihen.wcdxg.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;
import com.yuaihen.wcdxg.base.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据缓存工具类
 */
public class SPUtils {

    private static final String TAG = "SPUtils";
    private static MMKV mmkv, cookieMMKV, historySearchMMKV;

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
            cookieMMKV = MMKV.mmkvWithID("cookie");
        }

        return cookieMMKV;
    }

    public static MMKV getHistorySearchPreferences() {
        if (historySearchMMKV == null) {
            historySearchMMKV = MMKV.mmkvWithID("search");
        }
        return historySearchMMKV;
    }

    /**
     * 添加历史搜索记录
     */
    public static void addHistorySearch(List<String> list) {
        String result = GsonUtil.INSTANCE.getInstance().toJson(list);
        if (!TextUtils.isEmpty(result)) {
            getHistorySearchPreferences().encode(Constants.HISTORY_SEARCH, result);
        }
    }

    /**
     * 获取历史搜索记录列表
     */
    public static List<String> getHistorySearchList() {
        List<String> list = new ArrayList<>();
        String searchStr = getHistorySearchPreferences().decodeString(Constants.HISTORY_SEARCH);
        try {
            List<String> resultList = GsonUtil.INSTANCE.getInstance().fromJson(searchStr, new TypeToken<List<String>>() {
            }.getType());

            if (resultList != null) {
                list.addAll(resultList);
            }
        } catch (Exception e) {
            LogUtil.INSTANCE.d("SPUtil", Objects.requireNonNull(e.getLocalizedMessage()));
        }

        return list;
    }

    /**
     * 清空历史搜索记录
     */
    public static void clearHistorySearch() {
        getHistorySearchPreferences().clear().apply();
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
