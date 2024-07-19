package com.dofi.tb1.extension

import android.content.Context
import android.content.SharedPreferences

fun Context.getPreferences(): SharedPreferences {
    return this.getSharedPreferences(
        "CorePref",
        Context.MODE_PRIVATE
    )
}

fun Context.putStringPref(key: String, value: String) {
    getPreferences().edit().putString(key, value).apply()
}

fun Context.getStringPref(key: String): String =
    getPreferences().getString(key, "").orEmpty()

fun String.changeImageUrl(): String {
    return this.replace("https://i.ibb.co", "https://i.ibb.co.com")
}