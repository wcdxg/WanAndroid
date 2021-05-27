package com.yuaihen.wcdxg.base

import android.os.Environment


/**
 * Created by Yuaihen.
 * on 2020/7/23
 * 常量类
 */

object Constants {

    const val Cookie = "cookie"

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