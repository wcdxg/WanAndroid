package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.paging.HomeArticlePagingSource
import com.yuaihen.wcdxg.mvvm.repository.HomeRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.ApiService
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.net.model.BannerModel
import com.yuaihen.wcdxg.utils.LogUtil
import com.yuaihen.wcdxg.utils.isSuccess
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class HomeViewModel : BaseViewModel() {

    private val repository = HomeRepository()
    private val _bannerLiveData = MutableLiveData<List<BannerModel.Data>>()
    val bannerLiveData = _bannerLiveData
    private val _articleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    val articleLiveData = _articleLiveData
    private val _topArticleLiveData = MutableLiveData<List<ArticleModel>>()
    val topArticleLiveData = _topArticleLiveData

    /**
     * 获取轮播图
     */
    fun getBanner() {
        launch(
            {
                val response = repository.getBanner()
                if (response.errorCode.isSuccess()) {
                    val data = response.data
                    _bannerLiveData.postValue(data)
                } else {
                    errorLiveData.postValue(response.errorMsg)
                }
            }, {
                errorLiveData.postValue(it)
            }, {
//                loadingLiveData.postValue(false)
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
                HomeArticlePagingSource()
            }.flow.cachedIn(this).collectLatest {
                _articleLiveData.value = it
            }
        }
    }

    /**
     * 获取置顶文章列表
     */
    fun getArticleTop() {
        launch({
            val response = ApiManage.getInstance().getTopArticleList()
            if (response.errorCode.isSuccess()) {
                _topArticleLiveData.postValue(response.data)
            } else {
                errorLiveData.postValue(response.errorMsg)
                LogUtil.d("hello", "getArticleTop: request error")
            }
        }, {
            errorLiveData.postValue(it)
        }, {

        }, isShowLoading = false)
    }

    /**
     * 收藏站内文章
     */
    fun collectArticle(id: Int) {
        launch({
            val response = ApiManage.getInstance().collectArticle(id)
            when {
                response.errorCode.isSuccess() -> {
                    errorLiveData.postValue("收藏成功")
                }
                response.errorCode == ApiService.UN_LOGIN -> {
                    errorLiveData.postValue(response.errorMsg)
                    unLoginStateLiveData.postValue(true)
                }
                else -> {
                    errorLiveData.postValue(response.errorMsg)
                }
            }
        }, {
            errorLiveData.postValue(it)
        }, {

        }, isShowLoading = false)
    }

    /**
     * 取消收藏-从文章列表页
     */
    fun uncollectByOriginId(id: Int) {
        launch({
            val response = ApiManage.getInstance().uncollectByOriginId(id)
            when {
                response.errorCode.isSuccess() -> {
                    errorLiveData.postValue("取消收藏成功")
                }
                response.errorCode == ApiService.UN_LOGIN -> {
                    errorLiveData.postValue(response.errorMsg)
                    unLoginStateLiveData.postValue(true)
                }
                else -> {
                    errorLiveData.postValue(response.errorMsg)
                }
            }
        }, {
            errorLiveData.postValue(it)
        }, {

        }, isShowLoading = false)
    }
}