package com.zerodev.dicostories.view.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerodev.dicostories.model.FileUploadResponse
import com.zerodev.dicostories.service.StoryClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel : ViewModel() {
    private val _uploadResponse = MutableLiveData<FileUploadResponse>()
    fun addStory(
        token: String,
        image: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): LiveData<FileUploadResponse> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    StoryClient.storyService.addStory("Bearer $token", image, description, lat, lon)
                if (response.isSuccessful) {
                    _uploadResponse.postValue(response.body())
                }
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
            }
        }
        return _uploadResponse
    }
}