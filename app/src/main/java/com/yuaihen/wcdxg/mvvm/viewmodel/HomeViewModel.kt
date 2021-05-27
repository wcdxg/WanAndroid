package com.yuaihen.wcdxg.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yuaihen.wcdxg.mvvm.BaseViewModel
import com.yuaihen.wcdxg.mvvm.repository.HomeRepository
import com.yuaihen.wcdxg.net.model.BannerModel
import com.yuaihen.wcdxg.utils.isSuccess

/**
 * Created by Yuaihen.
 * on 2021/5/26
 */
class HomeViewModel : BaseViewModel() {

    private val repository = HomeRepository()
    private val _bannerLiveData = MutableLiveData<List<BannerModel.Data>>()
    val bannerLiveData = _bannerLiveData

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
                loadingLiveData.postValue(false)

            }
        )
    }

    /**
     * 获取首页文章列表 页码从0开始
     */
    fun getArticle() {

    }
}