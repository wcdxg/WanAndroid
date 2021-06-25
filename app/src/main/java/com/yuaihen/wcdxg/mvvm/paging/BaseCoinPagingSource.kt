package com.yuaihen.wcdxg.mvvm.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.CoinRecordModel
import com.yuaihen.wcdxg.utils.isSuccess


/**
 * Created by Yuaihen.
 * on 2021/5/28
 * 积分获取记录
 */
class BaseCoinPagingSource(private val index: Int) :
    PagingSource<Int, CoinRecordModel.Data.CoinData>() {

    companion object {
        const val COIN_RANK = 1
        const val COIN_RECORD = 2
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinRecordModel.Data.CoinData> {
        var response: CoinRecordModel? = null
        //默认加载第1页数据
        val nextPageNumber = params.key ?: 1
        try {
            when (index) {
                COIN_RANK -> {
                    response = ApiManage.getInstance().getCoinRank(nextPageNumber)
                }
                COIN_RECORD -> {
                    response = ApiManage.getInstance().getCoinRecord(nextPageNumber)
                }
                else -> {
                }
            }

            response?.let {
                if (it.errorCode.isSuccess()) {
                    return LoadResult.Page(
                        data = it.data.datas,
                        prevKey = null,
                        nextKey = if (nextPageNumber == it.data.pageCount || it.data.over) {
                            null
                        } else {
                            nextPageNumber + 1
                        }
                    )
                }
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

        return LoadResult.Error(Error("error"))
    }

    override fun getRefreshKey(state: PagingState<Int, CoinRecordModel.Data.CoinData>): Int? {
        return state.anchorPosition
            ?.let {
                val anchorPage = state.closestPageToPosition(it)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
    }
}