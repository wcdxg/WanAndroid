package com.yuaihen.policeinfo.net
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.yuaihen.policeinfo.net.ApiService.Companion.getInstance
import com.yuaihen.policeinfo.ui.interf.DownloadCallback
import com.yuaihen.policeinfo.utils.FileUtil
import com.yuaihen.policeinfo.utils.FileUtil.writeFile
import com.yuaihen.policeinfo.utils.LogUtil
import kotlinx.coroutines.*
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
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
        val newUrl = RetrofitUrlManager.getInstance().setUrlNotChange(url)
        launch {
            withContext(Dispatchers.IO) {
                LogUtil.e(TAG, "onSubscribe: 开始下载")
                val response = getInstance().downloadFile(newUrl)
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