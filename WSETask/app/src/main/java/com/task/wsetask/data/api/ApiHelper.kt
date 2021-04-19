package com.task.wsetask.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getArticles(author : String, page : Int) = apiService.getArticles(author, page)
}