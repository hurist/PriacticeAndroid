package com.hurist.testapplication.network.service

import com.hurist.wanandroid.data.responseData.*
import retrofit2.Response
import retrofit2.http.*

/**
 * author: spike
 * version：1.0
 * create data：2020/7/10
 * Description：WanAndroid
 */
interface WanAndroid {
    /**
     * 获取首页文章列表
     * @param page 页数
     */
    @GET("article/list/{page}/json")
    suspend fun getIndex(@Path("page") page: Int): Response<ArticleList>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("test") test: String
    ): Response<UserInfo>

    /**
     * 获取置顶文章列表
     */
    @GET("article/top/json")
    suspend fun fetchTopArticle(): Response<ArrayList<Article>>

    @GET("banner/json")
    suspend fun fetchBanner(): Response<ArrayList<Banner>>
}