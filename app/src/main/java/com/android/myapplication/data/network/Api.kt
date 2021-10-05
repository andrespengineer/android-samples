package com.android.myapplication.data.network

class Api {
    object Parameters {
        const val USER_ID = "userId"
    }

    object Endpoints {
        const val GET_POSTS = "getPosts/"
        const val GET_STORY = "/getStory/{" + Parameters.USER_ID + "}"
        const val GET_STORIES = "getStories/"
    }

    object Base {
        const val BASE_URL = "https://efded537-85a0-4f66-a8c0-0751529f3b52.mock.pstmn.io"
        const val TIMEOUT: Long = 20
    }
}