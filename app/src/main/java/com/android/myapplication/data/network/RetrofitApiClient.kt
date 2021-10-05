package com.android.myapplication.data.network

import com.android.myapplication.data.models.Post
import com.android.myapplication.data.models.Story
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApiClient {
    @get:GET(Api.Endpoints.GET_POSTS)
    val posts: Call<List<Post?>?>

    @get:GET(Api.Endpoints.GET_STORIES)
    val story: Call<List<Story?>?>

    @GET(Api.Endpoints.GET_STORY)
    fun getStory(@Path(Api.Parameters.USER_ID) userId: String?): Call<Story?>?
}