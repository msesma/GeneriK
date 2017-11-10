package com.paradigmadigital.api.model

import com.google.gson.annotations.SerializedName

class Login {
    @SerializedName("token")
    var token: String = ""

    @SerializedName("uid")
    var uid: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("phone")
    var phone: String = ""

    @SerializedName("email")
    var email: String = ""
}