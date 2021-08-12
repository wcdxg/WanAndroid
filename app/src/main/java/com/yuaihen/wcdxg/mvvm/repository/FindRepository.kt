package com.yuaihen.wcdxg.mvvm.repository

import com.yuaihen.wcdxg.mvvm.BaseRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.BaseResponse
import com.yuaihen.wcdxg.net.model.ArticleListModel

/**
 * Created by Yuaihen.
 * on 2021/6/23
 */
class FindRepository : BaseRepository {

    suspend fun getKnowledgeTree(): BaseResponse<List<ArticleListModel>> {
        return ApiManage.getInstance().getKnowledgeTree()
    }

    suspend fun getNavigationData(): BaseResponse<List<ArticleListModel>> {
        return ApiManage.getInstance().getNavigationData()
    }

    suspend fun getOfficialAccounts(): BaseResponse<List<ArticleListModel>> {
        return ApiManage.getInstance().getOfficialAccounts()
    }

    suspend fun getProjectTree(): BaseResponse<List<ArticleListModel>> {
        return ApiManage.getInstance().getProjectTree()
    }
}