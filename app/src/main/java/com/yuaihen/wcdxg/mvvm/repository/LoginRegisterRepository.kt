package com.yuaihen.wcdxg.mvvm.repository

import com.yuaihen.wcdxg.mvvm.BaseRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.BaseResponse
import com.yuaihen.wcdxg.net.model.LoginModel

/**
 * Created by Yuaihen.
 * on 2021/5/24
 */
class LoginRegisterRepository : BaseRepository {

    suspend fun login(userName: String, userPassword: String): BaseResponse<LoginModel> {
        return ApiManage.getInstance().login(userName, userPassword)
    }

    suspend fun register(userName: String, userPassword: String): BaseResponse<LoginModel> {
        return ApiManage.getInstance().register(userName, userPassword, userPassword)
    }
}