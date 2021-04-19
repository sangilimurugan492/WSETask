package com.task.wsetask.data.model

import com.google.gson.annotations.SerializedName

data class Article(

    @SerializedName("page")
    val page :Int,
    @SerializedName("total_page")
    val totalPage :Int,
    @SerializedName("per_page")
    val perPage :Int,
    @SerializedName("total")
    val total :Int,
    @SerializedName("content")
    val contents :ArrayList<Content>
)
