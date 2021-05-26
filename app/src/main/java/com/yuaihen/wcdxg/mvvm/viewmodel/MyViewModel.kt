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

    fun logout() {
        launch(
            {
                val response = repository.logout()
                if (response.isSuccess()) {
                    _logoutSuccess.postValue(true)
                } else {
                    _logoutSuccess.postValue(false)
                }
            }, {
                errorLiveData.postValue(it)
            }, {
                loadingLiveData.postValue(false)
            }, false

        )


    }
}