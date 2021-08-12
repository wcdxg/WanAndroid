package com.yuaihen.wcdxg.net

import com.google.gson.annotations.SerializedName

/**
 * Created by Yuaihen.
 * on 2020/6/10
 */
data class BaseResponse<T>(

    @SerializedName("errorCode")
    var errorCode: Int,

    @SerializedName("errorMsg")
    var errorMsg: String,

    @SerializedName("data")
    var data: T?,

    )