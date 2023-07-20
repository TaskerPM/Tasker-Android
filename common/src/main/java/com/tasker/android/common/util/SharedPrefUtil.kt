package com.tasker.android.common.util

import android.content.SharedPreferences

fun getSharedPrefAccessToken(sharedPref: SharedPreferences): String {
    return sharedPref.getString("access_token", "") ?: ""
}

fun getSharedPrefRefreshToken(sharedPref: SharedPreferences): String {
    return sharedPref.getString("refresh_token", "") ?: ""
}

fun setSharedPrefAccessToken(sharedPref: SharedPreferences, token: String) {
    sharedPref.edit().apply {
        putString("access_token", token)
        apply()
    }
}

fun setSharedPrefRefreshToken(sharedPref: SharedPreferences, token: String) {
    sharedPref.edit().apply {
        putString("refresh_token", token)
        apply()
    }
}