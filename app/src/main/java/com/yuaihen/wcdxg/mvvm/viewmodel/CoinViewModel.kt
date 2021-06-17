package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.paging.CoinRankPagingSource
import com.yuaihen.wcdxg.mvvm.paging.CoinRecordPagingSource
import com.yuaihen.wcdxg.mvvm.repository.CoinRepository
import com.yuaihen.wcdxg.net.model.CoinRecordModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/17
 */
class CoinViewModel : BaseViewModel() {

    private val repository = CoinRepository()
    private val _coinRecordLiveData = MutableLiveData<PagingData<CoinRecordModel.Data.Data>>()
    val coinRecordLiveData = _coinRecordLiveData
    private val _coinRankRecordLiveData = MutableLiveData<PagingData<CoinRecordModel.Data.Data>>()
    val coinRankRecordLiveData = _coinRankRecordLiveData

    /**
     * 获取积分记录
     */
    fun getCoinRecord() {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 1, initialLoadSize = 20)
            ) {
                CoinRecordPagingSource()
            }.flow.cachedIn(this).collectLatest {
                _coinRecordLiveData.value = it
            }
        }
    }

    /**
     * 获取积分排行榜记录
     */
    fun getCoinRankRecord() {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 1, initialLoadSize = 20)
            ) {
                CoinRankPagingSource()
            }.flow.cachedIn(this).collectLatest {
                _coinRankRecordLiveData.value = it
            }
        }
    }
}