package com.zerodev.dicostories.utils

import android.content.Context
import com.zerodev.dicostories.database.getDatabase
import com.zerodev.dicostories.repository.StoryRepository
import com.zerodev.dicostories.service.StoryClient

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val db = getDatabase(context)
        val storyService = StoryClient.storyService
        return StoryRepository.getInstance(db, storyService)
    }
}