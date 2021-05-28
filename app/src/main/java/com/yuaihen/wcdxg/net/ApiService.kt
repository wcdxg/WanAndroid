package com.yuaihen.wcdxg.net

import com.blankj.utilcode.util.NetworkUtils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.yuaihen.wcdxg.BuildConfig
import com.yuaihen.wcdxg.base.BaseApplication
import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.base.NetConstants
import com.yuaihen.wcdxg.net.model.BannerModel
import com.yuaihen.wcdxg.net.model.LoginModel
import com.yuaihen.wcdxg.utils.LogUtil
import com.yuaihen.wcdxg.utils.SPUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by Yuaihen.
 * on 2020/6/10
 */
interface ApiService {

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(NetConstants.REGISTER)
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): LoginModel

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(NetConstants.LOGIN)
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
    ): LoginModel

    /**
     * 退出登录
     */
    @GET(NetConstants.LOGOUT)
    suspend fun logout(): LoginModel

    /**
     * 获取轮播图
     */
    @GET(NetConstants.GET_BANNER)
    suspend fun getBanner(): BannerModel

    /**
     * 下载文件
     */
    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String): ResponseBody


    /*--------------------------------------Retrofit-----------------------------------------------------*/
    companion object {
        val TAG = "data"

        //定义后台返回的异常code
        const val SUCCESS = 0 // 成功
        const val UN_LOGIN = -1001 //未登录的错误码
        const val FAILURE = -1 // 失败
        const val ERROR_500 = "服务器开小差了，请检查网络连接后重试~"
        const val ERROR_404 = "链接地址不存在，请重试~"
        const val ERROR_OTHER = "网络连接出错，请重试~"

        @Volatile
        private var instance: ApiService? = null
        private var cookieJar: PersistentCookieJar? = null

        fun getInstance(): ApiService = instance ?: synchronized(ApiService::class.java) {
            instance ?: Retrofit.Builder()
                .baseUrl(NetConstants.APP_BASE_URL)
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
                .apply { instance = this }

        }

        fun getCookieJar(): PersistentCookieJar {
            return cookieJar ?: PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(BaseApplication.getContext())
            )
        }

        private fun getClient(): OkHttpClient {
            val httpCacheDir = File(BaseApplication.getContext().externalCacheDir, "response")
            //缓存10M
            val cacheSize = 10 * 1024 * 1024.toLong()
            val cache = Cache(httpCacheDir, cacheSize)
            val builder = OkHttpClient.Builder()
            builder.apply {
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
//                cookieJar(getCookieJar())
//                addInterceptor(CacheInterceptor(BaseApplication.getContext()))
                retryOnConnectionFailure(true)
                cache(cache)
                addInterceptor {
                    var request = it.request()
                    if (!NetworkUtils.isAvailable()) {
                        //网络不可用 离线缓存保存4周 单位秒
                        val maxStale = 60 * 60 * 24 * 7
                        val tempCacheControl = CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(maxStale, TimeUnit.SECONDS)
                            .build()
                        request = request.newBuilder()
                            .cacheControl(tempCacheControl)
                            .build()
                    }

                    it.proceed(request)
                }
                addNetworkInterceptor {
                    //只有网络拦截器环节才会写入缓存写入缓存,在有网络的时候 设置缓存时间
                    val request = it.request()
                    val originalResponse = it.proceed(request)
                    val maxAge = 1 * 60 //在线缓存在1分钟内可读取 单位秒
                    originalResponse.newBuilder()
                        .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build()

                }

//                addInterceptor() {
//                    //get response cookie
//                    val request = it.request()
//                    val response = it.proceed(request)
//                    val requestUrl = request.url.toString()
//                    val domain = request.url.host
//                    //保存登录时返回的cookie
//                    if ((requestUrl.contains(NetConstants.LOGIN) || requestUrl.contains(NetConstants.REGISTER))
//                        && response.headers("Set-Cookie").isNotEmpty()
//                    ) {
//                        val cookies = response.headers("Set-Cookie")
//                        val cookie = encodeCookie(cookies)
//                        saveCookie(requestUrl, domain, cookie)
//                    }
//                    response
//
//                }
//                addInterceptor {
//                    //set request cookie
//                    val request = it.request()
//                    val builder = request.newBuilder()
//                    val domain = request.url.host
//                    //get domain cookie
//                    if (domain.isNotEmpty()) {
//                        val cookie =
//                            SPUtils.getCookiePreferences().decodeString(domain, "") ?: ""
//                        if (cookie.isNotEmpty()) {
//                            builder.addHeader("Cookie", cookie)
//                        }
//                    }
//                    it.proceed(request)
//                }
            }

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

        /**
         * Cookie去重
         */
        private fun encodeCookie(cookies: List<String>): String {
            val sb = StringBuilder()
            val set = hashSetOf<String>()
            cookies.forEach {
                LogUtil.d("hello", "encodeCookie: $it")
                val value = it.split(";")
                value.forEach { v ->
                    set.add(v)
                }
            }

            set.forEachIndexed { index, s ->
                sb.append(s)
                if (index != set.size - 1) {
                    sb.append(";")
                }
            }
            return sb.toString()
        }

        /**
         * 保存Cookie到SP
         */
        private fun saveCookie(requestUrl: String?, domain: String?, cookie: String) {
            requestUrl ?: return
            SPUtils.getCookiePreferences().encode(requestUrl, cookie)
            domain ?: return
            SPUtils.getCookiePreferences().encode(domain, cookie)
            SPUtils.getCookiePreferences().encode(Constants.Cookie, cookie)
        }
    }
}