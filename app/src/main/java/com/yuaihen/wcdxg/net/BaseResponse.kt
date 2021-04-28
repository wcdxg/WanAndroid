package com.yuaihen.wcdxg.net

import com.google.gson.annotations.SerializedName

/**
 * Created by Yuaihen.
 * on 2020/6/10
 */
data class BaseResponse<T>(
    @SerializedName("code")
    var code: Int,
    @SerializedName("msg")
    var message: String,
    @SerializedName("data")
    var data: T,
    @SerializedName("timestamp")
    var timestamp: Long
)