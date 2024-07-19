package com.dofi.tb1.data.response.image


import com.google.gson.annotations.SerializedName

data class BaseResponseImageUpload(
    @SerializedName("data") val data: Data?,
    @SerializedName("status") val status: Int?,
    @SerializedName("success") val success: Boolean?
)