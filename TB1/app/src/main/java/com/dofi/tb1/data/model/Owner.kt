package com.dofi.tb1.data.model


import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("firstName") val firstName: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("picture") val picture: String? = null,
    @SerializedName("title") val title: String? = null
)

fun Owner.getFullNames(): String {
    return "${firstName?.split("_||_")?.get(0)}"
}