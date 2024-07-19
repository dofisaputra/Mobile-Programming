package com.dofi.tb1.data.model.post


import com.dofi.tb1.data.model.Owner
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Post(
    @SerializedName("id") val id: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("likes") val likes: Int? = null,
    @SerializedName("owner") val owner: Owner? = null,
    @SerializedName("publishDate") val publishDate: String? = null,
    @SerializedName("tags") val tags: List<String?>? = null,
    @SerializedName("text") val text: String? = null
)

fun Post.getPostDate(): String? {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale("id", "ID"))

    return publishDate?.let { pubDate ->
        inputDateFormat.parse(pubDate)?.let { outputDateFormat.format(it) }
    }
}