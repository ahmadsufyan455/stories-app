package com.zerodev.dicostories.view.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.repository.StoryRepository
import kotlinx.coroutines.launch

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun refreshDataFromRepository(token: String) {
        viewModelScope.launch {
            storyRepository.refreshStory(token)
        }
    }

    fun getStories(): LiveData<List<Story>> = storyRepository.getStories()
}