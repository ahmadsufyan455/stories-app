package com.zerodev.dicostories.repository

import androidx.lifecycle.LiveData
import com.zerodev.dicostories.database.StoryDatabase
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.service.StoryClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StoryRepository(private val database: StoryDatabase) {
    suspend fun refreshStory(token: String) {
        withContext(Dispatchers.IO) {
            val response = StoryClient.storyService.getStories("Bearer $token")
            response.body()?.let { database.storyDao.insertAll(it.listStory) }
        }
    }

    suspend fun getStoriesWithLocation(token: String): List<Story>? {
        val response = StoryClient.storyService.getStories("Bearer $token", 1)
        return response.body()?.listStory
    }

    fun getStories(): LiveData<List<Story>> = database.storyDao.getStories()

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(db: StoryDatabase): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(db)
            }.also { instance = it }
    }
}