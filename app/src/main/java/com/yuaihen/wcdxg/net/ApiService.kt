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
    ): BaseResponse<LoginModel>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(NetConstants.LOGIN)
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
    ): BaseResponse<LoginModel>

    /**
     * 退出登录
     */
    @GET(NetConstants.LOGOUT)
    suspend fun logout(): BaseResponse<String>

    /**
     * 获取轮播图
     */
    @GET(NetConstants.GET_BANNER)
    suspend fun getBanner(): BaseResponse<List<BannerModel>>

    /**
     * 获取首页文章列表
     * 页码，拼接在连接中，从0开始
     */
    @GET("article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int): BaseResponse<HomeArticleModel>

    /**
     * 获取置顶文章
     */
    @GET(NetConstants.GET_TOP_ARTICLE)
    suspend fun getTopArticleList(): BaseResponse<List<ArticleModel>>

    /**
     * 获取收藏文章列表
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectArticleList(@Path(Constants.PAGE) page: Int): BaseResponse<HomeArticleModel>

    /**
     * 获取收藏网站列表
     */
    @GET(NetConstants.GET_COLLECT_WEBSITE)
    suspend fun getCollectWebSiteList(): BaseResponse<List<ArticleModel>>

    /**
     * 收藏网站
     */
    @GET(NetConstants.COLLECT_WEBSITE)
    suspend fun collectWebSite(
        @Field(Constants.NAME) name: String,
        @Field(Constants.LINK) link: String
    ): BaseResponse<HomeArticleModel>

    /**
     * 编辑收藏网站
     */
    @GET(NetConstants.UPDATE_COLLECT_WEBSITE)
    suspend fun updateCollectWebSite(
        @Field(Constants.ID) id: Int,
        @Field(Constants.NAME) name: String,
        @Field(Constants.LINK) link: String
    ): BaseResponse<String>

    /**
     * 删除收藏网站
     */
    @GET(NetConstants.DELETE_COLLECT_WEBSITE)
    suspend fun deleteCollectWebSite(
        @Field(Constants.ID) id: Int,
    ): BaseResponse<String>

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path(Constants.ID) id: Int): BaseResponse<String>

    /**
     * 收藏站外文章
     */
    @FormUrlEncoded
    @POST(NetConstants.COLLECT_OTHER_WEBSITE_ARTICLE)
    suspend fun collectArticle(
        @Field(Constants.TITLE) title: String,
        @Field(Constants.AUTHOR) author: String,
        @Field(Constants.LINK) link: String,
    ): BaseResponse<String>

    /**
     * 取消收藏-从文章列表
     */
    @POST("lg/uncollect_originId/{uncollect_originId}/json")
    suspend fun unCollectByOriginId(@Path(Constants.UNCOLLECT_ORIGINID) uncollect_originId: Int): BaseResponse<String>

    /**
     * 取消收藏-从我的收藏列表取消
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    suspend fun unCollectById(
        @Path(Constants.ID) id: Int,
        @Field(Constants.ORIGIN_ID) originId: Int = -1
    ): BaseResponse<String>

    /**
     * 获取个人积分，需要登录后访问
     */
    @GET(NetConstants.GET_USER_INFO)
    suspend fun getUserInfo(): BaseResponse<UserInfoModel>

    /**
     * 获取用户积分记录
     */
    @GET("lg/coin/list/{page}/json")
    suspend fun getCoinRecord(@Path(Constants.PAGE) page: Int): BaseResponse<CoinRecordModel>

    /**
     * 获取积分排行榜记录
     */
    @GET("coin/rank/{page}/json")
    suspend fun getCoinRank(@Path(Constants.PAGE) page: Int): BaseResponse<CoinRecordModel>

    /**
     * 问答
     */
    @GET("wenda/list/{page}/json")
    suspend fun getWendaList(@Path(Constants.PAGE) page: Int): BaseResponse<HomeArticleModel>

    /**
     * 知识体系
     */
    @GET("tree/json")
    suspend fun getKnowledgeTree(): BaseResponse<List<ArticleListModel>>

    /**
     * 获取知识体系下的文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getKnowledgeArticleByCid(
        @Path(Constants.PAGE) page: Int,
        @Query(Constants.CID) cid: Int
    ): BaseResponse<HomeArticleModel>

    /**
     * 获取导航分类
     */
    @GET("navi/json")
    suspend fun getNavigationData(): BaseResponse<List<ArticleListModel>>

    /**
     * 公众号列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun getOfficialAccounts(): BaseResponse<List<ArticleListModel>>

    /**
     * 获取公众号内的所有文章
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWxArticle(
        @Path(Constants.PAGE) page: Int,
        @Path(Constants.ID) id: Int
    ): BaseResponse<HomeArticleModel>

    /**
     * 获取项目列表
     */
    @GET("project/tree/json")
    suspend fun getProjectTree(): BaseResponse<List<ArticleListModel>>

    /**
     * 项目分类下的文章列表
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectArticle(
        @Path(Constants.PAGE) page: Int,
        @Query(Constants.ID) id: Int
    ): BaseResponse<HomeArticleModel>

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    suspend fun getSearchHotKey(): BaseResponse<List<ArticleListModel>>

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun getSearchResult(
        @Path(Constants.PAGE) page: Int,
        @Field("k") key: String
    ): BaseResponse<HomeArticleModel>
}