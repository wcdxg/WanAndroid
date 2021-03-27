package com.yuaihen.policeinfo.utils

import com.google.gson.Gson


/**
 * Created by Yuaihen.
 * on 2020/7/21
 */
object GsonUtil {

    private var instance: Gson? = null

    fun getInstance(): Gson {
        return instance ?: Gson()
    }

}