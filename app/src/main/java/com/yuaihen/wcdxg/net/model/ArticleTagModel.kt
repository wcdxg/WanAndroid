package com.yuaihen.wcdxg.net.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Yuaihen.
 * on 2021/6/7
 */
@Parcelize
data class ArticleTagModel(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
) : Parcelable {
}