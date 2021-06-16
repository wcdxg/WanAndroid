package com.yuaihen.wcdxg.mvvm.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/5/28
 * 收藏的文章列表 頁碼從0開始
 */
class CollectPagingSource : PagingSource<Int, ArticleModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        try {
            //默认加载第0页数据
            val nextPageNumber = params.key ?: 0
            val response = ApiManage.getInstance().getCollectArticleList(nextPageNumber)
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