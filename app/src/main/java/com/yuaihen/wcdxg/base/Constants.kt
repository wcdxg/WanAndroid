package com.yuaihen.wcdxg.base

import android.os.Environment


/**
 * Created by Yuaihen.
 * on 2020/7/23
 * 常量类
 */

object Constants {

    const val Cookie = "cookie"
    const val LOGIN_STATUS = "loginStatus"
    const val URL = "url"
    const val ID = "id"
    const val UNCOLLECT_ORIGINID = "uncollect_originId"
    const val PAGE = "page"
    const val NAME = "name"
    const val LINK = "link"
    const val AUTHOR = "author"
    const val TITLE = "title"
    const val COLLECT = "collect"

    @JvmField
    val IMAGE_CACHE_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/Wcdxg/ImageCache/"

    @JvmField
    val FILE_CACHE_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/Wcdxg/FileCache/"

    @JvmField
    val NET_CACHE_DIRECTORY =
        Environment.getExternalStorageDirectory().path + "/Wcdxg/Cache/"

}