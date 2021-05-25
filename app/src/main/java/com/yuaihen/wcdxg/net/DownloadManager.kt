package com.yuaihen.wcdxg.net

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.yuaihen.wcdxg.net.ApiService.Companion.getInstance
import com.yuaihen.wcdxg.ui.interf.DownloadCallback
import com.yuaihen.wcdxg.utils.FileUtil
import com.yuaihen.wcdxg.utils.FileUtil.writeFile
import com.yuaihen.wcdxg.utils.LogUtil
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

/**
 * Created by Yuaihen.
 * on 2020/6/10
 */
object DownloadManager : CoroutineScope, LifecycleObserver {

    val TAG = "NetManager"

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    /**
     * 下载文件
     */
    fun downloadFile(url: String, callback: DownloadCallback?, tag: Int = -1) {
        var filePath = ""
        var size: Long = 0
        callback?.onStart()
        //避免BaseUrl更改下载地址被替换hosts
        launch {
            withContext(Dispatchers.IO) {
                LogUtil.e(TAG, "onSubscribe: 开始下载")
                val response = getInstance().downloadFile(url)
                size = response.contentLength()
                val fileName = FileUtil.getFileName(url)
                val file = File(FileUtil.getFileTypePath(fileName) + fileName)
                filePath = file.absolutePath
                writeFile(response.byteStream(), file, size, callback)
                callback?.onSuccess(filePath, tag);
                LogUtil.e(TAG, "onComplete: 下载完成")
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancel() {
        job.cancel()
    }
}