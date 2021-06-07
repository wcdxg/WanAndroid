package com.yuaihen.wcdxg.net

import com.blankj.utilcode.util.NetworkUtils
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.yuaihen.wcdxg.BuildConfig
import com.yuaihen.wcdxg.base.BaseApplication
import com.yuaihen.wcdxg.base.NetConstants
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * Created by Yuaihen.
 * on 2020/6/10
 */
class ApiManage {

    companion object {

        const val TAG = "data"

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
                //保存Cookie
                cookieJar(getCookieJar())
//                addInterceptor(CacheInterceptor(BaseApplication.getContext()))
                retryOnConnectionFailure(true)
                //添加缓存拦截器
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
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                }
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

    }
}