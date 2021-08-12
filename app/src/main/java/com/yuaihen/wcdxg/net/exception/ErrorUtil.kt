package com.yuaihen.wcdxg.net.exception

import retrofit2.HttpException
import java.lang.NullPointerException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by Yuaihen.
 * on 2021/08/06
 * 网络请求错误工具类
 */
object ErrorUtil {

    const val CODE_ERROR_500 = "服务器开小差了，请检查网络连接后重试~"
    const val CODE_ERROR_404 = "访问的链接地址不存在，请重试~"
    const val CODE_ERROR_OTHER = "网络连接出错，请重试~"
    const val CODE_ERROR_CONNECT = "无法连接到服务器，请重试~"
    const val CODE_ERROR_SOCKET_TIMEOUT = "连接超时，请重试~"
    const val CODE_ERROR_NULL_POINT_EXCEPTION = "NULL_POINT_EXCEPTION"
    const val CODE_LOGIN_FAIL_MSG = "账号登录失败，请重新登录"
    const val CODE_LOGIN_FAIL = -1001    //登录失效，需要重新登录
    const val CODE_RESPONSE_ERROR = -1   //
    const val CODE_RESPONSE_SUCCESS = 0  //errorCode如果为负数则认为错误

    /**
     * 根据错误码返回对应提示异常信息
     */
    fun getErrorMsg(e: Exception): MyException {
        e.printStackTrace()
        return when (e) {
            is HttpException -> {
                when (e.code()) {
                    500 -> MyException(e.code(), CODE_ERROR_500)
                    404 -> MyException(e.code(), CODE_ERROR_404)
                    else -> MyException(e.code(), CODE_ERROR_OTHER)
                }
            }
            is ConnectException -> MyException(CODE_RESPONSE_ERROR, CODE_ERROR_CONNECT)
            is SocketTimeoutException -> MyException(CODE_RESPONSE_ERROR, CODE_ERROR_SOCKET_TIMEOUT)
            is MyException -> MyException(e.errorCode, e.errorMsg)
            is NullPointerException -> MyException(CODE_RESPONSE_ERROR, CODE_ERROR_NULL_POINT_EXCEPTION)
            else -> MyException((e as MyException).errorCode, e.errorMsg)
        }
    }
}