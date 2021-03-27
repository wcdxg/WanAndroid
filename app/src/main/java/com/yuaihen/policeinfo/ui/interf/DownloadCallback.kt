package com.yuaihen.policeinfo.ui.interf

/**
 * Created by Yuaihen.
 * on 2020/6/18
 * 文件下载回调
 */
interface DownloadCallback {

    fun onStart()

    fun onProgress(progress: Int)

    fun onSuccess(filePath: String, tag: Int)

    fun onFail(errorMsg: String, tag: Int = -1)
}