package com.andtv.flicknplay.workbrowse.data.remote.api

import retrofit2.Call
import retrofit2.http.GET

interface AccountSettingsApi {

    @GET("value-lists/timezones,countries,localizations")
    fun getLanguagesCountiesTimeZones(): Call<Any>
}