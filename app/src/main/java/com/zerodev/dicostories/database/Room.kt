package com.zerodev.dicostories.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.zerodev.dicostories.model.Story

@Dao
interface StoryDao {
    @Query("select * from story order by createdAt desc")
    fun getStories(): LiveData<List<Story>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stories: List<Story>)
}

@Database(entities = [Story::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract val storyDao: StoryDao
}

private lateinit var INSTANCE: StoryDatabase

fun getDatabase(context: Context): StoryDatabase {
    synchronized(StoryDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                StoryDatabase::class.java,
                "stories"
            ).build()
        }
    }
    return INSTANCE
}