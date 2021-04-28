package com.yuaihen.wcdxg.ui.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by Yuaihen.
 * on 2021/3/28
 * 自动滚动的TextView,跑马灯效果
 */
class ScrollTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        ellipsize = TextUtils.TruncateAt.MARQUEE;
        marqueeRepeatLimit = -1;
        isSingleLine = true;
    }


    override fun isFocused(): Boolean {
        return true
    }
}