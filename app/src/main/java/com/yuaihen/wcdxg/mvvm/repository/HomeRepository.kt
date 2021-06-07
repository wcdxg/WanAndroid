package com.yuaihen.wcdxg.mvvm.repository

import com.yuaihen.wcdxg.mvvm.BaseRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.BannerModel

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class HomeRepository : BaseRepository {

    suspend fun getBanner(): BannerModel {
        return ApiManage.getInstance().getBanner()
    }


}