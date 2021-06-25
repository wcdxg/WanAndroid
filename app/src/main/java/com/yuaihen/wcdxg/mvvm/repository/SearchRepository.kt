package com.yuaihen.wcdxg.mvvm.repository

import com.yuaihen.wcdxg.mvvm.BaseRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.ArticleListModel

/**
 * Created by Yuaihen.
 * on 2021/6/25
 */
class SearchRepository : BaseRepository {

    suspend fun getHotSearchList(): ArticleListModel {
        return ApiManage.getInstance().getSearchHotKey()
    }
}