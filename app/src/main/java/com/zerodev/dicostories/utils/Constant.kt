package com.zerodev.dicostories.utils

import android.Manifest

object Constant {
    const val BASE_URL = "https://story-api.dicoding.dev/v1/"

    // preferences
    const val PREFS_NAME = "user_pref"
    const val NAME = "name"
    const val UID = "uid"
    const val TOKEN = "token"

    const val CAMERA_X_RESULT = 200
    val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.CAMERA,
    )
    const val REQUEST_CODE_PERMISSIONS = 10
}