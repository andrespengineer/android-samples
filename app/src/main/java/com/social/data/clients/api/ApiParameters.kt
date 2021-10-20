package com.social.data.clients.api

class ApiParameters {
    object Variables {
        const val USER_ID = "USER_ID"
        const val PAGE = "PAGE"
        const val SEARCH = "SEARCH"
        const val MENU_CATEGORY = "MENU_CATEGORY"
    }

    object Endpoints {
        const val GET_ADVERTISEMENTS = "ads"
        const val GET_PROFILE = "profile/{${Variables.USER_ID}}"
        const val GET_CHAT_MESSAGES = "chat/userId={${Variables.USER_ID}}?page=${Variables.PAGE}"
        const val GET_FEED = "feed/userId={${Variables.USER_ID}}?page=${Variables.PAGE}"
        const val GET_FILTERS = "photo-filters"
        const val GET_LAST_PLAYLIST = "playlist/last/userId={${Variables.USER_ID}}"
        const val GET_MENU = "menu/userId={${Variables.USER_ID}}?category={${Variables.MENU_CATEGORY}}&page={${Variables.PAGE}}&search={${Variables.SEARCH}}"
        const val GET_SONGS = "playlist/userId={${Variables.USER_ID}}?page={${Variables.PAGE}}&search={${Variables.SEARCH}}"
    }

    object Base {
        const val BASE_URL = "https://d875c957-b888-4e0f-afbb-2152bd1fd247.mock.pstmn.io"
        const val BASE_TIMEOUT: Long = 20
    }
}