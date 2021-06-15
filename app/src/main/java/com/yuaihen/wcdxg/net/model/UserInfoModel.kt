package com.yuaihen.wcdxg.net.model


import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("errorCode")
    val errorCode: Int = 0,
    @SerializedName("errorMsg")
    val errorMsg: String = ""
) {
    data class Data(
        @SerializedName("coinCount")
        val coinCount: Int = 0,
        @SerializedName("rank")
        val rank: String = "0",
        @SerializedName("userId")
        val userId: Int = 0,
        @SerializedName("username")
        val username: String = ""
    )
}