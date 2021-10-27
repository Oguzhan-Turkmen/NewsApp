package com.oguzhanturkmen.newsapp.API

import com.oguzhanturkmen.newsapp.models.NewsResponse
import com.oguzhanturkmen.newsapp.util.Constans.Companion.Apı_Key
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "tr",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Apı_Key
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Apı_Key
    ):Response<NewsResponse>

}