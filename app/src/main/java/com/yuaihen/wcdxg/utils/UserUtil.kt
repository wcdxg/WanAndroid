package com.yuaihen.wcdxg.utils

import com.yuaihen.wcdxg.base.Constants


/**
 * Created by Yuaihen.
 * on 2020/10/28
 */
object UserUtil {

    fun setUserIsLogin(value: Boolean) {
        SPUtils.setBoolean(Constants.USER_IS_LOGIN, value)
    }

    fun getUserIsLogin(): Boolean {
        return SPUtils.getBoolean(Constants.USER_IS_LOGIN)
    }


}