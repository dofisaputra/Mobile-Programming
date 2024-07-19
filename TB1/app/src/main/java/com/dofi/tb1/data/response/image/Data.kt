package com.dofi.tb1.data.response.image


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("delete_url") val deleteUrl: String?,
    @SerializedName("display_url") val displayUrl: String?,
    @SerializedName("expiration") val expiration: Int?,
    @SerializedName("height") val height: Int?,
    @SerializedName("id") val id: String?,
    @SerializedName("image") val image: Image?,
    @SerializedName("medium") val medium: Medium?,
    @SerializedName("size") val size: Int?,
    @SerializedName("thumb") val thumb: Thumb?,
    @SerializedName("time") val time: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("url_viewer") val urlViewer: String?,
    @SerializedName("width") val width: Int?
)