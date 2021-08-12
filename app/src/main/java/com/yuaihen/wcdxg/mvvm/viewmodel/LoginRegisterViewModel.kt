package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.LoginRegisterRepository

/**
 * Created by Yuaihen.
 * on 2021/5/24
 */
class LoginRegisterViewModel : BaseViewModel() {

    private val repository = LoginRegisterRepository()
    private val _loginLiveData = MutableLiveData(false)
    val loginLiveData = _loginLiveData

    fun login(userName: String, userPassword: String) {
        newRequest(
            {
                val response = repository.login(userName, userPassword)
                _loginLiveData.postValue(true)
            }, false
        )
    }


    fun register(userName: String, userPassword: String) {
        newRequest(
            {
                val response = repository.register(userName, userPassword)
                _loginLiveData.postValue(true)
            }, false

        )
    }
}