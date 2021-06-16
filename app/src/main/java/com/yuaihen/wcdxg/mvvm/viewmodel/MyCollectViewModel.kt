package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.paging.CollectPagingSource
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.utils.isSuccess
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/16
 */
class MyCollectViewModel : BaseViewModel() {

    private val _collectArticleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    val collectArticleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    private val _collectWebSiteLiveData = MutableLiveData<List<ArticleModel>>()
    val collectWebSiteLiveData = MutableLiveData<List<ArticleModel>>()

    /**
     * 收藏的文章列表  页码：拼接在链接中，从0开始。
     */
    fun getCollectPage() {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 1, initialLoadSize = 20)
            ) {
                CollectPagingSource()
            }.flow.cachedIn(this).collectLatest {
                _collectArticleLiveData.value = it
            }
        }
    }

    /**
     * 收藏的网站
     */
    fun getCollectWebSite() {
        launch({
            val response = ApiManage.getInstance().getCollectWebSiteList()
            if (response.errorCode.isSuccess()) {
                _collectWebSiteLiveData.postValue(response.data.datas)
            } else {
                errorLiveData.postValue(response.errorMsg)
            }
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

}