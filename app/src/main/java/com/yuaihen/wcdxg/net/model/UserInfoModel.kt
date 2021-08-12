package com.yuaihen.wcdxg.net.model


import com.google.gson.annotations.SerializedName

data class UserInfoModel(

    @SerializedName("coinCount")
    val coinCount: Int = 0,
    @SerializedName("rank")
    val rank: String = "0",
    @SerializedName("userId")
    val userId: Int = 0,
    @SerializedName("username")
    val username: String = ""
)
