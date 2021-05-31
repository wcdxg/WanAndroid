package com.yuaihen.wcdxg.ui.interf

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/**
 * Created by Yuaihen.
 * on 2021/5/31
 * 监听AppBar滚动状态
 */
abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    enum class State {
        //Appbar的三种状态 展开 折叠 介于两者之间
        EXPANDED, COLLAPSED, IDLE
    }

    private var currentState = State.IDLE
    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        when {
            verticalOffset == 0 -> {
                //折叠状态
                if (currentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED)
                }
                currentState = State.EXPANDED
            }
            abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                if (currentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED)
                }
                currentState = State.COLLAPSED
            }
            else -> {
                if (currentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE)
                }
                currentState = State.IDLE
            }
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}