package com.yuaihen.wcdxg.net.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleListModel(

    @SerializedName("children")
    val children: List<ArticleListModel> = listOf(),
    @SerializedName("courseId")
    val courseId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("parentChapterId")
    val parentChapterId: Int = 0,
    @SerializedName("userControlSetTop")
    val userControlSetTop: Boolean = false,
    @SerializedName("visible")
    val visible: Int = 0,
    @SerializedName("link")
    val link: String? = "",
    @SerializedName("articles")
    val articles: List<ArticleModel> = listOf(),
    @SerializedName("cid")
    val cid: Int = 0,
) : Parcelable
