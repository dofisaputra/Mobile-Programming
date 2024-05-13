package com.dofi.tb1.data.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("dateOfBirth") val dateOfBirth: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("location") val location: Location?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("picture") val picture: String?,
    @SerializedName("registerDate") val registerDate: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("updatedDate") val updatedDate: String?
)

fun User.getFullNames(): String {
    return "$firstName $lastName"
}