package com.yuaihen.wcdxg.utils

import com.yuaihen.wcdxg.base.Constants


/**
 * Created by Yuaihen.
 * on 2020/10/28
 */
object UserUtil {

    fun clearCookie() {
        SPUtils.getCookiePreferences().clearAll()
    }

    fun getCookie(): String {
        return SPUtils.getCookiePreferences().decodeString(Constants.Cookie, "")
    }
}