package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.MyRepository
import com.yuaihen.wcdxg.net.model.UserInfoModel
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class MyViewModel : BaseViewModel() {

    private val repository = MyRepository()
    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess = _logoutSuccess
    private var _userInfoLiveData = MutableLiveData<UserInfoModel.Data>()
    val userInfoLiveData = _userInfoLiveData

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

    /**
     * 获取用户个人信息
     */
    fun getUserInfo() {
        launch({
            val response = repository.getUserInfo()
            if (response.errorCode.isSuccess()) {
                _userInfoLiveData.postValue(response.data)
            } else {
                errorLiveData.postValue(response.errorMsg)
            }
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }
}