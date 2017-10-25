package com.paradigmadigital.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Login {
    @SerializedName("token")
    var token: String = ""

    @SerializedName("uid")
    var uid: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("registerDate")
    var registerDate: Date = Date(0)

    @SerializedName("phone")
    var phone: String = ""

    @SerializedName("email")
    var email: String = ""
}