package com.yuaihen.wcdxg.utils

import android.util.Log
import com.blankj.utilcode.util.AppUtils

/**
 * Created by Yuaihen.
 * on 2021/3/25
 */
const val LOGGER = "logger"

object LogUtil {

    var IS_RELEASE = !AppUtils.isAppDebug()

    fun v(tag: String = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.v(tag, msg)
    }

    fun d(tag: String? = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.d(tag, msg)
    }

    fun i(tag: String = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.i(tag, msg)
    }

    fun w(tag: String = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.w(tag, msg)
    }

    fun e(tag: String = LOGGER, msg: String) {
        if (IS_RELEASE) return
        Log.e(tag, msg)
    }
}