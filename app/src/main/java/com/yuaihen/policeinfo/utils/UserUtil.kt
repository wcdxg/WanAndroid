package com.yuaihen.policeinfo.utils

import com.yuaihen.policeinfo.base.Constants


/**
 * Created by Yuaihen.
 * on 2020/10/28
 */
object UserUtil {

    fun setToken(value: String?) {
        SPUtils.setString(Constants.TOKEN, value)
    }

    fun getToken(): String {
        return SPUtils.getString(Constants.TOKEN)
    }

    fun setUserId(userId: String) {
        SPUtils.setString(Constants.USER_ID, userId)
    }

    fun getUserId(): String {
        return SPUtils.getString(Constants.USER_ID)
    }

}