package com.yuaihen.policeinfo.utils

import android.util.Log

/**
 * Created by Yuaihen.
 * on 2021/3/25
 */
object LogUtil {

    var IS_RELEASE = AppUtil.isRelease()

    const val LOGGER = "logger"

    fun v(tag: String = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.v(tag, msg)
    }

    fun d(tag: String? = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.v(tag, msg)
    }

    fun i(tag: String = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.v(tag, msg)
    }

    fun w(tag: String = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.v(tag, msg)
    }

    fun e(tag: String = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.v(tag, msg)
    }
}