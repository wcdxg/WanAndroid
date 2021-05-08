package com.yuaihen.wcdxg.net

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.yuaihen.wcdxg.BuildConfig
import com.yuaihen.wcdxg.base.BaseApplication
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.net.converter.MyGsonConverterFactory
import com.yuaihen.wcdxg.utils.AppUtil
import com.yuaihen.wcdxg.utils.UserUtil
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.platform.Platform
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory


/**
 * Created by Yuaihen.
 * on 2020/6/10
 */
interface ApiService {


    /**
     * 获取Token
     */
//    @FormUrlEncoded
//    @POST("gmUserRelate/getToken")
//    suspend fun getToken(@Field("languageType") languageType: String): BaseResponse<TokenBean>

    /**
     * 获取广告轮播图
     */
//    @POST("common/getAdInfoList")
//    suspend fun getBanner(): BaseResponse<BannerPicBean>

    /**
     * 下载文件
     */
    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String): ResponseBody


    /*--------------------------------------Retrofit-----------------------------------------------------*/
    companion object {
        val TAG = "Api"

        var BASE_URL = "http://10.1.30.7:9098/"                     //测试环境
        private val SERVER_URL = "http://gmapi.xxlimageim.com/"          //线上环境
        //  var BASE_URL = "http://10.1.21.251:9015/"               //黄军霞
        //  var BASE_URL = "http://glassapi.prev.xxlimageim.com/"   //预发布

        @Volatile
        private var instance: ApiService? = null

        fun getInstance(): ApiService = instance ?: synchronized(ApiService::class.java) {
            instance ?: Retrofit.Builder()
                .baseUrl(if (AppUtil.isRelease()) SERVER_URL else BASE_URL)
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MyGsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
                .apply { instance = this }

        }


        private fun getClient(): OkHttpClient {
            val builder = RetrofitUrlManager.getInstance().with(OkHttpClient.Builder())
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(MyIntercepter())
            if (BuildConfig.DEBUG) {
                val log = LoggingInterceptor.Builder()
                    .setLevel(Level.BODY)
                    .log(Platform.INFO)
                    .tag(TAG)
//                        .request("Request")
//                        .response("Response")
                    .build()

                builder.addInterceptor(log)
            }
            return builder.build()
        }
    }


    /**
     * 自定义Log打印和添加自定义参数
     * post请求采用form表单方式
     */
    class MyIntercepter : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            lateinit var newRequestBuild: Request.Builder
            lateinit var newRequest: Request
            val request = chain.request()
            val method: String = request.method
            var postBodyString = ""
            if (method.equals("POST", ignoreCase = true)) {
                val body = request.body
                when (body) {
                    is FormBody? -> {
                        val formBody = body as FormBody?
                        val builder = FormBody.Builder()
                        builder.add(Constants.MAC, AppUtil.getMacAddress())
                            .add(
                                Constants.VERSION,
                                AppUtil.getVersionCode(BaseApplication.getContext()).toString()
                            )

                        formBody?.let {
                            for (index in 0 until formBody.size) {
                                builder.add(
                                    formBody.encodedName(index),
                                    formBody.encodedValue(index)
                                )
                            }
                        }

                        postBodyString += (if (postBodyString.isNotEmpty()) "&" else "") + bodyToString(
                            builder.build()
                        )
                        newRequestBuild = request.newBuilder()
                        newRequestBuild.post(postBodyString.toRequestBody("application/x-www-form-urlencoded;charset=UTF-8".toMediaTypeOrNull()))
                    }

                    is MultipartBody? -> {
                        val oldPartList = body?.parts
                        oldPartList?.let {
                            val builder = MultipartBody.Builder()
                            builder.setType(MultipartBody.FORM)
                            val requestBody1 = AppUtil.getMacAddress()
                                .toRequestBody("text/plain".toMediaTypeOrNull())
                            val requestBody2 =
                                AppUtil.getVersionCode(BaseApplication.getContext()).toString()
                                    .toRequestBody("text/plain".toMediaTypeOrNull())
//                            val requestBody3 =
//                                UserUtil.getToken().toRequestBody("text/plain".toMediaTypeOrNull())
                            for (part in it) {
                                builder.addPart(part)
                                postBodyString += bodyToString(part.body) + "\n"
                            }
                            postBodyString += bodyToString(requestBody1) + "\n"
                            postBodyString += bodyToString(requestBody2) + "\n"
//                            postBodyString += bodyToString(requestBody3) + "\n"
                            //              builder.addPart(oldBody);  //不能用这个方法，因为不知道oldBody的类型，可能是PartMap过来的，也可能是多个Part过来的，所以需要重新逐个加载进去
                            builder.addPart(requestBody1)
                            builder.addPart(requestBody2)
//                            builder.addPart(requestBody3)
                            newRequestBuild = request.newBuilder()
                            newRequestBuild.post(builder.build())
                        }
                    }
                    //More Type
                    else -> {
                        val formBodyBuilder = FormBody.Builder()
//                        formBodyBuilder.addEncoded(Constants.TOKEN, UserUtil.getToken())
                        formBodyBuilder.addEncoded(Constants.MAC, AppUtil.getMacAddress())
                        formBodyBuilder.addEncoded(
                            Constants.VERSION,
                            AppUtil.getVersionCode(BaseApplication.getContext()).toString() + ""
                        )
                        newRequestBuild = request.newBuilder()

                        val formBody: RequestBody = formBodyBuilder.build()
                        postBodyString = bodyToString(request.body)
                        postBodyString += (if (postBodyString.isNotEmpty()) "&" else "") + bodyToString(
                            formBody
                        )
                        newRequestBuild.post(postBodyString.toRequestBody("application/x-www-form-urlencoded;charset=UTF-8".toMediaTypeOrNull()))

                    }
                }
                newRequest = newRequestBuild
                    .addHeader("Accept", "application/json")
                    .addHeader("Accept-Language", "zh")
                    .build()
            } else {
                //GET
                newRequest = request
            }

            return chain.proceed(newRequest)
        }

        private fun bodyToString(request: RequestBody?): String {
            return try {
                val buffer = Buffer()
                if (request != null) {
                    request.writeTo(buffer)
                } else {
                    return ""
                }
                buffer.readUtf8()
            } catch (e: IOException) {
                "did not work"
            }
        }
    }
}