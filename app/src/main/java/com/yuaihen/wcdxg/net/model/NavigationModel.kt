package com.yuaihen.wcdxg.net.model


import com.google.gson.annotations.SerializedName

data class NavigationModel(
    @SerializedName("data")
    val `data`: List<Data> = listOf(),
    @SerializedName("errorCode")
    val errorCode: Int = 0,
    @SerializedName("errorMsg")
    val errorMsg: String = ""
) {
    data class Data(
        @SerializedName("articles")
        val articles: List<ArticleModel> = listOf(),
        @SerializedName("cid")
        val cid: Int = 0,
        @SerializedName("name")
        val name: String = ""
    )
}