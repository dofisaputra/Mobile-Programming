package com.dofi.tb1.data.model


import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Post(
    @SerializedName("id") val id: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("likes") val likes: Int?,
    @SerializedName("owner") val owner: Owner?,
    @SerializedName("publishDate") val publishDate: String?,
    @SerializedName("tags") val tags: List<String?>?,
    @SerializedName("text") val text: String?
)

fun Post.getPostDate(): String? {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale("id", "ID"))

    return publishDate?.let { pubDate ->
        inputDateFormat.parse(pubDate)?.let { outputDateFormat.format(it) }
    }
}