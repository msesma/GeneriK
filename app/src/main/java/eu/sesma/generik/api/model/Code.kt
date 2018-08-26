package eu.sesma.generik.api.model

import com.google.gson.annotations.SerializedName


data class Code(
        @SerializedName("email") var email: String = "",

        @SerializedName("code") var code: String = ""
)