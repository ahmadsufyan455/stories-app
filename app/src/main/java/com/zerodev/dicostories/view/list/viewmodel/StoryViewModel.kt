package com.zerodev.dicostories.view.list.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.service.StoryClient
import com.zerodev.dicostories.utils.ThemePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryViewModel(private val pref: ThemePreferences) : ViewModel() {
    private val _stories = MutableLiveData<ArrayList<Story>>()

    fun setStories(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = StoryClient.storyService.getStories("Bearer $token")
                if (response.isSuccessful) {
                    _stories.postValue(response.body()?.listStory)
                }
            } catch (e: Exception) {
                Log.e("StoryVM", e.toString())
            }
        }
    }

    fun getStories(): LiveData<ArrayList<Story>> = _stories

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}