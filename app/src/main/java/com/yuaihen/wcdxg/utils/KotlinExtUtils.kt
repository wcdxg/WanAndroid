package com.yuaihen.wcdxg.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

/**
 * Created by Yuaihen.
 * on 2020/6/16
 * Kotlin扩展函数工具类
 */
fun View.gone() {
    if (!this.isGone) {
        this.visibility = View.GONE
    }
}

fun View.visible() {
    if (!this.isVisible) {
        this.visibility = View.VISIBLE
    }
}

fun View.invisible() {
    if (!this.isInvisible) {
        this.visibility = View.INVISIBLE
    }
}

fun String.loadIntoImage(iv: ImageView) {
    GlideUtil.showImageView(iv, this)
}

@BindingAdapter("isVisible")
fun bindIsVisible(view: View, visible: Boolean) {
    view.isVisible = visible
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

