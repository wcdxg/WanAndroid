package com.yuaihen.wcdxg.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yuaihen.
 * on 2020/12/16
 */
abstract class BaseViewModel : ViewModel() {

    open var isShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    open var errorMessage: MutableLiveData<String> = MutableLiveData()
}