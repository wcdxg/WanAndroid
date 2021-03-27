package com.yuaihen.policeinfo.utils

/**
 * Created by Yuaihen.
 * on 2020/11/4
 */
object IntentDataHelper {

    private val bigData: MutableMap<String, Any> = mutableMapOf();

    fun putData(data: Any, tag: String = ""): String {
        val key = tag + System.currentTimeMillis()
        bigData[key] = data
        return key
    }

    fun <T> getData(key: String?): T? {
        if (key == null) return null
        val data = bigData[key]
        bigData.remove(key)
        return data as? T
    }
}