package com.zerodev.dicostories.view.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.zerodev.dicostories.model.LoginModel
import com.zerodev.dicostories.model.LoginResponse
import com.zerodev.dicostories.model.RegisterModel
import com.zerodev.dicostories.model.RegisterResponse
import com.zerodev.dicostories.service.StoryClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    private val _isResponseSuccess = MutableLiveData<Boolean>()
    private val _responseMessage = MutableLiveData<String>()
    fun login(loginData: LoginModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = StoryClient.storyService.login(loginData)
                if (response.isSuccessful) {
                    _isResponseSuccess.postValue(true)
                    _loginResponse.postValue(response.body())
                } else {
                    _isResponseSuccess.postValue(false)
                    val responseBody = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        LoginResponse::class.java
                    )
                    _responseMessage.postValue(responseBody.message)
                }
            } catch (e: Exception) {
                _isResponseSuccess.postValue(false)
            }
        }
    }

    fun register(registerData: RegisterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = StoryClient.storyService.register(registerData)
                if (response.isSuccessful) {
                    _isResponseSuccess.postValue(true)
                    _registerResponse.postValue(response.body())
                } else {
                    _isResponseSuccess.postValue(false)
                    val responseBody = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        RegisterResponse::class.java
                    )
                    _responseMessage.postValue(responseBody.message)
                }
            } catch (e: Exception) {
                _isResponseSuccess.postValue(false)
            }
        }
    }

    fun isResponseSuccess(): LiveData<Boolean> = _isResponseSuccess
    fun getLoginResponse(): LiveData<LoginResponse> = _loginResponse
    fun getRegisterResponse(): LiveData<RegisterResponse> = _registerResponse
    fun getResponseMessage(): LiveData<String> = _responseMessage
}