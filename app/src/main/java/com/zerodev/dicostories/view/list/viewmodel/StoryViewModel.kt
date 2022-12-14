package com.zerodev.dicostories.view.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.repository.StoryRepository

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getStories(token: String): LiveData<PagingData<Story>> =
        storyRepository.getStories("Bearer $token")
}