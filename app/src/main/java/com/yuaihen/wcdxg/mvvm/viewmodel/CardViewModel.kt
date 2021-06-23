package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.CardRepository
import com.yuaihen.wcdxg.net.model.KnowLedgeTreeModel
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/6/23
 */
class CardViewModel : BaseViewModel() {

    private val repository = CardRepository()
    private val _knowledgeLiveData = MutableLiveData<List<KnowLedgeTreeModel.Data>>()
    val knowledgeLiveData = _knowledgeLiveData

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

    }

    fun getOfficialAccounts() {

    }

    fun getProject() {

    }

}