package com.yuaihen.wcdxg.mvvm.repository

import com.yuaihen.wcdxg.mvvm.BaseRepository
import com.yuaihen.wcdxg.net.ApiService
import com.yuaihen.wcdxg.net.BaseResponse
import com.yuaihen.wcdxg.net.model.LoginModel

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class MyRepository : BaseRepository {

    suspend fun logout(): BaseResponse<LoginModel> {
        return ApiService.getInstance().logout()
    }
}