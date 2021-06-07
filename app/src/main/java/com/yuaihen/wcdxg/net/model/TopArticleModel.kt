package com.yuaihen.wcdxg.net.model


import com.google.gson.annotations.SerializedName

data class TopArticleModel(
    @SerializedName("data")
    val `data`: List<ArticleModel> = arrayListOf(),
    @SerializedName("errorCode")
    val errorCode: Int = 0,
    @SerializedName("errorMsg")
    val errorMsg: String = ""
) {

}