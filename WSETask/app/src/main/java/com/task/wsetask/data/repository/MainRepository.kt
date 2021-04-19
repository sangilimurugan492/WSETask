package com.task.wsetask.data.repository

import com.task.wsetask.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getArticles(author : String, page : Int) = apiHelper.getArticles(author, page)
}