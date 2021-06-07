package com.yuaihen.wcdxg.mvvm.repository

import com.yuaihen.wcdxg.mvvm.BaseRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.BaseModel

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class MyRepository : BaseRepository {

    suspend fun logout(): BaseModel {
        return ApiManage.getInstance().logout()
    }
}