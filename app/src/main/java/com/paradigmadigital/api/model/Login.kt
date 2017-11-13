package com.paradigmadigital.api.model

import com.google.gson.annotations.SerializedName

data class Login(
        @SerializedName("token") val token: String = "",

        @SerializedName("uid") val uid: String = "",

        @SerializedName("name") val name: String = "",

        @SerializedName("phone") val phone: String = "",

        @SerializedName("email") val email: String = ""
)