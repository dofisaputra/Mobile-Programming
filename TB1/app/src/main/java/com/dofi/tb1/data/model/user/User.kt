package com.dofi.tb1.data.model.user


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("dateOfBirth") val dateOfBirth: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("firstName") val firstName: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("location") val location: Location? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("picture") val picture: String? = null,
    @SerializedName("registerDate") val registerDate: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("updatedDate") val updatedDate: String? = null
)

fun User.getFullNames(): String {
    return "${firstName?.split("_||_")?.get(0)}"
}