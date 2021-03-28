package com.yuaihen.policeinfo.base

import android.os.Environment


/**
 * Created by Yuaihen.
 * on 2020/7/23
 * 常量类
 */

object Constants {

    const val MAC = "mac"
    const val TOKEN = "token"
    const val VERSION = "version"
    const val USER_ID = "userId"

    @JvmField
    val IMAGE_CACHE_DIRECTORY =
        Environment.getExternalStorageDirectory().toString() + "/zhzg/imageCache/"

    @JvmField
    val FILE_CACHE_DIRECTORY =
        Environment.getExternalStorageDirectory().toString() + "/zhzg/fileCache/"

}