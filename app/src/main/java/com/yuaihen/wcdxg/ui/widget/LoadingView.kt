package com.yuaihen.wcdxg.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.yuaihen.wcdxg.R

/**
 * Created by Yuaihen.
 * on 2021/6/15
 * LoadingView 加载圈
 */
class LoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this)
    }
}