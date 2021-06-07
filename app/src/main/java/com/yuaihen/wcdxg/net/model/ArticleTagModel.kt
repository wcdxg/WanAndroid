package com.yuaihen.wcdxg.net.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Yuaihen.
 * on 2021/6/7
 */
data class ArticleTagModel(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
) {
}