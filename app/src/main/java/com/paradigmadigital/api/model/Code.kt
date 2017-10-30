package com.paradigmadigital.api.model

import com.google.gson.annotations.SerializedName


class Code {
    @SerializedName("email")
    var email: String = ""

    @SerializedName("code")
    var code: String = ""
}