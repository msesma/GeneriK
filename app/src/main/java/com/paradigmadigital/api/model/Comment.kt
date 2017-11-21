package com.paradigmadigital.api.model

import com.google.gson.annotations.SerializedName


data class Comment(
        @SerializedName("id") val id: Int? = null,

        @SerializedName("postId") val postId: Int? = null,

        @SerializedName("name") val name: String? = null,

        @SerializedName("email") val email: String? = null,

        @SerializedName("body") val body: String? = null
)