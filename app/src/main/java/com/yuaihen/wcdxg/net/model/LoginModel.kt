package com.yuaihen.wcdxg.net.model

import com.google.gson.annotations.SerializedName

data class LoginModel(

    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("errorCode")
    val errorCode: Int = 0,
    @SerializedName("errorMsg")
    val errorMsg: String = ""
) {
    data class Data(
        @SerializedName("admin")
        val admin: Boolean,
        @SerializedName("chapterTops")
        val chapterTops: List<Any>,
        @SerializedName("coinCount")
        val coinCount: Int,
        @SerializedName("collectIds")
        val collectIds: List<Int>,
        @SerializedName("email")
        val email: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("publicName")
        val publicName: String,
        @SerializedName("token")
        val token: String,
        @SerializedName("type")
        val type: Int,
        @SerializedName("username")
        val username: String,
    )

}

