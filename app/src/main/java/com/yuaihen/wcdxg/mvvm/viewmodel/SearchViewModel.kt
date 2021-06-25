package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.SearchRepository
import com.yuaihen.wcdxg.net.model.ArticleListModel
import com.yuaihen.wcdxg.utils.isSuccess

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
    private val _hotSearchLiveData = MutableLiveData<List<ArticleListModel.Data>>()
    val hotSearchLiveData = _hotSearchLiveData

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
        launch({
            val response = repository.getHotSearchList()
            if (response.errorCode.isSuccess()) {
                _hotSearchLiveData.postValue(response.data)
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