package com.yuaihen.wcdxg.mvvm.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.net.model.HomeArticleModel
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/6/21
 * 文章列表页通用的PagingSource
 */
class BaseArticlePagingSource(private val index: Int) : PagingSource<Int, ArticleModel>() {
    companion object {
        const val HOME_ARTICLE = 1
        const val COLLECT_ARTICLE = 2
        const val WENDA_ARTICLE = 3
    }

    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        return state.anchorPosition
            ?.let {
                val anchorPage = state.closestPageToPosition(it)
                anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
            }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        var nextPageNumber = 0
        var response: HomeArticleModel? = null
        try {
            when (index) {
                HOME_ARTICLE -> {
                    //默认加载第0页数据
                    nextPageNumber = params.key ?: 0
                    response = ApiManage.getInstance().getHomeArticleList(nextPageNumber)
                }
                COLLECT_ARTICLE -> {
                    nextPageNumber = params.key ?: 0
                    response = ApiManage.getInstance().getCollectArticleList(nextPageNumber)
                }
                WENDA_ARTICLE -> {
                    //默认加载第1页数据
                    nextPageNumber = params.key ?: 1
                    response = ApiManage.getInstance().getWendaList(nextPageNumber)
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
}