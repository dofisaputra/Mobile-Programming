package com.dofi.tb1.data.model

import com.google.gson.annotations.SerializedName

data class UserLogin(
    @SerializedName("id") val id: String? = null,
    @SerializedName("fullName") val fullName: String? = null,
    @SerializedName("emailOrPhone") val emailOrPhone: String? = null,
    @SerializedName("password") val password: String? = null
)