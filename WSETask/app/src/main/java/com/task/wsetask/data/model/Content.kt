package com.task.wsetask.data.model

import com.google.gson.annotations.SerializedName

class Content(

@SerializedName("title")
var title :String,
@SerializedName("link")
val link :String,
@SerializedName("points")
val points :String,
@SerializedName("num_comments")
val numComments :String,
@SerializedName("author")
val author :String,
@SerializedName("created_date")
val createdDate :String,
@SerializedName("created_time")
val createdTime :String,
@SerializedName("day_of_the_week")
val dayOfTheWeek :String,
)

