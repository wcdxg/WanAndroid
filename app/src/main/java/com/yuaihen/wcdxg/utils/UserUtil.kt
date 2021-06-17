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

    fun setLoginStatus(loginStatus: Boolean) {
        SPUtils.setBoolean(Constants.LOGIN_STATUS, loginStatus)
    }

    fun getLoginStatus(): Boolean {
        return SPUtils.getBoolean(Constants.LOGIN_STATUS)
    }

    fun setUserName(userName: String) {
        SPUtils.setString(Constants.USER_NAME, userName)
    }

    fun getUserName(): String {
        return SPUtils.getString(Constants.USER_NAME)
    }

    fun setUserCoinCount(coinCount: Int) {
        SPUtils.setInt(Constants.COIN_COUNT, coinCount)
    }

    fun getUserCoinCount(): Int {
        return SPUtils.getInt(Constants.COIN_COUNT)
    }
}