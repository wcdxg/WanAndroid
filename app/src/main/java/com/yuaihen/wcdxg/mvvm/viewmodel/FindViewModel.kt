package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.FindRepository
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.model.KnowLedgeTreeModel
import com.yuaihen.wcdxg.net.model.NavigationModel
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/6/23
 */
class FindViewModel : BaseViewModel() {

    private val repository = FindRepository()
    private val _knowledgeLiveData = MutableLiveData<List<KnowLedgeTreeModel.Data>>()
    val knowledgeLiveData = _knowledgeLiveData
    private val _navigationLiveData = MutableLiveData<List<NavigationModel.Data>>()
    val navigationLiveData = _navigationLiveData

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

    fun getOfficialAccounts() {

    }

    fun getProject() {

    }

}