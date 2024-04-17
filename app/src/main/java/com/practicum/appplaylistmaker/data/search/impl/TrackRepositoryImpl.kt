package com.practicum.appplaylistmaker.data.search.impl

import com.google.gson.Gson
import com.practicum.appplaylistmaker.api.ITunesApi
import com.practicum.appplaylistmaker.data.api.SearchTrackResponse
import com.practicum.appplaylistmaker.domain.models.Track
import com.practicum.appplaylistmaker.data.search.TrackRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TrackRepositoryImpl(val iTunesService: ITunesApi) : TrackRepository {

    override fun getCurrentTrack(trackJson: String?): Track? {
        return Gson().fromJson(trackJson, Track::class.java)
    }

    override fun search(trackName: String,
                        onSuccess: (tracks: List<Track>) -> Unit,
                        onFailure: () -> Unit) {
        iTunesService.search(trackName)
            .enqueue(object : Callback<SearchTrackResponse> {
                override fun onResponse(
                    call: Call<SearchTrackResponse>, response: Response<SearchTrackResponse>
                ) {
                    println("Got response " + response.code())
                    if (trackName.isEmpty()) {
                        return
                    }
                    when (response.code()) {
                        200 -> onSuccess(response.body()!!.results)
                        else -> onFailure()
                    }

                }

                override fun onFailure(call: Call<SearchTrackResponse>, t: Throwable) {
                    if (trackName.isEmpty()) {
                        return
                    }
                    onFailure()
                }
            })
    }

}