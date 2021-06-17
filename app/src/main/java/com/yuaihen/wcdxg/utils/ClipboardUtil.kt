package com.yuaihen.wcdxg.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Created by Yuaihen.
 * on 2021/6/17
 * 剪切板管理器
 */
object ClipboardUtil {

    fun copyStrToClipboard(context: Context, str: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Label", str)
        cm.setPrimaryClip(clipData)
    }
}