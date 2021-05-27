package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.LoginRegisterRepository
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/5/24
 */
class LoginRegisterViewModel : BaseViewModel() {

    private val repository = LoginRegisterRepository()
    private val _loginLiveData = MutableLiveData(false)
    val loginLiveData = _loginLiveData

    fun login(userName: String, userPassword: String) {
        launch(
            {
                val response = repository.login(userName, userPassword)
                if (response.errorCode.isSuccess()) {
                    _loginLiveData.postValue(
                        true
                    )
                } else {
                    errorLiveData.postValue(response.errorMsg)
                }
            },
            {
                errorLiveData.postValue(it)
            },
            {
                loadingLiveData.postValue(false)
            }
        )
    }


    fun register(userName: String, userPassword: String) {
        launch(
            {
                val response = repository.register(userName, userPassword)
                if (response.errorCode.isSuccess()) {
                    _loginLiveData.postValue(
                        true
                    )
                } else {
                    errorLiveData.postValue(response.errorMsg)
                }
            },
            {
                errorLiveData.postValue(it)
            },
            {
                loadingLiveData.postValue(false)
            }
        )
    }
}