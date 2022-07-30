package com.zerodev.dicostories.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: ArrayList<Story>
)

@Parcelize
data class Story(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double? = null,
    val lon: Double? = null,
) : Parcelable
