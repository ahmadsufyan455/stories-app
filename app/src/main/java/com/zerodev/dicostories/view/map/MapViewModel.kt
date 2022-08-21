package com.zerodev.dicostories.view.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.repository.StoryRepository
import kotlinx.coroutines.launch

class MapViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    private val _storiesWithLocation = MutableLiveData<List<Story>>()
    val storiesWithLocation get() = _storiesWithLocation
    fun setStoriesWithLocation(token: String) {
        viewModelScope.launch {
            _storiesWithLocation.postValue(storyRepository.getStoriesWithLocation(token))
        }
    }
}