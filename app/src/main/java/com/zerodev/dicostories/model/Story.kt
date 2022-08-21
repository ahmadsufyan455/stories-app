package com.zerodev.dicostories.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: List<Story>
)

@Entity(tableName = "story")
@Parcelize
data class Story(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double? = null,
    val lon: Double? = null,
) : Parcelable
