package com.practicum.appplaylistmaker.api

import com.practicum.appplaylistmaker.data.api.SearchTrackResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<SearchTrackResponse>
}