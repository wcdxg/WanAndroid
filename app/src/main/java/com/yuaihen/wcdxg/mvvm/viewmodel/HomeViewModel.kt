package com.yuaihen.wcdxg.mvvm.viewmodel

import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.HomeRepository

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class HomeViewModel : BaseViewModel() {

    private val repository = HomeRepository()

    fun getBanner() {

    }
}