package com.dofi.tb1.data.model.comment

import com.google.gson.annotations.SerializedName

data class CommentCreate (
    @SerializedName("message") val message: String? = null,
    @SerializedName("owner") val owner: String? = null,
    @SerializedName("post") val post: String? = null,
)