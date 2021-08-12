package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.paging.BaseArticlePagingSource
import com.yuaihen.wcdxg.mvvm.repository.HomeRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.net.model.BannerModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class HomeViewModel : BaseViewModel() {

    private val repository = HomeRepository()
    private val _bannerLiveData = MutableLiveData<List<BannerModel>>()
    val bannerLiveData = _bannerLiveData
    private val _articleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    val articleLiveData = _articleLiveData
    private val _topArticleLiveData = MutableLiveData<List<ArticleModel>>()
    val topArticleLiveData = _topArticleLiveData

    /**
     * 获取轮播图
     */
    fun getBanner() {
        newRequest(
            {
                val response = repository.getBanner()
                _bannerLiveData.postValue(response.data ?: arrayListOf())
            }, false
        )
    }

    /**
     * 获取首页文章列表 页码从0开始
     */
    fun getArticle() {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 3, initialLoadSize = 20)
            ) {
                BaseArticlePagingSource(BaseArticlePagingSource.HOME_ARTICLE)
            }.flow.cachedIn(this).collectLatest {
                _articleLiveData.value = it
            }
        }
    }

    /**
     * 获取置顶文章列表
     */
    fun getArticleTop() {
        newRequest({
            val response = ApiManage.getInstance().getTopArticleList()
            _topArticleLiveData.postValue(response.data)
        }, false)
    }


}