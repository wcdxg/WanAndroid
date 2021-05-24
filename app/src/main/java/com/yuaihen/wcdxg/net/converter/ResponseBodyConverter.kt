package com.yuaihen.wcdxg.net.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.io.StringReader

/**
 * Created by Yuaihen.
 * on 2019/7/8
 * 解析服务器返回的异常
 * 通过json中的code
 */
class ResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {

    companion object {
        private const val TAG = "Api"

        //定义后台返回的异常code
        const val SUCCESS = 0 // 成功
        const val UN_LOGIN = -1001 //未登录的错误码
        const val FAILURE = -1 // 失败

        const val ERROR_500 = "服务器开小差了，请检查网络连接后重试~"
        const val ERROR_404 = "链接地址不存在，请重试~"
        const val ERROR_OTHER = "网络连接出错，请重试~"
    }


    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val json = value.string()
        value.use {
            return it.use {
                adapter.read(gson.newJsonReader(StringReader(json)))
            }
        }

    }
}