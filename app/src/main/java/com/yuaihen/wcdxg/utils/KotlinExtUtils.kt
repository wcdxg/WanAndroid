package com.yuaihen.wcdxg.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

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

fun String.trimHtml(): String {
    this.replace("</p>", "")
    this.replace("<p>", "")
    this.replace("\r", "")
    this.replace("<strong>", "")
    this.replace("</strong>", "")
    return this
}

//@BindingAdapter("isGoneFromStr")
//fun bindIsGone(view: View, str: String? = "") {
//    view.visibility = if (str.isNullOrEmpty()) {
//        View.GONE
//    } else {
//        View.VISIBLE
//    }
//}
//
//
//@BindingAdapter("app:selected")
//fun setSelected(view: View, bool: Boolean) {
//    view.isSelected = bool
//}

