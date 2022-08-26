package com.zerodev.dicostories

import com.zerodev.dicostories.model.*

object DataDummy {
    fun generateDummyRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            error = true,
            message = "User created"
        )
    }

    fun generateDummyLoginResponse(): LoginResponse {
        return LoginResponse(
            error = false,
            message = "success",
            loginResult = LoginResult(
                userId = "user-yj5pc_LARC_AgK61",
                name = "Ahmad Sufyan",
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXlqNXBjX0xBUkNfQWdLNjEiLCJpYXQiOjE2NDE3OTk5NDl9.flEMaQ7zsdYkxuyGbiXjEDXO8kuDTcI__3UjCwt6R_I"
            )
        )
    }

    fun generateDummyStories(): List<Story> {
        val items = arrayListOf<Story>()
        for (i in 0..10) {
            val story = Story(
                id = "story-HbyMcHrnK_mu3_Vz",
                name = "Ahmad Sufyan",
                description = "lorem ipsum",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1661415837176_GfT8ZgC_.jpg",
                createdAt = "2022-08-25T08:23:57.179Z",
                lat = null,
                lon = null
            )
            items.add(story)
        }
        return items
    }

    fun generateDummyStoriesWithLocation(): List<Story> {
        val items = arrayListOf<Story>()
        for (i in 0..10) {
            val story = Story(
                id = "story-HbyMcHrnK_mu3_Vz",
                name = "Ahmad Sufyan",
                description = "lorem ipsum",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1661415837176_GfT8ZgC_.jpg",
                createdAt = "2022-08-25T08:23:57.179Z",
                lat = -16.0,
                lon = 177.0
            )
            items.add(story)
        }
        return items
    }

    fun generateDummyFileUploadResponse(): FileUploadResponse {
        return FileUploadResponse(
            error = false,
            message = "success"
        )
    }
}