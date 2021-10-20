package com.social.data.clients.api

import com.social.data.models.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiClient {

    @GET(ApiParameters.Endpoints.GET_ADVERTISEMENTS)
    fun getAdvertisements(): Flow<List<AdvertiseModel>>

    @GET(ApiParameters.Endpoints.GET_PROFILE)
    fun getProfile(@Path(ApiParameters.Variables.USER_ID) userId: Long) : Flow<ProfileModel?>

    @GET(ApiParameters.Endpoints.GET_CHAT_MESSAGES)
    fun getChatMessages(@Path(ApiParameters.Variables.USER_ID) userId: Long, @Query(ApiParameters.Variables.PAGE) page: Int) : Flow<List<ChatMessageModel>>

    @GET(ApiParameters.Endpoints.GET_FEED)
    fun getFeed(@Path(ApiParameters.Variables.USER_ID) userId: Long, @Query(ApiParameters.Variables.PAGE) page: Int) : Flow<List<FeedModel>>

    @GET(ApiParameters.Endpoints.GET_FILTERS)
    fun getPhotoFilters() : Flow<List<String>>

    @GET(ApiParameters.Endpoints.GET_MENU)
    fun getMenu(@Path(ApiParameters.Variables.USER_ID) userId: Long, @Query(ApiParameters.Variables.MENU_CATEGORY) menuCategory: Int, @Query(
        ApiParameters.Variables.PAGE) page: Int, @Query(ApiParameters.Variables.SEARCH) query: String): Flow<List<MenuModel>>

    @GET(ApiParameters.Endpoints.GET_LAST_PLAYLIST)
    fun getLastPlaylist(@Path(ApiParameters.Variables.USER_ID) userId: Long) : Flow<List<PlaylistModel>>

    @GET(ApiParameters.Endpoints.GET_SONGS)
    fun getPlaylist(@Path(ApiParameters.Variables.USER_ID) userId: Long, @Query(ApiParameters.Variables.PAGE) page: Int, @Query(
        ApiParameters.Variables.SEARCH) query: String): Flow<List<PlaylistModel>>

}