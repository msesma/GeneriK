package com.paradigmadigital.api.model

import com.google.gson.annotations.SerializedName


data class CommentData(
        @SerializedName("postId") val postId: Int? = null
)