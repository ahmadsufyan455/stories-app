package com.zerodev.dicostories.database

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.*
import com.zerodev.dicostories.model.RemoteKeys
import com.zerodev.dicostories.model.Story

@Dao
interface StoryDao {
    @Query("select * from story order by createdAt desc")
    fun getStories(): PagingSource<Int, Story>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stories: List<Story>)

    @Query("delete from story")
    suspend fun deleteAll()
}

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("select * from remote_keys where id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeys?

    @Query("delete from remote_keys")
    suspend fun deleteRemoteKeys()
}

@Database(entities = [Story::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract val storyDao: StoryDao
    abstract val remoteKeysDao: RemoteKeysDao
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