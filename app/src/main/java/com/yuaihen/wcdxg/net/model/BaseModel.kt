package com.yuaihen.wcdxg.net.model

import com.google.gson.annotations.SerializedName

data class BaseModel(

    @SerializedName("data")
    val `data`: Any?,
    @SerializedName("errorCode")
    val errorCode: Int = 0,
    @SerializedName("errorMsg")
    val errorMsg: String = ""
)

