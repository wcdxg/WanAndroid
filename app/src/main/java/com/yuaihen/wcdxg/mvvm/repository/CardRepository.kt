package com.yuaihen.wcdxg.mvvm.repository

import com.yuaihen.wcdxg.mvvm.BaseRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.HomeArticleModel
import com.yuaihen.wcdxg.net.model.KnowLedgeTreeModel

/**
 * Created by Yuaihen.
 * on 2021/6/23
 */
class CardRepository : BaseRepository {

    suspend fun getKnowledgeTree(): KnowLedgeTreeModel {
        return ApiManage.getInstance().getKnowledgeTree()
    }

    suspend fun getNavPage(): HomeArticleModel {
        return ApiManage.getInstance().getNaviData()
    }

    suspend fun getOfficialAccounts() {

    }

    suspend fun getProject() {

    }
}