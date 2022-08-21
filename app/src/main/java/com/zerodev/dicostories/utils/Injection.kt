package com.zerodev.dicostories.utils

import android.content.Context
import com.zerodev.dicostories.database.getDatabase
import com.zerodev.dicostories.repository.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val db = getDatabase(context)
        return StoryRepository.getInstance(db)
    }
}