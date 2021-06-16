package com.yuaihen.wcdxg.ui.interf

/**
 * Created by Yuaihen.
 * on 2021/6/7
 */
interface OnCollectClickListener {

    fun onCollect(id: Int)

    fun unCollect(id: Int, originId: Int = -1, position: Int = 0)

}