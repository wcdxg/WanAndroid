package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.paging.BaseArticlePagingSource
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.exception.ErrorUtil
import com.yuaihen.wcdxg.net.exception.MyException
import com.yuaihen.wcdxg.net.model.ArticleModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/16
 */
class MyCollectViewModel : BaseViewModel() {

    private val _collectArticleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    val collectArticleLiveData = _collectArticleLiveData
    private val _collectWebSiteLiveData = MutableLiveData<List<ArticleModel>>()
    val collectWebSiteLiveData = _collectWebSiteLiveData
    val unCollectStatus = MutableLiveData(false)
    private val _knowledgeArticleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    val knowledgeArticleLiveData = _knowledgeArticleLiveData

    /**
     * 获取收藏的文章列表  页码：拼接在链接中，从0开始。
     */
    fun getCollectArticle() {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 5, initialLoadSize = 20)
            ) {
                BaseArticlePagingSource(BaseArticlePagingSource.COLLECT_ARTICLE)
            }.flow.cachedIn(this).collectLatest {
                _collectArticleLiveData.value = it
            }
        }
    }

    /**
     * 获取收藏的网站
     */
    fun getCollectWebSite() {
        newRequest({
            val response = ApiManage.getInstance().getCollectWebSiteList()
            _collectWebSiteLiveData.postValue(response.data)
        }, false)
    }

    /**
     * 取消收藏文章-从收藏页面
     */
    fun unCollectById(id: Int, originId: Int) {
        try {
            newRequest({
                val response = ApiManage.getInstance().unCollectById(id, originId)
                when (response.errorCode) {
                    ErrorUtil.CODE_RESPONSE_SUCCESS -> {
                        unCollectStatus.postValue(true)
                    }
                    ErrorUtil.CODE_LOGIN_FAIL -> {
                        errorLiveData.postValue(
                            MyException(
                                ErrorUtil.CODE_LOGIN_FAIL,
                                ErrorUtil.CODE_LOGIN_FAIL_MSG
                            )
                        )
                        unLoginStateLiveData.postValue(true)
                    }
                    else -> {
                        errorLiveData.postValue(MyException(response.errorCode, response.errorMsg))
                    }
                }
            }, false)
        } catch (e: Exception) {
            errorLiveData.postValue(ErrorUtil.getErrorMsg(e))
        }

    }

    /**
     * 取消收藏网站-从收藏页面
     */
    fun deleteCollectWebSite(id: Int) {
        try {
            newRequest({
                val response = ApiManage.getInstance().deleteCollectWebSite(id)
                when (response.errorCode) {
                    ErrorUtil.CODE_RESPONSE_SUCCESS -> {
                        unCollectStatus.postValue(true)
                    }
                    ErrorUtil.CODE_LOGIN_FAIL -> {
                        errorLiveData.postValue(
                            MyException(
                                ErrorUtil.CODE_LOGIN_FAIL,
                                ErrorUtil.CODE_LOGIN_FAIL_MSG
                            )
                        )
                        unLoginStateLiveData.postValue(true)
                    }
                    else -> {
                        errorLiveData.postValue(MyException(response.errorCode, response.errorMsg))
                    }
                }
            }, false)
        } catch (e: Exception) {
            errorLiveData.postValue(ErrorUtil.getErrorMsg(e))
        }
    }

    /**
     * 根据cid获取知识体系下的文章
     */
    fun getKnowledgeArticleByCid(cid: Int) {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 5, initialLoadSize = 20)
            ) {
                BaseArticlePagingSource(BaseArticlePagingSource.KNOWLEDGE_ARTICLE, cid)
            }.flow.cachedIn(this).collectLatest {
                _knowledgeArticleLiveData.value = it
            }
        }
    }

}