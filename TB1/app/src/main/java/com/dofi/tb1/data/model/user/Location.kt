package com.dofi.tb1.data.model.user


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("city") val city: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("street") val street: String? = null,
    @SerializedName("timezone") val timezone: String? = null
)