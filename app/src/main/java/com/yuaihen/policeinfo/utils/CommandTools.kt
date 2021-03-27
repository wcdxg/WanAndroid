package com.yuaihen.policeinfo.utils
import android.view.View
import android.widget.ImageView

/**
 * Created by Yuaihen.
 * on 2020/6/16
 * Kotlin扩展函数工具类
 */
fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun String.setGlideUrl(iv: ImageView) {
    GlideUtil.showImageView(iv, this)
}