package com.yuaihen.wcdxg.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuaihen.wcdxg.net.ApiService
import com.yuaihen.wcdxg.utils.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException

/**
 * Created by Yuaihen.
 * on 2020/12/16
 */
abstract class BaseViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<String>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun launch(
        block: suspend () -> Unit,
        error: suspend (String) -> Unit,
        complete: suspend () -> Unit,
        isShowLoading: Boolean = true
    ) {
        loadingLiveData.postValue(isShowLoading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                LogUtil.d(ApiService.TAG, "request fail ${e.localizedMessage}")
                error(getErrorMsg(e))
            } finally {
                complete()
            }

        }
    }

    private fun getErrorMsg(e: Exception): String {
        return when (e) {
            is retrofit2.HttpException -> {
                when (e.code()) {
                    500 -> "Code=${e.code()} ${ApiService.ERROR_500}"
                    404 -> "Code=${e.code()} ${ApiService.ERROR_404}"
                    else -> "Code=${e.code()} ${ApiService.ERROR_OTHER}"
                }
            }
            is ConnectException -> {
                ApiService.ERROR_OTHER
            }
            else -> {
                ApiService.ERROR_OTHER
            }
        }
    }
}