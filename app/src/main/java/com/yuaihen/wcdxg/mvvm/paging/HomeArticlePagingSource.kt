package com.yuaihen.wcdxg.mvvm.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yuaihen.wcdxg.net.ApiService
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/5/28
 */
class HomeArticlePagingSource : PagingSource<Int, ArticleModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        try {
            //默认加载第0页数据
            val nextPageNumber = params.key ?: 0
            val response = ApiService.getInstance().getHomeArticleList(nextPageNumber)
            if (response.errorCode.isSuccess()) {
                return LoadResult.Page(
                    data = response.data.datas,
                    prevKey = null,
                    nextKey = if (nextPageNumber == response.data.pageCount || response.data.over) {
                        null
                    } else {
                        response.data.curPage
                    }
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

        return LoadResult.Error(Error("error"))
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        return state.anchorPosition
            ?.let {
                val anchorPage = state.closestPageToPosition(it)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
    }
}