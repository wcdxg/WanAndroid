package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.MyRepository
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class MyViewModel : BaseViewModel() {

    private val repository = MyRepository()
    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess = _logoutSuccess

    /**
     * 退出登录
     */
    fun logout() {
        launch(
            {
                val response = repository.logout()
                if (response.errorCode.isSuccess()) {
                    _logoutSuccess.postValue(true)
                } else {
                    errorLiveData.postValue(response.errorMsg)
                }
            }, {
                errorLiveData.postValue(it)
            }, {
                loadingLiveData.postValue(false)
            }, false

        )
    }
}