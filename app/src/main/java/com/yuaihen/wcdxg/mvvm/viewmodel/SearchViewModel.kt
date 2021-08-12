package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.paging.BaseArticlePagingSource
import com.yuaihen.wcdxg.mvvm.repository.SearchRepository
import com.yuaihen.wcdxg.net.model.ArticleListModel
import com.yuaihen.wcdxg.net.model.ArticleModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/25
 */
class SearchViewModel : BaseViewModel() {
    private val repository = SearchRepository()
    private val _historySearchCount = MutableLiveData(0)
    val historySearchCount: LiveData<Int> = _historySearchCount
    private val _isExpandLiveData = MutableLiveData(true)
    var isExpandLiveData: LiveData<Boolean> = _isExpandLiveData
    private val _hotSearchLiveData = MutableLiveData<List<ArticleListModel>>()
    val hotSearchLiveData = _hotSearchLiveData
    private val _searchArticleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    val searchArticleLiveData = _searchArticleLiveData

    fun setExpand(expand: Boolean) {
        _isExpandLiveData.value = expand
    }

    fun setHistorySearchCount(count: Int) {
        _historySearchCount.value = count
    }

    /**
     * 热门搜索记录
     */
    fun getHotSearchList() {
        newRequest({
            val response = repository.getHotSearchList()
            _hotSearchLiveData.postValue(response.data!!)
        }, false)
    }

    /**
     * 获取搜索结果
     */
    fun getSearchResult(key: String) {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 3, initialLoadSize = 20)
            ) {
                BaseArticlePagingSource(BaseArticlePagingSource.SEARCH_ARTICLE, searchKey = key)
            }.flow.cachedIn(this).collectLatest {
                _searchArticleLiveData.value = it
            }
        }
    }

}