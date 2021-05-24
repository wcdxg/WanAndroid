package com.yuaihen.wcdxg.utils

import com.yuaihen.wcdxg.net.ApiService
import com.yuaihen.wcdxg.net.BaseResponse


/**
 * Created by Yuaihen.
 * on 2020/12/18
 * 根据errorCode返回对应提示语句
 */

fun <T> BaseResponse<T>.isSuccess(): Boolean {
    return this.errorCode == ApiService.SUCCESS
}

fun Int.getErrorMsg(): String {
    return when (this) {
        -1 -> "请求出错,请重试~ 错误码: $this"
        999 -> "mac地址不存在，该设备尚未入库~ 错误码: $this"
        10086 -> "token参数不存在~ 请重试 错误码: $this"
        500 -> "服务器访问异常,请稍后重试~  错误码: $this"
        else -> "访问异常,请稍后重试 错误码: $this"
    }
}
