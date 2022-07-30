package com.zerodev.dicostories.utils

import android.content.Context
import com.zerodev.dicostories.model.LoginResult
import com.zerodev.dicostories.utils.Constant.NAME
import com.zerodev.dicostories.utils.Constant.PREFS_NAME
import com.zerodev.dicostories.utils.Constant.TOKEN
import com.zerodev.dicostories.utils.Constant.UID

internal class UserPreferences(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: LoginResult) {
        prefs.edit().apply {
            putString(UID, value.userId)
            putString(NAME, value.name)
            putString(TOKEN, value.token)
            apply()
        }
    }

    fun getUser(): LoginResult {
        val user = LoginResult().apply {
            userId = prefs.getString(UID, "")
            name = prefs.getString(NAME, "")
            token = prefs.getString(TOKEN, "")
        }

        return user
    }

    fun clearData() {
        prefs.edit().apply {
            clear()
            apply()
        }
    }
}