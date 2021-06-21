package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.paging.BaseArticlePagingSource
import com.yuaihen.wcdxg.net.model.ArticleModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/21
 */
class WenDaViewModel : BaseViewModel() {

    private val _wenDaArticleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    val wenDaArticleLiveData = _wenDaArticleLiveData

    fun getWenDaList() {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 3, initialLoadSize = 20)
            ) {
                BaseArticlePagingSource(BaseArticlePagingSource.WENDA_ARTICLE)
            }.flow.cachedIn(this).collectLatest {
                _wenDaArticleLiveData.value = it
            }
        }
    }

}