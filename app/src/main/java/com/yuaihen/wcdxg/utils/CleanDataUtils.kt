package com.yuaihen.wcdxg.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.math.BigDecimal

/**
 * Created by Yuaihen.
 * on 2021/6/18
 * 缓存清理
 */
object CleanDataUtils {
    /**
     * 获取缓存值
     */
    fun getTotalCacheSize(context: Context): String {
        var cacheSize: Long = getFolderSize(context.cacheDir)
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.externalCacheDir)
        }
        return getFormatSize(cacheSize.toDouble())
    }

    /**
     * 清除所有缓存
     */
    fun clearAllCache(context: Context) {
        deleteDir(context.cacheDir)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(context.externalCacheDir)
            //有网页清理时注意排错，是否存在/data/data/应用package目录下找不到database文件夹的问题
            try {
                context.deleteDatabase("webview.db")
                context.deleteDatabase("webviewCache.db")
            } catch (e: Exception) {
                Log.d("hello", "clearAllCache: ${e.message}")
            }
        }
    }

    /**
     * 删除某个文件
     */
    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children: Array<String> = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            return dir.delete()
        }
        return dir?.delete() ?: false
    }

    /**
     * 获取文件
     */
    private fun getFolderSize(file: File?): Long {
        var size: Long = 0
        file?.let {
            val fileList: Array<File> = it.listFiles()
            if (fileList.isNotEmpty()) {
                for (i in fileList.indices) {
                    // 如果下面还有文件
                    size += if (fileList[i].isDirectory) {
                        getFolderSize(fileList[i])
                    } else {
                        fileList[i].length()
                    }
                }
            }

        }
        return size
    }

    /**
     * 格式化单位
     */
    private fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        val megaByte = kiloByte / 1024
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(megaByte.toString())
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString().toString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(gigaByte.toString())
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString().toString() + "GB"
        }
        val result4: BigDecimal = BigDecimal.valueOf(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
            .toString() + "TB"
    }

}