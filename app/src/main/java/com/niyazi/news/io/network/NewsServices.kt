package com.niyazi.news.io.network

import com.niyazi.news.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsServices {

    @GET("everything")
    @JvmSuppressWildcards
    suspend fun getAllArticles(
        @QueryMap params: Map<String, Any>
    ): Response<ArticleResponse>


}