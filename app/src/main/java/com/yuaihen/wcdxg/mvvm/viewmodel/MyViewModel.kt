package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.MyRepository
import com.yuaihen.wcdxg.net.model.UserInfoModel

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class MyViewModel : BaseViewModel() {

    private val repository = MyRepository()
    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess = _logoutSuccess
    private var _userInfoLiveData = MutableLiveData<UserInfoModel>()
    val userInfoLiveData = _userInfoLiveData

    /**
     * 退出登录
     */
    fun logout() {
        newRequest(
            {
                val response = repository.logout()
                _logoutSuccess.postValue(true)
            }, false

        )
    }

    /**
     * 获取用户个人信息
     */
    fun getUserInfo() {
        newRequest({
            val response = repository.getUserInfo()
            _userInfoLiveData.postValue(response.data!!)
        }, false)
    }
}