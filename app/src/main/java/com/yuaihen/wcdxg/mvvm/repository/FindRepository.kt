package com.yuaihen.wcdxg.mvvm.repository

import com.yuaihen.wcdxg.mvvm.BaseRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.KnowLedgeTreeModel
import com.yuaihen.wcdxg.net.model.NavigationModel
import com.yuaihen.wcdxg.net.model.OfficialAccountsModel

/**
 * Created by Yuaihen.
 * on 2021/6/23
 */
class FindRepository : BaseRepository {

    suspend fun getKnowledgeTree(): KnowLedgeTreeModel {
        return ApiManage.getInstance().getKnowledgeTree()
    }

    suspend fun getNavigationData(): NavigationModel {
        return ApiManage.getInstance().getNavigationData()
    }

    suspend fun getOfficialAccounts(): OfficialAccountsModel {
        return ApiManage.getInstance().getOfficialAccounts()
    }

    suspend fun getProjectTree(): OfficialAccountsModel {
        return ApiManage.getInstance().getProjectTree()
    }
}