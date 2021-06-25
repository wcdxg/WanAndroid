package com.yuaihen.wcdxg.net.model


import com.google.gson.annotations.SerializedName

data class CoinRecordModel(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("errorCode")
    val errorCode: Int = 0,
    @SerializedName("errorMsg")
    val errorMsg: String = ""
) {
    data class Data(
        @SerializedName("curPage")
        val curPage: Int = 0,
        @SerializedName("datas")
        val datas: List<CoinData> = listOf(),
        @SerializedName("offset")
        val offset: Int = 0,
        @SerializedName("over")
        val over: Boolean = false,
        @SerializedName("pageCount")
        val pageCount: Int = 0,
        @SerializedName("size")
        val size: Int = 0,
        @SerializedName("total")
        val total: Int = 0
    ) {
        data class CoinData(
            @SerializedName("coinCount")
            val coinCount: Int = 0,
            @SerializedName("date")
            val date: Long = 0,
            @SerializedName("desc")
            val desc: String = "",
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("reason")
            val reason: String = "",
            @SerializedName("type")
            val type: Int = 0,
            @SerializedName("userId")
            val userId: Int = 0,
            @SerializedName("userName")
            val userName: String = "",
            @SerializedName("level")
            val level: Int = 0,
            @SerializedName("rank")
            val rank: String = "",
            @SerializedName("nickname")
            val nickname: String = "",
            @SerializedName("username")
            val username: String = "",
        )
    }
}