package com.yuaihen.wcdxg.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yuaihen.wcdxg.R

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * SwipeRefreshLayout管理
 */
class SwipeRefreshHelper(context: Context, attrs: AttributeSet? = null) :
    SwipeRefreshLayout(context, attrs) {

    init {
        //下拉主题颜色（最多四种 最少一种）
        setColorSchemeColors(ContextCompat.getColor(context, R.color.bili_bili_pink))
    }

}