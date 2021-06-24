package com.yuaihen.wcdxg.net

import com.yuaihen.wcdxg.base.Constants
import com.yuaihen.wcdxg.base.NetConstants
import com.yuaihen.wcdxg.net.model.*
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by Yuaihen.
 * on 2021/6/7
 */
interface ApiService {

    companion object {
        //定义后台返回的异常code
        const val SUCCESS = 0 // 成功
        const val UN_LOGIN = -1001 //未登录的错误码

        //const val FAILURE = -1 // 失败
        const val ERROR_500 = "服务器开小差了，请检查网络连接后重试~"
        const val ERROR_404 = "链接地址不存在，请重试~"
        const val ERROR_OTHER = "网络连接出错，请重试~"
    }

    /**
     * 下载文件
     */
    @Streaming
    suspend fun downloadFile(@Url url: String): ResponseBody

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
    suspend fun logout(): BaseModel

    /**
     * 获取轮播图
     */
    @GET(NetConstants.GET_BANNER)
    suspend fun getBanner(): BannerModel

    /**
     * 获取首页文章列表
     * 页码，拼接在连接中，从0开始
     */
    @GET("article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int): HomeArticleModel

    /**
     * 获取置顶文章
     */
    @GET(NetConstants.GET_TOP_ARTICLE)
    suspend fun getTopArticleList(): TopArticleModel

    /**
     * 获取收藏文章列表
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectArticleList(@Path(Constants.PAGE) page: Int): HomeArticleModel

    /**
     * 获取收藏网站列表
     */
    @GET(NetConstants.GET_COLLECT_WEBSITE)
    suspend fun getCollectWebSiteList(): TopArticleModel

    /**
     * 收藏网站
     */
    @GET(NetConstants.COLLECT_WEBSITE)
    suspend fun collectWebSite(
        @Field(Constants.NAME) name: String,
        @Field(Constants.LINK) link: String
    ): HomeArticleModel

    /**
     * 编辑收藏网站
     */
    @GET(NetConstants.UPDATE_COLLECT_WEBSITE)
    suspend fun updateCollectWebSite(
        @Field(Constants.ID) id: Int,
        @Field(Constants.NAME) name: String,
        @Field(Constants.LINK) link: String
    ): BaseModel

    /**
     * 删除收藏网站
     */
    @GET(NetConstants.DELETE_COLLECT_WEBSITE)
    suspend fun deleteCollectWebSite(
        @Field(Constants.ID) id: Int,
    ): BaseModel

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path(Constants.ID) id: Int): BaseModel

    /**
     * 收藏站外文章
     */
    @FormUrlEncoded
    @POST(NetConstants.COLLECT_OTHER_WEBSITE_ARTICLE)
    suspend fun collectArticle(
        @Field(Constants.TITLE) title: String,
        @Field(Constants.AUTHOR) author: String,
        @Field(Constants.LINK) link: String,
    ): BaseModel

    /**
     * 取消收藏-从文章列表
     */
    @POST("lg/uncollect_originId/{uncollect_originId}/json")
    suspend fun unCollectByOriginId(@Path(Constants.UNCOLLECT_ORIGINID) uncollect_originId: Int): BaseModel

    /**
     * 取消收藏-从我的收藏列表取消
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    suspend fun unCollectById(
        @Path(Constants.ID) id: Int,
        @Field(Constants.ORIGIN_ID) originId: Int = -1
    ): BaseModel

    /**
     * 获取个人积分，需要登录后访问
     */
    @GET(NetConstants.GET_USER_INFO)
    suspend fun getUserInfo(): UserInfoModel

    /**
     * 获取用户积分记录
     */
    @GET("lg/coin/list/{page}/json")
    suspend fun getCoinRecord(@Path(Constants.PAGE) page: Int): CoinRecordModel

    /**
     * 获取积分排行榜记录
     */
    @GET("coin/rank/{page}/json")
    suspend fun getCoinRank(@Path(Constants.PAGE) page: Int): CoinRecordModel

    /**
     * 问答
     */
    @GET("wenda/list/{page}/json")
    suspend fun getWendaList(@Path(Constants.PAGE) page: Int): HomeArticleModel

    /**
     * 知识体系
     */
    @GET("tree/json")
    suspend fun getKnowledgeTree(): KnowLedgeTreeModel

    /**
     * 获取知识体系下的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getKnowledgeArticleByCid(
        @Path(Constants.PAGE) page: Int,
        @Query(Constants.CID) cid: Int
    ): HomeArticleModel

    /**
     * 获取导航分类
     */
    @GET("navi/json")
    suspend fun getNavigationData(): NavigationModel

}