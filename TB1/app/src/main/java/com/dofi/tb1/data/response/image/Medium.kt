package com.dofi.tb1.data.response.image


import com.google.gson.annotations.SerializedName

data class Medium(
    @SerializedName("extension") val extension: String?,
    @SerializedName("filename") val filename: String?,
    @SerializedName("mime") val mime: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)