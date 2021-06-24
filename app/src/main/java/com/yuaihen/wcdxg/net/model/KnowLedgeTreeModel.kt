package com.yuaihen.wcdxg.net.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KnowLedgeTreeModel(
    @SerializedName("data")
    val `data`: List<Data> = listOf(),
    @SerializedName("errorCode")
    val errorCode: Int = 0,
    @SerializedName("errorMsg")
    val errorMsg: String = ""
) : Parcelable {
    @Parcelize
    data class Data(
        @SerializedName("children")
        val children: List<Data> = listOf(),
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
        val visible: Int = 0
    ) : Parcelable {

    }
}