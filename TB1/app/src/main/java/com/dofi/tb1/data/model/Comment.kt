package com.dofi.tb1.data.model


import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Comment(
    @SerializedName("id") val id: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("owner") val owner: Owner?,
    @SerializedName("post") val post: String?,
    @SerializedName("publishDate") val publishDate: String?
)

fun Comment.getPostDate(): String? {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale("id", "ID"))

    return publishDate?.let { pubDate ->
        inputDateFormat.parse(pubDate)?.let { outputDateFormat.format(it) }
    }
}