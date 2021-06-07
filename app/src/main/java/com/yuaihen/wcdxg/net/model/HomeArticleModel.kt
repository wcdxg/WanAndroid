package com.yuaihen.wcdxg.net.model


import com.google.gson.annotations.SerializedName

data class HomeArticleModel(
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
        val datas: List<ArticleModel> = listOf(),
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
    )
}