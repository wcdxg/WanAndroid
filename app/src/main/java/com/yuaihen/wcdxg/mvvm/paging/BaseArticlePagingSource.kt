package com.yuaihen.wcdxg.mvvm.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.BaseResponse
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.net.model.HomeArticleModel

/**
 * Created by Yuaihen.
 * on 2021/6/21
 * 文章列表页通用的PagingSource
 */
class BaseArticlePagingSource(
    private val index: Int,
    private val id: Int = 0,
    private val searchKey: String? = ""
) :
    PagingSource<Int, ArticleModel>() {
    companion object {
        const val HOME_ARTICLE = 1      //首页文章列表
        const val COLLECT_ARTICLE = 2   //我的收藏
        const val WENDA_ARTICLE = 3     //每日问答
        const val KNOWLEDGE_ARTICLE = 4 //知识体系对应tag文章
        const val WX_ARTICLE = 5        //微信公众号文章
        const val PROJECT_ARTICLE = 6        //项目分类下的文章
        const val SEARCH_ARTICLE = 7        //搜索结果
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
        var response: BaseResponse<HomeArticleModel>? = null
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
                KNOWLEDGE_ARTICLE -> {
                    nextPageNumber = params.key ?: 0
                    response = ApiManage.getInstance().getKnowledgeArticleByCid(nextPageNumber, id)
                }
                WX_ARTICLE -> {
                    nextPageNumber = params.key ?: 1
                    response = ApiManage.getInstance().getWxArticle(nextPageNumber, id)
                }
                PROJECT_ARTICLE -> {
                    nextPageNumber = params.key ?: 1
                    response = ApiManage.getInstance().getProjectArticle(nextPageNumber, id)
                }
                SEARCH_ARTICLE -> {
                    nextPageNumber = params.key ?: 0
                    response = ApiManage.getInstance().getSearchResult(nextPageNumber, searchKey!!)
                }
                else -> {

                }
            }

            response?.let {
                return LoadResult.Page(
                    data = it.data!!.datas,
                    prevKey = null,
                    nextKey = if (nextPageNumber == it.data!!.pageCount || it.data!!.over) {
                        null
                    } else {
                        nextPageNumber + 1
                    }
                )
            }
        } catch (e: Exception) {
            Log.d("hello", "BaseArticlePagingSource error: $e")
            return LoadResult.Error(e)
        }
        return LoadResult.Error(Error("error"))
    }
}