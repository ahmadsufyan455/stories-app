package com.zerodev.dicostories.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.zerodev.dicostories.database.StoryDatabase
import com.zerodev.dicostories.model.Story
import com.zerodev.dicostories.service.StoryService

class StoryRepository(private val database: StoryDatabase, private val storyService: StoryService) {

    fun getStories(token: String): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = StoryRemoteMediator(token, database, storyService),
            pagingSourceFactory = {
                database.storyDao.getStories()
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(db: StoryDatabase, storyService: StoryService): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(db, storyService)
            }.also { instance = it }
    }
}