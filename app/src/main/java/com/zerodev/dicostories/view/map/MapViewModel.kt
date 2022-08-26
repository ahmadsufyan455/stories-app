package com.zerodev.dicostories.view.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.service.StoryClient
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val _storiesWithLocation = MutableLiveData<List<Story>>()

    fun setStoriesWithLocation(token: String) {
        viewModelScope.launch {
            try {
                val response = StoryClient.storyService.getStories("Bearer $token", location = 1)
                if (response.isSuccessful) {
                    _storiesWithLocation.postValue(response.body()?.listStory)
                }
            } catch (e: Exception) {
                Log.e("exception", e.toString())
            }
        }
    }

    fun getStoriesWithLocation(): LiveData<List<Story>> = _storiesWithLocation
}