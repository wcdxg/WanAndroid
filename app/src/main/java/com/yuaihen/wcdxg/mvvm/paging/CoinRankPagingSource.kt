package com.yuaihen.wcdxg.mvvm.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.CoinRecordModel
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * 积分排行榜获取记录
 */
class CoinRankPagingSource : PagingSource<Int, CoinRecordModel.Data.Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinRecordModel.Data.Data> {
        try {
            //默认加载第1页数据
            val nextPageNumber = params.key ?: 1
            val response = ApiManage.getInstance().getCoinRank(nextPageNumber)
            if (response.errorCode.isSuccess()) {
                return LoadResult.Page(
                    data = response.data.datas,
                    prevKey = null,
                    nextKey = if (nextPageNumber == response.data.pageCount || response.data.over) {
                        null
                    } else {
                        nextPageNumber + 1
                    }
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

        return LoadResult.Error(Error("error"))
    }

    override fun getRefreshKey(state: PagingState<Int, CoinRecordModel.Data.Data>): Int? {
        return state.anchorPosition
            ?.let {
                val anchorPage = state.closestPageToPosition(it)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
    }
}