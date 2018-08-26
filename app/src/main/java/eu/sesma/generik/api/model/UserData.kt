package eu.sesma.generik.api.model

import com.google.gson.annotations.SerializedName


data class UserData(
        @SerializedName("id") var id: Int? = null,

        @SerializedName("name") var name: String? = null,

        @SerializedName("email") var email: String? = null
)
