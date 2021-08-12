package com.yuaihen.wcdxg.net.converter

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.yuaihen.wcdxg.net.exception.MyException
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
        val response = value.string()
        value.use {
            val parse = JsonParser().parse(response)
            //判断是否有error字段且error字段不为0，并且拥有reason字段
            if (parse.isJsonObject &&
                parse.asJsonObject.getAsJsonPrimitive("errorCode").asInt != 0 &&
                parse.asJsonObject.has("errorMsg")
            ) {
                //异常处理
                val error = parse.asJsonObject
                val myException = MyException(
                    error.getAsJsonPrimitive("errorCode").asInt,
                    error.getAsJsonPrimitive("errorMsg").asString
                )

                /**
                 * 抛出异常 统一交由
                 * BaseViewModel#getErrorMsg(Throwable)处理
                 */
                throw myException
            }

            return it.use {
                adapter.read(gson.newJsonReader(StringReader(response)))
            }
        }

    }
}