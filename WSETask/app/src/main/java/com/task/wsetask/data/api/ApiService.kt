package com.task.wsetask.data.api

import com.task.wsetask.data.model.Article
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("news")
    suspend fun getArticles(@Query("author") author : String, @Query("page") page : Int): Article

}