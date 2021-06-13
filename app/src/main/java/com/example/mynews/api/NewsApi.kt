package com.example.mynews.api

import com.example.mynews.api.ResponseNews
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines?country=us&apiKey=8f3a469090cf4bb08394cd110a0ee05c")
    suspend fun getArticlesByCategory(
        @Query("category") category: String
    ): Response<ResponseNews>

    @GET("top-headlines?country=us&apiKey=8f3a469090cf4bb08394cd110a0ee05c")
    suspend fun getAllArticles(
    ): Response<ResponseNews>
}
