package com.yuaihen.wcdxg.mvvm.repository

import com.yuaihen.wcdxg.mvvm.BaseRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.BaseResponse
import com.yuaihen.wcdxg.net.model.UserInfoModel

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class MyRepository : BaseRepository {

    suspend fun logout(): BaseResponse<String> {
        return ApiManage.getInstance().logout()
    }

    suspend fun getUserInfo(): BaseResponse<UserInfoModel> {
        return ApiManage.getInstance().getUserInfo()
    }
}