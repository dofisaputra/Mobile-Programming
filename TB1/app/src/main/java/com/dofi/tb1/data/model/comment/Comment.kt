package com.dofi.tb1.data.model.comment


import com.dofi.tb1.data.model.Owner
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Comment(
    @SerializedName("id") val id: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("owner") val owner: Owner? = null,
    @SerializedName("post") val post: String? = null,
    @SerializedName("publishDate") val publishDate: String? = null
)

fun Comment.getPostDate(): String? {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale("id", "ID"))

    return publishDate?.let { pubDate ->
        inputDateFormat.parse(pubDate)?.let { outputDateFormat.format(it) }
    }
}