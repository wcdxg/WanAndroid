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
import com.yuaihen.wcdxg.net.model.BannerModel
import com.yuaihen.wcdxg.net.model.HomeArticleModel
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
    private val _articleLiveData = MutableLiveData<PagingData<HomeArticleModel.Data.Data>>()
    val articleLiveData = _articleLiveData

    /**
     * 获取轮播图
     */
    fun getBanner() {
        val a = "sa"
        a.isNullOrBlank()
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
                loadingLiveData.postValue(false)

            }
        )
    }

    /**
     * 获取首页文章列表 页码从0开始
     */
    fun getArticle() {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 0)
            ) {
                HomeArticlePagingSource()
            }.flow.cachedIn(this).collectLatest {
                _articleLiveData.value = it
            }
        }

    }
}