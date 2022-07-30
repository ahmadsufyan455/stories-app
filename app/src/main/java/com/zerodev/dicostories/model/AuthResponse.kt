package com.zerodev.dicostories.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("loginResult")
    val loginResult: LoginResult
)

data class LoginResult(
    @SerializedName("userId")
    var userId: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("token")
    var token: String? = null
)

data class RegisterResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)