package com.dofi.tb1.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data") val data: List<T>?,
    @SerializedName("total") val total: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("limit") val limit: Int?
)
