package com.yuaihen.wcdxg.utils
import com.yuaihen.wcdxg.net.BaseResponse
import com.yuaihen.wcdxg.net.converter.ResponseBodyConverter


/**
 * Created by Yuaihen.
 * on 2020/12/18
 * 根据errorCode返回对应提示语句
 */

fun <T> BaseResponse<T>.isSuccess(): Boolean {
    return this.code == ResponseBodyConverter.SUCCESS
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

//定义后台返回的异常code
const val SUCCESS = 0 // 成功
const val FAILURE = -1 // 失败
const val TOKEN_NOT_EXIST = 10086 //token不存在
const val SERVER_EXCEPTION = 502 //服务器异常
const val SERVER_EXCEPTION_2 = 500 //服务器异常
const val MAC_NOT_EXIST = 999 //mac地址不存在，未入库
const val POLLING_GET = 10  //需要轮询接口
const val GENDER_MAN = 11  //男性性别
const val FACE_FAIL_CODE = 9  //人脸特征识别失败，需要重新拍摄