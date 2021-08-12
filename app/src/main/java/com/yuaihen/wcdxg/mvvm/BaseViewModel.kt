package com.yuaihen.wcdxg.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuaihen.wcdxg.net.ApiManage
import com.yuaihen.wcdxg.net.BaseResponse
import com.yuaihen.wcdxg.net.exception.ErrorUtil
import com.yuaihen.wcdxg.net.exception.MyException
import com.yuaihen.wcdxg.net.model.BaseModel
import com.yuaihen.wcdxg.utils.LogUtil
import com.yuaihen.wcdxg.utils.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Yuaihen.
 * on 2020/12/16
 */
abstract class BaseViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<MyException>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val unLoginStateLiveData = MutableLiveData(false)

    fun newRequest(
        request: suspend () -> Unit,
        isShowLoading: Boolean = true
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            loadingLiveData.value = isShowLoading
            try {
                withContext(Dispatchers.IO) {
                    request()
                }
            } catch (e: Exception) {
                LogUtil.d(ApiManage.TAG, "newRequest fail:  ${e.localizedMessage}")
                errorLiveData.postValue(ErrorUtil.getErrorMsg(e))
            } finally {
                loadingLiveData.value = false
            }

        }
    }

    /**
     * 收藏或取消收藏站内文章
     */
    fun collectOrCancelArticle(id: Int, isCollect: Boolean) {
        try {
            newRequest({
                val response: BaseResponse<String> = if (isCollect) {
                    ApiManage.getInstance().collectArticle(id)
                } else {
                    ApiManage.getInstance().unCollectByOriginId(id)
                }

                when (response.errorCode) {
                    ErrorUtil.CODE_RESPONSE_SUCCESS -> {
                        if (isCollect) {
                            ToastUtil.show("收藏成功")
                        } else {
                            ToastUtil.show("取消收藏成功")
                        }
                        //                    errorLiveData.postValue("收藏成功")
                    }
                    ErrorUtil.CODE_LOGIN_FAIL -> {
                        errorLiveData.postValue(
                            MyException(
                                ErrorUtil.CODE_LOGIN_FAIL,
                                ErrorUtil.CODE_LOGIN_FAIL_MSG
                            )
                        )
                        unLoginStateLiveData.postValue(true)
                    }
                    else -> {
                        errorLiveData.postValue(MyException(response.errorCode, response.errorMsg))
                    }
                }
            }, false)
        } catch (e: Exception) {
            errorLiveData.postValue(ErrorUtil.getErrorMsg(e))
        }
    }

}