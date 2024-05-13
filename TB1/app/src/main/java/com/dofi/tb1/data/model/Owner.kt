package com.dofi.tb1.data.model


import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("picture") val picture: String?,
    @SerializedName("title") val title: String?
)

fun Owner.getFullNames(): String {
    return "$firstName $lastName"
}