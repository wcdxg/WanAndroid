package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.paging.BaseArticlePagingSource
import com.yuaihen.wcdxg.mvvm.repository.FindRepository
import com.yuaihen.wcdxg.net.model.ArticleModel
import com.yuaihen.wcdxg.net.model.ArticleListModel
import com.yuaihen.wcdxg.utils.isSuccess
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Yuaihen.
 * on 2021/6/23
 */
class FindViewModel : BaseViewModel() {

    private val repository = FindRepository()
    private val _knowledgeLiveData = MutableLiveData<List<ArticleListModel.Data>>()
    val knowledgeLiveData = _knowledgeLiveData
    private val _navigationLiveData = MutableLiveData<List<ArticleListModel.Data>>()
    val navigationLiveData = _navigationLiveData
    private val _officialAccountLiveData = MutableLiveData<List<ArticleListModel.Data>>()
    val officialAccountLiveData = _officialAccountLiveData
    private val _wxArticleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    val wxArticleLiveData = _wxArticleLiveData
    private val _projectTreeList = MutableLiveData<List<ArticleListModel.Data>>()
    val projectTreeList = _projectTreeList
    private val _projectArticleLiveData = MutableLiveData<PagingData<ArticleModel>>()
    val projectArticleLiveData = _projectArticleLiveData

    /**
     * 获取知识体系下的所有分类和子类
     */
    fun getKnowledgeTree() {
        launch({
            val response = repository.getKnowledgeTree()
            if (response.errorCode.isSuccess()) {
                _knowledgeLiveData.postValue(response.data)
            } else {
                errorLiveData.postValue(response.errorMsg)
            }
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

    /**
     * 获取导航栏目内容
     */
    fun getNavPage() {
        launch({
            val response = repository.getNavigationData()
            if (response.errorCode.isSuccess()) {
                _navigationLiveData.postValue(response.data)
            } else {
                errorLiveData.postValue(response.errorMsg)
            }
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

    /**
     * 获取公众号列表
     */
    fun getOfficialAccounts() {
        launch({
            val response = repository.getOfficialAccounts()
            if (response.errorCode.isSuccess()) {
                _officialAccountLiveData.postValue(response.data)
            } else {
                errorLiveData.postValue(response.errorMsg)
            }
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

    /**
     * 获取微信公众号文章列表
     */
    fun getWxArticleList(id: Int) {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 3, initialLoadSize = 20)
            ) {
                BaseArticlePagingSource(BaseArticlePagingSource.WX_ARTICLE, id)
            }.flow.cachedIn(this).collectLatest {
                _wxArticleLiveData.value = it
            }
        }
    }

    /**
     * 获取项目分类列表
     */
    fun getProjectTree() {
        launch({
            val response = repository.getProjectTree()
            if (response.errorCode.isSuccess()) {
                _projectTreeList.postValue(response.data)
            } else {
                errorLiveData.postValue(response.errorMsg)
            }
        }, {
            errorLiveData.postValue(it)
        }, {
            loadingLiveData.postValue(false)
        })
    }

    /**
     * 获取项目下的文章列表
     */
    fun getProjectArticleList(id: Int) {
        viewModelScope.launch {
            Pager(
                //pageSize一页加载多少条  prefetchDistance表示距离底部多少条数据开始预加载，设置0则表示滑到底部才加载
                PagingConfig(pageSize = 20, prefetchDistance = 3, initialLoadSize = 20)
            ) {
                BaseArticlePagingSource(BaseArticlePagingSource.PROJECT_ARTICLE, id)
            }.flow.cachedIn(this).collectLatest {
                _projectArticleLiveData.value = it
            }
        }
    }
}