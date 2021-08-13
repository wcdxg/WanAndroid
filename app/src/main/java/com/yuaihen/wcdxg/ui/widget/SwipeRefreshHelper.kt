package com.yuaihen.wcdxg.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
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

    private var mTouchSlop: Int = 0
    private var mStartY = 0f

    init {
        //下拉主题颜色（最多四种 最少一种）
        setColorSchemeColors(ContextCompat.getColor(context, R.color.bili_bili_pink))
//        /***
//         * 触发移动事件的最小距离，自定义View处理touch事件的时候，
//         * 有的时候需要判断用户是否真的存在movie，系统提供了这样的方法。
//         * 表示滑动的时候，手的移动要大于这个返回的距离值才开始移动控件
//         */
//        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop * 8


        //设置下拉触发距离
        setDistanceToTriggerSync(300)
    }

}