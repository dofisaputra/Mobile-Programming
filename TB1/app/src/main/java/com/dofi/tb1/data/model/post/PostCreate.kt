package com.dofi.tb1.data.model.post

import com.google.gson.annotations.SerializedName

data class PostCreate (
    @SerializedName("image") val image: String? = null,
    @SerializedName("likes") val likes: Int? = null,
    @SerializedName("owner") val owner: String? = null,
    @SerializedName("tags") val tags: List<String?>? = null,
    @SerializedName("text") val text: String? = null
)